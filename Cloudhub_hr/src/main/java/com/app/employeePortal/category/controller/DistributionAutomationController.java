package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.DistributionAutomationMapper;
import com.app.employeePortal.category.service.DistributionAutomationService;

@RestController
@CrossOrigin(maxAge = 3600)

public class DistributionAutomationController {
	@Autowired
	DistributionAutomationService distributionAutomationService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@PutMapping("/api/v1/distributionAutomation/save")
	public ResponseEntity<?> saveDistributionAutomation(@RequestBody DistributionAutomationMapper distributionAutomationMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			distributionAutomationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			distributionAutomationMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			DistributionAutomationMapper customerTypeId = distributionAutomationService.saveDistributionAutomation(distributionAutomationMapper);
			
			return new ResponseEntity<>(customerTypeId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/distributionAutomation/{distributionAutomationId}")
	public ResponseEntity<?> getDistributionAutomationByDistributionAutomationId(@PathVariable("distributionAutomationId") String distributionAutomationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			DistributionAutomationMapper distributionAutomationMapper = distributionAutomationService.getDistributionAutomationByDistributionAutomationId(distributionAutomationId);
		
			return new ResponseEntity<>(distributionAutomationMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/distributionAutomation/{orgId}/{type}")
		public ResponseEntity<?> getDistributionAutomationByOrgIdAndType(@PathVariable("orgId") String orgId,@PathVariable("type") String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			DistributionAutomationMapper distributionAutomationMapper = distributionAutomationService.getDistributionAutomationByOrgIdAndType(orgId,type);
			
			return new ResponseEntity<>(distributionAutomationMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
}
