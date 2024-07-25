package com.app.employeePortal.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.task.entity.SubTaskDetails;

public interface SubTaskDetailsRepository extends JpaRepository<SubTaskDetails, Long>{

	SubTaskDetails findByTaskIdAndTaskChecklistStagelinkId(String taskId, String taskChecklistStagelinkId);

}
