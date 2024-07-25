package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.service.LeadsCategoryService;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
public class LeadsCategoryController {

	@Autowired
	LeadsCategoryService leadsCategoryService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping(value = "/api/v1/leadsCategory")
	public ResponseEntity<?> CreateLeadsCategory(@RequestBody LeadsCategoryMapper leadsCategoryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			leadsCategoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			LeadsCategoryMapper leadsCategoryId = leadsCategoryService.CreateLeadsCategory(leadsCategoryMapper);
			return new ResponseEntity<>(leadsCategoryId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leadsCategory/{leadsCatagoryId}")
	public ResponseEntity<?> getLeadsCategoryById(@PathVariable("leadsCatagoryId") String leadsCatagoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			LeadsCategoryMapper list = leadsCategoryService.getLeadsCatagoryById(leadsCatagoryId);
			return new ResponseEntity<LeadsCategoryMapper>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leadsCategory/organisation/{orgId}")
	public ResponseEntity<?> getAllLeadsCategory(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsCategoryMapper> list = leadsCategoryService.getLeadsCategoryListByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leadsCategory/count/{orgId}")
	public ResponseEntity<?> getLeadsCategoryCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(leadsCategoryService.getLeadsCategoryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
