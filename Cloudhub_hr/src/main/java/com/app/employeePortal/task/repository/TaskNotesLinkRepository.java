package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.task.entity.TaskNotesLink;
@Repository
public interface TaskNotesLinkRepository  extends JpaRepository<TaskNotesLink, String>{

	@Query(value = "select a  from TaskNotesLink a  where a.taskId=:taskId and a.liveInd = true" )
	public	List<TaskNotesLink> getNotesIdByTaskId(@Param(value="taskId") String taskId);

	@Query(value = "select a  from TaskNotesLink a  where a.notesId=:notesId" )
	public TaskNotesLink findByNotesId(@Param(value="notesId") String notesId);

}
