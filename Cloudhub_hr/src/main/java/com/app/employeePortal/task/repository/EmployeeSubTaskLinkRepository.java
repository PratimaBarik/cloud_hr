package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.task.entity.EmployeeSubTaskLink;

public interface EmployeeSubTaskLinkRepository extends JpaRepository<EmployeeSubTaskLink, Long>{

	List<EmployeeSubTaskLink> findByTaskIdAndTaskChecklistStagelinkId(String taskId, String taskChecklistStagelinkId);

}
