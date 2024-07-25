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
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesRequestMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesResponseMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfReqMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfRespMapper;
import com.app.employeePortal.unboardingWorkflow.service.UnboardingWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class UnboardingWorkflowController {

	@Autowired
	UnboardingWorkflowService unboardingWorkflowService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/unboardingWorkflow")
	public ResponseEntity<?> createUnboardingWorkflow(@RequestBody UnboardingWfReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			UnboardingWfRespMapper id = unboardingWorkflowService.saveUnboardingWorkflow(requestMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unboardingWorkflow/{orgId}")
	public ResponseEntity<?> getUnboardingWorkflowListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<UnboardingWfRespMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = unboardingWorkflowService.getUnboardingWorkflowListByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/unboardingWorkflow/{unboardingWorkflowDetailsId}")
	public ResponseEntity<?> updateUnboardingWorkflowDetails(
			@PathVariable("unboardingWorkflowDetailsId") String unboardingWorkflowDetailsId,
			@RequestBody UnboardingWfReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			UnboardingWfRespMapper mapper = unboardingWorkflowService
					.updateUnboardingWorkflowDetails(unboardingWorkflowDetailsId, requestMapper);
			return new ResponseEntity<UnboardingWfRespMapper>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/unboardingWorkflow/{unboardingWorkflowDetailsId}")
	public ResponseEntity<?> deleteUnboardingWorkflow(
			@PathVariable("unboardingWorkflowDetailsId") String unboardingWorkflowDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			unboardingWorkflowService.deleteUnboardingWorkflow(unboardingWorkflowDetailsId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/unboardingWorkflow/unboardingStages")
	public ResponseEntity<?> createOpportunityStages(@RequestBody UnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (0 <= (requestMapper.getProbability())) {
				boolean b = unboardingWorkflowService.stageExistsByWeightage(requestMapper.getProbability(),
						requestMapper.getUnboardingWorkflowDetailsId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			UnboardingStagesResponseMapper id = unboardingWorkflowService.saveOpportunityStages(requestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unboardingWorkflow/unboardingStages/details/{unboardingWorkflowId}")
	public List<UnboardingStagesResponseMapper> getStagesByUnboardingWorkflowId(
			@PathVariable("unboardingWorkflowId") String unboardingWorkflowId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {
		List<UnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = unboardingWorkflowService.getStagesByUnboardingWorkflowId(unboardingWorkflowId);
		}
		return mapperList;
	}

	@PutMapping("/api/v1/unboardingWorkflow/unboardingStages/{unboardingStagesId}")
	public ResponseEntity<?> updateUnboardingStages(@PathVariable("unboardingStagesId") String unboardingStagesId,
			@RequestBody UnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			// opportunityStagesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			UnboardingStagesResponseMapper responseMapper = unboardingWorkflowService
					.updateUnboardingStages(unboardingStagesId, requestMapper);
			return new ResponseEntity<UnboardingStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/unboardingWorkflow/unboardingStages/{unboardingStagesId}")
	public ResponseEntity<?> deleteUnboardingStages(@PathVariable("unboardingStagesId") String unboardingStagesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			unboardingWorkflowService.deleteUnboardingStages(unboardingStagesId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unboardingWorkflow/unboardingStages/{orgId}")
	public ResponseEntity<?> getStagesByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<UnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = unboardingWorkflowService.getStagesByOrgId(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/unboardingStages/update/publishInd")
	public ResponseEntity<?> updateUnboardingStagesPubliahInd(@RequestBody UnboardingStagesRequestMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			UnboardingStagesResponseMapper responseMapper = unboardingWorkflowService
					.updateUnboardingStagesPubliahInd(requestMapper);
			return new ResponseEntity<UnboardingStagesResponseMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/unboardingWorkflow/update/publishInd")
	public ResponseEntity<?> updateUnboardingWorkflowDetailsPublishInd(@RequestBody UnboardingWfReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			UnboardingWfRespMapper responseMapper = unboardingWorkflowService
					.updateUnboardingWorkflowDetailsPublishInd(requestMapper);
			return new ResponseEntity<UnboardingWfRespMapper>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unboardingWorkflow/for_dropdown/{orgId}")
	public ResponseEntity<?> getUnboardingWorkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<UnboardingWfRespMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = unboardingWorkflowService.getUnboardingWorkflowListByOrgIdForDropdown(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unboardingStages/for_dropdown/{orgId}")
	public ResponseEntity<?> getStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<UnboardingStagesResponseMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = unboardingWorkflowService.getStagesByOrgIdForDropdown(orgId);
			if (null != mapperList) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
