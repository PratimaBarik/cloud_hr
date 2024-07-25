package com.app.employeePortal.task.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.employeePortal.candidate.mapper.CandidateEmailDetailsMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.mapper.ApprovedStatusMapper;
import com.app.employeePortal.task.mapper.SubTaskMapper;
import com.app.employeePortal.task.mapper.SubViewTaskMapper;
import com.app.employeePortal.task.mapper.TaskCommentMapper;
import com.app.employeePortal.task.mapper.TaskDragDropMapper;
import com.app.employeePortal.task.mapper.TaskMapper;
import com.app.employeePortal.task.mapper.TaskStepsMapper;
import com.app.employeePortal.task.mapper.TaskTypeDropMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.mapper.TeamEmployeeMapper;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;


public interface TaskService {

	public TaskViewMapper saveTaskProcess(TaskMapper taskMapper,String through) throws IOException, TemplateException;

	public TaskViewMapper getTaskDetails(String taskId);

	public List<TaskViewMapper> getTaskDetailsByEmployeeIdPagination(String employeeId,int pageNo,boolean filterTaskInd);

	public List<TaskViewMapper> getTaskDetailsByOrganizationId(String organizationId);

	public TaskViewMapper updateTaskDetails(String taskId ,TaskMapper taskMapper) throws IOException, TemplateException;

	public boolean delinkTask(String employeeId, String taskId);
	
	public TaskViewMapper approveLeaveTask(String taskId);

	public TaskViewMapper rejectLeaveTask(String taskId);

	public TaskViewMapper approveTask(String taskId,String userId);

	public TaskViewMapper rejectTask(String taskId,String userId, String rejectReason);
	
	public List<TaskViewMapper> getTaskListByUserIdWithDateRange(String userId, String startDate, String endDate);
	
	public List<TaskViewMapper> getTaskListByOrgIdWithDateRange(String orgId, String startDate, String endDate);

	public String saveTaskType(TaskMapper taskMapper);

	public List<TaskMapper> getTaskTypeByOrgId(String orgIdFromToken);

	public TaskMapper updateTaskType(String taskTypeId, TaskMapper taskMapper);

	public List<TaskViewMapper> getTaskListOfCandidateByCandidateId(String candidateId);

	public ContactViewMapper saveToTaskConvertPartnerContact(String contactId, String userId, String organizationId);

	String createEmailTask(String candidateId, String userId, String orgId, String body);

	public String saveCandidateEmailDetails(CandidateEmailDetailsMapper candidateEmailDetailsMapper) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException;

	public void deleteTaskTypeById(String taskTypeId);

	public HashMap getTaskListsByUserIdStartdateAndEndDate(String userId, String startDate, String endDate);

	public Map<String, List<TaskViewMapper>> getTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate);

	public List<TaskViewMapper> getTaskByCandidateIdForWebsite(String candidateId);

	public TaskViewMapper updateCandidateComplitionStatus(TaskMapper taskMapper, String candidateId,
			String taskId);

	TaskViewMapper getTaskDetailsForCandidate(String candidateId,String taskId);

	public List<TaskViewMapper> getListOfTaskByProjectId(String projectId);

	TaskViewMapper updatetaskCompletionInd(String taskId, TaskMapper taskMapper);

	public List<TaskViewMapper> getListOfTaskByCandidateId(String candidateId);

	TaskViewMapper getUnCompletedTaskDetailsForCandidate(String candidateId, String taskId);

	public List<TaskViewMapper> getDeletedTaskListByEmpId(String employeeId);

	public TaskCommentMapper createTaskComment(TaskCommentMapper taskContactMapper);

	public TaskCommentMapper getTaskCommentByTaskCommentId(String taskCommentId);

	public List<TaskCommentMapper> getTaskCommentListByTaskId(String taskId);

	public TaskViewMapper reApplyLeavesByLeaveId(String leaveId);

	public String deleteLeave(String leaveId);

	public List<TaskViewMapper> getTaskDetailsByEmployeeId(String userId);

//	TaskViewMapper getFilterTaskDetails(String taskId, boolean filterTaskInd);

	public Map getOpenTaskListsByUserId(String userId);

	public TaskMapper activeTaskCheckList(TaskMapper taskMapper);

	public List<TeamEmployeeMapper> getTaskTeamListByTaskId(String taskId);

	public List<TaskMapper> getAllTaskTypeTaskcheckList(String orgIdFromToken);

	public SubViewTaskMapper  createSubTask(SubTaskMapper subTaskMapper) throws Exception;

	public List<SubViewTaskMapper> getSubTaskByTaskId(String taskId);

	public Object getOpenTaskCountByUserId(String userId,String organizationId);

