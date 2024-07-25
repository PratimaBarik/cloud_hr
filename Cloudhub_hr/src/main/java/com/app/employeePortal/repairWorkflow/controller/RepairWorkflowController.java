package com.app.employeePortal.repairWorkflow.controller;

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
import com.app.employeePortal.repairWorkflow.mapper.RepairStagesRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairStagesResponseMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowResponseMapper;
import com.app.employeePortal.repairWorkflow.service.RepairWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class RepairWorkflowController {

	@Autowired
	RepairWorkflowService repairWorkflowService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/repairWorkflow")
	public ResponseEntity<?> createRepairWorkflow(@RequestBody RepairWorkflowRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			RepairWorkflowResponseMapper id = repairWorkflowService.saveRepairWorkflow(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/repairWorkflow/{orgId}")
	public ResponseEntity<?> getRepairWorkflowListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<RepairWorkflowResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = repairWorkflowService.getRepairWorkflowListByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/repairWorkflow/workflow/{repairWorkflowDetailsId}")
	public ResponseEntity<?> updateRepairWorkflowDetails(
			@PathVariable("repairWorkflowDetailsId") String repairWorkflowDetailsId,
			@RequestBody RepairWorkflowRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			RepairWorkflowResponseMapper responseMapper = repairWorkflowService
					.updateRepairWorkflowDetails(repairWorkflowDetailsId, requestMapper);

			return new ResponseEntity<RepairWorkflowResponseMapper>(responseMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/repairWorkflow/{repairWorkflowDetailsId}")

	public ResponseEntity<?> deleteRepairWorkflowDetails(
			@PathVariable("repairWorkflowDetailsId") String repairWorkflowDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			repairWorkflowService.deleteRepairWorkflowDetails(repairWorkflowDetailsId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/repairWorkflow/stages")
	public ResponseEntity<?> createOpportunityStages(@RequestBody RepairStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (0 <= (requestMapper.getProbability())) {
				boolean b = repairWorkflowService.stageExistsByWeightage(requestMapper.getProbability(),
						requestMapper.getRepairWorkflowDetailsId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			RepairStagesResponseMapper id = repairWorkflowService.saveRepairStages(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/repairWorkflow/Stages/{repairWorkflowDetailsId}")
	public List<RepairStagesResponseMapper> getStagesByRepairWorkflowDetailsId(
			@PathVariable("repairWorkflowDetailsId") String repairWorkflowDetailsId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {
		List<RepairStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = repairWorkflowService.getStagesByRepairWorkflowDetailsId(repairWorkflowDetailsId);
		}
		return mapperList;
	}

	@PutMapping("/api/v1/repairWorkflow/stages/{repairStagesId}")
	public ResponseEntity<?> updateRepairStagesId(@PathVariable("repairStagesId") String repairStagesId,
			@RequestBody RepairStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			RepairStagesResponseMapper responseMapper = repairWorkflowService
					.updateRepairStagesId(repairStagesId, requestMapper);
			return new ResponseEntity<RepairStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/workflow/repairStages/{repairStagesId}")
	public ResponseEntity<?> deleteRepairStages(@PathVariable("repairStagesId") String repairStagesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			repairWorkflowService.deleteRepairStagesById(repairStagesId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}


	@PutMapping("/api/v1/repairStages/update/publishInd")
	public ResponseEntity<?> updateRepairStagesPubliahInd(@RequestBody RepairStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			RepairStagesResponseMapper responseMapper = repairWorkflowService.updateRepairStagesPubliahInd( requestMapper);
			return new ResponseEntity<RepairStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/repairWorkflow/update/publishInd")
	public ResponseEntity<?> updateRepairWorkflowDetailsPublishInd(@RequestBody RepairStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			RepairWorkflowResponseMapper responseMapper = repairWorkflowService.updateRepairWorkflowDetailsPublishInd(requestMapper);
			return new ResponseEntity<RepairWorkflowResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/workflow/repairWorkflow/for_dropdown/{orgId}")
	public ResponseEntity<?> getrepairWorkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		List<RepairWorkflowResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = repairWorkflowService.getRepairWorkflowListByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/workflow/repair/stages/for_dropdown/{orgId}")
	public ResponseEntity<?> getRepairStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<RepairStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = repairWorkflowService.getRepairStagesByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
