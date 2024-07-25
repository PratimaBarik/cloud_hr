package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitmentSkillsetLink;

public interface RecruitmentSkillsetLinkRepository extends JpaRepository<RecruitmentSkillsetLink, String> {
	
	
	@Query(value = "select a  from RecruitmentSkillsetLink a  where a.opportunity_id=:oppId and a.recruitment_id=:recruitId and a.live_ind = true" )
	public RecruitmentSkillsetLink getRecruitmentSkillSetLink(@Param(value="oppId")String oppId,@Param(value="recruitId")String recruitId);
	
	@Query(value = "select a  from RecruitmentSkillsetLink a  where a.recruitment_id=:recruitId and a.profile_id =:profileId and a.live_ind = true" )
	public RecruitmentSkillsetLink getRecruitmentSkillSetLinkByRecruitId(@Param(value="recruitId")String recruitId,@Param(value="profileId")String profileId);
	
	@Query(value = "select a  from RecruitmentSkillsetLink a  where a.recruitment_id=:recruitId and a.live_ind = true" )
	public List<RecruitmentSkillsetLink> getRecruitmentSkillSetLinkByRecruitmentId(@Param(value="recruitId")String recruitId);
	
	@Query(value = "select a  from RecruitmentSkillsetLink a  where a.recruitment_id=:recruitId and a.live_ind = true" )
	public RecruitmentSkillsetLink getRecruitmentSkillSetLinkDetailsByRecruitmentId(@Param(value="recruitId")String recruitId);
	   
	@Query(value = "select a  from RecruitmentSkillsetLink a  where a.recruitment_id=:recruitId and a.live_ind = false" )
	public List<RecruitmentSkillsetLink> getDeletedRecruitmentSkillSetLinkByRecruitmentId(@Param(value="recruitId")String recruitId);
}
