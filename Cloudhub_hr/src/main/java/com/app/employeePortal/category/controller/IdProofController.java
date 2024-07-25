package com.app.employeePortal.category.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.EquipmentMapper;
import com.app.employeePortal.category.mapper.IdProofTypeMapper;
import com.app.employeePortal.category.service.IdProofService;

@RestController
@CrossOrigin(maxAge = 3600)

public class IdProofController {

	@Autowired
	IdProofService idProofService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/idProofType")
	public ResponseEntity<?> createIdProofType(@RequestBody IdProofTypeMapper idProofTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			idProofTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			idProofTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(idProofTypeMapper.getIdProofType())) {
				boolean b = idProofService.checkIdProofNameInIdProofTypebyOrgLevel(idProofTypeMapper.getIdProofType(),orgId);
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "IdProof can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					IdProofTypeMapper id = idProofService.saveIdProofType(idProofTypeMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a IdProof");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/idProofType/{idProofTypeId}")
	public ResponseEntity<?> getIdProofTypeById(@PathVariable("idProofTypeId") String idProofTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			IdProofTypeMapper idProofTypeMapper = idProofService.getIdProofTypeById(idProofTypeId);

			return new ResponseEntity<IdProofTypeMapper>(idProofTypeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/idProofType/update")
	public ResponseEntity<?> updateIdProofType(@RequestBody IdProofTypeMapper idProofTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			idProofTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			idProofTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			if (!StringUtils.isEmpty(idProofTypeMapper.getIdProofType())) {
				boolean b = idProofService.checkIdProofNameInIdProofTypebyOrgLevel(idProofTypeMapper.getIdProofType(),orgId);
				if (b == true) {
					Map map = new HashMap<>();
					map.put("skillInd", b);
					map.put("message", "IdProof can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String idProofTypeId = idProofTypeMapper.getIdProofTypeId();
			IdProofTypeMapper idProofTypeMapper1 = idProofService.updateIdProofType(idProofTypeId, idProofTypeMapper);

			return new ResponseEntity<IdProofTypeMapper>(idProofTypeMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/idProofType/all-list")
	public ResponseEntity<?> getAllIdProofType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<IdProofTypeMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = idProofService.getIdProofTypesByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (null != typeList && !typeList.isEmpty()) {

				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/idProofType/all-list/website")
	public ResponseEntity<?> getAllIdProofTypeToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		boolean b = idProofService.ipAddressExists(url);
		if (b == true) {
			List<IdProofTypeMapper> idProofTypeMappernew = idProofService.getIdProofTypeByUrl(url);
			Collections.sort(idProofTypeMappernew, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(idProofTypeMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/idProofType/search/{name}")
	public ResponseEntity<?> getIdProofDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<IdProofTypeMapper> idProofTypeMapper = idProofService.getIdProofDetailsByNameByOrgLevel(name,orgId);
			if (null != idProofTypeMapper && !idProofTypeMapper.isEmpty()) {
				return new ResponseEntity<>(idProofTypeMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/idProofType/{idProofTypeId}")

	public ResponseEntity<?> deleteIdProofType(@PathVariable("idProofTypeId") String idProofTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			idProofService.deleteIdProofTypeById(idProofTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/idProofType/count/{orgId}")
	public ResponseEntity<?> getIdProofTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(idProofService.getIdProofTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