//	public List<ApprovedStatusMapper> getApproveStatusByTaskId(String taskId,String taskType);

	public List<ApprovedStatusMapper> getLeaveStatusByLeaveId(String leaveId);

	public List<ApprovedStatusMapper> getMileageStatusByVoucherId(String voucherId);

	public List<ApprovedStatusMapper> getExpenseStatusByVoucherId(String voucherId);

	public List<Map<String, List<TaskViewMapper>>> getTaskListByUserId(String userId);

    List<TaskViewMapper> getTaskDetailsByLeads(String leadsId, int pageNo);

	List<ApprovedStatusMapper> getApproveStatusByTaskId(String taskId,String userId);

	public Map getHighPriorityByUserIdStartdateAndEndDate(String userId, String startDate, String endDate);

	public List<DocumentMapper> getDocumentListByTaskId(String taskId);

	public void deleteDocumentsById(String documentId);

	public Map<String, Integer> getOpenTaskCountByUserIdBetwennStartDate(String userId, String startDate, String endDate);

	public Map<String, Integer>  getDeadlineTaskByUserIdBetweenDate(String userId, String startDate, String endDate);

	public List<TaskViewMapper>  getOpenTasktByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<TaskViewMapper>  getMyTasktByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<TaskViewMapper>  getOpenTaskListByUserId(String userId);

	public List<TaskViewMapper> getTaskDetailsByInvestorLeadsId(String investorLeadsId, int pageNo);

	public List<Map<String, Integer>> getCountTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate);

	public List<TaskViewMapper> getCompletedTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate, String typeName);

	public String saveTaskNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByTaskId(String taskId);

//	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public List<TaskViewMapper> getTaskListByTaskTypeIdAndUserId(String userId, String Typeame);

	public void deleteTaskNotesById(String notesId);

	public ActivityMapper saveActivityTask(TaskMapper taskMapper);

	List<TaskViewMapper> getCloseTaskListByUserId(String userId, String type);

	public List<TaskViewMapper> getTaskListPageWiseByIncludedUserId(String userId, int pageNo, int pageSize);

	public Object getCountListByIncludedUserId(String userId);

	public HashMap getTaskTypeCountByOrgId(String orgId);

	public ByteArrayInputStream exportTaskTypeListToExcel(List<TaskMapper> list);

	public List<TaskViewMapper> getTeamTaskByUnderUserId(String userId, int pageNo, int pageSize);

	public HashMap getTeamTaskCountByUnderUserId(String userId);

	public List<TaskViewMapper> getTaskListByTaskTypeIdAndUserIdAndQuarterAndYear(String userId, String typeName,
			String quarter, int year);

	public TaskStepsMapper createTaskStep(TaskStepsMapper mapper, String loggedInUserId);

	public List<TaskStepsMapper> getTaskStepsByTaskId(String taskId);

	public TaskStepsMapper updateTaskStepsByStepId(TaskStepsMapper mapperString, String loggedInUserId);

	public String deleteTaskStepsByStepId(String stepId);

	public TaskViewMapper transferTaskOneWeakToAnother(TaskDragDropMapper mapper);

	public List<TaskViewMapper> getPendingTaskListByTaskTypeIdAndUserId(String userId, String typeName);

	List<Map<String, Object>> calculateHoursAndDefinedHours(String startDate, String endDate, String userId);

	public boolean checkTaskNameInTaskTypeByOrgLevel(String taskType, String orgIdFromToken);

	public List<TaskMapper> getTasktypeByNameByOrgLevel(String name, String orgId);

	public List<TaskViewMapper> getTaskListByUserIdWithYearAndQuarter(String userId);

	ActivityMapper updateActivityTaskDetails(String taskId, TaskMapper taskMapper)
			throws IOException, TemplateException;

	public List<TaskViewMapper> getTaskListsByTaskTypeIdAndUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate, String type);

	List<TaskTypeDropMapper> getTaskTypeForDropDownByOrgId(String orgId);

	public List<TaskViewMapper> getTaskDetailsByRoomId(String roomId, int pageNo);

	TaskViewMapper getTaskDetailsOjectResponse(TaskDetails taskDetails);

	List<TaskViewMapper> getTasksByUserIdAndPriority(String employeeId,String priority, int pageNo, boolean filterTaskInd);

	public List<TaskViewMapper> getTaskDetailsByNameByOrgId(String name, String orgId);

	public List<TaskViewMapper> getTaskDetailsByNameForTeam(String name, String userId, String orgId);

	public List<TaskViewMapper> getTaskDetailsByNameByUserId(String name, String userId, String orgId);

}
