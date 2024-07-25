package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.ProcessStageTaskLink;

public interface ProcessStageTaskLinkRepository extends JpaRepository<ProcessStageTaskLink, String>{

	@Query(value = "select a  from ProcessStageTaskLink a  where a.stageId=:stageId and a.liveInd=true" )
	List<ProcessStageTaskLink> getTasksOfStages(@Param(value = "stageId")String stageId);

}
