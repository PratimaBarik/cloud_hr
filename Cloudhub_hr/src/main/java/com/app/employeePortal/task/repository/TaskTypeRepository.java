package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.task.entity.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, String> {

	// public List<TaskType> findByorgId(String orgId);

	public TaskType findByTaskTypeId(String taskTypeId);

	@Query(value = "select a  from TaskType a  where a.taskTypeId=:taskTypeId")
	public List<TaskType> getByTaskTypeId(@Param(value = "taskTypeId") String taskTypeId);

//	public List<TaskType> findByTaskTypeContaining(String name);

	//public List<TaskType> findByTaskType(String taskType);

	public List<TaskType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	public List<TaskType> findByUserIdAndLiveInd(String userId, boolean b);

	public List<TaskType> findByOrgIdAndLiveIndAndTaskCheckListInd(String orgId, boolean liveInd, boolean taskCheckListInd);

//	List<TaskType> findByTaskTypeAndLiveInd(String taskType, boolean b);
	
	public TaskType findByTaskType(String taskType);
	
	@Query(value = "select a  from TaskType a  where a.taskType=:taskType and a.liveInd=true")
	public TaskType getActiveTaskTypeByTaskType(@Param(value = "taskType") String taskType);
	
	public TaskType findByTaskTypeIdAndLiveInd(String taskTypeId,boolean liveInd);

	public List<TaskType> findByTaskTypeContainingAndOrgId(String name, String orgId);

	
	public List<TaskType> findByTaskTypeAndLiveIndAndOrgId(String taskType, boolean b, String orgId);

	@Query(value = "select a  from TaskType a  where a.taskType=:taskType and a.orgId=:orgId and  a.liveInd=true")
	public TaskType getByTaskTypeAndOrgId(@Param(value = "taskType")String taskType,@Param(value = "orgId") String orgId);
		

}

