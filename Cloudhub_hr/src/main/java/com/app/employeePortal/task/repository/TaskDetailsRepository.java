package com.app.employeePortal.task.repository;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.task.entity.TaskDetails;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, String> {

	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.liveInd=true ")
	public TaskDetails getTaskDetailsById(@Param(value = "taskId") String taskId);

	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId ")
	public TaskDetails getTaskDetailsByIdWithOutLiveInd(@Param(value = "taskId") String taskId);
	
	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.liveInd=true and a.complitionInd=false")
	public TaskDetails getUnCompletedTaskDetailsByTaskId(@Param(value = "taskId") String taskId);

	@Query(value = "select a  from TaskDetails a  where a.organization_id=:orgId and liveInd=true")
	public List<TaskDetails> getTaskListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from TaskDetails a  where a.user_id=:userId and a.creation_date BETWEEN :startDate AND :endDate")
	public List<TaskDetails> getTasksByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.organization_id=:orgId and a.creation_date BETWEEN :startDate AND :endDate")
	public List<TaskDetails> getTasksByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.task_id=:task_id and a.start_date BETWEEN :startDate AND :endDate and liveInd=true")
	public List<TaskDetails> getTaskListByTaskIdAndStartDate(@Param(value = "task_id") String task_id,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where a.task_id IN :taskIds and a.start_date BETWEEN :startDate AND :endDate and liveInd=true")
	public List<TaskDetails> getTaskListByTaskIdsAndStartDate(@Param(value = "taskIds") List<String> taskIds,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.user_id=:userId and a.liveInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTaskListsByUserIdAndStartdateAndEndDateAndLiveInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.user_id=:userId and a.complitionInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTaskListsByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.user_id=:userId and a.complitionInd=false and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTaskListByUserIdAndStartdateAndEndDateAndComplitionInd(
			@Param(value = "userId") String userId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.task_type=:taskTypeId and a.liveInd=true and a.creation_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTaskListsByTaskTypeIdAndStartdateAndEndDateAndLiveInd(
			@Param(value = "taskTypeId") String taskTypeId, @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	public List<TaskDetails> getByProjectNameAndLiveInd(String projectId,boolean b);

	@Query(value = "select a  from TaskDetails a  where  a.liveInd=true and a.end_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTodayTaskDetailsList(	@Param(value = "startDate") Date startDate,	@Param(value = "endDate") Date endDate);

//	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.liveInd=true and a.filterTaskInd=:filterTaskInd")
//	public TaskDetails getFilterTaskDetails(@Param(value = "taskId") String taskId,@Param(value = "filterTaskInd") boolean filterTaskInd);

	@Query(value = "select a  from TaskDetails a  where a.user_id=:userId and a.task_status!=:taskStatus and a.liveInd=true")
	public List<TaskDetails> getTaskListsByUserIdAndStatusAndLiveInd(String userId, String taskStatus);

//	@Query(value = "select a  from TaskDetails a  where a.task_id IN :taskId and a.task_type=:taskTypeId and a.complitionInd=:complitionInd and a.liveInd=true" )
//	public List<TaskDetails> getDataByTaskIdAndTaskTypeAndComplitionInd(@Param(value="taskId")List<String> taskIds, 
//			@Param(value = "taskTypeId") String taskTypeId,@Param(value = "complitionInd") boolean complitionInd);
	
	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.task_type=:taskTypeId and a.complitionInd=:complitionInd and a.liveInd=true" )
	public TaskDetails getDataByTaskIdAndTaskTypeAndComplitionInd(@Param(value="taskId")String taskId, 
			@Param(value = "taskTypeId") String taskTypeId,@Param(value = "complitionInd") boolean complitionInd);

	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.priority=:priority and a.liveInd=true" )
	public TaskDetails getDataByTaskIdAndTaskPriority(@Param(value = "taskId") String taskId,@Param(value = "priority") String priority);

	@Query(value = "select a  from TaskDetails a  where  a.liveInd=true and a.start_date BETWEEN :startDate AND :endDate ")
	public List<TaskDetails> getTaskListsByStartdateAndEndDateAndLiveInd( @Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where a.priority=:priority and a.liveInd=true and a.start_date BETWEEN :startDate AND :endDate")
	public List<TaskDetails> getTaskListsByPriorityAndStartdateAndEndDateAndLiveInd(@Param(value = "priority") String priority,
			@Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where a.complitionInd=:complitionInd and a.liveInd=true and a.start_date BETWEEN :startDate AND :endDate")
	public List<TaskDetails> getTaskListsByComplitionIndAndStartdateAndEndDateAndLiveInd(@Param(value = "complitionInd") boolean complitionInd,
			@Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where a.complitionInd=:complitionInd and a.liveInd=true and a.end_date>:startDate AND a.end_date<=:endDate")
	public List<TaskDetails> getDeadlineTaskByUserIdBetweenDate(@Param(value = "complitionInd") boolean complitionInd,
			@Param(value = "startDate") Date startDate,
			@Param(value = "endDate") Date endDate);

	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.task_type=:taskTypeId and a.creation_date BETWEEN :startDate AND :endDate and a.complitionInd=true" )
	public TaskDetails getTaskListByTaskTypeIdAndStartdateAndEndDateAndComplitionInd(@Param(value = "taskTypeId")String taskTypeId,@Param(value = "taskId") String taskId,
			@Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.liveInd=true and a.complitionInd=true")
	public TaskDetails getCompletedTaskDetailsByTaskId(@Param(value = "taskId") String taskId);

	@Query(value = "select a  from TaskDetails a  where a.end_date=:endDate and a.end_time=:time and a.liveInd=true")
	public List<TaskDetails> getEndingTasks( @Param(value = "endDate")Date endDate, @Param(value = "time") long time);
	
	@Query(value = "select a  from TaskDetails a  where  a.task_type=:taskTypeId and a.task_id=:taskId and a.end_date BETWEEN :startDate AND :endDate and a.liveInd=true" )
	public TaskDetails getTaskListByTaskTypeIdAndStartdateAndEndDateQuarterWise(@Param(value = "taskTypeId")String taskTypeId,@Param(value = "taskId") String taskId,
			@Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate);
	
	@Query(value = "select a  from TaskDetails a  where  a.task_type=:taskTypeId and a.task_id=:taskId and a.end_date < :startDate and a.liveInd=true" )
	public TaskDetails getTaskListByTaskTypeIdAndEndDateBeforeStartdate(@Param(value = "taskTypeId")String taskTypeId,@Param(value = "taskId") String taskId,
			@Param(value = "startDate")Date startDate);
	
	@Query(value = "select a  from TaskDetails a  where a.task_id=:taskId and a.task_type=:taskTypeId and a.start_date BETWEEN :startDate AND :endDate" )
	public TaskDetails getTaskListByTaskTypeIdAndStartdateAndEndDate(@Param(value = "taskTypeId")String taskTypeId,@Param(value = "taskId") String taskId,
			@Param(value = "startDate")Date startDate, @Param(value = "endDate")Date endDate);
	
	@Query("SELECT td FROM TaskDetails td " +
	           "JOIN EmployeeTaskLink etl ON td.task_id = etl.task_id " +
	           "WHERE etl.employee_id = :userId " +
	           "AND td.priority = :priority " +
	           "AND etl.live_ind = true " +
	           "AND etl.filterTaskInd = false " +
	           "AND td.liveInd = true")
	    Page<TaskDetails> findByUserIdAndPriority(@Param("userId") String userId, @Param("priority") String priority, Pageable  pageable);

	@Query("SELECT a FROM TaskDetails a WHERE a.organization_id=:orgId AND a.task_type=:taskTypeId AND a.liveInd = true ")
	public List<TaskDetails> getByTaskTypeAndOrgId(String taskTypeId, String orgId);

	@Query("SELECT a FROM TaskDetails a WHERE (a.user_id IN :userIds OR a.assigned_to IN :userIds) AND a.task_type=:taskTypeId AND a.liveInd = true ")
	public List<TaskDetails> getByTaskTypeAndUserIds(@Param(value = "taskTypeId")String taskTypeId,@Param(value = "userIds")Set<String> userIds);

	@Query(value = "select exp  from TaskDetails exp  where (exp.user_id = :userId OR exp.assigned_to = :userId) and exp.task_type=:taskTypeId and exp.liveInd=true")
	public List<TaskDetails> getByTaskTypeAndLiveIndAndUserId(@Param(value = "taskTypeId")String taskTypeId, @Param(value = "userId")String userId);

}
