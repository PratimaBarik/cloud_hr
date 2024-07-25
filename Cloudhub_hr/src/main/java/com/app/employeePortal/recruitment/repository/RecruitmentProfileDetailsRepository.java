package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;

public interface RecruitmentProfileDetailsRepository extends JpaRepository<RecruitProfileLinkDetails, String>{
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.opp_id=:oppId and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfilesByOppId(@Param(value="oppId")String oppId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.opp_id=:oppId and a.profile_id= :profileId and a.live_ind = true" )
	public RecruitProfileLinkDetails getProfilesByOppIdAndProfileId(@Param(value="oppId")String oppId,@Param(value="profileId")String profileId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.profile_id=:profileId and a.live_ind = true" )
	public RecruitProfileLinkDetails getprofiledetails(@Param(value="profileId")String profileId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.recruitment_id=:recruitment_id and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByRecruitmentId(@Param(value="recruitment_id")String recruitment_id);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.recruitment_id=:recruitmentId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByRecruitmentIdAndonBoardInd(@Param(value="recruitmentId")String recruitmentId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.opp_id=:oppId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getByOppIdAndOnboardIndAndLiveInd(@Param(value="oppId")String oppId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.stage_id=:stageId and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByStageId(@Param(value="stageId")String stageId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.stage_id=:stageId and a.recruitment_id=:recruitmentId and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByStageIdAndRecruitmentId(@Param(value="stageId")String stageId, @Param(value="recruitmentId")String recruitmentId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.opp_id=:opportunityId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByOppertunityIdAndonBoardInd(@Param(value="opportunityId")String opportunityId);

	@Query(value = "select a from RecruitProfileLinkDetails a  where a.recruitment_id=:recruitmentId and a.onboard_ind = true and a.onboard_date BETWEEN :startDate AND :endDate and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(@Param(value="recruitmentId")String recruitmentId,
															@Param(value ="startDate") Date startDate, 
															@Param(value ="endDate") Date endDate);
			

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.recruitment_id=:recruitmentId and a.live_ind = true and a.creation_date BETWEEN :startDate AND :endDate" )
	public List<RecruitProfileLinkDetails> getProfileDetailByDateRange(@Param(value="recruitmentId")String recruitmentId,
															@Param(value ="startDate") Date startDate, 
															@Param(value ="endDate") Date endDate);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.profile_id=:profileId and a.onboard_ind = true and a.live_ind = true" )
	public RecruitProfileLinkDetails getByProfileIdAndOnboardedInd(@Param(value="profileId")String profileId);

	//@Query(value = "select distinct a.candidateId,a.creation_date from RecruitProfileLinkDetails a  where a.userId=:userId and a.onboard_ind = true and a.live_ind = true" )
	@Query(value = "SELECT DISTINCT t.candidate_id, t.creation_date FROM (SELECT candidate_id, MAX(creation_date) AS creation_date FROM recruitment_profile_details WHERE user_id =:userId AND onboard_ind = true AND live_ind = true GROUP BY candidate_id) t ORDER BY t.creation_date desc",nativeQuery = true)
	public Page<Object[]> getProfileDetailsByUserIdAndOnboardedInd(@Param(value="userId") String userId, Pageable paging1);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.onboard_ind = true and a.live_ind = true order by onboard_date desc " )
	public List<RecruitProfileLinkDetails> getProfileDetailsByCandidateIdAndOnboardedInd(@Param(value="candidateId")String candidateId, Pageable paging);


	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailsByCandidateId(@Param(value="candidateId")String candidateId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId " )
	public List<RecruitProfileLinkDetails> getProfileDetailsByCandidateIdWithOutLiveInd(@Param(value="candidateId")String candidateId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.orgId=:orgId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailsByOrgId(@Param(value="orgId")String orgId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.orgId=:orgId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByPOrgIdAndonBoardInd(@Param(value="orgId")String orgId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.recruitOwner=:userId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByPUserIdAndonBoardInd(@Param(value="userId")String userId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByCandidateIdAndonBoardInd(@Param(value="candidateId")String candidateId);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.recruitment_id IN :recruitmentId and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByRecruitmentIdInAndonBoardInd(@Param(value="recruitmentId")List<String> recruitmentId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.projectName=:projectName and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailsByCandidateIdAndProjectName(@Param(value="candidateId")String candidateId, @Param(value="projectName")String projectName);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getActiveProfileDetailsByCandidateId(@Param(value="candidateId")String candidateId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.customerId=:customerId and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailsByCustomerId(@Param(value="customerId")String customerId);

	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.projectName=:projectName and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getCandidateByProjectName(@Param(value="projectName")String projectName);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.candidateId=:candidateId and a.projectName=:projectName and a.onboard_ind = true and a.live_ind = true" )
	public RecruitProfileLinkDetails getProfileDetailByCandidateIdAndProjectName(@Param(value="candidateId")String candidateId, @Param(value="projectName")String projectName);
	
	@Query(value = "select a  from RecruitProfileLinkDetails a  where a.customerId=:customerId and a.projectName=:projectName and a.onboard_ind = true and a.live_ind = true" )
	public List<RecruitProfileLinkDetails> getProfileDetailByCustomerIdAndProjectName(@Param(value="customerId")String customerId, @Param(value="projectName")String projectName);
	
}
