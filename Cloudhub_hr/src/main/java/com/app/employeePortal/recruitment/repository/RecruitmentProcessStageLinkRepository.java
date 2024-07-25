package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.GlobalProcessStageLink;

@Repository
public interface RecruitmentProcessStageLinkRepository extends JpaRepository<GlobalProcessStageLink, String>{



	@Query(value = "select a  from GlobalProcessStageLink a  where a.recruitmentStageId=:stageId" )
	List<GlobalProcessStageLink> getRecruitmentProcessStageLinkByStageId(@Param(value="stageId")String recruitmentStageId);

	@Query(value = "select a  from GlobalProcessStageLink a  where a.recruitmentProcessId=:recruitmentProcessId and a.liveInd=true" )
	List<GlobalProcessStageLink> getRecruitmentProcessStageLinkByProcessId(@Param(value="recruitmentProcessId")String recruitmentProcessId);
	
	@Query(value = "select a  from GlobalProcessStageLink a  where a.recruitmentProcessId=:recruitmentProcessId and a.recruitmentStageId=:stageId and a.liveInd=true" )
	GlobalProcessStageLink getRecruitStageLinkByProcessIdAndStageId(@Param(value="recruitmentProcessId")String recruitmentStageId, @Param(value="stageId")String stageId);

	@Query(value = "select a  from GlobalProcessStageLink a  where a.recruitmentProcessId=:recruitmentProcessId and a.liveInd=true" )
	List<GlobalProcessStageLink> getByRecruitmentProcessId(@Param(value="recruitmentProcessId")String recruitmentProcessId);

}
