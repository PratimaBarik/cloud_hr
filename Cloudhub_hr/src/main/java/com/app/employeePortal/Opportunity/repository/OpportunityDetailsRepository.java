package com.app.employeePortal.Opportunity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.investor.entity.InvestorOpportunity;

@Repository
public interface OpportunityDetailsRepository extends JpaRepository<OpportunityDetails, String> {

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	Page<OpportunityDetails> getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndPageWise(
			@Param(value = "userId") String userId, Pageable paging);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.liveInd=true and exp.closeInd=false and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<OpportunityDetails> getOpportunityListByCustomerIdAndLiveInd(@Param(value = "customerId") String customerId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.contactId=:contactId and exp.liveInd=true and exp.closeInd=false and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<OpportunityDetails> getOpportunityListByContactIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(
			@Param(value = "contactId") String contactId);

	List<OpportunityDetails> getOpportunityListByContactIdAndLiveInd(String contactId, boolean liveInd);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.liveInd=true")
	public OpportunityDetails getOpportunityDetailsByOpportunityId(
			@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	public OpportunityDetails getOpenOpportunityDetailsByOpportunityId(
			@Param(value = "opportunityId") String opportunityId);

	// public OpportunityDetails findByOpportunityId(String opportunityId);

	// @Query(value = "select a from OpportunityDetails a where
	// a.opportunityName=:opportunityName " )

	List<OpportunityDetails> findByOpportunityNameContainingAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(
			String opportunityName, boolean liveInd, boolean a, boolean l, boolean b, boolean c);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	OpportunityDetails findByOppId(@Param(value = "opportunityId") String opportunityId);

	List<OpportunityDetails> findByReinstateIndAndLiveInd(boolean reinstateInd, boolean liveInd);

	OpportunityDetails getByOpportunityIdAndReinstateIndAndLiveInd(String opportunityName, boolean reinstateInd,
			boolean liveInd);
	// public List<OpportunityDetails> findByActive(boolean active);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.liveInd=true")
	public List<OpportunityDetails> findByUserIdAndLiveInd(@Param(value = "userId") String userId);

	// @Query(value = "select exp from OpportunityDetails exp where
	// exp.liveInd=true" )

	List<OpportunityDetails> findByliveInd(boolean liveInd);

	// public List<OpportunityDetails> findByCreationDateAndLiveInd(Date
	// date,boolean liveInd);

//	List<OpportunityDetails> findByCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
//			boolean b);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.liveInd=true")
	public OpportunityDetails getopportunityDetailsById(@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId=:opportunityId and exp.liveInd=true")
	public List<OpportunityDetails> getopportunityDetailsByOrgId(@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where  exp.liveInd=true ")
	List<OpportunityDetails> getAllCustomer();

	List<OpportunityDetails> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
			boolean b);

	List<OpportunityDetails> findByOrgIdAndReinstateIndAndLiveIndAndCloseIndAndLostIndAndWonInd(String orgId, boolean b,
			boolean c, boolean d, boolean e, boolean f);

	// List<OpportunityDetails> findByReinstateInd(boolean b);

	// List<OpportunityDetails> findByReinstateIndAndUserId(boolean b, String
	// userId);

	List<OpportunityDetails> findByLiveInd(boolean b);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=true and exp.liveInd=true ")
	Page<OpportunityDetails> getOpportunityDetailsPaseWiseUserIdAndCloseIndAndLiveInd(
			@Param(value = "userId") String userId, Pageable paging);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.lostInd=true and exp.liveInd=true  ")
	Page<OpportunityDetails> getOpportunityDetailsPaseWiseUserIdAndLostIndAndLiveInd(
			@Param(value = "userId") String userId, Pageable paging);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.wonInd=true and exp.liveInd=true  ")
	Page<OpportunityDetails> getOpportunityDetailsPaseWiseUserIdAndWonIndAndLiveInd(
			@Param(value = "userId") String userId, Pageable paging);

	Page<OpportunityDetails> findByReinstateIndAndLiveIndAndUserId(boolean b, boolean c, String loggeduserId,
			Pageable paging);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.contactId=:contactId and exp.liveInd=true")
	List<OpportunityDetails> getOpportunityListByContactIdAndLiveInd(@Param(value = "contactId") String contactId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.liveInd=true and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpportunityListByCustomerIdAndLiveIndAndDateRange(
			@Param(value = "customerId") String customerId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	List<OpportunityDetails> findByOrgIdAndReinstateIndAndLiveIndAndCloseInd(String orgId, boolean reinstateInd,
			boolean liveInd, boolean closeInd);

	@Query(value = "select a  from OpportunityDetails a  where a.oppStage=:oppStage")
	public OpportunityDetails getOppStagedetails(@Param(value = "oppStage") String oppStage);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<OpportunityDetails> getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(
			@Param(value = "userId") String userId);

	List<OpportunityDetails> findByUserIdAndReinstateIndAndLiveInd(String userId, boolean b, boolean c);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=true and exp.liveInd=true ")
	List<OpportunityDetails> getOpportunityDetailsUserIdAndCloseIndAndLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.lostInd=true and exp.liveInd=true  ")
	List<OpportunityDetails> getOpportunityDetailsUserIdAndLostIndAndLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.wonInd=true and exp.liveInd=true  ")
	List<OpportunityDetails> getOpportunityDetailsUserIdAndWonIndAndLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=true and exp.liveInd=true "
			+ "and exp.closeDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getClosedOpportunityDetailsUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getAddedOpportunityDetailsUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	Page<OpportunityDetails> getOpportunityDetailsListByOrgId(@Param("orgId") String orgId, Pageable paging);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityListByOrgId(@Param("orgId") String orgId);

	@Query("select a from OpportunityDetails a where a.userId IN :userIds AND a.liveInd = true")
	Page<OpportunityDetails> getTeamOppListByUserIdsPaginated(@Param(value = "userIds") List<String> userIds,
			Pageable paging);

	@Query("select a from OpportunityDetails a where a.userId IN :userIds AND a.liveInd = true")
	List<OpportunityDetails> getTeamOppListByUserIds(@Param(value = "userIds") List<String> userIds);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.wonInd=true and exp.liveInd=true  ")
	List<OpportunityDetails> getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(
			@Param(value = "customerId") String customerId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId=:orgId and exp.closeInd=true and exp.liveInd=true "
			+ "and exp.closeDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getClosedOpportunityDetailsOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=true and exp.liveInd=true "
			+ "and exp.closeDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getClosedOpportunityDetailsuserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId=:orgId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.startDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpenOpportunityDetailsByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.startDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpenOpportunityDetailsByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query("select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.liveInd=true and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpportunityDetailsByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query("select exp  from OpportunityDetails exp  where exp.orgId=:orgId and exp.liveInd=true and exp.creationDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpportunityDetailsByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query("select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.liveInd=true and exp.reinstateInd=false ")
	OpportunityDetails getByOpportunityIdAndReinstateIndAndLiveInd(
			@Param(value = "opportunityId") String opportunityId);

	@Query("select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.liveInd=true and exp.reinstateInd=false ")
	OpportunityDetails getByOpportunityIdAndCloseIndAndLiveInd(@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.closeInd=true and exp.liveInd=true ")
	OpportunityDetails getOpportunityDetailsOpportunityIdAndCloseIndAndLiveInd(
			@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.lostInd=true and exp.liveInd=true  ")
	OpportunityDetails getOpportunityDetailsOpportunityIdAndLostIndAndLiveInd(
			@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.opportunityId=:opportunityId and exp.wonInd=true and exp.liveInd=true  ")
	OpportunityDetails getOpportunityDetailsOpportunityIdAndWonIndAndLiveInd(
			@Param(value = "opportunityId") String opportunityId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityByUserId(@Param(value = "userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.reinstateInd=false and exp.liveInd=true ")
	List<OpportunityDetails> getByUserIdAndReinstateIndAndLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=true and exp.liveInd=true ")
	List<OpportunityDetails> getByUserIdAndCloseIndAndLiveInd(@Param("userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.lostInd=true and exp.liveInd=true ")
	List<OpportunityDetails> getByUserIdAndLostIndAndLiveInd(@Param("userId") String userId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.wonInd=true and exp.liveInd=true ")
	List<OpportunityDetails> getByUserIdAndWonIndAndLiveInd(@Param("userId") String userId);

	@Query("select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<OpportunityDetails> getOpenOpportunityByCustomerId(@Param(value = "customerId") String customerId);

	@Query("select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false and exp.creationDate BETWEEN :start_month AND :end_month ")
	List<OpportunityDetails> getOpportunityByCustomerIdWithDateRange(@Param(value = "customerId") String customerId,
			@Param(value = "start_month") Date start_month, @Param(value = "end_month") Date end_month);

	@Query("select exp  from OpportunityDetails exp  where exp.contactId=:contactId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false ")
	List<OpportunityDetails> getOpenOpportunityListByContactId(@Param(value = "contactId") String contactId);

	@Query("select exp  from OpportunityDetails exp  where exp.contactId=:contactId and exp.wonInd=true and exp.liveInd=true ")
	List<OpportunityDetails> getWonOpportunityListByContactId(@Param(value = "contactId") String contactId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.customerId=:customerId and exp.liveInd=true ")
	List<OpportunityDetails> getOpportunityByCustomerId(@Param(value = "customerId") String customerId);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.userId=:userId and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false "
			+ "and exp.endDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getOpportunityDetailsByUserIdAndQuarter(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.wonInd=true and exp.liveInd=true and exp.wonDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getWonDataBetweenStartDateAndEmployeeId(@Param("userId") String userId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where exp.orgId = :orgId and exp.wonInd=true and exp.liveInd=true and exp.wonDate BETWEEN :startDate AND :endDate ")
	List<OpportunityDetails> getWonOppListByWonDateAndOrgId(@Param("orgId") String orgId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.lostInd=true and exp.liveInd=true and exp.lostDate BETWEEN :startDate AND :endDate")
	List<OpportunityDetails> getLostDataBetweenStartDateAndEmployeeId(@Param("userId") String userId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	int countByCreationDate(Date creationDate);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityByUserIdAndOppName(@Param(value = "userId") String userId);

	List<OpportunityDetails> findByOpportunityNameContainingAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonIndAndOrgId(
			String opportunityName, boolean b, boolean c, boolean d, boolean e, boolean f, String orgId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId IN :userIdss OR exp.assignedTo IN :userIdss) and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityByUserIdsAndOppName(@Param(value = "userIdss") List<String> userIdss);

	List<OpportunityDetails> findByNewOppIdContainingAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonIndAndOrgId(
			String name, boolean b, boolean c, boolean d, boolean e, boolean f, String orgId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.newOppId LIKE %:newOppId% and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityByUserIdAndNewOppId(@Param(value = "userId") String userId, @Param(value = "newOppId")String newOppId);

	@Query(value = "select exp  from OpportunityDetails exp  where (exp.userId IN :userIdss OR exp.assignedTo IN :userIdss) and exp.newOppId LIKE %:newOppId% and exp.closeInd=false and exp.liveInd=true and exp.reinstateInd=true and exp.lostInd=false and exp.wonInd=false")
	List<OpportunityDetails> getOpportunityByUserIdsAndNewOppId(@Param(value = "userIdss") List<String> userIdss, @Param(value = "newOppId")String newOppId);

    List<OpportunityDetails> findByLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonIndAndOrgId(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, String orgId);

//	@Query("SELECT od FROM OpportunityDetails od " +
//            "JOIN OpportunityIncludedLink osul ON od.opportunityId = osul.opportunity_id " +
//            "WHERE osul.employee_id = :userId AND od.startDate BETWEEN :startDate AND :endDate")
//    List<OpportunityDetails> findByStartDateAndEmployeeId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") String userId);
}
