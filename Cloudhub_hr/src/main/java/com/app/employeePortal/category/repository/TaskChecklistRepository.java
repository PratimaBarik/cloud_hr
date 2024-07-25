package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.TaskChecklist;
import com.app.employeePortal.task.entity.TaskType;

@Repository
public interface TaskChecklistRepository extends JpaRepository<TaskChecklist, String> {

	List<TaskChecklist> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<TaskChecklist> findByOrgId(String orgId);

	TaskChecklist findByTaskChecklistId(String taskChecklistId);

	List<TaskChecklist> findByTaskType(TaskType taskType);


}
