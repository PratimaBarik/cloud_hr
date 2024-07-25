package com.app.employeePortal.investorleads.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeads;
import com.app.employeePortal.leads.entity.Leads;

@Repository
public interface InvestorLeadsRepository extends JpaRepository<InvestorLeads, String> {

	@Query(value = "select a  from InvestorLeads a  where a.investorLeadsId=:investorLeadsId and a.liveInd=true  ")
	public InvestorLeads getInvestorLeadsByIdAndLiveInd(@Param(value = "investorLeadsId") String investorLeadsId);

	public List<InvestorLeads> findByUserIdAndLiveInd(String userId, boolean b);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<InvestorLeads> getInvestorLeadsListByUserId(String userId);

	@Query("select a from InvestorLeads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.liveInd = true and a.convertInd=false and a.junkInd=false")
	Page<InvestorLeads> getInvestorLeadsListByUserIdAndPagging(@Param("userId") String userId, Pageable paging);

	@Query("SELECT a FROM InvestorLeads a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.liveInd=true and a.convertInd=false and a.junkInd=false")
	Page<InvestorLeads> getTeamInvestorLeadsListByUserIdAndPagging(@Param("userIds") List<String> userIds,
			Pageable paging);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.liveInd=true and a.convertInd=true and a.junkInd=false "
			+ "and a.convertionDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getQualifiedInvestorLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.creationDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getCreatedInvestorLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=true")
	public List<InvestorLeads> getJunkLeadsListByUserId(@Param(value = "userId") String userId);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=true "
			+ "and a.junkedDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getJunkedInvestorLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.type=:type and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.typeUpdationDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getInvestorLeadsByUserIdAndTypeWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "type") String type, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where a.userId=:userId and a.source=:source and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<InvestorLeads> getInvestorLeadsListByUserIdAndSource(@Param(value = "userId") String userId,
			@Param(value = "source") String source);

	public List<InvestorLeads> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
			boolean b);

	public List<InvestorLeads> findByNameContainingAndLiveInd(String name, boolean liveInd);

	public List<InvestorLeads> findBySectorAndLiveInd(String sectorId, boolean liveInd);

	@Query("select a from InvestorLeads a where a.organizationId = :orgId  and a.liveInd = true and a.convertInd=false and a.junkInd=false")
	Page<InvestorLeads> getInvestorLeadsListByOrgIdAndPagging(@Param("orgId") String orgId, Pageable paging);

	@Query("select a from InvestorLeads a where a.organizationId = :orgId  and a.liveInd = true and a.convertInd=false and a.junkInd=false")
	List<InvestorLeads> getInvestorLeadsListByOrgId(@Param("orgId") String orgId);

	@Query("select a from InvestorLeads a where a.organizationId = :orgId  and a.liveInd = true and a.convertInd=false and a.junkInd=false")
	public List<InvestorLeads> getByOrgIdAndLiveInd(@Param("orgId") String orgId);

	@Query(value = "select a  from InvestorLeads a  where  a.organizationId=:orgId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	List<InvestorLeads> getInvestorLeadsListByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where  a.userId=:userId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	List<InvestorLeads> getInvestorLeadsListByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from InvestorLeads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=true and a.junkInd=false "
			+ "and a.convertionDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getQualifiedInvestorLeadsListByOrgIdAndDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	public List<InvestorLeads> getByUrl(String url);

	@Query(value = "select a  from InvestorLeads a  where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.creationDate BETWEEN :startDate AND :endDate")
	public List<InvestorLeads> getLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	public List<InvestorLeads> getByEmail(String email);

	@Query("SELECT a FROM InvestorLeads a WHERE a.userId IN :userIdss OR a.assignedTo IN :userIdss AND a.liveInd=true and a.convertInd=false and a.junkInd=false")
	List<InvestorLeads> getTeamInvestorLeadsListByUserIds(@Param("userIdss") List<String> userIdss);

	@Query("select a from InvestorLeads a where a.organizationId = :orgId  and a.type =:type and a.liveInd = true and a.convertInd=false and a.junkInd=false")
	public Page<InvestorLeads> getInvLeadsListByOrgIdPaggingAndType(@Param(value = "orgId") String orgId,
			Pageable paging, @Param(value = "type") String type);

	@Query("SELECT a FROM InvestorLeads a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) "
			+ "AND a.type =:type AND a.liveInd = true AND a.convertInd = false AND a.junkInd = false")
	public Page<InvestorLeads> getTeamInvestorsLeadsListByUserIdsPaginatedAType(
			@Param(value = "userIds") List<String> userIds, Pageable paging, @Param(value = "type") String type);

	@Query("select a from InvestorLeads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.type =:type AND a.liveInd = true and a.convertInd=false and a.junkInd=false")
	public Page<InvestorLeads> getInvestorsLeadsListByUserIdPaggingAndType(@Param(value = "userId") String userId,
			Pageable paging, @Param(value = "type") String type);

	@Query("select a from InvestorLeads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.liveInd = true")
	public List<InvestorLeads> getInvLeadsListByUserId(@Param(value = "userId") String userId);

	@Query("SELECT a FROM InvestorLeads a WHERE a.organizationId =:organizationId AND a.liveInd=true And a.name LIKE %:name% and a.convertInd=false and a.junkInd=false")
	List<InvestorLeads> findByNameContainingAndLiveIndAndOrganizationId(
			@Param(value = "organizationId") String organizationId, @Param("name") String name);

	@Query("SELECT a FROM InvestorLeads a WHERE a.userId IN :userId OR a.assignedTo IN :userId AND a.liveInd=true And a.name LIKE %:name% and a.convertInd=false and a.junkInd=false")
	List<InvestorLeads> findByNameContainingAndLiveIndAndUserId(@Param("name") String name,
			@Param(value = "userId") String userId);

	@Query("select a from InvestorLeads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.liveInd = true And a.name LIKE %:name% and a.convertInd=false and a.junkInd=false")
	List<InvestorLeads> findByNameContainingAndLiveIndAndUserIds(@Param("name") String name,
			@Param(value = "userId") List<String> userId);

	public List<InvestorLeads> findBySourceAndLiveIndAndOrganizationId(String sourceId, boolean b, String orgId);

	public List<InvestorLeads> findBySectorAndLiveIndAndOrganizationId(String sectorId, boolean b, String orgId);

	@Query("SELECT a FROM InvestorLeads a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.sector=:sectorId and a.liveInd=true")
	List<InvestorLeads> getTeamLeadsListByUserIdsAndSector(@Param("userIds") List<String> userIds,
			@Param("sectorId") String sectorId);

	@Query("SELECT a FROM InvestorLeads a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.source=:sorceId and a.liveInd=true")
    List<InvestorLeads> getTeaminvestorLeadsListByUserIdsAndSource(@Param("userIds") List<String> userIds, @Param("sorceId") String sorceId);

	@Query("select a from InvestorLeads a where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true")
    List<InvestorLeads> getByUserIdandLiveInd(@Param("userId") String userId);

	public List<InvestorLeads> findBySectorAndLiveIndAndUserId(String sectorId, boolean b, String userId);

	public List<InvestorLeads> findBySourceAndLiveIndAndUserId(String sourceId, boolean b, String userId);
		

}
