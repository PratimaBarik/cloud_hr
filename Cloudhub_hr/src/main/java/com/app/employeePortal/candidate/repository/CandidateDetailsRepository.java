package com.app.employeePortal.candidate.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateDetails;

@Repository

public interface CandidateDetailsRepository extends JpaRepository<CandidateDetails, String>{
	
	@Query(value = "select exp  from CandidateDetails exp  where exp.candidateId=:candidateId and exp.liveInd=true" )
	public CandidateDetails getcandidateDetailsById(@Param(value="candidateId") String candidateId);
	
	@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
    public Page<CandidateDetails> getCandidateListPageWiseByUserId(@Param(value="userId")String userId, Pageable paging);
	
	@Query(value = "select a  from CandidateDetails a  where a.userId IN :userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
    public Page<CandidateDetails> getCandidateListPageWiseByUserIds(@Param(value="userId")List<String> userId, Pageable paging);
	
	@Query(value = "select a  from CandidateDetails a  where a.organizationId=:orgId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
    public List<CandidateDetails> getCandidateListByOrgId(@Param(value="orgId")String orgId);	
	
	 @Query(value = "select a  from CandidateDetails a  where a.skill=:skill " )
	 public List<CandidateDetails> getcandidateDetailsBySkill(@Param(value="skill")String skill);	

	
	@Query(value = "select a  from CandidateDetails a  where  a.liveInd=true" )
	public List<CandidateDetails> getCandidateDetailsById();

	@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.liveInd=true" )
	public List<CandidateDetails> findByUserIdandLiveInd(@Param(value="userId") String userId);

	@Query(value = "select a  from CandidateDetails a  where a.emailId=:emailId and a.liveInd=true" )
	public CandidateDetails findByEmail(@Param(value="emailId") String emailId);

	
    @Query(value = "select a from CandidateDetails a where a.organizationId =:orgId and a.liveInd = true")
	public List<CandidateDetails> getCandidateDetailsBasedOnrectuitmentDetails(@Param(value="orgId")String orgId);
			                                                                   
    
    @Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.emailId=:emailId and a.liveInd=true" )
	public List<CandidateDetails> getCandidateByUserIdAndEmailId(@Param(value="userId")String userId,@Param(value="emailId") String emailId);
    
    @Query(value = "select a  from CandidateDetails a  where a.fullName=:fullName" )
	public List<CandidateDetails> getCandidateListByFullName(@Param(value="fullName") String fullName);

	//public List<CandidateDetails> findByFullNameContainingAndLiveInd(String fullName, boolean liveInd);

	public List<CandidateDetails> findByCategoryAndUserIdAndLiveIndAndBlockListIndAndReInStateInd(String category,String userId,boolean liveInd,boolean blockListInd,boolean reInStateInd);
	
	//@Query(value = "select a  from CandidateDetails a  where a.fullName LIKE %fullName%" )
	public List<CandidateDetails> findByFullNameLike(String fullName);

	public List<CandidateDetails> findByIdNumberAndLiveIndAndOrganizationId(String idNumber, boolean liveInd, String orgId);

	// @Query(value = "select a  from CandidateDetails a  where a.roleType=:roleType and a.billing=:billing and a.liveInd=true" )
	public List<CandidateDetails> findByRoleTypeAndBillingLessThanEqualAndLiveInd(String roleType, String billing, boolean liveInd);

	public List<CandidateDetails> findCandidatesBySkillAndOrganizationId(String organizationId, String skill);
	
	public List<CandidateDetails> findByCategoryAndOrganizationIdAndLiveInd(String category,String organizationId,boolean liveInd);

	public List<CandidateDetails> findByBillingAndLiveInd(String billing, boolean liveInd);

	public List<CandidateDetails> findByRoleTypeAndLiveInd(String roleType, boolean b);

	

	public List<CandidateDetails> findByRoleTypeAndWorkLocationAndLiveInd(String roleType, String workLocation,
			boolean b);

	public List<CandidateDetails> findByBillingAndWorkLocationAndLiveInd(String billing,
			String workLocation, boolean b);

	public List<CandidateDetails> findByRoleTypeAndWorkPreferanceAndLiveInd(String roleType, String workPreference,
			boolean b);

	public List<CandidateDetails> findByBillingAndWorkPreferanceAndLiveInd(String billing,
			String workPreference, boolean b);

	public List<CandidateDetails> findByEmailId(String emailId);

	@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.blockListInd=true and a.liveInd=true" )
	public List<CandidateDetails> getBlackListCandidateByUserId(@Param(value="userId") String userId);

	public List<CandidateDetails> findByRoleTypeOrWorkLocationAndLiveInd(String roleType, String workLocation,
			boolean b);

	public List<CandidateDetails> findByBillingOrWorkLocationAndLiveInd(String billing, String workLocation, boolean b);

	public List<CandidateDetails> findByRoleTypeOrWorkPreferanceAndLiveInd(String roleType, String workPreference,
			boolean b);

	public List<CandidateDetails> findByBillingOrWorkPreferanceAndLiveInd(String billing, String workPreference,
			boolean b);

	public List<CandidateDetails> findByWorkLocationAndLiveInd(String billing, boolean b);

	public List<CandidateDetails> findByWorkPreferanceAndLiveInd(String roleType, boolean b);

	@Query(value = "select a  from CandidateDetails a  where a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true" )
	public List<CandidateDetails> getCandidateListByDateRange(@Param(value ="startDate") Date startdate,@Param(value ="endDate") Date enddate);
	
	//@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.blockListInd=true" )
	//public List<CandidateDetails> getCandidateRoleByRoleType(String roleTypeId);
	
	@Query(value = "select a  from CandidateDetails a  where a.roleType=:roleTypeId" )
	public List<CandidateDetails> getCandidateRoleByRoleType(@Param(value="roleTypeId") String roleTypeId);
	
	public List<CandidateDetails> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate,
			Date nextDate, boolean b);

	public List<CandidateDetails> findByFullNameContainingAndLiveIndAndOrganizationId(String fullName, boolean b, String orgId);

	public List<CandidateDetails> findByFullNameContainingAndLiveIndAndUserId(String fullName, boolean b,
			String userId);

	public List<CandidateDetails> findByIdNumberAndLiveIndAndUserId(String idNumber, boolean b, String userId);

	public List<CandidateDetails> findByFullNameContainingAndLiveInd(String fullName,boolean b);

	public List<CandidateDetails> findByCandidateId(String candidateId);

	@Query(value = "select a  from CandidateDetails a  where a.candidateId=:candidateId and a.liveInd=true " )
	public CandidateDetails getByCandidateId(@Param(value="candidateId")String candidateId);
	
	@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
    public List<CandidateDetails> getCandidateByUserId(@Param(value="userId")String userId);

	@Query(value = "select a  from CandidateDetails a  where a.userId=:userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=true" )
    public Page<CandidateDetails> getDeletedCandidatePageByUserId(@Param(value="userId")String userId, Pageable paging);
	
	@Query(value = "select a  from CandidateDetails a  where a.category=:category and a.userId=:userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
	public Page<CandidateDetails> getCandidateListByCategoryAndUserIdAndLiveInd(@Param(value="category")String category,@Param(value="userId") String userId,
			Pageable paging);
	
	@Query(value = "select a  from CandidateDetails a  where a.category=:category and a.userId IN :userId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
	public Page<CandidateDetails> getCandidateListByCategoryAndUserIdsAndLiveInd(@Param(value="category")String category,@Param(value="userId") List<String> userId,
			Pageable paging);
	
	@Query(value = "select a  from CandidateDetails a  where a.category=:category and a.candidateId=:candidateId and a.liveInd=true and a.blockListInd=false and a.reInStateInd=false" )
	public CandidateDetails getCandidateListByCategoryAndCandidateIdAndLiveInd(@Param(value="category")String category,@Param(value="candidateId") String candidateId);

	public List<CandidateDetails> findByFullNameContainingAndCategoryAndLiveInd(String fullName,String category, boolean liveInd);
	
	public List<CandidateDetails> findByIdNumberAndCategoryAndLiveInd(String idNumber, String category, boolean b);
	
}
