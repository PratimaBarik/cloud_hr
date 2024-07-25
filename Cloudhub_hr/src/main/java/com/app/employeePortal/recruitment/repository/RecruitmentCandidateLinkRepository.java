package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitmentCandidateLink;


public interface RecruitmentCandidateLinkRepository extends JpaRepository<RecruitmentCandidateLink, String> {

	//RecruitmentCandidateLink fetchRecruitmentCandidateLink(String opportunityId, String profileId, boolean b);
	
	
	  @Query(value = "select a  from RecruitmentCandidateLink a  where a.opportunity_id=:opportunityId and a.profileId=:profileId and a.live_ind=true")
	  RecruitmentCandidateLink getRecruitmentCandidateLink(@Param(value ="opportunityId") String opportunityId,
			  @Param(value = "profileId") String profileId);

	 
	@Query(value = "select a  from RecruitmentCandidateLink a  where a.candidate_id=:candidateId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateRecruitmentLinkByCandidateId(@Param(value ="candidateId") String candidateId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.candidate_id=:candidateId")
	List<RecruitmentCandidateLink> getCandidateRecruitmentLinkByCandidateIdWithOutLiveInd(@Param(value ="candidateId") String candidateId);
	 
	  @Query(value = "select a  from RecruitmentCandidateLink a  where a.recruitment_id=:recruitmentId and a.profileId=:profileId and a.live_ind=true")
	  RecruitmentCandidateLink getRecruitmentCandidateLinkByRecruitId(@Param(value ="recruitmentId") String recruitmentId,
			  @Param(value = "profileId") String profileId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.profileId=:profileId and a.live_ind=true")
	RecruitmentCandidateLink getCandidateProfile(@Param(value = "profileId") String profileId);
	
	@Query(value = "select a  from RecruitmentCandidateLink a  where a.opportunity_id=:oppId and a.recruitment_id=:recruitmentId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateRecruitmentLinkByOppIdAndRecruitId(@Param(value ="oppId") String oppId, @Param(value ="recruitmentId") String recruitmentId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.recruitment_id=:recruitmentId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateList(@Param(value ="recruitmentId")String recruitmentId);
	
	 @Query(value = "select a  from RecruitmentCandidateLink a  where a.opportunity_id=:opportunityId  and a.live_ind=true")
	  List<RecruitmentCandidateLink> getRecruitmentCandidateLinkList(@Param(value ="opportunityId") String opportunityId);


	RecruitmentCandidateLink findByProfileId(String profileId);
	
	@Query(value = "select a  from RecruitmentCandidateLink a  where a.candidate_id=:candidateId and a.live_ind=true and a.recruitment_id=:recruitmentId")
	List<RecruitmentCandidateLink> getCandidateRecruitmentLinkByCandidateIdAndRecruitmentId(@Param(value ="candidateId") String candidateId, @Param(value ="recruitmentId") String recruitmentId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.opportunity_id=:opportunityId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidatesList(@Param(value ="opportunityId")String opportunityId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.recruitment_id=:recruitmentId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateLinkByRecruitmentId(@Param(value ="recruitmentId")String recruitmentId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateLinkByDateRange(@Param(value ="startDate") Date startdate,@Param(value ="endDate") Date enddate);
 
	@Query(value = "select a  from RecruitmentCandidateLink a  where a.recruitment_id=:recruitmentId and a.live_ind=false")
	List<RecruitmentCandidateLink> getDeletedCandidateList(@Param(value ="recruitmentId")String recruitmentId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.live_ind=true")
	List<RecruitmentCandidateLink> getAllCandidateList();
	
	@Query(value = "select a  from RecruitmentCandidateLink a  where a.candidate_id=:candidateId and a.live_ind=true")
	RecruitmentCandidateLink getCandidateRecruitmentLinkByCandidateIds(@Param(value ="candidateId") String candidateId);

	@Query(value = "select a  from RecruitmentCandidateLink a  where a.user_id=:userId and a.live_ind=true")
	List<RecruitmentCandidateLink> getCandidateListByUserId(@Param(value ="userId") String userId);

//	@Query(value = "select a  from RecruitmentCandidateLink a  where a.orgId=:orgId")
//	List<RecruitmentCandidateLink> getCandidateByOrgId(@Param(value ="orgId")String orgId);

	//@Query(value = "select a  from RecruitmentCandidateLink a  where a.orgId=:orgId ")
	//List<RecruitmentCandidateLink> getCandidateByOrgId(@Param(value ="orgId")String orgId);
}
