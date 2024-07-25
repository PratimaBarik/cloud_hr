package com.app.employeePortal.leave.service;

import java.util.List;

import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leave.entity.LeaveDetails;
import com.app.employeePortal.leave.mapper.EmployeeLeaveMapper;
import com.app.employeePortal.leave.mapper.LeaveBalanceMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.leave.mapper.OrganizationLeaveRuleMapper;

public interface LeaveService {
	
	public OrganizationLeaveRuleMapper saveToOrganizationLeaveRuleProcess(OrganizationLeaveRuleMapper organizationLeaveRuleMapper);
	
	public String saveToLeaveProcess(LeavesMapper leavesMapper)throws Exception;
	
	public LeavesMapper getLeaveDetails(String leaveId) ;
	
	public List<OrganizationLeaveRuleMapper> getOrganizationLeaveRuleDetailsByOrgId(String orgId) ;
	
	public OrganizationLeaveRuleMapper getOrganizationLeaveRuleDetails(String organizationLeaveRuleId, String country);
	
	public LeavesMapper updateLeaveDetails(LeavesMapper leavesMapper)throws Exception;
	
	//public OrganizationLeaveRuleMapper updateOrganizationLeaveRuleDetails(OrganizationLeaveRuleMapper organizationLeaveRuleMapper);

	public List<LeavesMapper> getEmployeeLeavesList(String employeeId);

	public LeaveBalanceMapper calculateLeaveBalanceByEmpId(String employeeId);
	
	public List<LeavesMapper> getLeavesListByUserIdWithDateRange(String userId, String startDate, String endDate);
	
	public List<LeavesMapper> getLeavesListByOrgIdWithDateRange(String orgId, String startDate, String endDate);

	LeavesMapper getLeaveDetailsMapper(LeaveDetails leaveDetails);

	public List<LeavesMapper> getEmployeeleaveStatusListByEmployeeId(String employeeId, String status);

	public List<EmployeeLeaveMapper> getEmployeeLeaveListByOrgIdWithDateWise(String orgId, String startDate,
			String endDate);

	public List<NotesMapper> getNoteListByLeaveId(String leaveId);

	public NotesMapper saveLeaveNotes(NotesMapper notesMapper);

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public void deleteLeaveNotesById(String notesId);

	public List<LeavesMapper> getEmployeeleaveStatusListByEmployeeIdWithDateWise(String employeeId, String status,
			String startDate, String endDate);

	public List<EmployeeLeaveMapper> getEmployeeLeaveListByOrgIdWithDateWiseAndStatus(String orgId, String startDate,
			String endDate, String status);


	
	
}
