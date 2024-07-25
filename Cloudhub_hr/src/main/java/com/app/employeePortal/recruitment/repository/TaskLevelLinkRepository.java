package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.TaskLevelLink;


@Repository
public interface TaskLevelLinkRepository extends JpaRepository<TaskLevelLink, String>{

	@Query(value = "select a  from TaskLevelLink a  where a.taskId=:taskId and a.liveInd=true" )
	TaskLevelLink getTasklevelLink(@Param(value = "taskId")String taskId);
	
}
