package com.app.employeePortal.task.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.task.entity.TaskDocumentLink;
@Repository
public interface TaskDocumentLinkRepository  extends JpaRepository<TaskDocumentLink, String>{

	List<TaskDocumentLink> findByTaskId(String taskId);

	TaskDocumentLink findByDocumentId(String documentId);
	
}
