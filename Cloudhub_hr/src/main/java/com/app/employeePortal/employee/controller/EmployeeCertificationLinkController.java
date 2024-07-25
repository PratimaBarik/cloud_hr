package com.app.employeePortal.employee.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.employee.mapper.EmployeeCertificationLinkMapper;
import com.app.employeePortal.employee.repository.EmployeeCertificationLinkRepository;
import com.app.employeePortal.employee.service.EmployeeCertificationLinkService;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "employee" })

public class EmployeeCertificationLinkController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	EmployeeCertificationLinkRepository employeeCertificationLinkRepository;
	@Autowired
	EmployeeCertificationLinkService employeeCertificationLinkService;
	
	@PostMapping("/api/v1/employee/certification")
	public ResponseEntity<?> saveEmployeeCertification(
			@RequestBody EmployeeCertificationLinkMapper employeeCertificationLinkMapper,
			@RequestHeader("Authorization") String authorization) {
		
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(employeeCertificationLinkMapper.getEmployeeCertificationName())) {
				boolean b = employeeCertificationLinkService.checkCertificationInCertificationSet(employeeCertificationLinkMapper.getEmployeeCertificationName(),employeeCertificationLinkMapper.getEmployeeId());
				if (b == true) {
					map.put("CertificationInd", b);
					map.put("message", "Certification name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeCertificationLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			employeeCertificationLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = employeeCertificationLinkService.saveEmployeeCertification(employeeCertificationLinkMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/certification/{employeeId}")
	public ResponseEntity<?> getEmployeeCertificationById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeCertificationLinkMapper> employeeCertificationLinkMapper = employeeCertificationLinkService
					.getEmployeeCertificationDetails(employeeId);
			Collections.sort(employeeCertificationLinkMapper,
					(m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(employeeCertificationLinkMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/api/v1/employee/certification/{employeeCertificationLinkId}")
	public ResponseEntity<?> deleteEmployeeCertification(@PathVariable("employeeCertificationLinkId") String employeeCertificationLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(employeeCertificationLinkService.deleteEmployeeCertification(employeeCertificationLinkId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
