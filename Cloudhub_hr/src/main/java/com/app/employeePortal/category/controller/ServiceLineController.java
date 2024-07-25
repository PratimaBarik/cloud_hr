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
import com.app.employeePortal.category.mapper.ServiceLineReqMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.category.service.ServiceLineService;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.sector.mapper.SectorMapper;

@RestController
@CrossOrigin(maxAge = 3600)

public class ServiceLineController {
	@Autowired
	ServiceLineService serviceLineService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/serviceLine")
	public ResponseEntity<?> createServiceLine(@RequestBody ServiceLineReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			Map map = new HashMap();
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(requestMapper.getServiceLineName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = serviceLineService
						.checkServiceLineNameInServiceLineByOrgLevel(requestMapper.getServiceLineName(), orgId);
				if (b == true) {
					map.put("ServiceLineInd", b);
					map.put("message", "ServiceLine can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
					ServiceLineRespMapper responseMapper = serviceLineService.saveServiceLine(requestMapper);
					return new ResponseEntity<>(responseMapper, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide ServiceLine !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/serviceLine/All/{orgId}")
	public ResponseEntity<?> getServiceLineByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ServiceLineRespMapper> responseMapper = serviceLineService.getServiceLineByOrgId(orgId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/serviceLine/{serviceLineId}")
	public ResponseEntity<?> updateServiceLine(@PathVariable("serviceLineId") String serviceLineId,
			@RequestBody ServiceLineReqMapper requestMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			Map map = new HashMap();
			if (!StringUtils.isEmpty(requestMapper.getServiceLineName())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = serviceLineService
						.checkServiceLineNameInServiceLineByOrgLevel(requestMapper.getServiceLineName(), orgId);
				if (b == true) {
					map.put("ServiceLineInd", b);
					map.put("message", "ServiceLine can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<ServiceLineRespMapper>(
					serviceLineService.updateServiceLine(serviceLineId, requestMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/serviceLine/{serviceLineId}")
	public ResponseEntity<?> deleteServiceLine(@PathVariable("serviceLineId") String serviceLineId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			serviceLineService.deleteServiceLine(serviceLineId, userId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/serviceLine/search/{name}")
	public ResponseEntity<?> getServiceLineByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ServiceLineRespMapper> responseMapper = serviceLineService.getServiceLineByNameByOrgLevel(name,orgId);
			if (null != responseMapper && !responseMapper.isEmpty()) {
				return new ResponseEntity<>(responseMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/serviceLine/department/update/{departmentId}/{liveInd}")
	public ResponseEntity<?> updateServiceLineDepartment(@PathVariable("departmentId") String departmentId,
			@PathVariable("liveInd") boolean liveInd, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return new ResponseEntity<DepartmentMapper>(
					serviceLineService.updateServiceLineDepartment(orgId, departmentId, liveInd), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/serviceLine/details/{departmentId}")
	public ResponseEntity<?> getServiceLineByDepartmentId(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ServiceLineRespMapper> responseMapper = serviceLineService.getServiceLineByDepartmentId(departmentId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/serviceLine/count/{orgId}")
	public ResponseEntity<?> getServiceLineCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(serviceLineService.getServiceLineCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
