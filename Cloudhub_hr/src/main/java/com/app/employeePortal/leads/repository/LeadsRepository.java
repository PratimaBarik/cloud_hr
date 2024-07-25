package com.app.employeePortal.leads.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.leads.entity.Leads;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, String> {

	@Query(value = "select a  from Leads a  where a.leadsId=:leadsId and a.liveInd=true  ")
	public Leads getLeadsByIdAndLiveInd(@Param(value = "leadsId") String leadsId);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getLeadsListByUserId(@Param(value = "userId") String userId);

	@Query("select a from Leads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.liveInd = true and a.convertInd=false and a.junkInd=false")
	Page<Leads> getInvestorsListByUserIdPagging(@Param("userId") String userId, Pageable paging);

	@Query("SELECT a FROM Leads a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) "
			+ "AND a.liveInd = true AND a.convertInd = false AND a.junkInd = false")
	Page<Leads> getTeamInvestorsListByUserIdsPaginated(List<String> userIds, Pageable pageable);

	@Query("SELECT a FROM Leads a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) "
			+ "AND a.type =:type AND a.liveInd = true AND a.convertInd = false AND a.junkInd = false")
	Page<Leads> getTeamInvestorsListByUserIdsPaginatedAType(List<String> userIds, Pageable pageable, String type);

	public List<Leads> findByCompanyNameContainingAndLiveIndAndConvertIndAndJunkInd(String name, boolean b, boolean c,
			boolean d);

	@Query(value = "select a  from Leads a  where a.sector=:sector and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getSectorLinkBySector(@Param(value = "sector") String sector);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getLeadsListByOwnerId(@Param(value = "userId") String userId);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getLeadsListByOrgId(@Param(value = "orgId") String orgId);

	@Query("select a from Leads a where a.organizationId = :orgId AND a.liveInd = true and a.type =:type and a.convertInd=false and a.junkInd=false")
	public Page<Leads> getLeadsListByOrgIdPagging(@Param("orgId") String orgId, Pageable paging, String type);

	@Query(value = "select a  from Leads a  where a.type=:type and a.liveInd=true and a.convertInd=false  and a.junkInd=false")
	public List<Leads> getLeadsListByType(@Param(value = "type") String type);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=true and a.junkInd=false "
			+ "and a.convertionDate BETWEEN :startDate AND :endDate")
	public List<Leads> getQualifiedLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=true and a.junkInd=false "
			+ "and a.convertionDate BETWEEN :startDate AND :endDate")
	public List<Leads> getQualifiedLeadsListByOrgIdAndDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.creationDate BETWEEN :startDate AND :endDate")
	public List<Leads> getCreatedLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.creationDate BETWEEN :startDate AND :endDate")
	public List<Leads> getCreatedLeadsListByOrgIdAndDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=true "
			+ "and a.junkedDate BETWEEN :startDate AND :endDate")
	public List<Leads> getJunkedLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=false and a.junkInd=true "
			+ "and a.junkedDate BETWEEN :startDate AND :endDate")
	public List<Leads> getJunkedLeadsListByOrgIdAndDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.liveInd=true and a.convertInd=false and a.junkInd=true")
	public List<Leads> getJunkLeadsListByUserId(@Param(value = "userId") String userId);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.liveInd=true and a.convertInd=false and a.junkInd=true")
	public List<Leads> getJunkLeadsListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.type=:type and a.liveInd=true")
	public List<Leads> getLeadsByUserIdAndType(@Param(value = "userId") String userId,
			@Param(value = "type") String type);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.type=:type and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.typeUpdationDate BETWEEN :startDate AND :endDate")
	public List<Leads> getLeadsByUserIdAndTypeWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "type") String type, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.type=:type and a.liveInd=true")
	public List<Leads> getLeadsByOrgIdAndType(@Param(value = "orgId") String orgId, @Param(value = "type") String type);

	@Query(value = "select a  from Leads a  where a.organizationId=:orgId and a.type=:type and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.typeUpdationDate BETWEEN :startDate AND :endDate")
	public List<Leads> getLeadsByOrgIdAndTypeWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "type") String type, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where a.userId=:userId and a.source=:source and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getLeadsListByUserIdAndSource(@Param(value = "userId") String userId,
			@Param(value = "source") String source);

	public List<Leads> findByNameContainingAndLiveIndAndConvertIndAndJunkInd(String name, boolean b, boolean c,
			boolean d);

	@Query("SELECT a FROM Leads a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) "
			+ "AND a.liveInd = true AND a.convertInd = false AND a.junkInd = false")
	public List<Leads> getTeamLeadsListByUserIds(List<String> userIds);

	public List<Leads> getByUrl(String url);

	@Query(value = "select a  from Leads a  where  a.organizationId=:orgId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	public List<Leads> getLeadsListByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where  a.userId=:userId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	public List<Leads> getLeadsListByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Leads a  where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true and a.convertInd=false and a.junkInd=false")
	public List<Leads> getLeadListByUserId(@Param(value = "userId") String userId);

	@Query(value = "select a  from Leads a  where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true and a.convertInd=false and a.junkInd=false "
			+ "and a.creationDate BETWEEN :startDate AND :endDate")
	public List<Leads> getLeadsListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	public List<Leads> getByEmail(String email);

	public Leads findByEmail(String unencryptedemail);

	@Query("select a from Leads a where (a.userId = :userId OR a.assignedTo = :userId) AND a.type =:type AND a.liveInd = true and a.convertInd=false and a.junkInd=false")
	Page<Leads> getInvestorsListByUserIdPaggingAndType(@Param(value = "userId") String userId, Pageable paging,
			@Param(value = "type") String type);

	public List<Leads> findByLiveIndAndConvertIndAndJunkInd(boolean b, boolean c, boolean d);

	public List<Leads> findByLiveInd(boolean b);

	public List<Leads> findBySectorAndLiveIndAndOrganizationId(String sectorId, boolean b, String orgId);

	public List<Leads> findBySourceAndLiveIndAndOrganizationId(String sourceId, boolean b, String orgId);

	public List<Leads> findByUserIdAndLiveInd(String userId,boolean b);

	@Query("SELECT a FROM Leads a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.sector=:sectorId and a.liveInd=true")
	List<Leads> getTeamLeadsListByUserIdsAndSector(@Param("userIds") List<String> userIds,
			@Param("sectorId") String sectorId);

	@Query("SELECT a FROM Leads a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.source=:sorceId and a.liveInd=true")
    List<Leads> getTeamLeadsListByUserIdsAndSource(@Param("userIds") List<String> userIds, @Param("sorceId") String sorceId);

	@Query("select a from Leads a where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true")
    List<Leads> getByUserIdandLiveInd(@Param("userId") String userId);

	public List<Leads> findBySectorAndLiveIndAndUserId(String sectorId, boolean b, String userId);

	public List<Leads> findBySourceAndLiveIndAndUserId(String sourceId, boolean b, String userId);


}
