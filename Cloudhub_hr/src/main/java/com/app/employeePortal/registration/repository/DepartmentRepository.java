package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

	@Query(value = "select a  from Department a  where a.orgId =:orgId and a.departmentName =:deptName")
	public Department getDepartmentListByName(@Param(value = "orgId") String orgId,
			@Param(value = "deptName") String deptName);

	@Query(value = "select a from Department a where a.orgId=:orgId")
	public List<Department> getDepartmentListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from Department a  where a.department_id=:departmentTypeId and a.liveInd=true")
	public Department getDepartmentDetails(@Param(value = "departmentTypeId") String departmentTypeId);

	@Query(value = "select a  from Department a  where a.departmentName =:deptName")
	public Department findByName(@Param(value = "deptName") String deptName);

	@Query(value = "select a  from Department a  where a.orgId=:orgId And (a.departmentName=:departmentName1 OR a.departmentName=:departmentName2)")
	List<Department> getAllSalesAndManagementList(@Param(value = "orgId") String orgId,
			@Param(value = "departmentName1") String departmentName1,
			@Param(value = "departmentName2") String departmentName2);

	// public Department getDepartmentDetailsById(String departmentId);

	@Query(value = "select a  from Department a  where a.department_id=:departmentId")
	public Department getDepartmentDetailsById(@Param(value = "departmentId") String departmentId);

	@Query(value = "select a  from Department a  where a.orgId=:orgId and a.editInd = false")
	public List<Department> getDepartmentListByOrgIdAndEditInd(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from Department a  where a.sector_id=:sectorId ")
	public List<Department> getDepartmentListBySectorId(@Param(value = "sectorId") String sectorId);

	public Department findByDepartmentName(String departmentName);

	@Query(value = "select a from Department a where a.orgId=:orgId and a.liveInd=:liveInd")
	public List<Department> getDepartmentListByOrgIdAndLiveInd(@Param(value = "orgId") String orgId,
			@Param(value = "liveInd") boolean liveInd);
	
	@Query(value = "select a from Department a where a.orgId=:orgId and a.erpInd=:erpInd")
	public List<Department> getDepartmentListByOrgIdAndErpInd(@Param(value = "orgId") String orgId,
			@Param(value = "erpInd") boolean erpInd);
	
	@Query(value = "select a from Department a where a.orgId=:orgId  and a.liveInd= true")
	public List<Department> getDepartmentListsByOrgIdAndLiveInd(@Param(value = "orgId") String orgId);
	
	@Query(value = "select a from Department a where a.orgId=:orgId and a.crmInd=:crmInd")
	public List<Department> getDepartmentListByOrgIdAndCrmInd(@Param(value = "orgId") String orgId,
			@Param(value = "crmInd") boolean crmInd);
	
	@Query(value = "select a  from Department a  where a.orgId=:orgId and a.mandetoryInd = true")
	public List<Department> getDepartmentListByOrgIdAndMandetoryInd(@Param(value = "orgId") String orgId);
	
	@Query(value = "select a  from Department a  where a.departmentName =:deptName and a.liveInd=true")
	Department getByDepartmentName(@Param("deptName") String deptName);

	@Query(value = "select a from Department a where a.orgId=:orgId and a.imInd=:imInd")
	public List<Department> getDepartmentListByOrgIdAndImInd(@Param(value = "orgId") String orgId,
			@Param(value = "imInd") boolean imInd);
	
	@Query(value = "select a from Department a where a.orgId=:orgId and a.liveInd= true")
	public List<Department> getByOrgIdAndLiveInd(String orgId);

	public List<Department> findByDepartmentNameContainingAndOrgId(String name, String orgId);

	@Query(value = "select a  from Department a  where a.departmentName =:deptName and a.orgId=:orgId and a.liveInd=true")
	public Department getByDepartmentNameAndOrgId(@Param("deptName") String deptName, @Param(value = "orgId") String orgId);

}
