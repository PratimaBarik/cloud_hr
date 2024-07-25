package com.app.employeePortal.unboardingWorkflow.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

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
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingStagesRequestMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingStagesResponseMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingWfReqMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingWfRespMapper;
import com.app.employeePortal.unboardingWorkflow.service.SupplierUnboardingWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class SupplierUnboardingWorkflowController {

	@Autowired
	SupplierUnboardingWorkflowService supplierUnboardingWorkflowService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/supplier/unboardingWorkflow")
	public ResponseEntity<?> createSupplierUnboardingWorkflow(@RequestBody SupplierUnboardingWfReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			SupplierUnboardingWfRespMapper id = supplierUnboardingWorkflowService.saveSupplierUnboardingWorkflow(requestMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplier/unboardingWorkflow/{orgId}")
	public ResponseEntity<?> getSupplierUnboardingWorkflowListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<SupplierUnboardingWfRespMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = supplierUnboardingWorkflowService.getSupplierUnboardingWorkflowListByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/supplier/unboardingWorkflow/{supplierUnboardingWorkflowDetailsId}")
	public ResponseEntity<?> updateSupplierUnboardingWorkflowDetails(
			@PathVariable("supplierUnboardingWorkflowDetailsId") String supplierUnboardingWorkflowDetailsId,
			@RequestBody SupplierUnboardingWfReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			SupplierUnboardingWfRespMapper mapper = supplierUnboardingWorkflowService
					.updateSupplierUnboardingWorkflowDetails(supplierUnboardingWorkflowDetailsId, requestMapper);
			return new ResponseEntity<SupplierUnboardingWfRespMapper>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/supplier/unboardingWorkflow/{supplierUnboardingWorkflowDetailsId}")
	public ResponseEntity<?> deleteSupplierUnboardingWorkflow(
			@PathVariable("supplierUnboardingWorkflowDetailsId") String supplierUnboardingWorkflowDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			supplierUnboardingWorkflowService.deleteSupplierUnboardingWorkflow(supplierUnboardingWorkflowDetailsId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/supplier/unboardingWorkflow/supplierUnboardingStages")
	public ResponseEntity<?> createSupplierUnboardingStages(@RequestBody SupplierUnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (0 <= (requestMapper.getProbability())) {
				boolean b = supplierUnboardingWorkflowService.stageExistsByWeightage(requestMapper.getProbability(),
						requestMapper.getSupplierUnboardingWorkflowDetailsId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			SupplierUnboardingStagesResponseMapper id = supplierUnboardingWorkflowService.saveSupplierUnboardingStages(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplier/unboardingWorkflow/supplierUnboardingStages/details/{supplierUnboardingWorkflowId}")
	public List<SupplierUnboardingStagesResponseMapper> getStagesBySupplierUnboardingWorkflowId(
			@PathVariable("supplierUnboardingWorkflowId") String supplierUnboardingWorkflowId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {
		List<SupplierUnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = supplierUnboardingWorkflowService.getStagesBySupplierUnboardingWorkflowId(supplierUnboardingWorkflowId);
		}
		return mapperList;
	}
	
	@PutMapping("/api/v1/supplier/unboardingWorkflow/supplierUnboardingStages/{supplierUnboardingStagesId}")
	public ResponseEntity<?> updateSupplierUnboardingStages(@PathVariable("supplierUnboardingStagesId") String supplierUnboardingStagesId,
			@RequestBody SupplierUnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			SupplierUnboardingStagesResponseMapper responseMapper = supplierUnboardingWorkflowService
					.updateSupplierUnboardingStages(supplierUnboardingStagesId, requestMapper);
			return new ResponseEntity<SupplierUnboardingStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/supplier/unboardingWorkflow/supplierUnboardingStages/{supplierUnboardingStagesId}")
	public ResponseEntity<?> deleteSupplierUnboardingStages(@PathVariable("supplierUnboardingStagesId") String supplierUnboardingStagesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			supplierUnboardingWorkflowService.deleteSupplierUnboardingStages(supplierUnboardingStagesId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplier/unboardingWorkflow/supplierUnboardingStages/{orgId}")
	public ResponseEntity<?> getStagesByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<SupplierUnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = supplierUnboardingWorkflowService.getStagesByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/supplier/unboardingStages/update/publishInd")
	public ResponseEntity<?> updateSupplierUnboardingStagesPubliahInd(@RequestBody SupplierUnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			SupplierUnboardingStagesResponseMapper responseMapper = supplierUnboardingWorkflowService
					.updateSupplierUnboardingStagesPubliahInd(requestMapper);
			return new ResponseEntity<SupplierUnboardingStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/supplier/unboardingWorkflow/update/publishInd")
	public ResponseEntity<?> updateSupplierUnboardingWorkflowDetailsPublishInd(@RequestBody SupplierUnboardingWfReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			SupplierUnboardingWfRespMapper responseMapper = supplierUnboardingWorkflowService
					.updateSupplierUnboardingWorkflowDetailsPublishInd(requestMapper);
			return new ResponseEntity<SupplierUnboardingWfRespMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplier/unboardingWorkflow/for_dropdown/{orgId}")
	public ResponseEntity<?> getSupplierUnboardingWorkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<SupplierUnboardingWfRespMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = supplierUnboardingWorkflowService.getSupplierUnboardingWorkflowListByOrgIdForDropdown(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/supplier/unboardingStages/for_dropdown/{orgId}")
	public ResponseEntity<?> getStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<SupplierUnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = supplierUnboardingWorkflowService.getStagesByOrgIdForDropdown(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
