package com.app.employeePortal.registration.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.FunctionDetails;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.DepartmentRequestMapper;
import com.app.employeePortal.registration.mapper.DesignationMapper;
import com.app.employeePortal.registration.mapper.FunctionMapper;

public interface DepartmentService {

	public	List<Department> getDepartmentList();

	public List<DepartmentMapper> autocompleteDepartmentByName(String departmentName,String orgId, DepartmentMapper departmentMapper);

	public List<Department> createDepartment(String departmentName );

	public List<DesignationMapper> getDesignationByOrgId(String orgIdFromToken);

	public DesignationMapper saveDesignation(DesignationMapper designationMapper);

	public List<FunctionDetails> getFunctionList();

	public String saveFunction(FunctionMapper functionMapper);

	public DesignationMapper updateDesignation(String designationTypeId, DesignationMapper designationMapper);

	public FunctionMapper updateFunction(String functionTypeId, FunctionMapper functionMapper);

	public DepartmentMapper saveDepartment(DepartmentMapper departmentMapper);

	public DepartmentMapper updateDepartment(String departmentId, DepartmentMapper departmentMapper);

	public boolean deleteDepartment(String departmentTypeId);

	public List<DepartmentMapper> getDepartmentByOrgId(String orgIdFromToken);
	
	boolean ipAddressExists(String url);

	public List<DepartmentMapper> getAllDepartmentWhereEditInd(String orgIdFromToken);

	public List<DepartmentMapper> getDepartmentByUrl(String url);

	public List<DepartmentMapper> getDepartmentListBySectorId(String sectorId);

	public List<DesignationMapper> getDesignationByUrl(String url);

//    public List<DesignationMapper> getDesignationByName(String name);

	public void deleteDepartmentById(String departmentId);

	public void deleteDesignationById(String designationTypeId);

	public DepartmentMapper updateDepMandetoryInd(String departmentId, DepartmentMapper departmentMapper);

	public DepartmentMapper updateDepartmentToAddAccess(String departmentId, DepartmentMapper departmentMapper);

//	public DepartmentMapper updateDepCrmInd(String departmentId, DepartmentMapper departmentMapper);
//
//	public DepartmentMapper updateDepErpInd(String departmentId, DepartmentMapper departmentMapper);
//
//	public DepartmentMapper updateDepImInd(String departmentId, DepartmentMapper departmentMapper);
//
//	public DepartmentMapper updateAccountInd(String departmentId, DepartmentMapper departmentMapper);
//
//	public DepartmentMapper updateRecruitOppsInd(String departmentId, DepartmentMapper departmentMapper);
//
//	public DepartmentMapper updateHrInd(String departmentId, DepartmentMapper departmentMapper);

	public DepartmentMapper updateIndicator(String departmentId, DepartmentRequestMapper departmentMapper);

	public HashMap getDepartmentCountByOrgId(String orgId);

	public HashMap getDesignationCountByOrgId(String orgId);

	DepartmentMapper getDepartmentTypeById(String departmentId);

	public ByteArrayInputStream exportDesignationListToExcel(List<DesignationMapper> list);

	public ByteArrayInputStream exportDepartmentListToExcel(List<DepartmentMapper> list);

	public boolean checkDesignationInDesignationTypeByOrgLevel(String designationType, String orgIdFromToken);

	public List<DepartmentMapper> getDepartmentByNameByOrgLevel(String name, String orgId);

	public List<DesignationMapper> getDesignationByNameByOrgLevel(String name, String orgId);

	public boolean checkDepartmentNameInDepartmentByOrgLevel(String departmentName, String orgId);

	

}
