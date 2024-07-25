package com.app.employeePortal.task.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.processApproval.entity.ApproveTaskLink;
@Repository
public interface ApproveTaskLinkRepository extends JpaRepository<ApproveTaskLink, String>{

	public List<ApproveTaskLink> findByTaskId(String taskId);

	public ApproveTaskLink findByTaskIdAndUserId(String taskId, String userId);

	
}
