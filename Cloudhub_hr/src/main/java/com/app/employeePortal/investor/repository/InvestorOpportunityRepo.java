package com.app.employeePortal.investor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.investor.entity.InvestorOpportunity;

public interface InvestorOpportunityRepo extends JpaRepository<InvestorOpportunity, String> {
	@Query(value = "select exp  from InvestorOpportunity exp  where exp.invOpportunityId=:invOpportunityId and exp.liveInd=true")
	InvestorOpportunity getOpportunityDetailsByOpportunityId(@Param("invOpportunityId") String invOpportunityId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.userId=:userId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getInvOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndPageWise(
			@Param("userId") String userId, Pageable paging);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.invOpportunityId=:invOpportunityId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	InvestorOpportunity getOpenInvOpportunityDetailsByOpportunityId(@Param("invOpportunityId") String invOpportunityId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.contactId=:contactId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpportunityListByContactIdAndLiveInd(@Param("contactId") String contactId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.investorId=:investorId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpportunityListByInvestorIdAndLiveInd(@Param("investorId") String investorId);
	
	@Query(value = "select exp  from InvestorOpportunity exp  where exp.investorId=:investorId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=true")
	List<InvestorOpportunity> getOpportunityListByInvestorIdAndLiveIndAndWonInd(@Param("investorId") String investorId);

	List<InvestorOpportunity> findByOpportunityNameContainingAndLiveInd(String opportunityName, boolean b);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(
			@Param("userId") String userId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpenInvOpportunityDetailsListByOrgIdAndLiveInd(
		@Param("orgId") String orgId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.userId=:userId and exp.closeInd=true and exp.liveInd=true "
			+ "and exp.closeDate BETWEEN :startDate AND :endDate ")
	List<InvestorOpportunity> getClosedInvOpportunityDetailsUserIdWithDateRange(@Param("userId") String userId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.userId=:userId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<InvestorOpportunity> getAddedInvOpportunityDetailsUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	List<InvestorOpportunity> findByUserIdAndReinstateIndAndLiveInd(String userId, boolean b, boolean b1);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=true and exp.liveInd=true ")
	Page<InvestorOpportunity> getOpportunityDetailsPaseWiseUserIdAndCloseIndAndLiveInd(
			@Param(value = "userId") String userId, Pageable paging);

	List<InvestorOpportunity> findByUserIdAndCloseIndAndLiveInd(String userId, boolean b, boolean b1);

	List<InvestorOpportunity> findByUserIdAndLostIndAndLiveInd(String userId, boolean b, boolean b1);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.lostInd=true and exp.liveInd=true ")
	Page<InvestorOpportunity> getByUserIdAndLostIndAndLiveIndPagination(@Param("userId") String userId,
			Pageable paging);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.wonInd=true and exp.liveInd=true ")
	Page<InvestorOpportunity> getByUserIdAndWonIndAndLiveIndPagination(@Param("userId") String userId, Pageable paging);

	List<InvestorOpportunity> findByUserIdAndWonIndAndLiveInd(String userId, boolean b, boolean b1);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.reinstateInd=false and exp.liveInd=true ")
	Page<InvestorOpportunity> getByUserIdAndReinstateIndIndAndLiveIndPagination(@Param("userId") String userId,
			Pageable paging);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	Page<InvestorOpportunity> getOpenInvOpportunityDetailsListByOrgId(@Param("orgId") String orgId, Pageable paging);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.oppWorkflow=:investorOppWorkflowId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpportunityListByInvestorOppWorkflowIdAndLiveInd(
			@Param("investorOppWorkflowId") String investorOppWorkflowId);

	@Query("select a from InvestorOpportunity a where a.userId IN :userIds AND a.liveInd = true")
	Page<InvestorOpportunity> getTeamInvestorsOppListByUserIdsPaginated(@Param("userIds") List<String> userIds,
			Pageable paging);

	@Query("select a from InvestorOpportunity a where a.userId IN :userIds AND a.liveInd = true")
	List<InvestorOpportunity> getTeamInvestorOppListByUserIds(@Param("userIds") List<String> userIds);

	@Query("select a from InvestorOpportunity a where a.investorId=:investorId AND a.liveInd = true and a.reinstateInd=true")
	List<InvestorOpportunity> getByInvestorId(@Param("investorId") String investorId);

	@Query("select a from InvestorOpportunity a where a.investorId=:investorId AND a.wonInd = true AND a.liveInd = true")
	List<InvestorOpportunity> getByInvestorIdAndWonInd(@Param("investorId") String investorId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<InvestorOpportunity> getOpenInvOpportunityDetailsUserIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp from InvestorOpportunity exp  where exp.orgId=:orgId and exp.closeInd=true and exp.liveInd=true "
			+ "and exp.closeDate BETWEEN :startDate AND :endDate ")
	List<InvestorOpportunity> getClosedInvOpportunityDetailsOrgIdWithDateRange(@Param(value ="orgId") String orgId,
			@Param(value ="startDate") Date startDate, @Param(value ="endDate") Date endDate);

	@Query("select exp from InvestorOpportunity exp where exp.orgId=:orgId and exp.liveInd= true and exp.creationDate BETWEEN :start_date AND :end_date ")
	List<InvestorOpportunity> getInvOpportunityDetailsByOrgIdWithDateRange(@Param(value ="orgId")String orgId, @Param(value ="start_date")Date start_date, @Param(value ="end_date")Date end_date);

	@Query("select exp from InvestorOpportunity exp where exp.userId=:userId and exp.liveInd= true and exp.creationDate BETWEEN :start_date AND :end_date ")
	List<InvestorOpportunity> getInvOpportunityDetailsByUserIdWithDateRange(@Param(value ="userId")String userId, @Param(value ="start_date")Date start_date, @Param(value ="end_date")Date end_date);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> getOpportunityByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(@Param("userId") String userId);
	
	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.reinstateInd=false and exp.liveInd=true ")
	List<InvestorOpportunity> getByUserIdAndReinstateIndAndLiveInd(@Param("userId") String userId);
	
	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=true and exp.liveInd=true ")
	List<InvestorOpportunity> getByUserIdAndCloseIndAndLiveInd(@Param("userId") String userId);
	
	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.lostInd=true and exp.liveInd=true ")
	List<InvestorOpportunity> getByUserIdAndLostIndAndLiveInd(@Param("userId") String userId);
	
	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.wonInd=true and exp.liveInd=true ")
	List<InvestorOpportunity> getByUserIdAndWonIndAndLiveInd(@Param("userId") String userId);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	Page<InvestorOpportunity> getOpenInvOpportunityDetailsListByUserId(@Param("userId") String userId, Pageable paging);

	@Query("select exp  from InvestorOpportunity exp  where exp.contactId=:contactId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<InvestorOpportunity> getOpenInvOpportunityListByContactId(@Param(value = "contactId") String contactId);

	@Query("select exp  from InvestorOpportunity exp  where exp.contactId=:contactId and exp.wonInd=true and exp.liveInd=true ")
	List<InvestorOpportunity> getWonInvOpportunityListByContactId(@Param(value = "contactId") String contactId);

	@Query(value = "select exp  from InvestorOpportunity exp  where exp.investorId=:investorId and exp.liveInd=true ")
	List<InvestorOpportunity> getOpportunityListByInvestorId(@Param("investorId") String investorId);
	
	@Query("select exp  from InvestorOpportunity exp  where exp.investorId=:investorId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<InvestorOpportunity> getOpenInvestorOppByInvestorId(@Param(value = "investorId") String investorId);

	Page<InvestorOpportunity> findByReinstateIndAndLiveIndAndUserId(boolean b, boolean c, String loggeduserId,
			Pageable paging);
	
	int countByCreationDate(Date creationDate);

	@Query("select exp from InvestorOpportunity exp where exp.orgId=:orgId and exp.liveInd= true and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByOpportunityNameContainingAndOrgId(@Param("orgId") String orgId);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId IN :userId OR exp.assignedTo IN :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByOpportunityNameContainingAndUserIds(@Param("userId") List<String> userId);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByOpportunityNameContainingAndUserId(@Param("userId")String userId);

	@Query("select exp from InvestorOpportunity exp where exp.orgId=:orgId and exp.newDealNo LIKE %:name% and exp.liveInd= true and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByNewDealNoContainingAndOrgId(@Param("name") String name,@Param("orgId") String orgId);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId IN :userId OR exp.assignedTo IN :userId) and exp.newDealNo LIKE %:name% and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByNewDealNoContainingAndUserIds(@Param("name") String name,@Param("userId") List<String> userId);

	@Query(value = "select exp  from InvestorOpportunity exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.newDealNo LIKE %:name% and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<InvestorOpportunity> findByNewDealNoContainingAndUserId(@Param("name") String name,@Param("userId")String userId);
}
