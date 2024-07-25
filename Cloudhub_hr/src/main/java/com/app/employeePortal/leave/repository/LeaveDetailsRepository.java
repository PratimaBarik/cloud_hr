package com.app.employeePortal.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leave.entity.LeaveDetails;

@Repository
public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, String>{

	@Query(value = "select a  from LeaveDetails a  where a.leave_id=:leaveId and a.live_ind=true" )
	public LeaveDetails getLeaveDetailsByLeaveId(@Param(value="leaveId") String leaveId);
	
	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:empId" )
	public List<LeaveDetails> getLeaveListByEmpId(@Param(value="empId") String empId);
	
	@Query(value = "select a  from LeaveDetails a  where a.task_id=:taskId" )
	public LeaveDetails getLeaveDetailsByTaskId(@Param(value="taskId") String taskId);
	
	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:empId and a.status=:status and a.live_ind=true" )
	public List<LeaveDetails> getLeaveListByStatus(@Param(value="empId") String empId,@Param(value="status") String status);

	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:empId and a.creation_date BETWEEN :startDate AND :endDate" )
    public List<LeaveDetails> getLeavessByEmployeeIdWithDateRange(@Param(value="empId")String empId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);
	
	@Query(value = "select a  from LeaveDetails a  where a.org_id=:orgId and a.creation_date BETWEEN :startDate AND :endDate" )
    public List<LeaveDetails> getLeavessByOrgIdWithDateRange(@Param(value="orgId")String orgId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);

	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:employeeId and  a.creation_date BETWEEN :startDate AND :endDate and a.status=:status and a.live_ind=true" )
	public List<LeaveDetails> getLeavessByEmployeeIdWithDateRangeAndStatus(@Param(value="employeeId") String employeeId, 				
															@Param(value="startDate")Date startDate,
															@Param(value="endDate")Date endDate,
															@Param(value="status") String status);
	


	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:employeeId and  a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
	public List<LeaveDetails> getLeavessAllByEmployeeIdWithDateRange(@Param(value="employeeId") String employeeId, 				
															@Param(value="startDate")Date startDate,
															@Param(value="endDate")Date endDate);

	@Query(value = "select a  from LeaveDetails a  where a.employee_id=:empId and a.creation_date BETWEEN :startDate AND :endDate and a.live_ind=true" )
    public List<LeaveDetails> getAllLeavessByEmployeeIdWithDateRange(@Param(value="empId")String empId,
    		                                                   @Param(value="startDate")Date startDate,
    		                                                   @Param(value="endDate")Date endDate);

}
