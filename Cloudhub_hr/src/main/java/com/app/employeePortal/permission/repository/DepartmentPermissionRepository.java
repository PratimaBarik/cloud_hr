package com.app.employeePortal.permission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.DepartmentPermission;
@Repository

public interface DepartmentPermissionRepository extends JpaRepository<DepartmentPermission, String> {	 
	 
	 @Query(value = "select a  from DepartmentPermission a  where a.roleTypeId=:roleTypeId and a.orgId=:loggedInOrgId" )
	 public DepartmentPermission getDepartmentPermission(@Param(value="roleTypeId")String roleTypeId,
			 												@Param(value="loggedInOrgId") String loggedInOrgId);

	 @Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.customerCreateInd=true" )
	public List<DepartmentPermission> getDepartmentPermissionByOrganizationId(@Param(value="organizationId")String organizationId);	

	 @Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.vendorCreateInd=true" )
		public List<DepartmentPermission> getDepartmentPermissionsByOrganizationId(@Param(value="organizationId")String organizationId);	

	 @Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.opportunityCreateInd=true" )
		public List<DepartmentPermission> getDepartmentPermissionssByOrganizationId(@Param(value="organizationId")String organizationId);	

	 @Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.requirementCreateInd=true" )
		public List<DepartmentPermission> getDepartmentPermissionnByOrganizationId(@Param(value="organizationId")String organizationId);
	@Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.leadsCreateInd=true" )
	List<DepartmentPermission> getByOrganizationIdAndLeadsCreateInd(@Param(value="organizationId")String organizationId);
	@Query(value = "select a  from DepartmentPermission a  where a.orgId=:organizationId and a.contactCreateInd=true" )
	List<DepartmentPermission> getDepartmentPermissionsByOrganizationIdAndContactCreateInd(@Param(value="organizationId")String organizationId);
}