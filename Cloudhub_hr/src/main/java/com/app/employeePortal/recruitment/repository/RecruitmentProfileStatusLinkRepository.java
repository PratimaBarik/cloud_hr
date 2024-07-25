package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitProfileStatusLink;

public interface RecruitmentProfileStatusLinkRepository extends JpaRepository<RecruitProfileStatusLink, String>{

	@Query(value = "select a  from RecruitProfileStatusLink a  where a.candidateId=:candidateId and a.profileId=:profileId and a.liveInd=true")
	RecruitProfileStatusLink getRecruitProfileStatusLink(@Param(value ="candidateId")String candidateId,
														@Param(value ="profileId")String profileId);

	@Query(value = "select a  from RecruitProfileStatusLink a  where a.profileId=:profile_id and a.liveInd=true")
	RecruitProfileStatusLink getRecruitProfileStatusLinkByProfileId(@Param(value ="profile_id") String profile_id);
														
	@Query(value = "select a  from RecruitProfileStatusLink a  where a.recruitmentId=:recruitmentId and a.liveInd=true and a.approveInd=true")
	List<RecruitProfileStatusLink> getRecruitProfileStatusLinkByRecruitmentId(@Param(value ="recruitmentId") String recruitmentId);
	
	@Query(value = "select a  from RecruitProfileStatusLink a  where a.recruitmentId=:recruitmentId and a.liveInd=true and a.rejectInd=true")
	List<RecruitProfileStatusLink> getRecruitDropListByRecruitmentId(@Param(value ="recruitmentId") String recruitmentId);

	@Query(value = "select a  from RecruitProfileStatusLink a  where a.recruitmentId=:recruitmentId and a.liveInd=true and a.offerDate BETWEEN :startDate AND :endDate and a.approveInd=true")
	List<RecruitProfileStatusLink> getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(@Param(value ="recruitmentId") String recruitmentId,
														@Param(value ="startDate") Date startDate, 
														@Param(value ="endDate") Date endDate);
	
	@Query(value = "select a  from RecruitProfileStatusLink a  where a.profileId=:profileId and a.liveInd=true and a.rejectInd=:rejectInd and a.approveInd=:approveInd")
	RecruitProfileStatusLink getProfileListByProfileIdAndRejectAndSelectInd(@Param(value ="profileId") String profileId);


	List<RecruitProfileStatusLink> findByProfileIdAndRejectIndOrApproveInd(String profileId, boolean rejectInd, boolean approveInd);

	
	
	//List<RecruitProfileStatusLink> getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(String recruitmentId,Date start_date, Date end_date);
}