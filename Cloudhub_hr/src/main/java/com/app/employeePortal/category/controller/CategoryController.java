package com.app.employeePortal.category.controller;

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
import com.app.employeePortal.category.mapper.AdminSecreteKeyMapper;
import com.app.employeePortal.category.mapper.CategoryMapper;
import com.app.employeePortal.category.mapper.InvoiceCategoryMapper;
import com.app.employeePortal.category.mapper.MinimumActivityReqMapper;
import com.app.employeePortal.category.mapper.MinimumActivityRespMapper;
import com.app.employeePortal.category.mapper.ModuleRequestMapper;
import com.app.employeePortal.category.mapper.ModuleResponseMapper;
import com.app.employeePortal.category.mapper.RoleMapper;
import com.app.employeePortal.category.mapper.SkillLevelLinkMapper;
import com.app.employeePortal.category.mapper.TaskChecklistMapper;
import com.app.employeePortal.category.mapper.TaskChecklistStageLinkMapper;
import com.app.employeePortal.category.mapper.TicketTypeRequestMapper;
import com.app.employeePortal.category.mapper.TicketTypeResponseMapper;
import com.app.employeePortal.category.mapper.UnitMapper;
import com.app.employeePortal.category.service.CategoryService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping(value = "/api/v1/roleType")
	public ResponseEntity<?> CreateRoleType(@RequestBody RoleMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (!StringUtils.isEmpty(roleMapper.getRoleType())) {
				boolean b = categoryService.checkRoleNameInRoleTypeByorgLevel(roleMapper.getRoleType(),
						roleMapper.getDepartmentId(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("roleTypeInd", b);
					map.put("message", "Role can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					RoleMapper RoleTypeId = categoryService.CreateRoleType(roleMapper);
					return new ResponseEntity<>(RoleTypeId, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a role");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/roleType")
	public ResponseEntity<?> updateRoleType(@RequestBody RoleMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			roleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			roleMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(roleMapper.getRoleType())) {
				boolean b = categoryService.checkRoleNameInRoleTypeByorgLevel(roleMapper.getRoleType(),
						roleMapper.getDepartmentId(), jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("roleTypeInd", b);
					map.put("message", "Role can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			RoleMapper roleMapperr = categoryService.updateRoleType(roleMapper.getRoleTypeId(), roleMapper);
			return new ResponseEntity<RoleMapper>(roleMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/roleType/{orgId}")
	public ResponseEntity<?> getAllRoleType(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<RoleMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			typeList = categoryService.getRoleListByOrgId(orgId);

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

	@PostMapping(value = "/api/v1/libraryType")
	public ResponseEntity<?> CreateLibraryType(@RequestBody RoleMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (!StringUtils.isEmpty(roleMapper.getLibraryType())) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				boolean b = categoryService.checkLibraryNameInLibraryTypeInOrgLevel(roleMapper.getLibraryType(), orgId);
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "Library can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					RoleMapper LibraryTypeId = categoryService.CreateLibraryType(roleMapper);
					return new ResponseEntity<>(LibraryTypeId, HttpStatus.OK);

				}
			} else {
				map.put("message", "Please Provide LibraryType !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/libraryType")
	public ResponseEntity<?> updateLibraryType(@RequestBody RoleMapper roleMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(roleMapper.getLibraryType())) {
				Map map = new HashMap<>();
				boolean b = categoryService.checkLibraryNameInLibraryTypeInOrgLevel(roleMapper.getLibraryType(), orgId);
				if (b == true) {
					map.put("sectorInd", b);
					map.put("message", "SectorName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			RoleMapper roleMapperr = categoryService.updateLibraryType(roleMapper.getLibraryTypeId(), roleMapper);
			return new ResponseEntity<RoleMapper>(roleMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/libraryType/{orgId}")
	public ResponseEntity<?> getAllLibraryType(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<RoleMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX,"");

			typeList = categoryService.getAllLibraryType(orgId);

			return new ResponseEntity<>(typeList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/library/skills")
	public ResponseEntity<?> getAllLibrarySkills(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		RoleMapper typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX,"");

			typeList = categoryService.getAllLibrarySkills();

			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/roleType/website")
	public ResponseEntity<?> getAllRoleTypeToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		// List<DepartmentMapper> typeList = null;

		Map map = new HashMap();
		boolean b = categoryService.ipAddressExists(url);
		if (b == true) {
			List<RoleMapper> roleTypeMappernew = categoryService.getAllRoleTypeToWebsite(url);
			Collections.sort(roleTypeMappernew, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(roleTypeMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/roleType/search/{name}")
	public ResponseEntity<?> getroleDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<RoleMapper> roleMapper = categoryService.getRoleDetailsByNameByOrgLevel(name, orgId);
			if (null != roleMapper && !roleMapper.isEmpty()) {
				return new ResponseEntity<>(roleMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/library/{name}")
	public ResponseEntity<?> getlibraryDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<RoleMapper> roleMapper = categoryService.getLibraryDetailsByNameByOrgLevel(name, orgId);
			if (null != roleMapper && !roleMapper.isEmpty()) {
				return new ResponseEntity<>(roleMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/roleType/{roleTypeId}")

	public ResponseEntity<?> deleteRoleTypeDetails(@PathVariable("roleTypeId") String roleTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			categoryService.deleteRoleTypeDetailsById(roleTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/unit/save")
	public ResponseEntity<?> createUnit(@RequestBody UnitMapper unitMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			unitMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			unitMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(unitMapper.getUnitName())) {
				boolean b = categoryService.checkUnitNameExist(unitMapper.getUnitName());
				if (b) {
					Map map = new HashMap<>();
					map.put("unitInd", true);
					map.put("message", "unit Can't be created same name already exist !!!");
					return ResponseEntity.ok(map);
				}
			}
			UnitMapper unitId = categoryService.saveUnit(unitMapper);
			return ResponseEntity.ok(unitId);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/unit/all/{orgId}")
	public ResponseEntity<?> getAllUnitType(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<UnitMapper> unitList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			unitList = categoryService.getUnitListByOrgId(orgId);

			if (null != unitList && !unitList.isEmpty()) {
				Collections.sort(unitList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(unitList, HttpStatus.OK);
			} else {
				Map map = new HashMap();
				map.put("message", "Data Not Available!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/unit")
	public ResponseEntity<?> updateUnit(@RequestBody UnitMapper unitMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			unitMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			unitMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(unitMapper.getUnitName())) {
				boolean b = categoryService.checkUnitNameExist(unitMapper.getUnitName());
				if (b) {
					Map map = new HashMap<>();
					map.put("unitInd", true);
					map.put("message", "unit Can't be updated same name already exist !!!");
					return ResponseEntity.ok(map);
				}
			}
			UnitMapper unitMapperr = categoryService.updateUnit(unitMapper.getUnitId(), unitMapper);

			return new ResponseEntity<UnitMapper>(unitMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/unitDetails/{unitId}")
	public ResponseEntity<?> getUnitDetailsById(@PathVariable("unitId") String unitId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			UnitMapper unitMapper = categoryService.getUnitDetailsById(unitId);

			return new ResponseEntity<UnitMapper>(unitMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/search/unitDetails/{name}")
	public ResponseEntity<?> getUnitDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<UnitMapper> unitMapper = categoryService.getUnitDetailsByName(name);

			return new ResponseEntity<List<UnitMapper>>(unitMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/unit/{unitId}")
	public ResponseEntity<?> deleteUnitDetails(@PathVariable("unitId") String unitId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			categoryService.deleteUnitById(unitId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/category/task/checklist/save")
	public String createTaskChecklist(@RequestBody TaskChecklistMapper taskChecklistMapper,
			@RequestHeader("Authorization") String authorization) {

		String taskChecklistId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			taskChecklistMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskChecklistMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			taskChecklistId = categoryService.saveTaskChecklist(taskChecklistMapper);

		}

		return taskChecklistId;
	}

	@GetMapping("/api/v1/category/task/checklist/all/{orgId}")
	public ResponseEntity<?> getAllTaskChecklist(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskChecklistMapper> taskChecklistMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskChecklistMapper = categoryService.getAllTaskChecklistByOrgId(orgId);

			if (null != taskChecklistMapper && !taskChecklistMapper.isEmpty()) {
				// Collections.sort(taskChecklistMapper, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(taskChecklistMapper, HttpStatus.OK);
			} else {
				Map map = new HashMap();
				map.put("message", "Data Not Available!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/category/task/checklist/update")
	public ResponseEntity<?> updateTaskChecklist(@RequestBody TaskChecklistMapper taskChecklistMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskChecklistMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskChecklistMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			TaskChecklistMapper taskChecklistMapperr = categoryService
					.updateTaskChecklist(taskChecklistMapper.getTaskChecklistId(), taskChecklistMapper);

			return new ResponseEntity<TaskChecklistMapper>(taskChecklistMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/category/task/checklist/{taskChecklistId}")
	public ResponseEntity<?> getTaskChecklistDetailsById(@PathVariable("taskChecklistId") String taskChecklistId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			TaskChecklistMapper taskChecklistMapper = categoryService.getTaskChecklistDetailsById(taskChecklistId);

			return new ResponseEntity<TaskChecklistMapper>(taskChecklistMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/category/task/checklist/delete/{taskChecklistId}")
	public ResponseEntity<?> deleteTaskChecklist(@PathVariable("taskChecklistId") String taskChecklistId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			categoryService.deleteTaskChecklist(taskChecklistId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/category/task/checklist/stage/save")
	public String createTaskChecklistStageLink(@RequestBody TaskChecklistStageLinkMapper taskChecklistStageLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		String taskChecklistId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			taskChecklistStageLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskChecklistStageLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			taskChecklistId = categoryService.saveTaskChecklistStageLink(taskChecklistStageLinkMapper);

		}

		return taskChecklistId;
	}

	@GetMapping("/api/v1/category/task/checklist/stage/all/{orgId}")
	public ResponseEntity<?> getAllTaskChecklistStageLink(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskChecklistStageLinkMapper> taskChecklistStageLinkMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskChecklistStageLinkMapper = categoryService.getAllTaskChecklistStageLinkByOrgId(orgId);

			if (null != taskChecklistStageLinkMapper && !taskChecklistStageLinkMapper.isEmpty()) {
				// Collections.sort(taskChecklistMapper, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(taskChecklistStageLinkMapper, HttpStatus.OK);
			} else {
				Map map = new HashMap();
				map.put("message", "Data Not Available!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/category/task/checklist/stage/update")
	public ResponseEntity<?> updateTaskChecklistStageLink(
			@RequestBody TaskChecklistStageLinkMapper taskChecklistStageLinkMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskChecklistStageLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskChecklistStageLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			TaskChecklistStageLinkMapper taskChecklistStageLinkMapperr = categoryService.updateTaskChecklistStageLink(
					taskChecklistStageLinkMapper.getTaskChecklistStagelinkId(), taskChecklistStageLinkMapper);

			return new ResponseEntity<TaskChecklistStageLinkMapper>(taskChecklistStageLinkMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/category/task/checklist/stage/{taskChecklistStagelinkId}")
	public ResponseEntity<?> getTaskChecklistStageLinkDetailsById(
			@PathVariable("taskChecklistStagelinkId") String taskChecklistStagelinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			TaskChecklistStageLinkMapper taskChecklistStageLinkMapper = categoryService
					.getTaskChecklistStageLinkDetailsById(taskChecklistStagelinkId);

			return new ResponseEntity<TaskChecklistStageLinkMapper>(taskChecklistStageLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/category/task/checklist/stage/delete/{taskChecklistStagelinkId}")
	public ResponseEntity<?> deleteTaskChecklistStageLink(
			@PathVariable("taskChecklistStagelinkId") String taskChecklistStagelinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			categoryService.deleteTaskChecklistStageLink(taskChecklistStagelinkId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/category/task/checklist/stage/link/all/{taskChecklistId}")
	public ResponseEntity<?> getAllTaskChecklistStageLinkByTaskChecklistId(
			@PathVariable("taskChecklistId") String taskChecklistId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskChecklistStageLinkMapper> taskChecklistStageLinkMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskChecklistStageLinkMapper = categoryService
					.getAllTaskChecklistStageLinkByTaskChecklistId(taskChecklistId);

			if (null != taskChecklistStageLinkMapper && !taskChecklistStageLinkMapper.isEmpty()) {
				Collections.sort(taskChecklistStageLinkMapper,
						(m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(taskChecklistStageLinkMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(taskChecklistStageLinkMapper, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/roleType/department/{departmentId}")
	public ResponseEntity<?> getAllRoleTypeByDepartmentId(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<RoleMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			typeList = categoryService.getAllRoleTypeByDepartmentId(departmentId);

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

	@GetMapping("/api/v1/category/task/allChecklist/{taskTypeId}")
	public ResponseEntity<?> getAllTaskChecklistByTaskType(@PathVariable("taskTypeId") String taskTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskChecklistMapper> taskChecklistMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskChecklistMapper = categoryService.getAllTaskChecklistByTaskType(taskTypeId);

			if (null != taskChecklistMapper && !taskChecklistMapper.isEmpty()) {
				// Collections.sort(taskChecklistMapper, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(taskChecklistMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(taskChecklistMapper, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/skillLevel")
	public ResponseEntity<?> saveSkillLevel(@RequestBody SkillLevelLinkMapper skillLevelLinkMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(categoryService.saveSkillLevel(skillLevelLinkMapper));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/skillLevel/{countryId}/{orgId}")
	public ResponseEntity<List<SkillLevelLinkMapper>> getSkillLevelByCountryId(
			@PathVariable("countryId") String countryId, @PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(categoryService.getSkillLevelByCountry(countryId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/skillLevel")
	public ResponseEntity<List<SkillLevelLinkMapper>> getSkillLevel(
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(categoryService.getSkillLevel());
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping(value = "/api/v1/category/ticketsType/save")
	public ResponseEntity<?> CreateTicketType(@RequestBody TicketTypeRequestMapper ticketRequest,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		String LibraryTypeId = null;
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (!StringUtils.isEmpty(ticketRequest.getTicketType())) {
				boolean b = categoryService.TicketsTypeNameInTicketsType(ticketRequest.getTicketType());
				if (b == true) {
					map.put("ticketInd", b);
					map.put("message", "Ticket can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			TicketTypeResponseMapper response = categoryService.CreateTicketType(ticketRequest);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/ticketsType/{ticketsTypeId}")
	public ResponseEntity<?> getByTicketTypeId(@PathVariable("ticketsTypeId") String ticketsTypeId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(categoryService.getByTicketTypeId(ticketsTypeId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/ticketsType/get-all/{orgId}")
	public ResponseEntity<?> getAllTicketsTypelist(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TicketTypeResponseMapper> ticketResponseMapper = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			ticketResponseMapper = categoryService.getAllTicketsTypelist(orgId);
			if (null != ticketResponseMapper && !ticketResponseMapper.isEmpty()) {
				// Collections.sort(ticketResponseMapper, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
			} else {
				Map map = new HashMap();
				map.put("message", "Data Not Available!!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/category/ticketsType/update")
	public ResponseEntity<?> updateTicketsType(@RequestBody TicketTypeRequestMapper ticketRequestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			ticketRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// ticketRequestMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			TicketTypeResponseMapper ticketResponseMapper = categoryService.updateTicketsType(ticketRequestMapper);
			return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/category/ticketsType/delete/{ticketsTypeId}")

	public ResponseEntity<?> deleteTicketsType(@PathVariable("ticketsTypeId") String ticketsTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			categoryService.deleteTicketsType(ticketsTypeId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/ticketsType/search/{name}")
	public ResponseEntity<?> getTicketsTypeByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TicketTypeResponseMapper> ticketResponseMapper = categoryService.getTicketsTypeByName(name);
			if (null != ticketResponseMapper && !ticketResponseMapper.isEmpty()) {
//					Collections.sort(ticketResponseMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(ticketResponseMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/category/module/save")
	public ResponseEntity<?> updateModule(@RequestBody ModuleRequestMapper moduleRequestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			moduleRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			moduleRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ModuleResponseMapper id = categoryService.updateModule(moduleRequestMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/module/{orgId}")
	public ResponseEntity<?> getModuleByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			ModuleResponseMapper list = categoryService.getModuleByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/category/setAdminSecreteKey")
	public ResponseEntity<String> setAdminSecreteKey(@RequestBody AdminSecreteKeyMapper adminSecreteKeyMapper,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			adminSecreteKeyMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			adminSecreteKeyMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String b = categoryService.setAdminSecreteKey(adminSecreteKeyMapper);
			return new ResponseEntity<>(b, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/adminSecreteKey/{userId}")
	public ResponseEntity<?> getAdminSecreteKeyByOrgId(@PathVariable("userId") String userId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			AdminSecreteKeyMapper list = categoryService.getAdminSecreteKeyByOrgId(userId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/minimumActivity")
	public ResponseEntity<?> getMinimumActivity(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			MinimumActivityRespMapper mapper = categoryService.getMinimumActivity(orgId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/category/minimumActivity/save")
	public ResponseEntity<?> createMinimumActivity(@RequestBody MinimumActivityReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			MinimumActivityRespMapper responseMapper = categoryService.createMinimumActivity(requestMapper);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/roleType/count/{orgId}")
	public ResponseEntity<?> getRoleTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(categoryService.getRoleTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/libreryType/count/{orgId}")
	public ResponseEntity<?> getLibreryTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(categoryService.getLibreryTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/category/save")
	public ResponseEntity<?> createCategory(@RequestBody CategoryMapper categoryMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			categoryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			categoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(categoryMapper.getName())) {
				boolean b = categoryService.checkNameInCategory(categoryMapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("CategoryInd", b);
					map.put("message", "Category can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					CategoryMapper paymentId = categoryService.saveCategory(categoryMapper);
					return new ResponseEntity<>(paymentId, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a category !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/all/{orgId}")
	public ResponseEntity<?> getCategoryMapperByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CategoryMapper> categoryMapper = categoryService.getCategoryMapperByOrgId(orgId);

			return new ResponseEntity<>(categoryMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/category/update/{categoryId}")

	public ResponseEntity<?> updateCategory(@PathVariable("categoryId") String categoryId,
			@RequestBody CategoryMapper categoryMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			categoryMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			categoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(categoryMapper.getName())) {
				boolean b = categoryService.checkNameInCategory(categoryMapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("CategoryInd", b);
					map.put("message", "Category can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<CategoryMapper>(categoryService.updateCategory(categoryId, categoryMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/api/v1/category/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") String categoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			categoryService.deleteCategory(categoryId, userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/search/{name}")
	public ResponseEntity<?> getCategoryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CategoryMapper> mapper = categoryService.getCategoryByNameByOrgLevel(name, orgId);
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/category/count/{orgId}")
	public ResponseEntity<?> getCategoryCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(categoryService.getCategoryCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/category/invoice-category/save")
	public ResponseEntity<?> updateInvoiceCategory(@RequestBody InvoiceCategoryMapper invoiceCategoryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			invoiceCategoryMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvoiceCategoryMapper id = categoryService.updateInvoiceCategory(invoiceCategoryMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/category/invoice-category/{orgId}")
	public ResponseEntity<?> getInvoiceCategoryByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			InvoiceCategoryMapper list = categoryService.getInvoiceCategoryByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
