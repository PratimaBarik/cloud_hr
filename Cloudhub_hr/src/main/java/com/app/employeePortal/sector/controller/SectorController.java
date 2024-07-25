package com.app.employeePortal.sector.controller;

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
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.sector.service.SectorService;

@RestController
@CrossOrigin(maxAge = 3600)
public class SectorController {

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	SectorService sectorService;

	@PostMapping("/api/v1/sector")
	public ResponseEntity<?> createSector(@RequestBody SectorMapper sectorMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(sectorMapper.getSectorName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = sectorService.checkSectorNameInSectorDetailsByOrgLevel(sectorMapper.getSectorName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "SectorName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {

					sectorMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					sectorMapper.setOrganizationId(orgId);

					List<SectorMapper> sectorId = sectorService.saveToSectorDetails(sectorMapper);
					return new ResponseEntity<>(sectorId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide SectorName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/sector/{sectorId}")

	public ResponseEntity<?> getSectorById(@PathVariable("sectorId") String sectorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			SectorMapper sectorMapper = sectorService.getsectorDetailsById(sectorId);

			return new ResponseEntity<SectorMapper>(sectorMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/*
	 * @PutMapping("/api/v1/sector")
	 *
	 * public ResponseEntity<?> updateSector( @RequestBody SectorMapper
	 * sectorMapper,
	 *
	 * @RequestHeader("Authorization") String authorization, HttpServletRequest
	 * request) {
	 *
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 *
	 *
	 * SectorMapper sectorMapperr = sectorService.updateSector(sectorMapper);
	 *
	 * return new ResponseEntity<SectorMapper>(sectorMapperr, HttpStatus.OK);
	 *
	 * }
	 *
	 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 *
	 * }
	 */

	@GetMapping("/api/v1/sector")
	public ResponseEntity<?> getAllSector(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<SectorMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = sectorService.getSectorTypeByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (null != typeList && !typeList.isEmpty()) {
				// Collections.sort(typeList, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(typeList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/sector/user/{userId}")

	public ResponseEntity<List<SectorMapper>> getSectorDetailsByuserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<SectorMapper> sectorDetailsList = sectorService.getSectorDetailsByuserId(userId);
			Collections.sort(sectorDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return ResponseEntity.ok(sectorDetailsList);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/api/v1/sector/update")

	public ResponseEntity<?> updateSector(@RequestBody SectorMapper sectorMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			sectorMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			sectorMapper.setOrganizationId(orgId);
			if (!StringUtils.isEmpty(sectorMapper.getSectorName())) {
				
				boolean b = sectorService.checkSectorNameInSectorDetailsByOrgLevel(sectorMapper.getSectorName(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "SectorName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}else {
					String sectorId = sectorMapper.getSectorId();
					List<SectorMapper> sectorMapperr = sectorService.updateSector(sectorId, sectorMapper);
					return new ResponseEntity<>(sectorMapperr, HttpStatus.OK);
				}
			}else {
				map.put("message", "Please Provide SectorName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/sector/website")
	public ResponseEntity<?> getAllSectorToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<SectorMapper> typeList = null;

		Map map = new HashMap();
		boolean b = sectorService.ipAddressExists(url);
		if (b == true) {
			List<SectorMapper> sectorMappernew = sectorService.getSectorTypeByUrl(url);
			return new ResponseEntity<>(sectorMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/sector/search/{name}")
	public ResponseEntity<?> getSectorByName(@PathVariable("name") String name,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<SectorMapper> sectorMapper = sectorService.getSectorByNameByOrgLevel(name,orgId);
			if (null != sectorMapper && !sectorMapper.isEmpty()) {
				return new ResponseEntity<>(sectorMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/sector/{sectorId}")

	public ResponseEntity<?> deleteSectorDetails(@PathVariable("sectorId") String sectorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			sectorService.deleteSectorDetailsById(sectorId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/sector/count/{orgId}")
	public ResponseEntity<?> getSectorCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(sectorService.getSectorCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
