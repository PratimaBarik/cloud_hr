package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.task.entity.TaskComment;

public interface TaskCommentRepository extends JpaRepository<TaskComment, String> {

	List<TaskComment> getByTaskId(String taskId);

	

}
