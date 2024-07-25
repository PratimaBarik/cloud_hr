package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.app.employeePortal.category.entity.*;
import com.app.employeePortal.category.entity.Module;
import com.app.employeePortal.category.mapper.*;
import com.app.employeePortal.category.repository.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	MinimumActivityRepo minimumActivityRepo;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	LibraryTypeRepository libraryTypeRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	RoleTypeDeleteRepository roleTypeDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	UnitRepository unitRepository;
	@Autowired
	TaskChecklistRepository taskChecklistRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	TaskChecklistStageLinkRepository taskChecklistStageLinkRepository;
	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	SkillLevelLinkRepository skillLevelLinkRepository;
	@Autowired
	TicketsTypeRepository ticketsTypeRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	DefinationRepository definationRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	AdminSecreteKeyReypository adminSecreteKeyReypository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	private String[] headings = { "Name" };
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	RatingTypeRepository ratingTypeRepository;
	@Autowired
	InvoiceCategoryRepository invoiceCategoryRepository;

	@Override
	public RoleMapper CreateRoleType(RoleMapper roleMapper) {
		String roleTypeId = null;
		if (roleMapper != null) {
			RoleType roleType = new RoleType();
			roleType.setRoleType(roleMapper.getRoleType());
			roleType.setUserId(roleMapper.getUserId());
			roleType.setOrgId(roleMapper.getOrganizationId());
			roleType.setCreationDate(new Date());
			roleType.setLiveInd(true);
			roleType.setEditInd(roleMapper.isEditInd());
			System.out.println("Department id :" + roleMapper.getDepartment());
			if (!StringUtils.isEmpty(roleMapper.getDepartment())) {
				Department department = departmentRepository.getDepartmentDetailsById(roleMapper.getDepartment());
				if (null != department) {
					roleType.setDepartmentId(roleMapper.getDepartment());
					// dbDepartment.setSector_id(departmentMapper.getSectorId());
				}
			}
			roleTypeId = roleTypeRepository.save(roleType).getRoleTypeId();

			RoleTypeDelete roleTypeDelete = new RoleTypeDelete();
			roleTypeDelete.setRoleTypeId(roleTypeId);
			roleTypeDelete.setUserId(roleMapper.getUserId());
			roleTypeDelete.setOrgId(roleMapper.getOrganizationId());
			roleTypeDelete.setUpdationDate(new Date());
			roleTypeDelete.setUpdatedBy(roleMapper.getUserId());
			roleTypeDeleteRepository.save(roleTypeDelete);

		}
		RoleMapper resultMapper = getRoleTypeById(roleTypeId);
		return resultMapper;
	}

	@Override
	public RoleMapper updateRoleType(String roleTypeId, RoleMapper roleMapper) {
		RoleMapper resultMapper = null;

		if (null != roleTypeId) {
			RoleType dbRoleType = roleTypeRepository.findByRoleTypeId(roleTypeId);

			if (null != dbRoleType.getRoleTypeId()) {
				dbRoleType.setRoleType(roleMapper.getRoleType());
				dbRoleType.setEditInd(roleMapper.isEditInd());
				dbRoleType.setDepartmentId(roleMapper.getDepartmentId());
				roleTypeRepository.save(dbRoleType);

				RoleTypeDelete roleTypeDelete = roleTypeDeleteRepository.findByRoleTypeId(roleTypeId);
				if (null != roleTypeDelete) {
					roleTypeDelete.setUpdationDate(new Date());
					roleTypeDelete.setUpdatedBy(roleMapper.getUserId());
					roleTypeDeleteRepository.save(roleTypeDelete);
				} else {
					RoleTypeDelete roleTypeDelete1 = new RoleTypeDelete();
					roleTypeDelete1.setRoleTypeId(roleTypeId);
					roleTypeDelete1.setUserId(roleMapper.getUserId());
					roleTypeDelete1.setOrgId(roleMapper.getOrganizationId());
					roleTypeDelete1.setUpdationDate(new Date());
					roleTypeDelete1.setUpdatedBy(roleMapper.getUserId());
					roleTypeDeleteRepository.save(roleTypeDelete1);
				}
			}
			resultMapper = getRoleTypeById(roleTypeId);
		}
		return resultMapper;
	}

	public RoleMapper getRoleTypeById(String roleTypeId) {
		RoleType role = roleTypeRepository.findByRoleTypeId(roleTypeId);
		RoleMapper roleMapper = new RoleMapper();

		if (null != role) {
			roleMapper.setRoleTypeId(role.getRoleTypeId());

			roleMapper.setRoleType(role.getRoleType());
			roleMapper.setOrganizationId(role.getOrgId());
			roleMapper.setUserId(role.getUserId());
			roleMapper.setEditInd(role.isEditInd());
			roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));
			if (!StringUtils.isEmpty(role.getDepartmentId())) {
				Department department = departmentRepository.getDepartmentDetails(role.getDepartmentId());
				if (null != department) {
					roleMapper.setDepartmentId(department.getDepartment_id());
					roleMapper.setDepartment(department.getDepartmentName());
				}
			}
			List<RoleTypeDelete> list = roleTypeDeleteRepository.findByOrgId(role.getOrgId());
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

				roleMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				roleMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return roleMapper;
	}

	@Override
	public List<RoleMapper> getRoleListByOrgId(String orgId) {
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		List<RoleType> roleList = roleTypeRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != roleList && !roleList.isEmpty()) {
			roleList.stream().map(role -> {

				RoleMapper roleMapper = new RoleMapper();
				roleMapper.setRoleTypeId(role.getRoleTypeId());
				roleMapper.setRoleType(role.getRoleType());
				roleMapper.setEditInd(role.isEditInd());

				roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));

				if (!StringUtils.isEmpty(role.getDepartmentId())) {
					Department department = departmentRepository.getDepartmentDetails(role.getDepartmentId());
					if (null != department) {
						roleMapper.setDepartmentId(department.getDepartment_id());
						roleMapper.setDepartment(department.getDepartmentName());

					}
				}

				resultList.add(roleMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<RoleTypeDelete> roleTypeDelete = roleTypeDeleteRepository.findByOrgId(orgId);
		if (null != roleTypeDelete && !roleTypeDelete.isEmpty()) {
			Collections.sort(roleTypeDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(roleTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(roleTypeDelete.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}

		return resultList;
	}

	@Override
	public RoleMapper CreateLibraryType(RoleMapper roleMapper) {
		String libraryTypeId = null;
		if (roleMapper != null) {
			LibraryType libraryType = new LibraryType();
			libraryType.setLibraryType(roleMapper.getLibraryType());
			libraryType.setUserId(roleMapper.getUserId());
			libraryType.setOrgId(roleMapper.getOrganizationId());
			libraryTypeId = libraryTypeRepository.save(libraryType).getId();

		}
		RoleMapper resultMapper = getLibraryTypeById(libraryTypeId);
		return resultMapper;
	}

	@Override
	public RoleMapper updateLibraryType(String libraryTypeId, RoleMapper roleMapper) {
		RoleMapper resultMapper = null;

		if (null != libraryTypeId) {
			LibraryType dbLibraryType = libraryTypeRepository.getById(libraryTypeId);

			if (null != dbLibraryType.getId()) {
				dbLibraryType.setLibraryType(roleMapper.getLibraryType());
				libraryTypeRepository.save(dbLibraryType);

			}
			resultMapper = getLibraryTypeById(libraryTypeId);
		}
		return resultMapper;
	}

	public RoleMapper getLibraryTypeById(String libraryTypeId) {
		LibraryType dbLibraryType = libraryTypeRepository.getById(libraryTypeId);
		RoleMapper roleMapper = new RoleMapper();

		if (null != dbLibraryType) {
			roleMapper.setLibraryTypeId(dbLibraryType.getId());

			roleMapper.setLibraryType(dbLibraryType.getLibraryType());
			roleMapper.setOrganizationId(dbLibraryType.getOrgId());
			roleMapper.setUserId(dbLibraryType.getUserId());

		}
		return roleMapper;

	}

	@Override
	public List<RoleMapper> getAllLibraryType(String orgId) {
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		List<LibraryType> libraryList = libraryTypeRepository.findByOrgId(orgId);

		if (null != libraryList && !libraryList.isEmpty()) {
			libraryList.stream().map(library -> {
				RoleMapper roleMapper = new RoleMapper();
				roleMapper.setLibraryTypeId(library.getId());
				roleMapper.setLibraryType(library.getLibraryType());

				resultList.add(roleMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public RoleMapper getAllLibrarySkills() {
		RoleMapper resultList = new RoleMapper();
		List<LibraryType> libraryList = libraryTypeRepository.findAll();

		resultList.setSkillList((libraryList.stream().map(li -> li.getLibraryType()).collect(Collectors.toList())));
		// List<String> sortedList =
		// resultList.getSkillList().stream().sorted().collect(Collectors.toList());
		return resultList;
	}

	@Override
	public boolean ipAddressExists(String url) {
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			System.out.println("web>:>:::>::::::>>" + web.toString());
			return true;
		}
		return false;
	}

	@Override
	public List<RoleMapper> getAllRoleTypeToWebsite(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		List<RoleType> roleList = roleTypeRepository.findByorgId(web.getOrgId());

		if (null != roleList && !roleList.isEmpty()) {
			roleList.stream().map(role -> {

				RoleMapper roleMapper = new RoleMapper();
				roleMapper.setRoleTypeId(role.getRoleTypeId());
				roleMapper.setRoleType(role.getRoleType());
				roleMapper.setEditInd(role.isEditInd());

				roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));

				if (!StringUtils.isEmpty(role.getDepartmentId())) {
					Department department = departmentRepository.getDepartmentDetails(role.getDepartmentId());
					if (null != department) {
						roleMapper.setDepartmentId(department.getDepartment_id());
						roleMapper.setDepartment(department.getDepartmentName());

					}
				}

				resultList.add(roleMapper);
				return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<RoleMapper> getRoleDetailsByNameByOrgLevel(String name, String orgId) {

		List<RoleType> list = roleTypeRepository.findByRoleTypeContainingAndOrgId(name, orgId);
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(roleType -> {
				RoleMapper roleMapper = getroleDetailsById(roleType.getRoleTypeId());
				if (null != roleMapper) {
					resultList.add(roleMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;

	}

	private RoleMapper getroleDetailsById(String roleTypeId) {
		RoleType role = roleTypeRepository.findByRoleTypeId(roleTypeId);

		RoleMapper roleMapper = new RoleMapper();
		roleMapper.setRoleTypeId(role.getRoleTypeId());
		roleMapper.setRoleType(role.getRoleType());
		roleMapper.setEditInd(role.isEditInd());

		roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));

		if (!StringUtils.isEmpty(role.getDepartmentId())) {
			Department department = departmentRepository.getDepartmentDetails(role.getDepartmentId());
			if (null != department) {
				roleMapper.setDepartmentId(department.getDepartment_id());
				roleMapper.setDepartment(department.getDepartmentName());
			}

		}
		return roleMapper;

	}

	@Override
	public List<RoleMapper> getLibraryDetailsByNameByOrgLevel(String name, String orgId) {
		List<LibraryType> list = libraryTypeRepository.findByLibraryTypeContainingAndOrgId(name, orgId);
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(libraryType -> {
				System.out.println("libraryDetailsByName=========" + libraryType.getId());
				RoleMapper roleMapper = getLibraryTypeById(libraryType.getId());
				if (null != roleMapper) {
					resultList.add(roleMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public boolean checkRoleNameInRoleTypeByorgLevel(String roleType, String departmentId, String orgId) {
		List<RoleType> roleTypes = roleTypeRepository.findByRoleTypeAndLiveIndAndDepartmentIdAndOrgId(roleType, true,
				departmentId, orgId);
		if (roleTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLibraryNameInLibraryTypeInOrgLevel(String libraryType, String orgId) {
		List<LibraryType> libraryTypes = libraryTypeRepository.findByLibraryTypeAndOrgId(libraryType, orgId);
		if (libraryTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteRoleTypeDetailsById(String roleTypeId) {

		if (null != roleTypeId) {
			RoleType roleTypes = roleTypeRepository.findByRoleTypeId(roleTypeId);

			RoleTypeDelete roleTypeDelete = roleTypeDeleteRepository.findByRoleTypeId(roleTypeId);
			if (null != roleTypeDelete) {
				roleTypeDelete.setUpdationDate(new Date());
				roleTypeDelete.setUpdatedBy(roleTypes.getUserId());
				roleTypeDeleteRepository.save(roleTypeDelete);
			}
			roleTypes.setLiveInd(false);
			roleTypeRepository.save(roleTypes);
		}

	}

	@Override
	public UnitMapper saveUnit(UnitMapper unitMapper) {
		String unitId = null;
		if (unitMapper != null) {
			Unit unit = new Unit();
			unit.setUnitName(unitMapper.getUnitName());
			unit.setUserId(unitMapper.getUserId());
			unit.setOrgId(unitMapper.getOrgId());
			unit.setCreationDate(new Date());
			unit.setLiveInd(true);
			unit.setEditInd(true);
			unit.setUpdatedBy(unitMapper.getUserId());
			unit.setUpdationDate(new Date());
			unitId = unitRepository.save(unit).getUnitId();
		}
		UnitMapper resultMapper = getUnitDetailsById(unitId);
		return resultMapper;
	}

	@Override
	public List<UnitMapper> getUnitListByOrgId(String orgId) {
		List<UnitMapper> resultList = new ArrayList<UnitMapper>();
		List<Unit> unitList = unitRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != unitList && !unitList.isEmpty()) {
			unitList.stream().map(unit -> {
				UnitMapper unitMapper = new UnitMapper();
				unitMapper.setUnitId(unit.getUnitId());
				unitMapper.setCreationDate(Utility.getISOFromDate(unit.getCreationDate()));
				unitMapper.setUnitName(unit.getUnitName());
				unitMapper.setEditInd(unit.isEditInd());
				unitMapper.setLiveInd(unit.isLiveInd());
				unitMapper.setOrgId(unit.getOrgId());
				unitMapper.setUserId(unit.getUserId());
				resultList.add(unitMapper);

				return resultList;
			}).collect(Collectors.toList());
			Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			List<Unit> list = unitRepository.findByOrgId(orgId);
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

				resultList.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				resultList.get(0).setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return resultList;
	}

	@Override
	public UnitMapper updateUnit(String unitId, UnitMapper unitMapper) {
		UnitMapper resultMapper = null;

		if (null != unitId) {
			Unit unit = unitRepository.getById(unitId);

			if (null != unit.getUnitId()) {
				// unit.setCreationDate(new Date());
				// unit.setOrgId(unitMapper.getOrgId());
				unit.setEditInd(unitMapper.isEditInd());
				// unit.setLiveInd(unitMapper.isLiveInd());
				unit.setUnitName(unitMapper.getUnitName());
				// unit.setUserId(unitMapper.getUserId());
				unit.setUpdatedBy(unitMapper.getUserId());
				unit.setUpdationDate(new Date());
				unitRepository.save(unit);

			}
			resultMapper = getUnitDetailsById(unitId);

		}
		return resultMapper;
	}

	public UnitMapper getUnitDetailsById(String unitId) {
		Unit unit = unitRepository.getById(unitId);
		UnitMapper resultMapper = new UnitMapper();

		if (null != unit) {
			resultMapper.setCreationDate(Utility.getISOFromDate(unit.getCreationDate()));
			resultMapper.setEditInd(unit.isEditInd());
			resultMapper.setLiveInd(unit.isLiveInd());
			resultMapper.setOrgId(unit.getOrgId());
			resultMapper.setUnitName(unit.getUnitName());
			resultMapper.setUserId(unit.getUserId());
			resultMapper.setUnitId(unit.getUnitId());

			List<Unit> list = unitRepository.findByOrgId(unit.getOrgId());
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

				resultMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				resultMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return resultMapper;
	}

	@Override
	public void deleteUnitById(String unitId) {
		if (null != unitId) {
			Unit unit = unitRepository.findByUnitId(unitId);
			if (null != unit) {
				unit.setUpdatedBy(unit.getUserId());
				unit.setUpdationDate(new Date());
				unit.setLiveInd(false);
				unitRepository.save(unit);
			}
		}

	}

	@Override
	public String saveTaskChecklist(TaskChecklistMapper taskChecklistMapper) {
		String taskChecklistId = null;
		if (taskChecklistMapper != null) {
			TaskType type = taskTypeRepository.findById(taskChecklistMapper.getTaskTypeId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"TaskTypeId not found " + taskChecklistMapper.getTaskTypeId()));

			TaskChecklist taskChecklist = new TaskChecklist();

			taskChecklist.setTaskChecklistName(taskChecklistMapper.getTaskChecklistName());
			taskChecklist.setUserId(taskChecklistMapper.getUserId());
			taskChecklist.setOrgId(taskChecklistMapper.getOrgId());
			taskChecklist.setCreationDate(new Date());
			taskChecklist.setLastUpdateOn(new Date());
			taskChecklist.setLiveInd(true);
			taskChecklist.setLastUpdateById(taskChecklistMapper.getUserId());
			taskChecklist.setTaskType(type);
			taskChecklistId = taskChecklistRepository.save(taskChecklist).getTaskChecklistId();

		}
		return taskChecklistId;

	}

	@Override
	public List<TaskChecklistMapper> getAllTaskChecklistByOrgId(String orgId) {
		List<TaskChecklistMapper> resultList = new ArrayList<TaskChecklistMapper>();
		List<TaskChecklist> taskChecklists = taskChecklistRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != taskChecklists && !taskChecklists.isEmpty()) {
			taskChecklists.stream().map(taskChecklist -> {
				TaskChecklistMapper taskChecklistMapper = new TaskChecklistMapper();

				taskChecklistMapper.setTaskChecklistId(taskChecklist.getTaskChecklistId());
				taskChecklistMapper.setCreationDate(Utility.getISOFromDate(taskChecklist.getCreationDate()));
				taskChecklistMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
				// taskChecklistMapper.setLastUpdateOn(Utility.getISOFromDate(taskChecklist.getLastUpdateOn()));
				taskChecklistMapper.setLiveInd(taskChecklist.isLiveInd());
				taskChecklistMapper.setOrgId(taskChecklist.getOrgId());
				taskChecklistMapper.setUserId(taskChecklist.getUserId());
				taskChecklistMapper.setTaskTypeId(taskChecklist.getTaskType().getTaskTypeId());
				// taskChecklistMapper.setLastUpdateByName(employeeService.getEmployeeFullName(taskChecklist.getUserId()));

				resultList.add(taskChecklistMapper);

				return resultList;
			}).collect(Collectors.toList());

			Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			List<TaskChecklist> taskChecklists1 = taskChecklistRepository.findByOrgId(orgId);
			if (null != taskChecklists1 && !taskChecklists1.isEmpty()) {
				Collections.sort(taskChecklists1, (p1, p2) -> p2.getLastUpdateOn().compareTo(p1.getLastUpdateOn()));

				resultList.get(0).setLastUpdateOn(Utility.getISOFromDate(taskChecklists1.get(0).getLastUpdateOn()));
				resultList.get(0).setLastUpdateByName(
						employeeService.getEmployeeFullName(taskChecklists1.get(0).getLastUpdateById()));
			}

		}
		return resultList;
	}

	@Override
	public TaskChecklistMapper updateTaskChecklist(String taskChecklistId, TaskChecklistMapper taskChecklistMapper) {
		TaskChecklistMapper resultMapper = null;

		if (null != taskChecklistId) {
			TaskChecklist taskChecklist = taskChecklistRepository.getById(taskChecklistId);

			if (null != taskChecklist.getTaskChecklistId()) {

				taskChecklist.setTaskChecklistName(taskChecklistMapper.getTaskChecklistName());
				taskChecklist.setLastUpdateById(taskChecklistMapper.getUserId());
				taskChecklist.setLastUpdateOn(new Date());

				taskChecklistRepository.save(taskChecklist);

			}
			resultMapper = getTaskChecklistDetailsById(taskChecklistId);
		}
		return resultMapper;
	}

	@Override
	public TaskChecklistMapper getTaskChecklistDetailsById(String taskChecklistId) {
		TaskChecklist taskChecklist = taskChecklistRepository.getById(taskChecklistId);
		TaskChecklistMapper taskChecklistMapper = new TaskChecklistMapper();

		if (null != taskChecklist) {
			taskChecklistMapper.setTaskChecklistId(taskChecklist.getTaskChecklistId());
			taskChecklistMapper.setCreationDate(Utility.getISOFromDate(taskChecklist.getCreationDate()));
			taskChecklistMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
			// taskChecklistMapper.setLastUpdateOn(Utility.getISOFromDate(taskChecklist.getLastUpdateOn()));
			taskChecklistMapper.setLiveInd(taskChecklist.isLiveInd());
			taskChecklistMapper.setOrgId(taskChecklist.getOrgId());
			taskChecklistMapper.setUserId(taskChecklist.getUserId());
			// taskChecklistMapper.setLastUpdateByName(employeeService.getEmployeeFullName(taskChecklist.getUserId()));
		}
		return taskChecklistMapper;
	}

	@Override
	public void deleteTaskChecklist(String taskChecklistId, String userId) {
		if (null != taskChecklistId) {
			TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskChecklistId);
			if (null != taskChecklist) {
				taskChecklist.setLiveInd(false);
				taskChecklist.setLastUpdateById(userId);
				taskChecklist.setLastUpdateOn(new Date());
				taskChecklistRepository.save(taskChecklist);
			}

			List<TaskChecklistStageLink> taskChecklistStageLinks = taskChecklistStageLinkRepository
					.findByTaskChecklistIdAndLiveInd(taskChecklistId, true);
			if (null != taskChecklistStageLinks && !taskChecklistStageLinks.isEmpty()) {
				taskChecklistStageLinks.stream().map(taskChecklistStageLink -> {

					taskChecklistStageLink.setLiveInd(false);
					taskChecklistStageLink.setLastUpdateById(userId);
					taskChecklistStageLink.setLastUpdateOn(new Date());
					taskChecklistStageLinkRepository.save(taskChecklistStageLink);

					return null;
				}).collect(Collectors.toList());
			}

		}

	}

	@Override
	public String saveTaskChecklistStageLink(TaskChecklistStageLinkMapper taskChecklistStageLinkMapper) {
		String taskChecklistStagelinkId = null;
		if (taskChecklistStageLinkMapper != null) {
			TaskChecklistStageLink taskChecklistStageLink = new TaskChecklistStageLink();

			taskChecklistStageLink.setTaskChecklistStageName(taskChecklistStageLinkMapper.getTaskChecklistStageName());
			taskChecklistStageLink.setUserId(taskChecklistStageLinkMapper.getUserId());
			taskChecklistStageLink.setOrgId(taskChecklistStageLinkMapper.getOrgId());
			taskChecklistStageLink.setCreationDate(new Date());
			taskChecklistStageLink.setLastUpdateOn(new Date());
			taskChecklistStageLink.setLiveInd(true);
			taskChecklistStageLink.setLastUpdateById(taskChecklistStageLinkMapper.getUserId());
			taskChecklistStageLink.setProbability(taskChecklistStageLinkMapper.getProbability());
			taskChecklistStageLink.setDays(taskChecklistStageLinkMapper.getDays());

			taskChecklistStageLink.setTaskChecklistId(taskChecklistStageLinkMapper.getTaskChecklistId());

			taskChecklistStagelinkId = taskChecklistStageLinkRepository.save(taskChecklistStageLink)
					.getTaskChecklistStagelinkId();
		}
		return taskChecklistStagelinkId;
	}

	@Override
	public List<TaskChecklistStageLinkMapper> getAllTaskChecklistStageLinkByOrgId(String orgId) {
		List<TaskChecklistStageLinkMapper> resultList = new ArrayList<TaskChecklistStageLinkMapper>();
		List<TaskChecklistStageLink> taskChecklistStageLinks = taskChecklistStageLinkRepository
				.findByOrgIdAndLiveInd(orgId, true);

		if (null != taskChecklistStageLinks && !taskChecklistStageLinks.isEmpty()) {
			taskChecklistStageLinks.stream().map(taskChecklistStageLink -> {
				TaskChecklistStageLinkMapper taskChecklistStageLinkMapper = new TaskChecklistStageLinkMapper();

				taskChecklistStageLinkMapper
						.setCreationDate(Utility.getISOFromDate(taskChecklistStageLink.getCreationDate()));
				taskChecklistStageLinkMapper
						.setTaskChecklistStagelinkId(taskChecklistStageLink.getTaskChecklistStagelinkId());
				taskChecklistStageLinkMapper
						.setTaskChecklistStageName(taskChecklistStageLink.getTaskChecklistStageName());

				taskChecklistStageLinkMapper.setLiveInd(taskChecklistStageLink.isLiveInd());
				taskChecklistStageLinkMapper.setOrgId(taskChecklistStageLink.getOrgId());
				taskChecklistStageLinkMapper.setUserId(taskChecklistStageLink.getUserId());

				taskChecklistStageLinkMapper.setTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
				TaskChecklist taskChecklist = taskChecklistRepository
						.findByTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
				if (null != taskChecklist) {
					taskChecklistStageLinkMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
				}

				resultList.add(taskChecklistStageLinkMapper);

				return resultList;
			}).collect(Collectors.toList());

			Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			List<TaskChecklistStageLink> taskChecklists1 = taskChecklistStageLinkRepository.findByOrgId(orgId);
			if (null != taskChecklists1 && !taskChecklists1.isEmpty()) {
				Collections.sort(taskChecklists1, (p1, p2) -> p2.getLastUpdateOn().compareTo(p1.getLastUpdateOn()));

				resultList.get(0).setLastUpdateOn(Utility.getISOFromDate(taskChecklists1.get(0).getLastUpdateOn()));
				resultList.get(0).setLastUpdateByName(
						employeeService.getEmployeeFullName(taskChecklists1.get(0).getLastUpdateById()));
			}

		}
		return resultList;
	}

	@Override
	public TaskChecklistStageLinkMapper updateTaskChecklistStageLink(String taskChecklistStagelinkId,
			TaskChecklistStageLinkMapper taskChecklistStageLinkMapper) {
		TaskChecklistStageLinkMapper resultMapper = null;

		if (null != taskChecklistStagelinkId) {
			TaskChecklistStageLink taskChecklistStageLink = taskChecklistStageLinkRepository
					.getById(taskChecklistStagelinkId);

			if (null != taskChecklistStageLink.getTaskChecklistId()) {

				taskChecklistStageLink
						.setTaskChecklistStageName(taskChecklistStageLinkMapper.getTaskChecklistStageName());
				taskChecklistStageLink.setProbability(taskChecklistStageLinkMapper.getProbability());
				taskChecklistStageLink.setDays(taskChecklistStageLinkMapper.getDays());
				taskChecklistStageLink.setLastUpdateById(taskChecklistStageLinkMapper.getUserId());
				taskChecklistStageLink.setLastUpdateOn(new Date());

				taskChecklistStageLinkRepository.save(taskChecklistStageLink);

			}
			resultMapper = getTaskChecklistStageLinkDetailsById(taskChecklistStagelinkId);
		}
		return resultMapper;
	}

	@Override
	public TaskChecklistStageLinkMapper getTaskChecklistStageLinkDetailsById(String taskChecklistStagelinkId) {
		TaskChecklistStageLink taskChecklistStageLink = taskChecklistStageLinkRepository
				.getById(taskChecklistStagelinkId);
		TaskChecklistStageLinkMapper taskChecklistStageLinkMapper = new TaskChecklistStageLinkMapper();

		if (null != taskChecklistStageLink) {
			taskChecklistStageLinkMapper
					.setCreationDate(Utility.getISOFromDate(taskChecklistStageLink.getCreationDate()));
			taskChecklistStageLinkMapper
					.setTaskChecklistStagelinkId(taskChecklistStageLink.getTaskChecklistStagelinkId());
			taskChecklistStageLinkMapper.setTaskChecklistStageName(taskChecklistStageLink.getTaskChecklistStageName());

			taskChecklistStageLinkMapper.setLiveInd(taskChecklistStageLink.isLiveInd());
			taskChecklistStageLinkMapper.setOrgId(taskChecklistStageLink.getOrgId());
			taskChecklistStageLinkMapper.setUserId(taskChecklistStageLink.getUserId());
			taskChecklistStageLinkMapper.setDays(taskChecklistStageLink.getDays());
			taskChecklistStageLinkMapper.setProbability(taskChecklistStageLink.getProbability());

			taskChecklistStageLinkMapper.setTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
			TaskChecklist taskChecklist = taskChecklistRepository
					.findByTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
			if (null != taskChecklist) {
				taskChecklistStageLinkMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
			}
		}
		return taskChecklistStageLinkMapper;
	}

	@Override
	public void deleteTaskChecklistStageLink(String taskChecklistStagelinkId, String userId) {
		if (null != taskChecklistStagelinkId) {
			TaskChecklistStageLink taskChecklistStageLink = taskChecklistStageLinkRepository
					.findByTaskChecklistStagelinkId(taskChecklistStagelinkId);
			if (null != taskChecklistStageLink) {
				taskChecklistStageLink.setLiveInd(false);
				taskChecklistStageLink.setLastUpdateById(userId);
				taskChecklistStageLink.setLastUpdateOn(new Date());
				taskChecklistStageLinkRepository.save(taskChecklistStageLink);
			}
		}

	}

	@Override
	public List<TaskChecklistStageLinkMapper> getAllTaskChecklistStageLinkByTaskChecklistId(String taskChecklistId) {
		List<TaskChecklistStageLinkMapper> resultList = new ArrayList<TaskChecklistStageLinkMapper>();
		List<TaskChecklistStageLink> taskChecklistStageLinks = taskChecklistStageLinkRepository
				.findByTaskChecklistIdAndLiveInd(taskChecklistId, true);

		if (null != taskChecklistStageLinks && !taskChecklistStageLinks.isEmpty()) {
			taskChecklistStageLinks.stream().map(taskChecklistStageLink -> {
				TaskChecklistStageLinkMapper taskChecklistStageLinkMapper = new TaskChecklistStageLinkMapper();

				taskChecklistStageLinkMapper
						.setCreationDate(Utility.getISOFromDate(taskChecklistStageLink.getCreationDate()));
				taskChecklistStageLinkMapper
						.setTaskChecklistStagelinkId(taskChecklistStageLink.getTaskChecklistStagelinkId());
				taskChecklistStageLinkMapper
						.setTaskChecklistStageName(taskChecklistStageLink.getTaskChecklistStageName());
				taskChecklistStageLinkMapper.setProbability(taskChecklistStageLink.getProbability());
				taskChecklistStageLinkMapper.setDays(taskChecklistStageLink.getDays());
				taskChecklistStageLinkMapper.setLiveInd(taskChecklistStageLink.isLiveInd());
				taskChecklistStageLinkMapper.setOrgId(taskChecklistStageLink.getOrgId());
				taskChecklistStageLinkMapper.setUserId(taskChecklistStageLink.getUserId());

				taskChecklistStageLinkMapper.setTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
				TaskChecklist taskChecklist = taskChecklistRepository
						.findByTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());
				if (null != taskChecklist) {
					taskChecklistStageLinkMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
				}

				resultList.add(taskChecklistStageLinkMapper);

				return resultList;
			}).collect(Collectors.toList());

			Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			List<TaskChecklistStageLink> taskChecklists1 = taskChecklistStageLinkRepository
					.findByTaskChecklistId(taskChecklistId);
			if (null != taskChecklists1 && !taskChecklists1.isEmpty()) {
				Collections.sort(taskChecklists1, (p1, p2) -> p2.getLastUpdateOn().compareTo(p1.getLastUpdateOn()));

				resultList.get(0).setLastUpdateOn(Utility.getISOFromDate(taskChecklists1.get(0).getLastUpdateOn()));
				resultList.get(0).setLastUpdateByName(
						employeeService.getEmployeeFullName(taskChecklists1.get(0).getLastUpdateById()));
			}

		}
		return resultList;
	}

	@Override
	public List<UnitMapper> getUnitDetailsByName(String name) {
		List<Unit> units = unitRepository.findByUnitNameContainingAndLiveInd(name, true);
		List<UnitMapper> resultMapper = new ArrayList<>();

		if (null != units && !units.isEmpty()) {
			units.stream().map(unitType -> {
				System.out.println("UnitByName=========" + unitType.getUnitId());
				UnitMapper unitMapper = getUnitDetailsById(unitType.getUnitId());
				if (null != unitMapper) {
					resultMapper.add(unitMapper);

				}
				return resultMapper;
			}).collect(Collectors.toList());

		}
		return resultMapper;
	}

	@Override
	public List<RoleMapper> getAllRoleTypeByDepartmentId(String departmentId) {
		List<RoleMapper> resultList = new ArrayList<RoleMapper>();
		List<RoleType> roleList = roleTypeRepository.findByDepartmentIdAndLiveInd(departmentId, true);

		if (null != roleList && !roleList.isEmpty()) {
			roleList.stream().map(role -> {

				RoleMapper roleMapper = new RoleMapper();
				roleMapper.setRoleTypeId(role.getRoleTypeId());
				roleMapper.setRoleType(role.getRoleType());
				roleMapper.setEditInd(role.isEditInd());

				roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));

				if (!StringUtils.isEmpty(role.getDepartmentId())) {
					Department department = departmentRepository.getDepartmentDetails(role.getDepartmentId());
					if (null != department) {
						roleMapper.setDepartmentId(department.getDepartment_id());
						roleMapper.setDepartment(department.getDepartmentName());
						roleMapper.setCrmInd(department.isCrmInd());
						roleMapper.setErpInd(department.isErpInd());
						roleMapper.setImInd(department.isImInd());
						roleMapper.setAccountInd(department.isAccountInd());
						roleMapper.setRecruitOppsInd(department.isRecruitOppsInd());
						roleMapper.setHrInd(department.isHrInd());
						roleMapper.setInventoryInd(department.isInventoryInd());
						roleMapper.setLogisticsInd(department.isLogisticsInd());
						roleMapper.setOrderManagementInd(department.isOrderManagementInd());
						roleMapper.setProcurementInd(department.isProcurementInd());
						roleMapper.setProductionInd(department.isProductionInd());
						roleMapper.setRecruitProInd(department.isRecruitProInd());
						roleMapper.setRepairInd(department.isRepairInd());
						roleMapper.setELearningInd(department.isELearningInd());
						roleMapper.setFinanceInd(department.isFinanceInd());
					}
				}

				resultList.add(roleMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<TaskChecklistMapper> getAllTaskChecklistByTaskType(String taskTypeId) {
		TaskType taskType = taskTypeRepository.findById(taskTypeId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task type not found with id :" + taskTypeId));
		return taskChecklistRepository.findByTaskType(taskType).stream().map(taskChecklist -> {
			TaskChecklistMapper taskChecklistMapper = new TaskChecklistMapper();
			taskChecklistMapper.setTaskChecklistId(taskChecklist.getTaskChecklistId());
			taskChecklistMapper.setCreationDate(Utility.getISOFromDate(taskChecklist.getCreationDate()));
			taskChecklistMapper.setTaskChecklistName(taskChecklist.getTaskChecklistName());
			taskChecklistMapper.setLiveInd(taskChecklist.isLiveInd());
			taskChecklistMapper.setOrgId(taskChecklist.getOrgId());
			taskChecklistMapper.setUserId(taskChecklist.getUserId());
			taskChecklistMapper.setTaskTypeId(taskChecklist.getTaskType().getTaskTypeId());
			return taskChecklistMapper;
		}).collect(Collectors.toList());
	}

	@Override
	public boolean checkUnitNameExist(String unitName) {
		List<Unit> unit = unitRepository.findByUnitNameAndLiveInd(unitName, true);
		if (!unit.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public SkillLevelLinkMapper saveSkillLevel(SkillLevelLinkMapper skillLevelLinkMapper) {

		if (!StringUtils.isEmpty(skillLevelLinkMapper.getSkillLevelLinkId())) {
			SkillLevelLink skillLevelLink = skillLevelLinkRepository
					.findById(skillLevelLinkMapper.getSkillLevelLinkId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"skilllevel not found with id :" + skillLevelLinkMapper.getSkillLevelLinkId()));

			skillLevelLink.setSkillDefinationId(skillLevelLinkMapper.getSkillDefinationId());
			if (skillLevelLinkMapper.getSkillDefinationId() != null) {
				DefinationDetails definationDetails = definationRepository
						.findByDefinationId(skillLevelLinkMapper.getSkillDefinationId());
				skillLevelLink.setSkill(definationDetails.getName());
			}
			skillLevelLink.setCountryId(skillLevelLinkMapper.getCountryId());
			skillLevelLink.setLevel1(skillLevelLinkMapper.getLevel1());
			skillLevelLink.setLevel2(skillLevelLinkMapper.getLevel2());
			skillLevelLink.setLevel3(skillLevelLinkMapper.getLevel3());
			skillLevelLink.setLevel4(skillLevelLinkMapper.getLevel4());
			skillLevelLink.setLevel5(skillLevelLinkMapper.getLevel5());
			SkillLevelLink s1 = skillLevelLinkRepository.save(skillLevelLink);

			SkillLevelLinkMapper sm1 = new SkillLevelLinkMapper();

			sm1.setSkillDefinationId(s1.getSkillDefinationId());
			if (s1.getSkillDefinationId() != null) {
				DefinationDetails definationDetails = definationRepository
						.findByDefinationId(s1.getSkillDefinationId());
				sm1.setSkill(definationDetails.getName());
			}
			sm1.setCountryId(s1.getCountryId());
			sm1.setLevel1(s1.getLevel1());
			sm1.setLevel2(s1.getLevel2());
			sm1.setLevel3(s1.getLevel3());
			return sm1;
		} else {
			String skillLevelLinkId = skillLevelLinkMapper.getSkillLevelLinkId();
			if (skillLevelLinkMapper.getSkillLevelLinkId().trim().length() == 0
					|| skillLevelLinkMapper.getSkillLevelLinkId() == null) {
				SkillLevelLink skillLevelLink = new SkillLevelLink();
				skillLevelLink.setSkillDefinationId(skillLevelLinkMapper.getSkillDefinationId());
				if (skillLevelLinkMapper.getSkillDefinationId() != null) {
					DefinationDetails definationDetails = definationRepository
							.findByDefinationId(skillLevelLinkMapper.getSkillDefinationId());
					skillLevelLink.setSkill(definationDetails.getName());
				}
				skillLevelLink.setCountryId(skillLevelLinkMapper.getCountryId());
				skillLevelLink.setLevel1(skillLevelLinkMapper.getLevel1());
				skillLevelLink.setLevel2(skillLevelLinkMapper.getLevel2());
				skillLevelLink.setLevel3(skillLevelLinkMapper.getLevel3());
				skillLevelLink.setLevel4(skillLevelLinkMapper.getLevel4());
				skillLevelLink.setLevel5(skillLevelLinkMapper.getLevel5());
				skillLevelLink.setLiveInd(true);
				SkillLevelLink s1 = skillLevelLinkRepository.save(skillLevelLink);
				// }SkillLevelLinkMapper sm1=new SkillLevelLinkMapper();
				SkillLevelLinkMapper sm1 = new SkillLevelLinkMapper();
				sm1.setSkillDefinationId(s1.getSkillDefinationId());
				if (s1.getSkillDefinationId() != null) {
					DefinationDetails definationDetails = definationRepository
							.findByDefinationId(s1.getSkillDefinationId());
					sm1.setSkill(definationDetails.getName());
				}
				sm1.setCountryId(s1.getCountryId());
				sm1.setLevel1(s1.getLevel1());
				sm1.setLevel2(s1.getLevel2());
				sm1.setLevel3(s1.getLevel3());
				return sm1;

			}
		}
		return null;
	}

	@Override
	public List<SkillLevelLinkMapper> getSkillLevelByCountry(String countryId, String orgId) {
		List<SkillLevelLink> list = skillLevelLinkRepository.findByCountryIdAndLiveInd(countryId, true);
		List<SkillLevelLinkMapper> list2 = new ArrayList<SkillLevelLinkMapper>();
		if (null != list && !list.isEmpty()) {
			list2 = list.stream().map(skillLevelLink -> {
				SkillLevelLinkMapper skillLevelLinkMapper = new SkillLevelLinkMapper();
				skillLevelLinkMapper.setSkillLevelLinkId(skillLevelLink.getId());
				skillLevelLinkMapper.setSkillDefinationId(skillLevelLink.getSkillDefinationId());
				if (skillLevelLink.getSkillDefinationId() != null) {
					DefinationDetails definationDetails = definationRepository
							.findByDefinationId(skillLevelLink.getSkillDefinationId());
					skillLevelLinkMapper.setSkill(definationDetails.getName());
				}
				skillLevelLinkMapper.setLevel1(skillLevelLink.getLevel1());
				skillLevelLinkMapper.setLevel2(skillLevelLink.getLevel2());
				skillLevelLinkMapper.setLevel3(skillLevelLink.getLevel3());
				skillLevelLinkMapper.setLevel4(skillLevelLink.getLevel4());
				skillLevelLinkMapper.setLevel5(skillLevelLink.getLevel5());
				skillLevelLinkMapper.setCountryId(skillLevelLink.getCountryId());

				return skillLevelLinkMapper;
			}).filter(l -> l != null).collect(Collectors.toList());
		}
		return list2;
	}

	@Override
	public List<SkillLevelLinkMapper> getSkillLevel() {
		List<SkillLevelLink> list = skillLevelLinkRepository.findByLiveInd(true);
		List<SkillLevelLinkMapper> list2 = new ArrayList<SkillLevelLinkMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(skillLevelLink -> {
				SkillLevelLinkMapper skillLevelLinkMapper = new SkillLevelLinkMapper();
				skillLevelLinkMapper.setSkillLevelLinkId(skillLevelLink.getId());
				skillLevelLinkMapper.setSkillDefinationId(skillLevelLink.getSkillDefinationId());
				skillLevelLinkMapper.setLevel1(skillLevelLink.getLevel1());
				skillLevelLinkMapper.setLevel2(skillLevelLink.getLevel2());
				skillLevelLinkMapper.setLevel3(skillLevelLink.getLevel3());
				skillLevelLinkMapper.setLevel4(skillLevelLink.getLevel4());
				skillLevelLinkMapper.setLevel5(skillLevelLink.getLevel5());
				list2.add(skillLevelLinkMapper);

				return list2;
			}).collect(Collectors.toList());
		} else {
			SkillLevelLinkMapper skillLevelLinkMapper = new SkillLevelLinkMapper();
			skillLevelLinkMapper.setSkillLevelLinkId("null");
			skillLevelLinkMapper.setSkillDefinationId("null");
			skillLevelLinkMapper.setLevel1(0);
			skillLevelLinkMapper.setLevel2(0);
			skillLevelLinkMapper.setLevel3(0);
			skillLevelLinkMapper.setLevel4(0);
			skillLevelLinkMapper.setLevel5(0);
			list2.add(skillLevelLinkMapper);

			return list2;
		}
		return list2;
	}

	@Override
	public boolean TicketsTypeNameInTicketsType(String ticketsType) {
		List<TicketsType> list = ticketsTypeRepository.findByTicketType(ticketsType);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public TicketTypeResponseMapper CreateTicketType(TicketTypeRequestMapper ticketRequest) {
		TicketTypeResponseMapper resultMapper = new TicketTypeResponseMapper();
		if (ticketRequest != null) {
			TicketsType ticketsType = new TicketsType();
			ticketsType.setTicketType(ticketRequest.getTicketType());
			ticketsType.setUserId(ticketRequest.getUserId());
			ticketsType.setOrgId(ticketRequest.getOrganizationId());
			ticketsType.setCreationDate(new Date());
			ticketsType.setLiveInd(true);
			ticketsType.setEditInd(ticketRequest.isEditInd());

			ticketsType.setUpdationDate(new Date());
			ticketsType.setUpdatedBy(ticketRequest.getUserId());
			resultMapper = getByTicketTypeId(ticketsTypeRepository.save(ticketsType).getTicketTypeId());

		}
		return resultMapper;
	}

	@Override
	public TicketTypeResponseMapper getByTicketTypeId(String ticketTypeId) {
		TicketTypeResponseMapper resultMapper = new TicketTypeResponseMapper();

		TicketsType type = ticketsTypeRepository.getById(ticketTypeId);
		if (null != type) {
			resultMapper.setTicketType(type.getTicketType());
			// resultMapper.setUserId(type.getUserId());
			// resultMapper.setOrganizationId(type.getOrgId());
//			resultMapper.setCreationDate(Utility.getISOFromDate(type.getCreationDate()));
			resultMapper.setLiveInd(type.isLiveInd());
			resultMapper.setEditInd(type.isEditInd());
			resultMapper.setTicketTypeId(type.getTicketTypeId());
			resultMapper.setUpdationDate(Utility.getISOFromDate(type.getUpdationDate()));
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(type.getUpdatedBy()));
		}

		return resultMapper;
	}

	@Override
	public List<TicketTypeResponseMapper> getAllTicketsTypelist(String orgId) {
		List<TicketTypeResponseMapper> resultMapper = new ArrayList<>();

		List<TicketsType> typeList = ticketsTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != typeList && !typeList.isEmpty()) {
			resultMapper = typeList.stream().sorted((p1, p2) -> p1.getCreationDate().compareTo(p2.getCreationDate()))
					.map(type -> {
						TicketTypeResponseMapper ticketResponseMapper = new TicketTypeResponseMapper();

						ticketResponseMapper.setTicketType(type.getTicketType());
						// ticketResponseMapper.setUserId(type.getUserId());
						// ticketResponseMapper.setOrganizationId(type.getOrgId());
//					 ticketResponseMapper.setCreationDate(Utility.getISOFromDate(type.getCreationDate()));
						ticketResponseMapper.setLiveInd(type.isLiveInd());
						ticketResponseMapper.setEditInd(type.isEditInd());
						ticketResponseMapper.setTicketTypeId(type.getTicketTypeId());
						return ticketResponseMapper;
					}).collect(Collectors.toList());

//				Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			List<TicketsType> ticketsType = ticketsTypeRepository.findByOrgId(orgId);
			if (null != ticketsType && !ticketsType.isEmpty()) {
//					Collections.sort(ticketsType, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				ticketsType.stream().sorted((p1, p2) -> p1.getUpdationDate().compareTo(p2.getUpdationDate()))
						.collect(Collectors.toList());

				resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(ticketsType.get(0).getUpdationDate()));
				resultMapper.get(0)
						.setUpdatedBy(employeeService.getEmployeeFullName(ticketsType.get(0).getUpdatedBy()));
			}
		}

		return resultMapper;
	}

	@Override
	public TicketTypeResponseMapper updateTicketsType(TicketTypeRequestMapper ticketRequestMapper) {
		TicketTypeResponseMapper resultMapper = new TicketTypeResponseMapper();

		TicketsType type = ticketsTypeRepository.getById(ticketRequestMapper.getTicketTypeId());
		if (null != type) {
			type.setTicketType(ticketRequestMapper.getTicketType());
			type.setUpdationDate(new Date());
			type.setUpdatedBy(ticketRequestMapper.getUserId());
			resultMapper = getByTicketTypeId(ticketsTypeRepository.save(type).getTicketTypeId());
		}
		return resultMapper;
	}

	@Override
	public void deleteTicketsType(String ticketsTypeId, String userId) {
		if (null != ticketsTypeId) {
			TicketsType type = ticketsTypeRepository.getById(ticketsTypeId);
			if (null != type) {

				type.setUpdationDate(new Date());
				type.setUpdatedBy(userId);

				type.setLiveInd(false);
				ticketsTypeRepository.save(type);
			}
		}
	}

	@Override
	public List<TicketTypeResponseMapper> getTicketsTypeByName(String name) {
		List<TicketsType> list = ticketsTypeRepository.findByTicketTypeContainingAndLiveInd(name, true);
		if (null != list && !list.isEmpty()) {
			list.stream().sorted((p1, p2) -> p1.getUpdationDate().compareTo(p2.getUpdationDate()));
		}
		List<TicketTypeResponseMapper> resultMapper = new ArrayList<>();

		if (null != list && !list.isEmpty()) {
			list.stream().map(type -> {
				// System.out.println("idProofTyoeById=========" +
				// idProofType.getIdProofTypeId());
				TicketTypeResponseMapper ticketResponseMapper = getByTicketTypeId(type.getTicketTypeId());
				if (null != ticketResponseMapper) {
					resultMapper.add(ticketResponseMapper);

				}
				return ticketResponseMapper;
			}).collect(Collectors.toList());

		}
		return resultMapper;
	}

	@Override
	public ModuleResponseMapper updateModule(ModuleRequestMapper moduleRequestMapper) {
		ModuleResponseMapper id = new ModuleResponseMapper();
		Module module = moduleRepository.findByOrgId(moduleRequestMapper.getOrgId());
		if (null != module) {
			if (moduleRequestMapper.getType().equalsIgnoreCase("crm")) {
				if (moduleRequestMapper.isValue()) {
					module.setCrmInd(true);
				} else {
					module.setCrmInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setCrmInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("erp")) {
				if (moduleRequestMapper.isValue()) {
					module.setErpInd(true);
				} else {
					module.setErpInd(false);
					module.setProductionInd(false);
					module.setRepairInd(false);
					module.setOrderManagementInd(false);
					module.setLogisticsInd(false);
					module.setProcurementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setErpInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("im")) {
				if (moduleRequestMapper.isValue()) {
					module.setImInd(true);
				} else {
					module.setImInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setImInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("hr")) {
				if (moduleRequestMapper.isValue()) {
					module.setHrInd(true);
				} else {
					module.setHrInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setHrInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("inventory")) {
				if (moduleRequestMapper.isValue()) {
					module.setInventoryInd(true);
				} else {
					module.setInventoryInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setInventoryInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("logistics")) {
				if (moduleRequestMapper.isValue()) {
					module.setLogisticsInd(true);
				} else {
					module.setLogisticsInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setLogisticsInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("orderManagement")) {
				if (moduleRequestMapper.isValue()) {
					module.setOrderManagementInd(true);
				} else {
					module.setOrderManagementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setOrderManagementInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("procurement")) {
				if (moduleRequestMapper.isValue()) {
					module.setProcurementInd(true);
				} else {
					module.setProcurementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setProcurementInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("production")) {
				if (moduleRequestMapper.isValue()) {
					module.setProductionInd(true);
				} else {
					module.setProductionInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setProductionInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("recruitPro")) {
				if (moduleRequestMapper.isValue()) {
					module.setRecruitProInd(true);
				} else {
					module.setRecruitProInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setRecruitProInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("repair")) {
				if (moduleRequestMapper.isValue()) {
					module.setRepairInd(true);
				} else {
					module.setRepairInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setRepairInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("eLearning")) {
				if (moduleRequestMapper.isValue()) {
					module.setELearningInd(true);
				} else {
					module.setELearningInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setELearningInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("finance")) {
				if (moduleRequestMapper.isValue()) {
					module.setFinanceInd(true);
				} else {
					module.setFinanceInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setFinanceInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}

			if (moduleRequestMapper.getType().equalsIgnoreCase("projectModule")) {
				if (moduleRequestMapper.isValue()) {
					module.setProjectModInd(true);
				} else {
					module.setProjectModInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setProjectModInd(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("ecomModule")) {
				if (moduleRequestMapper.isValue()) {
					module.setEcomModInd(true);
				} else {
					module.setEcomModInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setEcomModInd(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("trading")) {
				if (moduleRequestMapper.isValue()) {
					module.setTradingInd(true);
				} else {
					module.setTradingInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setTrading_ind(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("customerPortal")) {
				if (moduleRequestMapper.isValue()) {
					module.setCustomerPortInd(true);
				} else {
					module.setCustomerPortInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setTrading_ind(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			module.setCreationDate(new Date());
			module.setUserId(moduleRequestMapper.getUserId());
			module.setOrgId(moduleRequestMapper.getOrgId());
			module.setUpdatedBy(moduleRequestMapper.getUserId());
			module.setUpdationDate(new Date());
			id = getModuleByOrgId(moduleRepository.save(module).getOrgId());
		} else {
			Module module1 = new Module();

			if (moduleRequestMapper.getType().equalsIgnoreCase("crm")) {
				if (moduleRequestMapper.isValue()) {
					module1.setCrmInd(true);
				} else {
					module1.setCrmInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setCrmInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("erp")) {
				if (moduleRequestMapper.isValue()) {
					module1.setErpInd(true);
				} else {
					module1.setErpInd(false);
					module1.setProductionInd(false);
					module1.setRepairInd(false);
					module1.setOrderManagementInd(false);
					module1.setLogisticsInd(false);
					module1.setProcurementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setErpInd(false);
							departmentRepository.save(department);
						}
					}

				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("im")) {
				if (moduleRequestMapper.isValue()) {
					module1.setImInd(true);
				} else {
					module1.setImInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setImInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("hr")) {
				if (moduleRequestMapper.isValue()) {
					module1.setHrInd(true);
				} else {
					module1.setHrInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setHrInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("inventory")) {
				if (moduleRequestMapper.isValue()) {
					module1.setInventoryInd(true);
				} else {
					module1.setInventoryInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setInventoryInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("logistics")) {
				if (moduleRequestMapper.isValue()) {
					module1.setLogisticsInd(true);
				} else {
					module1.setLogisticsInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setLogisticsInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("orderManagement")) {
				if (moduleRequestMapper.isValue()) {
					module1.setOrderManagementInd(true);
				} else {
					module1.setOrderManagementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setOrderManagementInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("procurement")) {
				if (moduleRequestMapper.isValue()) {
					module1.setProcurementInd(true);
				} else {
					module1.setProcurementInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setProcurementInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("production")) {
				if (moduleRequestMapper.isValue()) {
					module1.setProductionInd(true);
				} else {
					module1.setProductionInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setProductionInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("recruitPro")) {
				if (moduleRequestMapper.isValue()) {
					module1.setRecruitProInd(true);
				} else {
					module1.setRecruitProInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setRecruitProInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("repair")) {
				if (moduleRequestMapper.isValue()) {
					module1.setRepairInd(true);
				} else {
					module1.setRepairInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setRepairInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("eLearning")) {
				if (moduleRequestMapper.isValue()) {
					module1.setELearningInd(true);
				} else {
					module1.setELearningInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setELearningInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("finance")) {
				if (moduleRequestMapper.isValue()) {
					module1.setFinanceInd(true);
				} else {
					module1.setFinanceInd(false);

					List<Department> departmentList = departmentRepository
							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
					if (null != departmentList && !departmentList.isEmpty()) {
						for (Department department : departmentList) {
							department.setFinanceInd(false);
							departmentRepository.save(department);
						}
					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("projectModule")) {
				if (moduleRequestMapper.isValue()) {
					module1.setProjectModInd(true);
				} else {
					module1.setProjectModInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setProjectModInd(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("ecomModule")) {
				if (moduleRequestMapper.isValue()) {
					module1.setEcomModInd(true);
				} else {
					module1.setEcomModInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setEcomModInd(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("trading")) {
				if (moduleRequestMapper.isValue()) {
					module.setTradingInd(true);
				} else {
					module.setTradingInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setTrading_ind(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			if (moduleRequestMapper.getType().equalsIgnoreCase("customerPortal")) {
				if (moduleRequestMapper.isValue()) {
					module.setCustomerPortInd(true);
				} else {
					module.setCustomerPortInd(false);

//					List<Department> departmentList = departmentRepository
//							.getDepartmentListsByOrgIdAndLiveInd(moduleRequestMapper.getOrgId());
//					if (null != departmentList && !departmentList.isEmpty()) {
//						for (Department department : departmentList) {
//							department.setTrading_ind(false);
//							departmentRepository.save(department);
//						}
//					}
				}
			}
			module1.setCreationDate(new Date());
			module1.setUserId(moduleRequestMapper.getUserId());
			module1.setOrgId(moduleRequestMapper.getOrgId());
			module1.setUpdatedBy(moduleRequestMapper.getUserId());
			module1.setUpdationDate(new Date());
			id = getModuleByOrgId(moduleRepository.save(module1).getOrgId());
		}

		return id;
	}

	@Override
	public ModuleResponseMapper getModuleByOrgId(String orgId) {
		ModuleResponseMapper resultMapper = new ModuleResponseMapper();
		Module module = moduleRepository.findByOrgId(orgId);
		if (module != null) {
			resultMapper.setCrmInd(module.isCrmInd());
			resultMapper.setErpInd(module.isErpInd());
			resultMapper.setImInd(module.isImInd());
			resultMapper.setHrInd(module.isHrInd());
			resultMapper.setInventoryInd(module.isInventoryInd());
			resultMapper.setLogisticsInd(module.isLogisticsInd());
			resultMapper.setOrderManagementInd(module.isOrderManagementInd());
			resultMapper.setProcurementInd(module.isProcurementInd());
			resultMapper.setProductionInd(module.isProductionInd());
			resultMapper.setRecruitProInd(module.isRecruitProInd());
			resultMapper.setRepairInd(module.isRepairInd());
			resultMapper.setELearningInd(module.isELearningInd());
			resultMapper.setFinanceInd(module.isFinanceInd());
			resultMapper.setProjectModInd(module.isProjectModInd());
			resultMapper.setEcomModInd(module.isEcomModInd());
			resultMapper.setTradingInd(module.isTradingInd());
			resultMapper.setCustomerPortInd(module.isCustomerPortInd());

			resultMapper.setUpdationDate(Utility.getISOFromDate(module.getUpdationDate()));
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(module.getUpdatedBy()));
		}

		return resultMapper;

	}

	@Override
	public String setAdminSecreteKey(AdminSecreteKeyMapper adminSecreteKeyMapper) {
		UserSettings dbuserSettings = userSettingsRepository.findByUserIdAndLiveInd(adminSecreteKeyMapper.getUserId(),
				true);
		if (dbuserSettings.getUserType().equalsIgnoreCase("ADMIN")) {
			AdminSecreteKey adminSecreteKey = adminSecreteKeyReypository
					.findByUserId(adminSecreteKeyMapper.getUserId());
			System.out.println("adminSecreteKeyMapper.getUserId()" + adminSecreteKeyMapper.getUserId());
			if (null != adminSecreteKey) {

				adminSecreteKey.setAdminSecreteKey(
						new BCryptPasswordEncoder().encode(adminSecreteKeyMapper.getAdminSecreteKey()));
				adminSecreteKey.setCreationDate(new Date());
				adminSecreteKey.setLiveInd(true);
				adminSecreteKey.setOrgId(adminSecreteKeyMapper.getOrgId());
				adminSecreteKey.setUserId(adminSecreteKeyMapper.getUserId());
				adminSecreteKeyReypository.save(adminSecreteKey);

				return "secrete key updated successfully";
			} else {

				AdminSecreteKey adminSecreteKey1 = new AdminSecreteKey();
				adminSecreteKey1.setAdminSecreteKey(
						new BCryptPasswordEncoder().encode(adminSecreteKeyMapper.getAdminSecreteKey()));
				adminSecreteKey1.setCreationDate(new Date());
				adminSecreteKey1.setLiveInd(true);
				adminSecreteKey1.setOrgId(adminSecreteKeyMapper.getOrgId());
				adminSecreteKey1.setUserId(adminSecreteKeyMapper.getUserId());
				adminSecreteKeyReypository.save(adminSecreteKey1);

				return "secrete key added successfully";
			}
		}
		return "secrete key only added by admin";

	}

	@Override
	public AdminSecreteKeyMapper getAdminSecreteKeyByOrgId(String userId) {
		AdminSecreteKeyMapper resultMapper = new AdminSecreteKeyMapper();
		AdminSecreteKey adminSecreteKey = adminSecreteKeyReypository.findByUserId(userId);
		if (adminSecreteKey != null) {
			resultMapper.setAdminSecreteKey(new BCryptPasswordEncoder().encode(adminSecreteKey.getAdminSecreteKey()));
			resultMapper.setAdminSecreteKeyId(adminSecreteKey.getAdminSecreteKeyId());
			resultMapper.setCreationDate(Utility.getISOFromDate(adminSecreteKey.getCreationDate()));
			resultMapper.setLiveInd(adminSecreteKey.isLiveInd());
			resultMapper.setOrgId(adminSecreteKey.getOrgId());
			resultMapper.setUserId(userId);
		}
		return resultMapper;
	}

	@Override
	public MinimumActivityRespMapper getMinimumActivity(String orgId) {
		MinActivity minActivity = minimumActivityRepo.getByOrgId(orgId);
		MinimumActivityRespMapper resultMapper = new MinimumActivityRespMapper();
		if (null != minActivity) {
			resultMapper = getMinimumActivityById(minActivity.getMinimumActivityId());
		}
		return resultMapper;
	}

	@Override
	public MinimumActivityRespMapper getMinimumActivityById(String minimumActivityId) {
		MinActivity minActivity = minimumActivityRepo.getById(minimumActivityId);
		MinimumActivityRespMapper resultMapper = new MinimumActivityRespMapper();
		if (null != minActivity) {
			resultMapper.setCreationDate(Utility.getISOFromDate(minActivity.getCreationDate()));
			resultMapper.setOrgId(minActivity.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(minActivity.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(minActivity.getCreationDate()));
			resultMapper.setUserId(minActivity.getUserId());
			resultMapper.setMinimumActivityId(minActivity.getMinimumActivityId());
			resultMapper.setCallActivity(minActivity.getCallActivity());
			resultMapper.setTaskActivity(minActivity.getTaskActivity());
			resultMapper.setEventActivity(minActivity.getEventActivity());
		}

		return resultMapper;
	}

	@Override
	public MinimumActivityRespMapper createMinimumActivity(MinimumActivityReqMapper requestMapper) {
		MinimumActivityRespMapper resultMapper = new MinimumActivityRespMapper();
		MinActivity minActivity = minimumActivityRepo.getByOrgId(requestMapper.getOrgId());
		if (null != minActivity) {
			minActivity.setCallActivity(requestMapper.getCallActivity());
			minActivity.setTaskActivity(requestMapper.getTaskActivity());
			minActivity.setEventActivity(requestMapper.getEventActivity());
			minActivity.setCreationDate(new Date());
			minActivity.setUserId(requestMapper.getUserId());
			MinActivity minActivity1 = minimumActivityRepo.save(minActivity);
			resultMapper = getMinimumActivityById(minActivity1.getMinimumActivityId());
		} else {
			MinActivity minActivity1 = new MinActivity();
			minActivity1.setCallActivity(requestMapper.getCallActivity());
			minActivity1.setTaskActivity(requestMapper.getTaskActivity());
			minActivity1.setEventActivity(requestMapper.getEventActivity());
			minActivity1.setCreationDate(new Date());
			minActivity1.setUserId(requestMapper.getUserId());
			minActivity1.setOrgId(requestMapper.getOrgId());
			MinActivity minActivity2 = minimumActivityRepo.save(minActivity);
			resultMapper = getMinimumActivityById(minActivity2.getMinimumActivityId());
		}
		return resultMapper;
	}

	@Override
	public HashMap getRoleTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<RoleType> list = roleTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("RoleTypeCount", list.size());
		return map;
	}

	@Override
	public HashMap getLibreryTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<LibraryType> list = libraryTypeRepository.findByOrgId(orgId);
		map.put("LibraryTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportRoleTypeListToExcel(List<RoleMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (RoleMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getRoleType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public ByteArrayInputStream exportLibraryTypeListToExcel(List<RoleMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (RoleMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getLibraryType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public boolean checkNameInCategory(String name, String orgId) {
		List<Category> list = categoryRepo.findByNameAndOrgIdAndLiveInd(name, orgId, true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public CategoryMapper saveCategory(CategoryMapper mapper) {
		String Id = null;
		if (mapper != null) {
			Category category = new Category();
			category.setCreationDate(new Date());
			category.setLiveInd(true);
			category.setName(mapper.getName());
			category.setOrgId(mapper.getOrgId());
			category.setUserId(mapper.getUserId());
			category.setUpdatedBy(mapper.getUserId());
			category.setUpdationDate(new Date());
			Id = categoryRepo.save(category).getCategoryId();

		}
		CategoryMapper resultMapper = getCategoryById(Id);
		return resultMapper;
	}

	public CategoryMapper getCategoryById(String id) {

		Category category = categoryRepo.findByCategoryIdAndLiveInd(id, true);
		CategoryMapper resultMapper = new CategoryMapper();

		if (null != category) {
			resultMapper.setCreationDate(Utility.getISOFromDate(category.getCreationDate()));
			resultMapper.setLiveInd(category.isLiveInd());
			resultMapper.setName(category.getName());
			resultMapper.setOrgId(category.getOrgId());
			resultMapper.setUserId(category.getUserId());
			resultMapper.setCategoryId(id);
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(category.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(category.getCreationDate()));
		}

		return resultMapper;
	}

	@Override
	public List<CategoryMapper> getCategoryMapperByOrgId(String orgId) {
		return categoryRepo.findByOrgIdAndLiveInd(orgId, true).stream().map(li -> getCategoryById(li.getCategoryId()))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryMapper updateCategory(String categoryId, CategoryMapper mapper) {

		Category category = categoryRepo.findByCategoryIdAndLiveInd(categoryId, true);
		if (null != category) {

			category.setCreationDate(new Date());
			category.setLiveInd(true);
			category.setName(mapper.getName());
			category.setOrgId(mapper.getOrgId());
			category.setUserId(mapper.getUserId());
			category.setUpdatedBy(mapper.getUserId());
			category.setUpdationDate(new Date());
			categoryRepo.save(category);
		}
		CategoryMapper resultMapper = getCategoryById(categoryId);
		return resultMapper;
	}

	@Override
	public List<CategoryMapper> getCategoryByNameByOrgLevel(String name, String orgId) {
		return categoryRepo.findByNameContainingAndLiveIndAndOrgId(name, true, orgId).stream()
				.map(li -> getCategoryById(li.getCategoryId())).collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(String categoryId, String userId) {
		Category category = categoryRepo.findByCategoryIdAndLiveInd(categoryId, true);
		if (null != category) {
			category.setLiveInd(false);
			category.setUpdatedBy(userId);
			category.setUpdationDate(new Date());
			categoryRepo.save(category);
		}

	}

	@Override
	public HashMap getCategoryCountByOrgId(String orgId) {
		List<Category> list = categoryRepo.findByOrgIdAndLiveInd(orgId, true);
		HashMap map = new HashMap();
		map.put("categoryCount", list.size());
		return map;
	}

	@Override
	public RatingTypeMapper saveRatingType(RatingTypeMapper mapper) {
		RatingType ratingType = new RatingType();
		ratingType.setRatingType(mapper.getRatingType());
		ratingType.setCreationDate(new Date());
		ratingType.setLiveInd(true);
		ratingType.setOrgId(mapper.getOrgId());
		ratingType.setUserId(mapper.getUserId());
		ratingType.setUpdationDate(new Date());
		ratingType.setUpdatedBy(mapper.getUserId());
		return getRatingTypeById(ratingTypeRepository.save(ratingType).getRatingTypeId());
	}

	@Override
	public List<RatingTypeMapper> getRatingTypes(String orgId) {
		List<RatingType> ratingTypes = ratingTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != ratingTypes && !ratingTypes.isEmpty()) {
			return ratingTypes.stream().map(r -> getRatingTypeById(r.getRatingTypeId())).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

	private RatingTypeMapper getRatingTypeById(String ratingTypeId) {
		RatingTypeMapper ratingTypeMapper = new RatingTypeMapper();
		RatingType ratingType = ratingTypeRepository.findById(ratingTypeId).orElse(null);
		if (null != ratingType) {
			ratingTypeMapper.setRatingTypeId(ratingType.getRatingTypeId());
			ratingTypeMapper.setRatingType(ratingType.getRatingType());
			ratingTypeMapper.setOrgId(ratingType.getOrgId());
			ratingTypeMapper.setUserId(ratingType.getUserId());
			ratingTypeMapper.setCreationDate(ratingType.getCreationDate());
			ratingTypeMapper.setUpdationDate(ratingType.getUpdationDate());
		}
		return ratingTypeMapper;
	}

	@Override
	public InvoiceCategoryMapper updateInvoiceCategory(InvoiceCategoryMapper invoiceCategoryMapper) {
		InvoiceCategoryMapper id = new InvoiceCategoryMapper();
		InvoiceCategory invoiceCategory = invoiceCategoryRepository.findByOrgId(invoiceCategoryMapper.getOrgId());
		if (null != invoiceCategory) {

			if (invoiceCategoryMapper.getType().equalsIgnoreCase("autoCi")) {
				invoiceCategory.setAutoCiInd(true);
			}
			if (invoiceCategoryMapper.getType().equalsIgnoreCase("innitialInispection")) {
				invoiceCategory.setInniInspectInd(true);
			}
			if (invoiceCategoryMapper.getType().equalsIgnoreCase("pi")) {
				invoiceCategory.setPiInd(true);
			}
			invoiceCategory.setOrgId(invoiceCategoryMapper.getOrgId());
			id = getInvoiceCategoryByOrgId(invoiceCategoryRepository.save(invoiceCategory).getOrgId());
		} else {
			InvoiceCategory invoiceCategory1 = new InvoiceCategory();

			if (invoiceCategoryMapper.getType().equalsIgnoreCase("autoCi")) {

				invoiceCategory.setAutoCiInd(true);
			}
			if (invoiceCategoryMapper.getType().equalsIgnoreCase("innitialInispection")) {
				invoiceCategory.setInniInspectInd(true);
			}
			if (invoiceCategoryMapper.getType().equalsIgnoreCase("pi")) {
				invoiceCategory.setPiInd(true);
			}
			invoiceCategory.setOrgId(invoiceCategoryMapper.getOrgId());
			id = getInvoiceCategoryByOrgId(invoiceCategoryRepository.save(invoiceCategory).getOrgId());

		}

		return id;
	}
	@Override
	public InvoiceCategoryMapper getInvoiceCategoryByOrgId(String orgId) {
		InvoiceCategory invoiceCategory = invoiceCategoryRepository.findByOrgId(orgId);
		InvoiceCategoryMapper resultMapper = new InvoiceCategoryMapper();
		if (null != invoiceCategory) {
			resultMapper.setAutoCiInd(invoiceCategory.isAutoCiInd());
			resultMapper.setInniInspectInd(invoiceCategory.isInniInspectInd());
			resultMapper.setPiInd(invoiceCategory.isPiInd());
			resultMapper.setOrgId(orgId);
			resultMapper.setInvoiceCategoryId(invoiceCategory.getInvoiceCategoryId());
		}
		return resultMapper;
	}
}
