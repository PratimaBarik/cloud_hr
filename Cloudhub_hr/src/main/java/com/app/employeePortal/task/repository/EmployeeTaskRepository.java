package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.task.entity.EmployeeTaskLink;

@Repository
public interface EmployeeTaskRepository extends JpaRepository<EmployeeTaskLink, String> {

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and a.task_id=:taskId and live_ind=true")
	public EmployeeTaskLink getEmployeeTaskLink(@Param(value = "empId") String empId,
			@Param(value = "taskId") String taskId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:userId and live_ind=true")
	public List<EmployeeTaskLink> getTaskListByEmpId(@Param(value = "userId") String userId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and a.live_ind=false")
	public List<EmployeeTaskLink> getDeletedTaskListByEmpId(@Param(value = "empId") String empId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.task_id=:taskId and live_ind=true")
	public List<EmployeeTaskLink> getEmpListByTaskId(@Param(value = "taskId") String taskId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:userId and live_ind=true")
	public List<EmployeeTaskLink> getByEmployeeId(@Param(value = "userId") String userId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.task_id=:taskId and live_ind=true")
	public EmployeeTaskLink getEmpByTaskId(@Param(value = "taskId") String taskId);

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and live_ind=true and filterTaskInd=true")
	public Page<EmployeeTaskLink> getApproveTaskListByEmpIdPage(@Param("empId") String empId, Pageable page);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and live_ind=true and filterTaskInd=false")
	public Page<EmployeeTaskLink> getTaskListByEmpIdPage(@Param("empId") String empId, Pageable page);

	public List<EmployeeTaskLink> findByLevel(int level);

	@Query(value = "select a  from EmployeeTaskLink a  where a.task_id=:taskId  and a.level=:level and a.approveStatus IN ('approve', 'pending', 'reject') and live_ind=true")
	public List<EmployeeTaskLink> getTaskListByTaskIdAndLevel(String taskId, int level);

	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and a.task_id=:taskId and a.approveStatus=:approveStatus and live_ind=true")
	public EmployeeTaskLink getEmployeeTaskLinkAndApproveStatus(@Param(value = "empId") String empId,
			@Param(value = "taskId") String taskId, @Param(value = "approveStatus") String approveStatus);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:empId and a.task_id=:taskId and live_ind=true and filterTaskInd=false")
	public EmployeeTaskLink getMyTaskByUserIdAndTaskId(@Param("empId") String empId, @Param(value = "taskId") String taskId);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:userId and live_ind=true and filterTaskInd=false")
	public List<EmployeeTaskLink> getMyTaskListByEmpId(@Param(value = "userId") String userId);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id=:userId and live_ind=true and filterTaskInd=true")
	public List<EmployeeTaskLink> getMyApprovalTaskListByEmpId(@Param(value = "userId") String userId);

//	@Query("SELECT a FROM EmployeeTaskLink a WHERE a.live_ind=true and a.employee_id IN :employeeIds and a.task_id=:taskId")
//	public List<EmployeeTaskLink> getEmpListByUserIdsAndTaskId(@Param(value="employeeIds") List<String> employeeIds,@Param(value="taskId")String taskId);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id IN :userIds and live_ind=true and filterTaskInd=false")
	public Page<EmployeeTaskLink> getTaskListByEmpIdsPage(@Param(value = "userIds") List<String> userIds, Pageable page);
	
	@Query(value = "select a  from EmployeeTaskLink a  where a.employee_id IN :userIds and live_ind=true and filterTaskInd=false")
	public List<EmployeeTaskLink> getTasCountByEmpIdsPage(@Param(value = "userIds") List<String> userIds);
	
	}
