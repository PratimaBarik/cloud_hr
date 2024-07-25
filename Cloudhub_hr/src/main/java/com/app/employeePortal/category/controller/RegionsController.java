package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

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

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.RegionsDropDownMapper;
import com.app.employeePortal.category.mapper.RegionsMapper;
import com.app.employeePortal.category.mapper.RegionsTargetDashBoardMapper;
import com.app.employeePortal.category.mapper.RegionsTargetMapper;
import com.app.employeePortal.category.service.RegionsService;

@RestController
@CrossOrigin(maxAge = 3600)

public class RegionsController {
	@Autowired
	RegionsService regionsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/regions/save")
	public ResponseEntity<?> createRegions(@RequestBody RegionsMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			Map map = new HashMap();
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(requestMapper.getRegions())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = regionsService.checkRegionInRegionsByOrgLevel(requestMapper.getRegions(), orgId);
				if (b == true) {
					map.put("regionInd", b);
					map.put("message", "Region can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					RegionsMapper responseMapper = regionsService.createRegions(requestMapper);

					return new ResponseEntity<>(responseMapper, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide Region !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/{regionsId}")
	public ResponseEntity<?> getRegionsByRegionsId(@PathVariable("regionsId") String regionsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			RegionsMapper responseMapper = regionsService.getRegionsByRegionsId(regionsId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/regions/All/{orgId}")
	public ResponseEntity<?> getRegionsByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<RegionsMapper> responseMapper = regionsService.getRegionsByOrgId(orgId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/regions/update/{regionsId}")
	public ResponseEntity<?> updateRegions(@PathVariable("regionsId") String regionsId,
			@RequestBody RegionsMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(requestMapper.getRegions())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = regionsService.checkRegionInRegionsByOrgLevel(requestMapper.getRegions(), orgId);
				if (b == true) {
					map.put("regionInd", b);
					map.put("message", "Region can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(regionsService.updateRegions(regionsId, requestMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/regions/delete/{regionsId}")
	public ResponseEntity<?> deleteRegionsByRegionsId(@PathVariable("regionsId") String regionsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			regionsService.deleteRegionsByRegionsId(regionsId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/search/{regions}")
	public ResponseEntity<?> getRegionsByRegions(@PathVariable("regions") String regions,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<RegionsMapper> regionsMapper = regionsService.getRegionsByRegionsorgLevel(regions,orgId);
			if (null != regionsMapper && !regionsMapper.isEmpty()) {
				return new ResponseEntity<>(regionsMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/drop-down/{orgId}")
	public ResponseEntity<?> getRegionsByOrgIdForDropDown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<RegionsDropDownMapper> responseMapper = regionsService.getRegionsByOrgIdForDropDown(orgId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/regions/count/{orgId}")
	public ResponseEntity<?> getRegionsCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(regionsService.getRegionsCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/regions/target/save")
	public ResponseEntity<?> createRegionsTarget(@RequestBody RegionsTargetMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			RegionsTargetMapper responseMapper = regionsService.createRegionsTarget(requestMapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/target/{regionsId}/{year}/{quarter}")
	public ResponseEntity<?> getRegionsTargetByRegionsId(@PathVariable("regionsId") String regionsId,
			@PathVariable("year") double year, @PathVariable("quarter") String quarter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			RegionsTargetMapper responseMapper = regionsService.getRegionsTargetByRegionsId(regionsId, year, quarter);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/target/dash-board/{year}/{quarter}")
	public ResponseEntity<?> getRegionsTargetByYearAndQuarterForDashBoard(@PathVariable("year") double year,
			@PathVariable("quarter") String quarter, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			List<RegionsTargetMapper> responseMapper = regionsService.getRegionsTargetByYearAndQuarterForDashBoard(year,
					quarter, orgId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/regions/target/dash-board/{year}/{quarter}/{regionsId}/{type}")
	public ResponseEntity<?> getRegionsTargetByYearAndQuarterByRegionsIdForDashBoard(@PathVariable("year") int year,
			@PathVariable("quarter") String quarter, @PathVariable("regionsId") String regionsId,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));

			if (type.equalsIgnoreCase("Sales")) {
				List<RegionsTargetDashBoardMapper> responseMapper = regionsService
						.getRegionsTargetByYearAndQuarterByRegionsIdForDashBoard(year, quarter, orgId, regionsId, type);
				return new ResponseEntity<>(responseMapper, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
