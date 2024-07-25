package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.TaskChecklistStageLink;

@Repository
public interface TaskChecklistStageLinkRepository extends JpaRepository<TaskChecklistStageLink, String> {

	List<TaskChecklistStageLink> findByOrgId(String orgId);

	List<TaskChecklistStageLink> findByOrgIdAndLiveInd(String orgId, boolean b);

	TaskChecklistStageLink findByTaskChecklistStagelinkId(String taskChecklistStagelinkId);

	List<TaskChecklistStageLink> findByTaskChecklistIdAndLiveInd(String taskChecklistId, boolean b);

	List<TaskChecklistStageLink> findByTaskChecklistId(String taskChecklistId);

	TaskChecklistStageLink findByTaskChecklistIdAndTaskChecklistStagelinkIdAndLiveInd(String taskChecklistId,
			String taskChecklistStagelinkId, boolean b);

}
