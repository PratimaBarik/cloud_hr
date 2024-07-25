package com.app.employeePortal.permission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.permission.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

	@Query(value = "select a  from Permission a  where a.userId=:userId" )
	public Permission getPermission(@Param(value="userId")String userId);
	
	@Query(value = "select a  from Permission a  where a.candidateShareInd=true" )
	public List<Permission> getUserList();

	@Query(value = "select a  from Permission a  where a.contactInd=true" )
	public List<Permission> getUserListForContact();

	@Query(value = "select a  from Permission a  where a.customerInd=true" )
	public List<Permission> getUserListForCustomer();

	@Query(value = "select a  from Permission a  where a.opportunityInd=true" )
	public List<Permission> getUserListForOpportunity();

	@Query(value = "select a  from Permission a  where a.partnerInd=true" )
	public List<Permission> getUserListForPartner();

	//public List<Permission> findBycandidateShareInd(String userId, boolean candidateShareInd);

	@Query(value = "select a  from Permission a  where a.partnerContactInd=true" )
	public List<Permission> getUserListForPartnerContact();

	public Permission findByUserId(String userId);

	@Query(value = "select a  from Permission a  where a.callInd=true" )
	public List<Permission> getUserListForCall();

	@Query(value = "select a  from Permission a  where a.taskInd=true" )
	public List<Permission> getUserListForTask();
	
	@Query(value = "select a  from Permission a  where a.eventInd=true" )
	public List<Permission> getUserListForEvent();
	
	@Query(value = "select a  from Permission a  where a.plannerShareInd=true" )
	public List<Permission> getUserListforPlanner();

	@Query(value = "select a  from Permission a  where a.orgId=:orgId and a.candiEmpShareInd=true" )
	public Permission getcandidateShareTrueByOrgId(@Param(value="orgId")String orgId);

	public Permission findByOrgId(String orgId);


	
}
