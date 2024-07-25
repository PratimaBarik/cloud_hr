package com.app.employeePortal.productionWorkflow.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.productionWorkflow.mapper.ProductionStagesRequestMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionStagesResponseMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionWorkflowRequestMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionWorkflowResponseMapper;
import com.app.employeePortal.productionWorkflow.service.ProductionWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class ProductionWorkflowController {

	@Autowired
	ProductionWorkflowService productionWorkflowService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/productionWorkflow")
	public ResponseEntity<?> createProductionWorkflow(@RequestBody ProductionWorkflowRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ProductionWorkflowResponseMapper id = productionWorkflowService.saveProductionWorkflow(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/productionWorkflow/{orgId}")
	public ResponseEntity<?> getProductionWorkflowListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<ProductionWorkflowResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = productionWorkflowService.getProductionWorkflowListByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/productionWorkflow/workflow/{productionWorkflowDetailsId}")
	public ResponseEntity<?> updateOpportunityWorkflowDetails(
			@PathVariable("productionWorkflowDetailsId") String productionWorkflowDetailsId,
			@RequestBody ProductionWorkflowRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ProductionWorkflowResponseMapper responseMapper = productionWorkflowService
					.updateOpportunityWorkflowDetails(productionWorkflowDetailsId, requestMapper);

			return new ResponseEntity<ProductionWorkflowResponseMapper>(responseMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/productionWorkflow/{productionWorkflowDetailsId}")

	public ResponseEntity<?> deleteProductionWorkflowDetails(
			@PathVariable("productionWorkflowDetailsId") String productionWorkflowDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			productionWorkflowService.deleteProductionWorkflowDetails(productionWorkflowDetailsId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/productionWorkflow/stages")
	public ResponseEntity<?> createOpportunityStages(@RequestBody ProductionStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (0 <= (requestMapper.getProbability())) {
				boolean b = productionWorkflowService.stageExistsByWeightage(requestMapper.getProbability(),
						requestMapper.getProductionWorkflowDetailsId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			ProductionStagesResponseMapper id = productionWorkflowService.saveProductionStages(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/productionWorkflow/Stages/{productionWorkflowDetailsId}")
	public List<ProductionStagesResponseMapper> getStagesByProductionWorkflowDetailsId(
			@PathVariable("productionWorkflowDetailsId") String productionWorkflowDetailsId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {
		List<ProductionStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = productionWorkflowService.getStagesByProductionWorkflowDetailsId(productionWorkflowDetailsId);
		}
		return mapperList;
	}

	@PutMapping("/api/v1/productionWorkflow/stage/{productionStagesId}")
	public ResponseEntity<?> updateProductionStagesId(@PathVariable("productionStagesId") String productionStagesId,
			@RequestBody ProductionStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ProductionStagesResponseMapper responseMapper = productionWorkflowService
					.updateProductionStagesId(productionStagesId, requestMapper);
			return new ResponseEntity<ProductionStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/workflow/productionStages/{productionStagesId}")
	public ResponseEntity<?> deleteProductionStages(@PathVariable("productionStagesId") String productionStagesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			productionWorkflowService.deleteProductionStagesById(productionStagesId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}


	@PutMapping("/api/v1/productionStages/update/publishInd")
	public ResponseEntity<?> updateProductionStagesPubliahInd(@RequestBody ProductionStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			ProductionStagesResponseMapper responseMapper = productionWorkflowService.updateProductionStagesPubliahInd( requestMapper);
			return new ResponseEntity<ProductionStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/productionWorkflow/update/publishInd")
	public ResponseEntity<?> updateProductionWorkflowDetailsPublishInd(@RequestBody ProductionStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			ProductionWorkflowResponseMapper responseMapper = productionWorkflowService.updateProductionWorkflowDetailsPublishInd(requestMapper);
			return new ResponseEntity<ProductionWorkflowResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/workflow/productionWorkflow/for_dropdown/{orgId}")
	public ResponseEntity<?> getProductionWorkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		List<ProductionWorkflowResponseMapper> mapperList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = productionWorkflowService.getProductionWorkflowListByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/workflow/production/stages/for_dropdown/{orgId}")
	public ResponseEntity<?> getProductionStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<ProductionStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = productionWorkflowService.getProductionStagesByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
