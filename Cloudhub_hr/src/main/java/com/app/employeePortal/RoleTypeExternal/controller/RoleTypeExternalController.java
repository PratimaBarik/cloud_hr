package com.app.employeePortal.RoleTypeExternal.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.app.employeePortal.RoleTypeExternal.mapper.RoleTypeExternalMapper;
import com.app.employeePortal.RoleTypeExternal.service.RoleTypeExternalService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoleTypeExternalController {
	@Autowired
	RoleTypeExternalService roleTypeExternalService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping(value = "/api/v1/roleTypeExternal")
	public ResponseEntity<?> CreateRoleType(@RequestBody RoleTypeExternalMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			roleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			roleMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(roleMapper.getRoleType())) {
				boolean b = roleTypeExternalService.checkRoleNameInRoleTypeByOrgLevel(roleMapper.getRoleType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("roleTypeInd", true);
					map.put("message", "RoleTypeExternal can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					RoleTypeExternalMapper roleTypeExternalId = roleTypeExternalService
							.createRoleTypeExternal(roleMapper);
					return new ResponseEntity<>(roleTypeExternalId, HttpStatus.OK);
				}
			} else {
				map.put("message", "Please Provide RoleTypeExternal !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/roleTypeExternal")
	public ResponseEntity<?> updateRoleType(@RequestBody RoleTypeExternalMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			roleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			roleMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(roleMapper.getRoleType())) {
				boolean b = roleTypeExternalService.checkRoleNameInRoleTypeByOrgLevel(roleMapper.getRoleType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					Map map = new HashMap<>();
					map.put("roleTypeInd", true);
					map.put("message", "Role can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			RoleTypeExternalMapper roleMapperr = roleTypeExternalService
					.updateRoleTypeExternal(roleMapper.getRoleTypeExternalId(), roleMapper);

			return new ResponseEntity<>(roleMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/roleTypeExternal/{orgId}")
	public ResponseEntity<?> getAllRoleType(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<RoleTypeExternalMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			typeList = roleTypeExternalService.getRoleListByOrgId(orgId);

			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				Map map = new HashMap();
				map.put("message", "Data Not Available!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/roleTypeExternal/{roleTypeExternalId}")

	public ResponseEntity<?> deleteRoleTypeDetails(@PathVariable("roleTypeExternalId") String roleTypeExternalId,
			@RequestBody RoleTypeExternalMapper externalMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			externalMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			externalMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			roleTypeExternalService.deleteRoleTypeDetailsById(roleTypeExternalId, externalMapper);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/roleTypeExternal/search/{name}")
	public ResponseEntity<?> getRoleTypeExternalByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<RoleTypeExternalMapper> roleTypeExternalMapper = roleTypeExternalService
					.getRoleTypeExternalByNameByOrgLevel(name,orgId);
			if (null != roleTypeExternalMapper && !roleTypeExternalMapper.isEmpty()) {
				return new ResponseEntity<>(roleTypeExternalMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/roleTypeExternal/count/{orgId}")
	public ResponseEntity<?> getRoleTypeExternalCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(roleTypeExternalService.getRoleTypeExternalCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
