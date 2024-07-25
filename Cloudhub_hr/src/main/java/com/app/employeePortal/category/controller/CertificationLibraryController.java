package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.CertificationLibraryMapper;
import com.app.employeePortal.category.service.CertificationLibraryService;
import com.fasterxml.jackson.core.JsonGenerationException;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "certification" })

public class CertificationLibraryController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	CertificationLibraryService certificationLibraryService;

	@PostMapping("/api/v1/certificationLibrary/save")
	public ResponseEntity<?> createCertificationLibrary(
			@RequestBody CertificationLibraryMapper certificationLibraryMapper,
			@RequestHeader("Authorization") String authorization) {

		CertificationLibraryMapper certificationId = new CertificationLibraryMapper();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			certificationLibraryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			certificationLibraryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			Map map = new HashMap<>();
			if (!StringUtils.isEmpty(certificationLibraryMapper.getName())) {
				boolean b = certificationLibraryService
						.checkCertificationNameExistByOrgLevel(certificationLibraryMapper.getName(),orgId);
				if (b) {
					
					map.put("certificationInd", true);
					map.put("message", "Certification can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					certificationId = certificationLibraryService.saveCertificationLibrary(certificationLibraryMapper);
					return ResponseEntity.ok(certificationId);
				}
			} else {
				map.put("message", "Please Provide CertificationName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/certificationLibrary/{certificationId}")
	public ResponseEntity<?> getCertificationLibraryByCertificationId(
			@PathVariable("certificationId") String certificationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CertificationLibraryMapper certificationLibraryMapper = certificationLibraryService
					.getCertificationLibraryByCertificationId(certificationId);

			return new ResponseEntity<>(certificationLibraryMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/update/certificationLibrary/{certificationId}")

	public ResponseEntity<?> updateCertificationLibrary(@PathVariable("certificationId") String certificationId,
			@RequestBody CertificationLibraryMapper certificationLibraryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		CertificationLibraryMapper certificationLibraryMapper1 = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			certificationLibraryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			certificationLibraryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(certificationLibraryMapper.getName())) {
				boolean b = certificationLibraryService.checkCertificationNameExistForUpdateByOrgLevel(certificationId,
						certificationLibraryMapper.getName(),orgId);
				if (b) {
					Map map = new HashMap<>();
					map.put("certificationInd", true);
					map.put("message", "Certification can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			certificationLibraryMapper1 = certificationLibraryService.updateCertificationLibrary(certificationId,
					certificationLibraryMapper);

			return new ResponseEntity<CertificationLibraryMapper>(certificationLibraryMapper1, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/delete/certificationLibrary/{certificationId}")
	public ResponseEntity<?> deleteCertificationLibrary(@PathVariable("certificationId") String certificationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			certificationLibraryService.deleteCertificationLibrary(certificationId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/certificationsLibrary/{orgId}")
	public ResponseEntity<?> getCertificationLibraryByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CertificationLibraryMapper> certificationLibraryMapper = certificationLibraryService
					.getCertificationLibraryByOrgId(orgId);

			return new ResponseEntity<>(certificationLibraryMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/certificationsLibrary/search/{name}")
	public ResponseEntity<?> getCertificationsLibraryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CertificationLibraryMapper> certificationLibraryMapper = certificationLibraryService
					.getCertificationsLibraryByNameByOrgLevel(name,orgId);
			if (null != certificationLibraryMapper && !certificationLibraryMapper.isEmpty()) {
				return new ResponseEntity<>(certificationLibraryMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/certificationsLibrary/count/{orgId}")
	public ResponseEntity<?> getCertificationsLibraryCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(certificationLibraryService.getCertificationsLibraryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
