package com.app.employeePortal.investor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.Investor;

public interface InvestorRepository extends JpaRepository<Investor, String> {
//    @Query("select a from Investor a where a.userId=:userId and a.liveInd=true")
    @Query("select a from Investor a where (a.userId = :userId OR a.assignedTo = :userId) AND a.liveInd = true")
    Page<Investor> getInvestorsListByUserId(@Param("userId") String userId, Pageable paging);
    
    @Query("select a from Investor a where (a.userId = :userId OR a.assignedTo = :userId) AND a.club =:clubType AND a.liveInd = true")
    Page<Investor> getInvestorsListByUserIdAndClub(@Param("userId") String userId, @Param("clubType")String clubType, Pageable paging);

    @Query("select a from Investor a where a.liveInd=true")
    List<Investor> findByLiveInd();
    
    @Query(value = "select exp  from Investor exp  where exp.name=:name and exp.liveInd=true" )
    List<Investor> findByName(String name);
    
    @Query("select a from Investor a where a.userId=:userId and a.liveInd=true")
    List<Investor> findByUserIdandLiveInd(@Param("userId") String userId);

    List<Investor> findByUserIdAndSourceAndLiveInd(String userId, String sourceId, boolean b);

	List<Investor> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate, boolean b);

	List<Investor> findBySectorAndLiveInd(String sectorId, boolean liveInd);

	List<Investor> findBySectorAndLiveIndAndOrganizationId(String sectorId, boolean liveInd, String orgId);
	
	List<Investor> findBySourceAndLiveIndAndOrganizationId(String sourceId, boolean liveInd, String orgId);
	
	List<Investor> findByClubAndLiveIndAndOrganizationId(String clubId, boolean liveInd, String orgId);
	
	List<Investor> findBySectorAndLiveIndAndUserId(String sectorId, boolean liveInd, String userId);
	
	List<Investor> findBySourceAndLiveIndAndUserId(String sourceId, boolean liveInd, String userId);
	
	List<Investor> findByClubAndLiveIndAndUserId(String clubId, boolean liveInd, String userId);

	List<Investor> findByNameContainingAndLiveInd(String name, boolean liveInd);

	Investor findByNameAndLiveInd(String name, boolean liveInd);

    @Query("select a from Investor a where a.organizationId = :orgId AND a.liveInd = true")
	Page<Investor> getInvestorListPageWiseByOrgId(@Param("orgId")String orgId, Pageable paging);
    
    @Query("select a from Investor a where a.organizationId =:orgId AND a.club =:clubType AND a.liveInd = true")
	Page<Investor> getInvestorListPageWiseByOrgIdAndClub(@Param("orgId")String orgId, @Param("clubType")String clubType, Pageable paging);

    @Query("SELECT a FROM Investor a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.liveInd=true")
	Page<Investor> getTeamInvestorsListByUserIdsPaginated(@Param("userIds")List<String> userIds, Pageable paging);
    
    @Query("SELECT a FROM Investor a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.club =:clubType AND a.liveInd=true")
   	Page<Investor> getTeamInvestorsListByUserIdsAndClubPaginated(@Param("userIds")List<String> userIds, @Param("clubType")String clubType, Pageable paging);
   
//    @Query("select a from Investor a where (a.userId = :userIds OR a.assignedTo = :userIds) and a.liveInd=true")
//	List<Investor> getTeamInvestorsListByUserIds(@Param("userIds")List<String> userIds);

    @Query("SELECT a FROM Investor a WHERE a.userId IN :userIds OR a.assignedTo IN :userIds AND a.liveInd=true")
    List<Investor> getTeamInvestorsListByUserIds(@Param("userIds") List<String> userIds);
    
    @Query("SELECT a FROM Investor a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.sector=:sectorId and a.liveInd=true")
    List<Investor> getTeamInvestorsListByUserIdsAndSector(@Param("userIds") List<String> userIds, @Param("sectorId") String sectorId);
    
    @Query("SELECT a FROM Investor a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.source=:sorceId and a.liveInd=true")
    List<Investor> getTeamInvestorsListByUserIdsAndSource(@Param("userIds") List<String> userIds, @Param("sorceId") String sorceId);
    
    @Query("SELECT a FROM Investor a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.club=:clubId and a.liveInd=true")
    List<Investor> getTeamInvestorsListByUserIdsAndClub(@Param("userIds") List<String> userIds, @Param("clubId") String clubId);

    @Query("select a from Investor a where a.url=:url and a.liveInd=true")
	List<Investor> getByUrl(@Param("url")String url);
    
    @Query(value = "select a  from Investor a  where  a.userId=:userId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	List<Investor> getInvestorListByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate,
			@Param(value ="endDate") Date endDate);
    
    @Query(value = "select a  from Investor a  where  a.organizationId=:orgId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	List<Investor> getInvestorListByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate,
			@Param(value ="endDate") Date endDate);
    
    @Query(value = "select a  from Investor a  where a.investorId=:investorId and a.liveInd=true")
	public Investor getInvestorIdByIdAndLiveInd(@Param(value = "investorId") String investorId);
    
    @Query(value = "select a  from Investor a  where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true  and a.creationDate BETWEEN :startDate AND :endDate")
	public List<Investor> getInvestorListByUserIdAndDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
    
    @Query("select a from Investor a where (a.userId = :userId OR a.assignedTo = :userId) and a.liveInd=true")
    List<Investor> getByUserIdandLiveInd(@Param("userId") String userId);

    @Query(value = "select a  from Investor a  where a.investorId=:investorId and a.liveInd=true")
	List<Investor> getInvestorByIdAndLiveInd(@Param(value = "investorId") String investorId);
    
    @Query("select a from Investor a where a.organizationId = :orgId AND a.liveInd = true")
    List<Investor> getInvestorListByOrgId(@Param("orgId")String orgId);

	Investor findByEmail(String unencryptedemail);

	List<Investor> findByLiveInd(boolean b);

	
	List<Investor> findByClubAndLiveInd(String clubId, boolean b);

	@Query(value = "select exp  from Investor exp  where exp.organizationId = :orgId and exp.liveInd=true")
	public List<Investor> findByOrgIdandLiveInd(@Param(value = "orgId") String orgId);
}
