package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.task.entity.TaskTypeDelete;

public interface TaskTypeDeleteRepository extends JpaRepository<TaskTypeDelete, String> {

	List<TaskTypeDelete> findByOrgId(String orgId);

	TaskTypeDelete findByTaskTypeId(String taskTypeId);

}
