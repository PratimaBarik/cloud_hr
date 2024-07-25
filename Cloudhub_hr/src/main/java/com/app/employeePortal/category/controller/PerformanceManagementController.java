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
import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkRespMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtDropDownMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtReqMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtRespMapper;
import com.app.employeePortal.category.service.PerformanceManagementService;

@RestController
@CrossOrigin(maxAge = 3600)

public class PerformanceManagementController {
	@Autowired
	PerformanceManagementService performanceManagementService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/performanceManagement")
	public ResponseEntity<?> createPerformanceManagement(@RequestBody PerformanceMgmtReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(requestMapper.getKpi())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = performanceManagementService.checkKpiInPerformanceMgmtByOrgLevel(requestMapper.getKpi(),
						orgId);
				if (b == true) {
					map.put("pInd", b);
					map.put("message", "Kpi can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

					PerformanceMgmtRespMapper responseMapper = performanceManagementService
							.savePerformanceManagement(requestMapper);

					return new ResponseEntity<>(responseMapper, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide Kpi !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/performanceManagement/{performanceManagementId}")
	public ResponseEntity<?> getPerformanceManagementByPerformanceManagementId(
			@PathVariable("performanceManagementId") String performanceManagementId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PerformanceMgmtRespMapper responseMapper = performanceManagementService
					.getPerformanceManagementById(performanceManagementId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/performanceManagement/All/{orgId}")
	public ResponseEntity<?> getPerformanceManagementByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PerformanceMgmtRespMapper> responseMapper = performanceManagementService
					.getPerformanceManagementByOrgId(orgId);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/performanceManagement/{performanceManagementId}")
	public ResponseEntity<?> updatePerformanceManagement(
			@PathVariable("performanceManagementId") String performanceManagementId,
			@RequestBody PerformanceMgmtReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(requestMapper.getKpi())) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = performanceManagementService.checkKpiInPerformanceMgmtByOrgLevel(requestMapper.getKpi(),
						orgId);
				if (b == true) {
					map.put("pInd", b);
					map.put("message", "Kpi can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			return new ResponseEntity<PerformanceMgmtRespMapper>(
					performanceManagementService.updatePerformanceManagement(performanceManagementId, requestMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/performanceManagement/currency-ind/update/{performanceManagementId}")
	public ResponseEntity<?> updatePerformanceManagementForCurrencyInd(
			@PathVariable("performanceManagementId") String performanceManagementId,
			@RequestBody PerformanceMgmtReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<PerformanceMgmtRespMapper>(performanceManagementService
					.updatePerformanceManagementForCurrencyInd(performanceManagementId, requestMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/performanceManagement/{performanceManagementId}")
	public ResponseEntity<?> deletePerformanceManagement(
			@PathVariable("performanceManagementId") String performanceManagementId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			performanceManagementService.deletePerformanceManagement(performanceManagementId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/PerformanceManagement/search/{kpi}")
	public ResponseEntity<?> getPerformanceManagementByKpi(@PathVariable("kpi") String kpi,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getUserIdFromToken(authToken);
			List<PerformanceMgmtRespMapper> PerformanceManagementMapper = performanceManagementService
					.getPerformanceManagementByKpiByOrgLevel(kpi,orgId);
			if (null != PerformanceManagementMapper && !PerformanceManagementMapper.isEmpty()) {
				return new ResponseEntity<>(PerformanceManagementMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/api/v1/performanceManagement/department/{departmentId}")
//	public ResponseEntity<?> getPerformanceManagementByDepartmentId(@PathVariable("departmentId") String departmentId,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			List<PerformanceMgmtRespMapper> responseMapper = performanceManagementService
//					.getPerformanceManagementByDepartmentId(departmentId);
//
//			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@GetMapping("/api/v1/performanceManagement/count/{orgId}")
	public ResponseEntity<?> getPerformanceManagementCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(performanceManagementService.getPerformanceManagementCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/performanceManagement/department")
	public ResponseEntity<?> createDepartmentPerformanceMgmt(@RequestBody PerformanceMgmntDeptLinkMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			List<PerformanceMgmntDeptLinkRespMapper> responseMapper = performanceManagementService
					.saveDepartmentPerformanceMgmt(requestMapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/performanceManagement/department/{departmentId}/{roleTypeId}")
	public ResponseEntity<?> getDepartmentPerformanceMgmtByDepartmentIdAndRoleTypeId(
			@RequestHeader("Authorization") String authorization, @PathVariable("departmentId") String departmentId,
			@PathVariable("roleTypeId") String roleTypeId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<PerformanceMgmntDeptLinkRespMapper> responseMapper = performanceManagementService
					.getDepartmentPerformanceMgmtByDepartmentIdAndRoleTypeId(departmentId, roleTypeId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/performanceManagement/All/drop-down/{orgId}")
	public ResponseEntity<?> getPerformanceManagementByOrgIdForDropDown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<PerformanceMgmtDropDownMapper> responseMapper = performanceManagementService
					.getPerformanceManagementByOrgIdForDropDown(orgId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
