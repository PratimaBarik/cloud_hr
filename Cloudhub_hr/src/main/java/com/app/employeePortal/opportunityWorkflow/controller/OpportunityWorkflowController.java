package com.app.employeePortal.opportunityWorkflow.controller;

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
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityStagesMapper;
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityWorkflowMapper;
import com.app.employeePortal.opportunityWorkflow.service.OpportunityWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class OpportunityWorkflowController {

	@Autowired
	OpportunityWorkflowService opportunityWorkflowService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/workflow/opportunityWorkflow")
	public ResponseEntity<?> createOpportunityWorkflow(@RequestBody OpportunityWorkflowMapper opportunityWorkflowMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			opportunityWorkflowMapper.setUserId(loggedInUserId);
			opportunityWorkflowMapper.setOrgId(loggedInOrgId);

			OpportunityWorkflowMapper opportunityWorkflowDetailsId = opportunityWorkflowService
					.saveOpportunityWorkflow(opportunityWorkflowMapper);

			return new ResponseEntity<OpportunityWorkflowMapper>(opportunityWorkflowDetailsId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/workflow/opportunityWorkflow/{orgId}")
	public ResponseEntity<?> getWorkflowListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){

		List<OpportunityWorkflowMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = opportunityWorkflowService.getWorkflowListByOrgId(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunityWorkflow/{opportunityWorkflowDetailsId}")
	public ResponseEntity<?> updateOpportunityWorkflowDetails(
			@PathVariable("opportunityWorkflowDetailsId") String opportunityWorkflowDetailsId,
			@RequestBody OpportunityWorkflowMapper opportunityWorkflowMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityWorkflowMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityWorkflowMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityWorkflowMapper opportunityWorkflowMapperr = opportunityWorkflowService
					.updateOpportunityWorkflowDetails(opportunityWorkflowDetailsId, opportunityWorkflowMapper);

			return new ResponseEntity<OpportunityWorkflowMapper>(opportunityWorkflowMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/workflow/opportunityWorkflow/{opportunityWorkflowDetailsId}")

	public ResponseEntity<?> deleteOpportunityWorkflow(
			@PathVariable("opportunityWorkflowDetailsId") String opportunityWorkflowDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			opportunityWorkflowService.deleteOpportunityWorkflowById(opportunityWorkflowDetailsId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/workflow/opportunityStages")
	public ResponseEntity<?> createOpportunityStages(@RequestBody OpportunityStagesMapper opportunityStagesMapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			opportunityStagesMapper.setUserId(loggedInUserId);
			opportunityStagesMapper.setOrgId(loggedInOrgId);

			if (0 <= (opportunityStagesMapper.getProbability())) {
				boolean b = opportunityWorkflowService.stageExistsByWeightage(opportunityStagesMapper.getProbability(),
						opportunityStagesMapper.getOpportunityWorkflowDetailsId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			OpportunityStagesMapper opportunityStagesId = opportunityWorkflowService.saveOpportunityStages(opportunityStagesMapper);

			return new ResponseEntity<>(opportunityStagesId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/workflow/opportunityStages/{oppworkFlowId}")
	public List<OpportunityStagesMapper> getStagesByOppworkFlowId(@PathVariable("oppworkFlowId") String oppworkFlowId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<OpportunityStagesMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = opportunityWorkflowService.getStagesByOppworkFlowId(oppworkFlowId);

		}

		return mapperList;

	}

	@PutMapping("/api/v1/opportunityStages/{opportunityStagesId}")
	public ResponseEntity<?> updateOpportunityStages(@PathVariable("opportunityStagesId") String opportunityStagesId,
			@RequestBody OpportunityStagesMapper opportunityStagesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			// opportunityStagesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityStagesMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityStagesMapper opportunityStagesMapperr = opportunityWorkflowService
					.updateOpportunityStages(opportunityStagesId, opportunityStagesMapper);

			return new ResponseEntity<OpportunityStagesMapper>(opportunityStagesMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/workflow/opportunityStages/{opportunityStagesId}")

	public ResponseEntity<?> deleteOpportunityStages(@PathVariable("opportunityStagesId") String opportunityStagesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			opportunityWorkflowService.deleteOpportunityStagesById(opportunityStagesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/workflow/opportunity/stages/{orgId}")
	public ResponseEntity<?> getStagesByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityStagesMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = opportunityWorkflowService.getStagesByOrgId(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunityStages/update/publishInd")
	public ResponseEntity<?> updateOpportunityStagesPubliahInd(@RequestBody OpportunityStagesMapper opportunityStagesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			//String authToken = authorization.replace(TOKEN_PREFIX, "");
			//opportunityStagesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			//opportunityStagesMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityStagesMapper opportunityStagesMapperr = opportunityWorkflowService.updateOpportunityStagesPubliahInd( opportunityStagesMapper);

			return new ResponseEntity<OpportunityStagesMapper>(opportunityStagesMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/opportunityWorkflow/update/publishInd")
	public ResponseEntity<?> updateOpportunityWorkflowDetailsPublishInd(@RequestBody OpportunityWorkflowMapper opportunityWorkflowMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			//String authToken = authorization.replace(TOKEN_PREFIX, "");
			//opportunityWorkflowMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			//opportunityWorkflowMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityWorkflowMapper opportunityWorkflowMapperr = opportunityWorkflowService.updateOpportunityWorkflowDetailsPublishInd(opportunityWorkflowMapper);

			return new ResponseEntity<OpportunityWorkflowMapper>(opportunityWorkflowMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/workflow/opportunityWorkflow/for_dropdown/{orgId}")
	public ResponseEntity<?> getWorkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){

		List<OpportunityWorkflowMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = opportunityWorkflowService.getWorkflowListByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/workflow/opportunity/stages/for_dropdown/{orgId}")
	public ResponseEntity<?> getStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityStagesMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = opportunityWorkflowService.getStagesByOrgIdForDropdown(orgId);
			if(null!=mapperList) {
	   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
	   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
	   			}
	            return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
