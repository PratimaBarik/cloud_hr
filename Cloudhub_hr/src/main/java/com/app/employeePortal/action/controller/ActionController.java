package com.app.employeePortal.action.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.action.mapper.ActionMapper;
import com.app.employeePortal.action.service.ActionService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.recruitment.service.RecruitmentService;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "action" })

public class ActionController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	ActionService actionService;
	@Autowired
	RecruitmentService recruitmentService;

	@PostMapping("/api/v1/action/save")
	public String createAction(@RequestBody ActionMapper actionMapper,
			@RequestHeader("Authorization") String authorization) {

		String actionId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			actionMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			actionMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			actionId = actionService.saveAction(actionMapper);

		}

		return actionId;
	}

	@GetMapping("/api/v1/action/{orgId}")
	public ResponseEntity<?> getActionByOrgId(@PathVariable("orgId") String orgId,
		@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

		List<ActionMapper> actionMapper = actionService.getActionByOrgId(orgId);
		
		return new ResponseEntity<>(actionMapper, HttpStatus.OK);
	}

	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


	}

	@GetMapping("/api/v1/action/record/today/{userId}")

	public ResponseEntity<?> getRecordByToday(@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(actionService.getRecordOfToday(userId));

		}
		return null;
	}


	
	@GetMapping("/api/v1/action/history/{userId}")
	public ResponseEntity<?> getActionHistoryByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(type.equalsIgnoreCase("recruiter")) {
			return ResponseEntity.ok(
					recruitmentService.getRecruiterActionHistoryByUserId(userId));
			}else {
				return ResponseEntity
						.ok(recruitmentService.getSalesActionHistoryByUserId(userId));
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
	}



}