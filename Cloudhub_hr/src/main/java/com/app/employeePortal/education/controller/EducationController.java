package com.app.employeePortal.education.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.app.employeePortal.education.mapper.EducationTypeMapper;
import com.app.employeePortal.education.service.EducationService;

@RestController
@CrossOrigin(maxAge = 3600)
public class EducationController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	EducationService educationService;

	@PostMapping("/api/v1/educationType")
	public ResponseEntity<?> createEducationType(@RequestBody EducationTypeMapper educationTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(educationTypeMapper.getEducationType())) {
				boolean b = educationService.checkEducationNameInEducationTypeByOrgLevel(
						educationTypeMapper.getEducationType(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("educationInd", b);
					map.put("message", "EducationType can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					educationTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					educationTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					EducationTypeMapper sectorId = educationService.saveEducationType(educationTypeMapper);
					return new ResponseEntity<>(sectorId, HttpStatus.OK);
				}
			} else {
				map.put("message", "Please Provide EducationType !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/educationType/{educationTypeId}")
	public ResponseEntity<?> getEducationTypeById(@PathVariable("educationTypeId") String educationTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EducationTypeMapper educationTypeMapper = educationService.getEducationTypeById(educationTypeId);

			return new ResponseEntity<EducationTypeMapper>(educationTypeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/educationType/update")
	public ResponseEntity<?> updateEducationType(@RequestBody EducationTypeMapper educationTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			educationTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			educationTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(educationTypeMapper.getEducationType())) {
				Map map = new HashMap<>();
				boolean b = educationService.checkEducationNameInEducationTypeByOrgLevel(
						educationTypeMapper.getEducationType(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("educationInd", b);
					map.put("message", "EducationType can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String educationTypeId = educationTypeMapper.getEducationTypeId();
			EducationTypeMapper educationTypeMapper1 = educationService.updateEducationType(educationTypeId,
					educationTypeMapper);

			return new ResponseEntity<EducationTypeMapper>(educationTypeMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/educationType")
	public ResponseEntity<?> getAllEducationType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<EducationTypeMapper> typeList = educationService
					.getEducationTypesByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/educationType{name}")
	public ResponseEntity<?> geteducationTypeByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EducationTypeMapper> educationTypeMapper = educationService.getEducationTypeByNameByOrgLevel(name,orgId);
			if (null != educationTypeMapper && !educationTypeMapper.isEmpty()) {
				return new ResponseEntity<>(educationTypeMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/educationType/{educationTypeId}")

	public ResponseEntity<?> deleteEducationType(@PathVariable("educationTypeId") String educationTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			educationService.deleteEducationTypeById(educationTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/educationType/count/{orgId}")
	public ResponseEntity<?> getEducationTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(educationService.getEducationTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
