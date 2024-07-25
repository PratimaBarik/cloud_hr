package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitStageNoteLink;
import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetails;
@Repository

public interface RecruitStageNotelinkRepository extends JpaRepository<RecruitStageNoteLink, String> {
	
	@Query(value = "select a  from RecruitStageNoteLink a  where a.profile_id=:profileId")
	List<RecruitStageNoteLink> getStageNoteLink(@Param(value="profileId")String profileId);

	@Query(value = "select a  from RecruitStageNoteLink a  where a.profile_id=:profileId and a.stage_id=:stageId" )
	List<RecruitStageNoteLink> getNoteDetailsByProfileAndStageId(@Param(value="profileId")String profileId, @Param(value="stageId")String stageId);

	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.recruitmentStageId=:stageId" )
	RecruitmentProcessStageDetails getRecruitmentStageDetailsByStageId(@Param(value="stageId")String stageId);

	RecruitStageNoteLink findByRecruitmentStageNoteId(String recruitmentStageNoteId);

	@Query(value = "select a  from RecruitStageNoteLink a  where a.profile_id=:profileId")
	RecruitStageNoteLink getByProfileId(@Param(value="profileId")String profileId);

	//@Query(value = "select a  from RecruitStageNoteLink a  where a.profile_id=:profileId and a.live_ind=:true" )
	//List<RecruitStageNoteLink> getStageNoteLink(@Param(value="profileId")String profileId);

	//RecruitStageNoteLink getNoteDetailsByProfileAndStageId(String profile_id, String stage_id, boolean b);

	//List<RecruitStageNoteLink> getStageNoteLink(String profileId, boolean b);

}
