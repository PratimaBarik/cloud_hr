package com.app.employeePortal.Opportunity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.Opportunity.entity.OpportunitySalesUserLink;

public interface OpportunitySalesUserRepository extends JpaRepository<OpportunitySalesUserLink, String> {
	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:empId and a.live_ind =true" )
	List<OpportunitySalesUserLink> getSalesUserLinkByUserId(@Param(value="empId")String empId);
	
	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:empId and a.live_ind =true" )
	Page<OpportunitySalesUserLink> getSalesUserLinkByUserIdWithPagination(@Param(value="empId")String empId, Pageable paging);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.opportunity_id=:opportunityId" )
	OpportunitySalesUserLink getSalesUserByOppId(@Param(value="opportunityId")String opportunityId);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:employeeId" )
	List<OpportunitySalesUserLink> getemployeeListByEmployeeId(@Param(value="employeeId")String employeeId);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:employeeId" )
	List<OpportunitySalesUserLink> getemployeeListByEmployeeIdAndDateRange(@Param(value="employeeId")String employeeId);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:userId and a.live_ind=true and a.creationDate BETWEEN :startDate AND :endDate")
	List<OpportunitySalesUserLink> getemployeeListByEmployeeIdAndDateRange(@Param(value ="userId")String userId, 
																@Param(value ="startDate") Date startDate, 
																@Param(value ="endDate") Date endDate);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.orgId=:orgId and a.live_ind =true ")
	List<OpportunitySalesUserLink> getOpportunitySalesListByOrgId(@Param(value="orgId") String orgId);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.employee_id=:userId")
	List<OpportunitySalesUserLink> getCustomerListByUserId(@Param(value ="userId")String userId);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.orgId=:orgId and a.live_ind=true and a.creationDate BETWEEN :startDate AND :endDate")
	List<OpportunitySalesUserLink> getemployeeListByOrganisationIdAndDateRange(@Param(value ="orgId")String orgId,
																		@Param(value ="startDate") Date startDate, 
																		@Param(value ="endDate") Date endDate);

	@Query(value = "select a  from OpportunitySalesUserLink a  where a.opportunity_id=:opportunityId" )
	List<OpportunitySalesUserLink> getSalesUsersByOppId(@Param(value="opportunityId")String opportunityId);
}