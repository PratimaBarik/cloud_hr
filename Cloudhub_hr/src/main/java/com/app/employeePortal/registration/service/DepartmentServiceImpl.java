package com.app.employeePortal.registration.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.Module;
import com.app.employeePortal.category.mapper.ModuleDepartmentResponseMapper;
import com.app.employeePortal.category.repository.ModuleRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.DepartmentDelete;
import com.app.employeePortal.registration.entity.Designation;
import com.app.employeePortal.registration.entity.DesignationDelete;
import com.app.employeePortal.registration.entity.FunctionDetails;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.DepartmentRequestMapper;
import com.app.employeePortal.registration.mapper.DesignationMapper;
import com.app.employeePortal.registration.mapper.FunctionMapper;
import com.app.employeePortal.registration.repository.DepartmentDeleteRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationDeleteRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.registration.repository.FunctionRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	FunctionRepository functionRepository;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	DesignationDeleteRepository designationDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	DepartmentDeleteRepository departmentDeleteRepository;
	@Autowired
	EmployeeService  employeeService;
	@Autowired
	ModuleRepository moduleRepository;
	private String[] headings = {"Name"};

	@Override
	public List<Department> createDepartment(String departmentName) {

		Department department = new Department();
		department.setDepartmentName(departmentName);
		// department.setEditInd(false);
		department.setCreationDate(new Date());
		departmentRepository.save(department);

		List<Department> list = getDepartmentList();
		return list;
	}

	@Override
	public List<Department> getDepartmentList() {

		List<Department> list = departmentRepository.findAll();
		return list;
	}

	@Override
	public List<DepartmentMapper> autocompleteDepartmentByName(String departmentName, String orgId,
			DepartmentMapper departmentMapper) {
		List<DepartmentMapper> mapperList = new ArrayList<>();
		System.out.println("departmentName......... " + departmentName);
		System.out.println("orgId......... " + orgId);
		Department dbDepartment = departmentRepository.getDepartmentListByName(orgId.trim(),
				departmentName.trim().toUpperCase());
		List<Department> deptlist = null;
		System.out.println("list...... " + dbDepartment);
		if (null != dbDepartment) {

			deptlist = departmentRepository.getDepartmentListByOrgId(orgId);
			deptlist.stream().map(li -> {
				DepartmentMapper departmentMapper1 = new DepartmentMapper();
				departmentMapper1.setDepartmentId(li.getDepartment_id());
				departmentMapper1.setDepartmentName(li.getDepartmentName());
				if (!StringUtils.isEmpty(li.getSector_id())) {
					SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorId(li.getSector_id());
					if (null != sectorDetails) {
						departmentMapper1.setSectorName(sectorDetails.getSectorName());
						departmentMapper1.setSectorId(li.getSector_id());
					}
				}
				return mapperList.add(departmentMapper1);

			}).collect(Collectors.toList());
			return mapperList;

		} else {

			Department department = new Department();
			department.setDepartmentName(departmentName.trim().toUpperCase());
			department.setOrgId(orgId);
			if (!StringUtils.isEmpty(departmentMapper.getSectorId())) {
				SectorDetails sectorDetails = sectorDetailsRepository
						.getSectorDetailsBySectorId(departmentMapper.getSectorId());
				if (null != sectorDetails) {
					department.setSector_id(departmentMapper.getSectorId());
				}
				Department department1 = departmentRepository.save(department);
				System.out.println("department1......" + department1);
				deptlist = departmentRepository.getDepartmentListByOrgId(orgId);

			}

		}

		return mapperList;
	}

	@Override
	public List<DesignationMapper> getDesignationByOrgId(String orgId) {
		List<DesignationMapper> resultList = new ArrayList<DesignationMapper>();
		List<Designation> designationList = designationRepository.findByOrgIdAndLiveInd(orgId,true);

		if (null != designationList && !designationList.isEmpty()) {
			for (Designation designation : designationList) {

				DesignationMapper designationMapper = new DesignationMapper();
				designationMapper.setDesignationTypeId(designation.getDesignationTypeId());
				designationMapper.setDesignationType(designation.getDesignationType());
				designationMapper.setEditInd(designation.isEditInd());
				designationMapper.setOrganizationId(designation.getOrgId());
				designationMapper.setUserId(designation.getUserId());
				designationMapper.setCreationDate(Utility.getISOFromDate(designation.getCreationDate()));

				resultList.add(designationMapper);

			}

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<DesignationDelete> designationDelete = designationDeleteRepository.findByOrgId(orgId);
		if (null != designationDelete && !designationDelete.isEmpty()) {
			Collections.sort(designationDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(designationDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(designationDelete.get(0).getUserId());
			if(null!=employeeDetails) {
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
	public DesignationMapper saveDesignation(DesignationMapper designationMapper) {
		String designationId = null;

		if (designationMapper != null) {
			Designation designation = new Designation();

			designation.setDesignationType(designationMapper.getDesignationType());
			designation.setCreationDate(new Date());
			designation.setUserId(designationMapper.getUserId());
			designation.setOrgId(designationMapper.getOrganizationId());
			designation.setEditInd(designationMapper.isEditInd());
			designation.setLiveInd(true);

			Designation dbDesignation = designationRepository.save(designation);
			designationId = dbDesignation.getDesignationTypeId();
			
			DesignationDelete designationDelete=new DesignationDelete();
			designationDelete.setDesignationTypeId(designationId);
			designationDelete.setUserId(designationMapper.getUserId());
			designationDelete.setOrgId(designationMapper.getOrganizationId());
			designationDelete.setUpdationDate(new Date());
			designationDelete.setUpdatedBy(designationMapper.getUserId());
			designationDeleteRepository.save(designationDelete);
			
		}
		DesignationMapper resultMapper = getDesignationTypeById(designationId);
		return resultMapper;
	}

	@Override
	public List<FunctionDetails> getFunctionList() {
		return functionRepository.findAll();
	}

	@Override
	public String saveFunction(FunctionMapper functionMapper) {
		String functionId = null;

		if (functionMapper != null) {
			FunctionDetails functionDetails = new FunctionDetails();

			functionDetails.setFunctionType(functionMapper.getFunctionType());
			functionDetails.setCreationDate(new Date());
			functionDetails.setUserId(functionMapper.getUserId());
			functionDetails.setOrgId(functionMapper.getOrganizationId());

			FunctionDetails dbFunctionDetails = functionRepository.save(functionDetails);
			functionId = dbFunctionDetails.getFunctionTypeId();
		}

		return functionId;
	}

	@Override
	public DesignationMapper updateDesignation(String designationTypeId, DesignationMapper designationMapper) {
		DesignationMapper resultMapper = null;

		if (null != designationMapper.getDesignationTypeId()) {
			Designation dDesignation = designationRepository
					.findByDesignationTypeId(designationMapper.getDesignationTypeId());

			if (null != dDesignation.getDesignationTypeId()) {
				if(!StringUtils.isEmpty(designationMapper.getDesignationType())){
					dDesignation.setDesignationType(designationMapper.getDesignationType());
				}
				dDesignation.setEditInd(designationMapper.isEditInd());
				designationRepository.save(dDesignation);

				DesignationDelete designationDelete = designationDeleteRepository
						.findByDesignationTypeId(designationMapper.getDesignationTypeId());
				if (null !=designationDelete) {
				designationDelete.setUpdationDate(new Date());
				designationDelete.setUpdatedBy(designationMapper.getUserId());
				designationDeleteRepository.save(designationDelete);
				}else {
					DesignationDelete designationDelete1 = new DesignationDelete();
					designationDelete1.setDesignationTypeId(designationTypeId);
					designationDelete1.setUserId(designationMapper.getUserId());
					designationDelete1.setOrgId(designationMapper.getOrganizationId());
					designationDelete1.setUpdationDate(new Date());
					designationDelete1.setUpdatedBy(designationMapper.getUserId());
					designationDeleteRepository.save(designationDelete1);
				}
			}
			resultMapper = getDesignationTypeById(designationTypeId);
		}
		return resultMapper;
	}

	public DesignationMapper getDesignationTypeById(String designationTypeId) {
		Designation designation = designationRepository.findByDesignationTypeId(designationTypeId);
		DesignationMapper designationMapper = new DesignationMapper();

		if (null != designation) {
			designationMapper.setDesignationTypeId(designation.getDesignationTypeId());

			designationMapper.setDesignationType(designation.getDesignationType());
			designationMapper.setOrganizationId(designation.getOrgId());
			designationMapper.setUserId(designation.getUserId());
			designationMapper.setEditInd(designation.isEditInd());
			designationMapper.setCreationDate(Utility.getISOFromDate(designation.getCreationDate()));

			List<DesignationDelete> list = designationDeleteRepository.findByOrgId(designation.getOrgId());
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

				designationMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				designationMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return designationMapper;
	}

	@Override
	public FunctionMapper updateFunction(String functionTypeId, FunctionMapper functionMapper) {
		FunctionMapper resultMapper = null;

		if (null != functionMapper.getFunctionTypeId()) {
			FunctionDetails dbFunctionDetails = functionRepository
					.findByFunctionTypeId(functionMapper.getFunctionTypeId());

			if (null != dbFunctionDetails.getFunctionTypeId()) {
				dbFunctionDetails.setFunctionType(functionMapper.getFunctionType());
				functionRepository.save(dbFunctionDetails);

			}
			resultMapper = getFunctionTypeById(functionTypeId);
		}
		return resultMapper;
	}

	public FunctionMapper getFunctionTypeById(String functionTypeId) {
		FunctionDetails functionDetails = functionRepository.findByFunctionTypeId(functionTypeId);
		FunctionMapper functionMapper = new FunctionMapper();

		if (null != functionDetails) {
			functionMapper.setFunctionTypeId(functionDetails.getFunctionTypeId());

			functionMapper.setFunctionType(functionDetails.getFunctionType());
			functionMapper.setOrganizationId(functionDetails.getOrgId());
			functionMapper.setUserId(functionDetails.getUserId());

		}
		return functionMapper;
	}

	@Override
	public DepartmentMapper saveDepartment(DepartmentMapper departmentMapper) {
		String departmentId = null;
		if (departmentMapper != null) {
			Department dbDepartment = new Department();

			dbDepartment.setDepartmentName(departmentMapper.getDepartmentName());
			dbDepartment.setCreationDate(new Date());
			dbDepartment.setUser_id(departmentMapper.getUserId());
			dbDepartment.setOrgId(departmentMapper.getOrganizationId());
			dbDepartment.setEditInd(departmentMapper.isEditInd());
			dbDepartment.setLiveInd(true);
			dbDepartment.setMandetoryInd(false);
			dbDepartment.setCrmInd(departmentMapper.isCrmInd());
			dbDepartment.setErpInd(departmentMapper.isErpInd());
			dbDepartment.setImInd(departmentMapper.isImInd());
			dbDepartment.setAccountInd(departmentMapper.isAccountInd());
			dbDepartment.setRecruitOppsInd(departmentMapper.isRecruitOppsInd());
			dbDepartment.setHrInd(departmentMapper.isHrInd());
			dbDepartment.setInventoryInd(departmentMapper.isInventoryInd());
			dbDepartment.setLogisticsInd(departmentMapper.isLogisticsInd());
			dbDepartment.setOrderManagementInd(departmentMapper.isOrderManagementInd());
			dbDepartment.setProcurementInd(departmentMapper.isProcurementInd());
			dbDepartment.setProductionInd(departmentMapper.isProductionInd());
			dbDepartment.setRecruitProInd(departmentMapper.isRecruitProInd());
			dbDepartment.setRepairInd(departmentMapper.isRepairInd());
			dbDepartment.setELearningInd(departmentMapper.isELearningInd());
			dbDepartment.setFinanceInd(departmentMapper.isFinanceInd());

			if (!StringUtils.isEmpty(departmentMapper.getSectorId())) {
				SectorDetails sectorDetails = sectorDetailsRepository
						.getSectorDetailsById(departmentMapper.getSectorId());
				if (null != sectorDetails) {
					dbDepartment.setSectorName(departmentMapper.getSectorName());
					dbDepartment.setSector_id(departmentMapper.getSectorId());

				}
			}
			departmentId = departmentRepository.save(dbDepartment).getDepartment_id();

			DepartmentDelete departmentDelete=new DepartmentDelete();
			departmentDelete.setDepartmentId(departmentId);
			departmentDelete.setUserId(departmentMapper.getUserId());
			departmentDelete.setOrgId(departmentMapper.getOrganizationId());
			departmentDelete.setUpdationDate(new Date());
			departmentDelete.setUpdatedBy(departmentMapper.getUserId());
			departmentDeleteRepository.save(departmentDelete);
		}
		DepartmentMapper resultMapper = getDepartmentTypeById(departmentId);
		return resultMapper;
	}

	@Override
	public DepartmentMapper updateDepartment(String departmentId, DepartmentMapper departmentMapper) {
		DepartmentMapper resultMapper = null;

		if (null != departmentMapper.getDepartmentId()) {
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());

			if (null != dbDepartment.getDepartment_id()) {
				dbDepartment.setDepartmentName(departmentMapper.getDepartmentName());
				dbDepartment.setEditInd(departmentMapper.isEditInd());
				if (!StringUtils.isEmpty(departmentMapper.getSectorId())) {
					SectorDetails sectorDetails = sectorDetailsRepository
							.getSectorDetailsBySectorId(departmentMapper.getSectorId());
					if (null != sectorDetails) {
						dbDepartment.setSector_id(departmentMapper.getSectorId());
					}
				}
				departmentRepository.save(dbDepartment);

				DepartmentDelete departmentDelete=departmentDeleteRepository.findByDepartmentId(departmentMapper.getDepartmentId());
				if (null != departmentDelete) {
					departmentDelete.setUpdationDate(new Date());
					departmentDelete.setUpdatedBy(departmentMapper.getUserId());
					departmentDeleteRepository.save(departmentDelete);
				}else {
					DepartmentDelete departmentDelete1 = new DepartmentDelete();
					departmentDelete1.setDepartmentId(departmentId);
					departmentDelete1.setUserId(departmentMapper.getUserId());
					departmentDelete1.setOrgId(departmentMapper.getOrganizationId());
					departmentDelete1.setUpdationDate(new Date());
					departmentDelete1.setUpdatedBy(departmentMapper.getUserId());
					departmentDeleteRepository.save(departmentDelete1);
				}
			}
			resultMapper = getDepartmentTypeById(departmentId);
		}
		return resultMapper;
	}

	@Override
	public DepartmentMapper getDepartmentTypeById(String departmentId) {
		Department dbDepartment = departmentRepository.getDepartmentDetails(departmentId);
		DepartmentMapper departmentMapper = new DepartmentMapper();
		System.out.println("designationIddesignationId=="+departmentId);
		if (null != dbDepartment) {
			departmentMapper.setDepartmentId(dbDepartment.getDepartment_id());

			departmentMapper.setDepartmentName(dbDepartment.getDepartmentName());
			departmentMapper.setEditInd(dbDepartment.isEditInd());
			departmentMapper.setCreationDate(Utility.getISOFromDate(dbDepartment.getCreationDate()));
			departmentMapper.setMandetoryInd(dbDepartment.isMandetoryInd());
			departmentMapper.setErpInd(dbDepartment.isErpInd());
			departmentMapper.setCrmInd(dbDepartment.isCrmInd());
			departmentMapper.setImInd(dbDepartment.isImInd());
			departmentMapper.setAccountInd(dbDepartment.isAccountInd());
			departmentMapper.setRecruitOppsInd(dbDepartment.isRecruitOppsInd());
			departmentMapper.setHrInd(dbDepartment.isHrInd());
			departmentMapper.setInventoryInd(dbDepartment.isInventoryInd());
			departmentMapper.setLogisticsInd(dbDepartment.isLogisticsInd());
			departmentMapper.setOrderManagementInd(dbDepartment.isOrderManagementInd());
			departmentMapper.setProcurementInd(dbDepartment.isProcurementInd());
			departmentMapper.setProductionInd(dbDepartment.isProductionInd());
			departmentMapper.setRecruitProInd(dbDepartment.isRecruitProInd());
			departmentMapper.setRepairInd(dbDepartment.isRepairInd());
			departmentMapper.setELearningInd(dbDepartment.isELearningInd());
			departmentMapper.setFinanceInd(dbDepartment.isFinanceInd());
			departmentMapper.setServiceLineInd(dbDepartment.isServiceLineInd());
			Module module=moduleRepository.findByOrgId(dbDepartment.getOrgId());
			ModuleDepartmentResponseMapper resultMapper=new ModuleDepartmentResponseMapper();

			if(null!=module) {
				resultMapper.setCrmInd(module.isCrmInd());
				resultMapper.setErpInd(module.isErpInd());
				resultMapper.setImInd(module.isImInd());
				resultMapper.setInventoryInd(module.isInventoryInd());
				resultMapper.setLogisticsInd(module.isLogisticsInd());
				resultMapper.setOrderManagementInd(module.isOrderManagementInd());
				resultMapper.setProcurementInd(module.isProcurementInd());
				resultMapper.setProductionInd(module.isProductionInd());
				resultMapper.setRecruitProInd(module.isRecruitProInd());
				resultMapper.setRepairInd(module.isRepairInd());
				resultMapper.setELearningInd(module.isELearningInd());
				resultMapper.setFinanceInd(module.isFinanceInd());
			}
			
			departmentMapper.setModuleMapper(resultMapper);
			if (!StringUtils.isEmpty(dbDepartment.getSector_id())) {
				SectorDetails sectorDetails = sectorDetailsRepository
						.getSectorDetailsBySectorId(dbDepartment.getSector_id());
				if (null != sectorDetails) {
					departmentMapper.setSectorName(sectorDetails.getSectorName());
					departmentMapper.setSectorId(dbDepartment.getSector_id());
				}
			}
			
			List<DepartmentDelete>list=departmentDeleteRepository.findByOrgId(dbDepartment.getOrgId());
			if(null!=list&&!list.isEmpty()) {
				System.out.println("DDDDDD="+dbDepartment.getOrgId());
				Collections.sort(list,(p1,p2)->p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				
				departmentMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				departmentMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
			
		}
		return departmentMapper;
	}

	@Override
	public boolean deleteDepartment(String departmentId) {
		Department dbDepartment = departmentRepository.getById(departmentId);

		if (dbDepartment != null) {
			
			DepartmentDelete departmentDelete = departmentDeleteRepository.findByDepartmentId(departmentId);
			if(null!=departmentDelete) {
				departmentDelete.setUpdationDate(new Date());
				departmentDelete.setUpdatedBy(departmentDelete.getUserId());;
				departmentDeleteRepository.save(departmentDelete);
			}

			departmentRepository.delete(dbDepartment);
			return true;

		}

		return false;
	}

	@Override
	public List<DepartmentMapper> getDepartmentByOrgId(String orgId) {
		List<DepartmentMapper> resultList = new ArrayList<DepartmentMapper>();
		List<Department> departmentList = departmentRepository.getDepartmentListByOrgIdAndLiveInd(orgId,true);

		if (null != departmentList && !departmentList.isEmpty()) {
			for (Department department : departmentList) {

				DepartmentMapper departmentMapper = new DepartmentMapper();
				departmentMapper.setDepartmentId(department.getDepartment_id());
				departmentMapper.setDepartmentName(department.getDepartmentName());
				departmentMapper.setEditInd(department.isEditInd());
				departmentMapper.setCreationDate(Utility.getISOFromDate(department.getCreationDate()));
				departmentMapper.setMandetoryInd(department.isMandetoryInd());
				departmentMapper.setErpInd(department.isErpInd());
				departmentMapper.setCrmInd(department.isCrmInd());
				departmentMapper.setImInd(department.isImInd());
				departmentMapper.setAccountInd(department.isAccountInd());
				departmentMapper.setRecruitOppsInd(department.isRecruitOppsInd());
				departmentMapper.setHrInd(department.isHrInd());
				departmentMapper.setInventoryInd(department.isInventoryInd());
				departmentMapper.setLogisticsInd(department.isLogisticsInd());
				departmentMapper.setOrderManagementInd(department.isOrderManagementInd());
				departmentMapper.setProcurementInd(department.isProcurementInd());
				departmentMapper.setProductionInd(department.isProductionInd());
				departmentMapper.setRecruitProInd(department.isRecruitProInd());
				departmentMapper.setRepairInd(department.isRepairInd());
				departmentMapper.setELearningInd(department.isELearningInd());
				departmentMapper.setFinanceInd(department.isFinanceInd());
				departmentMapper.setServiceLineInd(department.isServiceLineInd());
				if (!StringUtils.isEmpty(department.getSector_id())) {
					SectorDetails sectorDetails = sectorDetailsRepository
							.getSectorDetailsBySectorId(department.getSector_id());
					if (null != sectorDetails) {
						departmentMapper.setSectorName(sectorDetails.getSectorName());
						departmentMapper.setSectorId(department.getSector_id());
					}
				}

				Module module=moduleRepository.findByOrgId(department.getOrgId());
				ModuleDepartmentResponseMapper mapper = new ModuleDepartmentResponseMapper();
				if(null!=module) {
					mapper.setInventoryInd(module.isInventoryInd());
					mapper.setLogisticsInd(module.isLogisticsInd());
					mapper.setOrderManagementInd(module.isOrderManagementInd());
					mapper.setProcurementInd(module.isProcurementInd());
					mapper.setProductionInd(module.isProductionInd());
					mapper.setRecruitProInd(module.isRecruitProInd());
					mapper.setRepairInd(module.isRepairInd());
					mapper.setELearningInd(module.isELearningInd());
					mapper.setHrInd(module.isHrInd());
					mapper.setCrmInd(module.isCrmInd());
					mapper.setErpInd(module.isErpInd());
					mapper.setImInd(module.isImInd());
					mapper.setFinanceInd(module.isFinanceInd());
				}
				departmentMapper.setModuleMapper(mapper);
				resultList.add(departmentMapper);

			}

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<DepartmentDelete> departmentDelete = departmentDeleteRepository.findByOrgId(orgId);
		if (null != departmentDelete && !departmentDelete.isEmpty()) {
			Collections.sort(departmentDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(departmentDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(departmentDelete.get(0).getUserId());
			if(null!=employeeDetails) {
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
	public boolean ipAddressExists(String url) {
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			System.out.println("web>:>:::>::::::>>" + web.toString());
			return true;
		}
		return false;
	}

	@Override
	public List<DepartmentMapper> getAllDepartmentWhereEditInd(String orgIdFromToken) {
		List<DepartmentMapper> resultList = new ArrayList<>();

		List<Department> deptlist = departmentRepository.getDepartmentListByOrgIdAndMandetoryInd(orgIdFromToken);
		if (null != deptlist) {
			deptlist.stream().map(li -> {
				DepartmentMapper departmentMapper = new DepartmentMapper();
				departmentMapper.setDepartmentId(li.getDepartment_id());
				departmentMapper.setDepartmentName(li.getDepartmentName());
				departmentMapper.setEditInd(li.isEditInd());
				departmentMapper.setCrmInd(li.isCrmInd());
				departmentMapper.setErpInd(li.isErpInd());
				departmentMapper.setImInd(li.isImInd());
				departmentMapper.setAccountInd(li.isAccountInd());
				departmentMapper.setRecruitOppsInd(li.isRecruitOppsInd());
				departmentMapper.setHrInd(li.isHrInd());
				departmentMapper.setInventoryInd(li.isInventoryInd());
				departmentMapper.setLogisticsInd(li.isLogisticsInd());
				departmentMapper.setOrderManagementInd(li.isOrderManagementInd());
				departmentMapper.setProcurementInd(li.isProcurementInd());
				departmentMapper.setProductionInd(li.isProductionInd());
				departmentMapper.setRecruitProInd(li.isRecruitProInd());
				departmentMapper.setRepairInd(li.isRepairInd());
				departmentMapper.setELearningInd(li.isELearningInd());
				departmentMapper.setFinanceInd(li.isFinanceInd());

				return resultList.add(departmentMapper);
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public List<DepartmentMapper> getDepartmentByUrl(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<DepartmentMapper> resultList = new ArrayList<DepartmentMapper>();
		List<Department> departmentList = departmentRepository.getDepartmentListByOrgId(web.getOrgId());

		if (null != departmentList && !departmentList.isEmpty()) {
			for (Department department : departmentList) {

				DepartmentMapper departmentMapper = new DepartmentMapper();
				departmentMapper.setDepartmentId(department.getDepartment_id());
				departmentMapper.setDepartmentName(department.getDepartmentName());
				departmentMapper.setEditInd(department.isEditInd());
				departmentMapper.setCreationDate(Utility.getISOFromDate(department.getCreationDate()));
				departmentMapper.setMandetoryInd(department.isMandetoryInd());
				departmentMapper.setCrmInd(department.isCrmInd());
				departmentMapper.setErpInd(department.isErpInd());
				departmentMapper.setImInd(department.isImInd());
				departmentMapper.setAccountInd(department.isAccountInd());
				departmentMapper.setRecruitOppsInd(department.isRecruitOppsInd());
				departmentMapper.setHrInd(department.isHrInd());
				departmentMapper.setInventoryInd(department.isInventoryInd());
				departmentMapper.setLogisticsInd(department.isLogisticsInd());
				departmentMapper.setOrderManagementInd(department.isOrderManagementInd());
				departmentMapper.setProcurementInd(department.isProcurementInd());
				departmentMapper.setProductionInd(department.isProductionInd());
				departmentMapper.setRecruitProInd(department.isRecruitProInd());
				departmentMapper.setRepairInd(department.isRepairInd());
				departmentMapper.setELearningInd(department.isELearningInd());
				departmentMapper.setFinanceInd(department.isFinanceInd());

				if (!StringUtils.isEmpty(department.getSector_id())) {
					SectorDetails sectorDetails = sectorDetailsRepository
							.getSectorDetailsBySectorId(department.getSector_id());
					if (null != sectorDetails) {
						departmentMapper.setSectorName(sectorDetails.getSectorName());
						departmentMapper.setSectorId(department.getSector_id());
					}
				}

				resultList.add(departmentMapper);

			}

		}

		return resultList;
	}

	@Override
	public List<DepartmentMapper> getDepartmentListBySectorId(String sectorId) {
		List<DepartmentMapper> resultList = new ArrayList<DepartmentMapper>();
		List<Department> departmentList = departmentRepository.getDepartmentListBySectorId(sectorId);
		if (null != departmentList && !departmentList.isEmpty()) {
			for (Department department : departmentList) {

				DepartmentMapper departmentMapper = new DepartmentMapper();
				departmentMapper.setDepartmentId(department.getDepartment_id());
				departmentMapper.setDepartmentName(department.getDepartmentName());
				departmentMapper.setEditInd(department.isEditInd());
				departmentMapper.setCreationDate(Utility.getISOFromDate(department.getCreationDate()));
				departmentMapper.setSectorId(department.getSector_id());
				departmentMapper.setMandetoryInd(department.isMandetoryInd());
				departmentMapper.setCrmInd(department.isCrmInd());
				departmentMapper.setErpInd(department.isErpInd());
				departmentMapper.setImInd(department.isImInd());
				departmentMapper.setAccountInd(department.isAccountInd());
				departmentMapper.setRecruitOppsInd(department.isRecruitOppsInd());
				departmentMapper.setHrInd(department.isHrInd());
				departmentMapper.setInventoryInd(department.isInventoryInd());
				departmentMapper.setLogisticsInd(department.isLogisticsInd());
				departmentMapper.setOrderManagementInd(department.isOrderManagementInd());
				departmentMapper.setProcurementInd(department.isProcurementInd());
				departmentMapper.setProductionInd(department.isProductionInd());
				departmentMapper.setRecruitProInd(department.isRecruitProInd());
				departmentMapper.setRepairInd(department.isRepairInd());
				departmentMapper.setELearningInd(department.isELearningInd());
				departmentMapper.setFinanceInd(department.isFinanceInd());

				resultList.add(departmentMapper);

			}

		}

		return resultList;
	}

	@Override
	public List<DesignationMapper> getDesignationByUrl(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<DesignationMapper> resultList = new ArrayList<DesignationMapper>();
		List<Designation> designationList = designationRepository.findByorgId(web.getOrgId());

		if (null != designationList && !designationList.isEmpty()) {
			for (Designation designation : designationList) {

				DesignationMapper designationMapper = new DesignationMapper();
				designationMapper.setDesignationTypeId(designation.getDesignationTypeId());
				designationMapper.setDesignationType(designation.getDesignationType());
				// designationMapper.setEditInd(designation.isEditInd());

				designationMapper.setCreationDate(Utility.getISOFromDate(designation.getCreationDate()));

				resultList.add(designationMapper);

			}

		}

		return resultList;
	}

	@Override
	public List<DepartmentMapper> getDepartmentByNameByOrgLevel(String name, String orgId) {

		List<Department> list = departmentRepository.findByDepartmentNameContainingAndOrgId(name,orgId);
		List<DepartmentMapper> resultList = new ArrayList<DepartmentMapper>();

		if (null != list && !list.isEmpty()) {
			for (Department department : list) {
				DepartmentMapper departmentMapper = getDepartmentTypeById(department.getDepartment_id());
				if (null != departmentMapper) {
					resultList.add(departmentMapper);
				}
			}

		}
		return resultList;
	}

	@Override
	public List<DesignationMapper> getDesignationByNameByOrgLevel(String name, String orgId){

		List<Designation> list = designationRepository.findByDesignationTypeContainingAndOrgId(name,orgId);
		List<DesignationMapper> resultList = new ArrayList<DesignationMapper>();
		if (null != list && !list.isEmpty()) {
			for (Designation designation : list) {
				DesignationMapper designationMapper = getDesignationTypeById(designation.getDesignationTypeId());
				if (null != designationMapper) {
					resultList.add(designationMapper);
				}
			}

		}
		return resultList;

	}

	@Override
	public boolean checkDepartmentNameInDepartmentByOrgLevel(String departmentName, String orgId){
		Department departments = departmentRepository.getByDepartmentNameAndOrgId(departmentName,orgId);
		if (null != departments) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDesignationInDesignationTypeByOrgLevel(String designationType, String orgId) {
		Designation designations = designationRepository.findByDesignationTypeIdAndLiveIndAndOrgId(designationType,true,orgId);
		if (null != designations) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteDepartmentById(String departmentId) {
		if (null != departmentId) {
			Department departments = departmentRepository.getDepartmentDetailsById(departmentId);

			DepartmentDelete departmentdelete=departmentDeleteRepository.findByDepartmentId(departmentId);
			if(null!=departmentdelete){
				departmentdelete.setUpdatedBy(departments.getUser_id());
				departmentdelete.setUpdationDate(new Date());
				departmentDeleteRepository.save(departmentdelete);
			}
			departments.setLiveInd(false);
			departmentRepository.save(departments);
		}

	}

	@Override
	public void deleteDesignationById(String designationTypeId) {
		if (null != designationTypeId) {
			Designation designations = designationRepository.findByDesignationTypeId(designationTypeId);
			
			DesignationDelete designationDelete = designationDeleteRepository.findByDesignationTypeId(designationTypeId);
			if(null!=designationDelete) {
				//designationDelete.setUpdatedBy(designationDelete.getUserId());
				designationDelete.setUpdationDate(new Date());
				designationDelete.setUpdatedBy(designations.getUserId());;
				designationDeleteRepository.save(designationDelete);
			}
			designations.setLiveInd(false);
			designationRepository.save(designations);
		}

	}

	@Override
	public DepartmentMapper updateDepMandetoryInd(String departmentId, DepartmentMapper departmentMapper) {
		DepartmentMapper resultMapper = null;
		if (null != departmentMapper.getDepartmentId()) {
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());

			if (null != dbDepartment.getDepartment_id()) {
				dbDepartment.setMandetoryInd(departmentMapper.isMandetoryInd());
				departmentRepository.save(dbDepartment);
			}
			resultMapper = getDepartmentTypeById(departmentId);	
		}
		return resultMapper;
	}
	
	@Override
	public DepartmentMapper updateDepartmentToAddAccess(String departmentId, DepartmentMapper departmentMapper) {
		DepartmentMapper resultMapper =null;

		if(null!=departmentMapper.getDepartmentId()) {
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
			
			if(null!=dbDepartment.getDepartment_id()) {	
				dbDepartment.setEditInd(departmentMapper.isEditInd());
				departmentRepository.save(dbDepartment);	
				
			}
			resultMapper = getDepartmentTypeById(departmentId);
		}
		return resultMapper;
	}
//
//	@Override
//	public DepartmentMapper updateDepCrmInd(String departmentId, DepartmentMapper departmentMapper) {
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setCrmInd(departmentMapper.isCrmInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	}
//	
//	@Override
//	public DepartmentMapper updateDepErpInd(String departmentId, DepartmentMapper departmentMapper) {
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setErpInd(departmentMapper.isErpInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	}
//	
//	@Override
//	public DepartmentMapper updateDepImInd(String departmentId, DepartmentMapper departmentMapper) {
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setImInd(departmentMapper.isImInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	}
//	
//	@Override
//	public DepartmentMapper updateAccountInd(String departmentId, DepartmentMapper departmentMapper) {
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setAccountInd(departmentMapper.isAccountInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	}
//
//	@Override
//	public DepartmentMapper updateRecruitOppsInd(String departmentId, DepartmentMapper departmentMapper) {
//
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setRecruitOppsInd(departmentMapper.isRecruitOppsInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	
//		
//	}
//	
//	@Override
//	public DepartmentMapper updateHrInd(String departmentId, DepartmentMapper departmentMapper) {
//
//		DepartmentMapper resultMapper = null;
//		if (null != departmentMapper.getDepartmentId()) {
//			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentMapper.getDepartmentId());
//	
//			if (null != dbDepartment.getDepartment_id()) {
//				dbDepartment.setHrInd(departmentMapper.isHrInd());
//				departmentRepository.save(dbDepartment);
//			}
//			resultMapper = getDepartmentTypeById(departmentId);	
//		}
//		return resultMapper;
//	}

	@Override
	public DepartmentMapper updateIndicator(String departmentId, DepartmentRequestMapper departmentRequestMapper) {
		DepartmentMapper resultMapper=new DepartmentMapper();
		
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentId);
	
			if (null != dbDepartment) {
				DepartmentDelete departmentDelete = departmentDeleteRepository.findByDepartmentId(dbDepartment.getDepartment_id());
				if(null!=departmentDelete) {
					departmentDelete.setUpdationDate(new Date());
					departmentDelete.setUpdatedBy(departmentRequestMapper.getUserId());;
					departmentDeleteRepository.save(departmentDelete);
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("account")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setAccountInd(true);
					}else {
						dbDepartment.setAccountInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("crm")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setCrmInd(true);
					}else {
						dbDepartment.setCrmInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("erp")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setErpInd(true);
					}else {
						dbDepartment.setErpInd(false);
						dbDepartment.setRepairInd(false);
						dbDepartment.setLogisticsInd(false);
						dbDepartment.setOrderManagementInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("hr")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setHrInd(true);
					}else {
						dbDepartment.setHrInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("im")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setImInd(true);
					}else {
						dbDepartment.setImInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("mandatory")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setMandetoryInd(true);
					}else {
						dbDepartment.setMandetoryInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("inventory")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setInventoryInd(true);
					}else {
						dbDepartment.setInventoryInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("logistics")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setLogisticsInd(true);
					}else {
						dbDepartment.setLogisticsInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("orderManagement")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setOrderManagementInd(true);
					}else {
						dbDepartment.setOrderManagementInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("procurement")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setProcurementInd(true);
					}else {
						dbDepartment.setProcurementInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("production")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setProductionInd(true);
					}else {
						dbDepartment.setProductionInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("recruitopps")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setRecruitOppsInd(true);
					}else {
						dbDepartment.setRecruitOppsInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("recruitPro")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setRecruitProInd(true);
					}else {
						dbDepartment.setRecruitProInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("repair")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setRepairInd(true);
					}else {
						dbDepartment.setRepairInd(false);
					}
				}	
				if(departmentRequestMapper.getType().equalsIgnoreCase("elearning")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setELearningInd(true);
					}else {
						dbDepartment.setELearningInd(false);
					}
				}
				if(departmentRequestMapper.getType().equalsIgnoreCase("finance")) {
					if(departmentRequestMapper.isValue()) {
						dbDepartment.setFinanceInd(true);
					}else {
						dbDepartment.setFinanceInd(false);
					}
				}
				departmentRepository.save(dbDepartment);
			}
			resultMapper = getDepartmentTypeById(departmentId);	
		
		return resultMapper;
	}

	@Override
	public HashMap getDepartmentCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Department> list = departmentRepository.getByOrgIdAndLiveInd(orgId);
		map.put("DepartmentCount", list.size());
		return map;
	}

	@Override
	public HashMap getDesignationCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Designation> list = designationRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("DesignationCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportDesignationListToExcel(List<DesignationMapper> list) {
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
			for (DesignationMapper sector : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(sector.getDesignationType());
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
	public ByteArrayInputStream exportDepartmentListToExcel(List<DepartmentMapper> list) {
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
			for (DepartmentMapper sector : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(sector.getDepartmentName());
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

}
