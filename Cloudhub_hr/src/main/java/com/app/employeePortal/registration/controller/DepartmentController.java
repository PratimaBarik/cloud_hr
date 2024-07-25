package com.app.employeePortal.registration.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.FunctionDetails;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.DepartmentRequestMapper;
import com.app.employeePortal.registration.mapper.DesignationMapper;
import com.app.employeePortal.registration.mapper.FunctionMapper;
import com.app.employeePortal.registration.service.DepartmentService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
//@RequestMapping("/api/v1")

public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping(value = "/api/v1/department/{departmentName}")
	public ResponseEntity<?> autocompleteDepartment(@PathVariable("departmentName") String departmentName,
			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DepartmentMapper> departmentList = departmentService.autocompleteDepartmentByName(departmentName,
					loggedInUserOrgId, departmentMapper);

			System.out.println("departmentList............ " + departmentList);
			if (null != departmentList && !departmentList.isEmpty()) {

				return new ResponseEntity<>(departmentList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping(value = "/department/{departmentName}")

	public ResponseEntity<?> createDepartment(@PathVariable("departmentName") String departmentName,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Department> departmentList = departmentService.createDepartment(departmentName);

			if (null != departmentList && !departmentList.isEmpty()) {

				return new ResponseEntity<>(departmentList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(value = "/departments")

	public ResponseEntity<?> getIndustryType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Department> departmentList = departmentService.getDepartmentList();

			if (null != departmentList && !departmentList.isEmpty()) {

				return new ResponseEntity<>(departmentList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/designation")
	public ResponseEntity<?> getAllDesignation(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<DesignationMapper> typeList = departmentService
					.getDesignationByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/designation")
	public ResponseEntity<?> createDesignation(@RequestBody DesignationMapper designationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (!StringUtils.isEmpty(designationMapper.getDesignationType())) {
				boolean b = departmentService.checkDesignationInDesignationTypeByOrgLevel(
						designationMapper.getDesignationType(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("DesignationInd", b);
					map.put("message", "Designation can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					designationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					designationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					DesignationMapper designationId = departmentService.saveDesignation(designationMapper);
					return new ResponseEntity<>(designationId, HttpStatus.OK);
				}
			} else {
				map.put("message", "Please Provide Designation !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/designation")
	public ResponseEntity<?> updateDesignation(@RequestBody DesignationMapper designationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			designationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			designationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(designationMapper.getDesignationType())) {
				Map map = new HashMap();
				boolean b = departmentService.checkDesignationInDesignationTypeByOrgLevel(
						designationMapper.getDesignationType(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("DesignationInd", b);
					map.put("message", "Designation can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			DesignationMapper designationMapperr = departmentService
					.updateDesignation(designationMapper.getDesignationTypeId(), designationMapper);

			return new ResponseEntity<DesignationMapper>(designationMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/function")
	public String createFunction(@RequestBody FunctionMapper functionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		String functionId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			functionMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			functionMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			functionId = departmentService.saveFunction(functionMapper);

		}

		return functionId;
	}

	@GetMapping("/api/v1/function")
	public ResponseEntity<?> getAllFunction(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<FunctionDetails> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = departmentService.getFunctionList();

			if (null != typeList && !typeList.isEmpty()) {

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/function")
	public ResponseEntity<?> updateFunction(@RequestBody FunctionMapper functionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			functionMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			functionMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			FunctionMapper functionMapperr = departmentService.updateFunction(functionMapper.getFunctionTypeId(),
					functionMapper);

			return new ResponseEntity<FunctionMapper>(functionMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/department")
	public ResponseEntity<?> createDepartment(@RequestBody DepartmentMapper departmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(departmentMapper.getDepartmentName())) {
				boolean b = departmentService
						.checkDepartmentNameInDepartmentByOrgLevel(departmentMapper.getDepartmentName(), orgId);
				if (b) {
					map.put("DepartmentInd", b);
					map.put("message", "DepartmentName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					DepartmentMapper departmentId = departmentService.saveDepartment(departmentMapper);
					return new ResponseEntity<>(departmentId, HttpStatus.OK);

				}
			} else {
				map.put("message", "Please Provide DepartmentName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/department")
	public ResponseEntity<?> updateDepartment(@RequestBody DepartmentMapper departmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(departmentMapper.getDepartmentName())) {
				boolean b = departmentService
						.checkDepartmentNameInDepartmentByOrgLevel(departmentMapper.getDepartmentName(), orgId);
				Map map = new HashMap();
				if (b) {
					map.put("DepartmentInd", b);
					map.put("message", "DepartmentName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			DepartmentMapper departmentMapperr = departmentService.updateDepartment(departmentMapper.getDepartmentId(),
					departmentMapper);

			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/department/{departmentId}")
	public ResponseEntity<?> deleteDepartment(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = departmentService.deleteDepartment(departmentId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/department")
	public ResponseEntity<?> getAllDepartment(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<DepartmentMapper> typeList = departmentService
					.getDepartmentByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/department/website")
	public ResponseEntity<?> getAllDepartmentToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		boolean b = departmentService.ipAddressExists(url);
		if (b == true) {
			List<DepartmentMapper> departmentMappernew = departmentService.getDepartmentByUrl(url);
			Collections.sort(departmentMappernew, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(departmentMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/department/accesss/{orgId}")
	public ResponseEntity<?> getAllDepartmentWhereEditInd(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<DepartmentMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			typeList = departmentService.getAllDepartmentWhereEditInd(orgId);

			// Collections.sort(typeList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			if (null != typeList && !typeList.isEmpty()) {

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/department/{sectorId}")
	public List<DepartmentMapper> getDepartmentListBySectorId(@PathVariable("sectorId") String sectorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		List<DepartmentMapper> mapperList = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			// String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			mapperList = departmentService.getDepartmentListBySectorId(sectorId);
		}
		return mapperList;
	}

	@GetMapping("/api/v1/designation/website")
	public ResponseEntity<?> getAllDesignationToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		boolean b = departmentService.ipAddressExists(url);
		if (b == true) {
			List<DesignationMapper> designationMappernew = departmentService.getDesignationByUrl(url);
			Collections.sort(designationMappernew, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(designationMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/department/search/{name}")
	public ResponseEntity<?> getDepartmentByName(@PathVariable("name") String name,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DepartmentMapper> departmentMapper = departmentService.getDepartmentByNameByOrgLevel(name, orgId);
			if (null != departmentMapper && !departmentMapper.isEmpty()) {
				return new ResponseEntity<>(departmentMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/designation/search/{name}")
	public ResponseEntity<?> getDesignationByName(@PathVariable("name") String name,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DesignationMapper> designationMapper = departmentService.getDesignationByNameByOrgLevel(name, orgId);
			if (null != designationMapper && !designationMapper.isEmpty()) {
				return new ResponseEntity<>(designationMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/departments/{departmentId}")

	public ResponseEntity<?> deleteDepartments(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			departmentService.deleteDepartmentById(departmentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/designation/{designationTypeId}")

	public ResponseEntity<?> deleteDesignation(@PathVariable("designationTypeId") String designationTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			departmentService.deleteDesignationById(designationTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/department/mandatoryInd/{departmentId}")
	public ResponseEntity<?> updateDepMandetoryInd(@PathVariable("departmentId") String departmentId,
			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			DepartmentMapper departmentMapperr = departmentService.updateDepMandetoryInd(departmentId,
					departmentMapper);

			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/department/add/access")
	public ResponseEntity<?> updateDepartmentToAddAccess(@RequestBody DepartmentMapper departmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			DepartmentMapper departmentMapperr = departmentService
					.updateDepartmentToAddAccess(departmentMapper.getDepartmentId(), departmentMapper);

			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//	@PutMapping("/api/v1/department/crmInd/{departmentId}")
//	public ResponseEntity<?> updateDepCrmInd(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateDepCrmInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//	@PutMapping("/api/v1/department/erpInd/{departmentId}")
//	public ResponseEntity<?> updateDepErpInd(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateDepErpInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//	@PutMapping("/api/v1/department/imInd/{departmentId}")
//	public ResponseEntity<?> updateDepImInd(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateDepImInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//	@PutMapping("/api/v1/department/accountInd/{departmentId}")
//	public ResponseEntity<?> updateAccountInd(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateAccountInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//	@PutMapping("/api/v1/department/recruitOppsInd/{departmentId}")
//	public ResponseEntity<?> updateRecruitOppsInd(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateRecruitOppsInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//	@PutMapping("/api/v1/department/hrInd/{departmentId}")
//	public ResponseEntity<?> updateHrId(@PathVariable("departmentId") String departmentId,
//			@RequestBody DepartmentMapper departmentMapper, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			departmentMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//			DepartmentMapper departmentMapperr = departmentService.updateHrInd(departmentId, departmentMapper);
//
//			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
//
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@PutMapping("/api/v1/department/all/indicator/{departmentId}")
	public ResponseEntity<?> updateIndicator(@PathVariable("departmentId") String departmentId,
			@RequestBody DepartmentRequestMapper departmentMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			departmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			departmentMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			DepartmentMapper departmentMapperr = departmentService.updateIndicator(departmentId, departmentMapper);
			return new ResponseEntity<DepartmentMapper>(departmentMapperr, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/department/count/{orgId}")
	public ResponseEntity<?> getDepartmentCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(departmentService.getDepartmentCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/designation/count/{orgId}")
	public ResponseEntity<?> getDesignationCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(departmentService.getDesignationCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
