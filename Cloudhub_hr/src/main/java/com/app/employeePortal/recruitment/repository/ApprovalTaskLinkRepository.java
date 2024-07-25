package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.ApprovalTaskLink;

@Repository
public interface ApprovalTaskLinkRepository extends JpaRepository<ApprovalTaskLink, String>{
	
	@Query(value = "select a  from ApprovalTaskLink a  where a.taskId=:taskId and a.liveInd=true" )
	ApprovalTaskLink getTaskApprovalLink(@Param(value = "taskId")String taskId);

}
