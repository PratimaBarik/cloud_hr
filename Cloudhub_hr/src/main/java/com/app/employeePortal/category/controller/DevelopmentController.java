package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.app.employeePortal.category.mapper.DevelopmentMapper;
import com.app.employeePortal.category.service.DevelopmentService;
import com.fasterxml.jackson.core.JsonGenerationException;

@RestController
@CrossOrigin(maxAge = 3600)

public class DevelopmentController {
	@Autowired
	DevelopmentService developmentService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/development")
	public ResponseEntity<?> createDevelopment(@RequestBody DevelopmentMapper developmentMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			developmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			developmentMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			DevelopmentMapper mapper = developmentService.createDevelopment(developmentMapper);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/development/{developmentId}")
	public ResponseEntity<?> updateDevelopment(@PathVariable("developmentId") String developmentId,
			@RequestBody DevelopmentMapper developmentMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			developmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			developmentMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<DevelopmentMapper>(
					developmentService.updateDevelopment(developmentId, developmentMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/development/{orgId}")
	public ResponseEntity<?> getDevelopmentByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<DevelopmentMapper> developmentMapper = developmentService.getDevelopmentByOrgId(orgId);

			return new ResponseEntity<>(developmentMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/development/{developmentId}")
	public ResponseEntity<?> deleteDevelopment(@PathVariable("developmentId") String developmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			developmentService.deleteDevelopment(developmentId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/development/count/{orgId}")
	public ResponseEntity<?> getDevelopmentCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(developmentService.getDevelopmentCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/development/{taskTypeId}/{value}")
	public ResponseEntity<?> getDevelopmentByTaskTypeAndValue(@PathVariable("taskTypeId") String taskTypeId,
			@PathVariable("value") String value, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<DevelopmentMapper> developmentMapper = developmentService.getDevelopmentByTaskTypeIdAndValue(taskTypeId,value,
					jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != developmentMapper && !developmentMapper.isEmpty()) {
				return new ResponseEntity<>(developmentMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
