package com.app.employeePortal.processApproval.service;

import com.app.employeePortal.processApproval.mapper.ProcessApprovalMapper;
import com.app.employeePortal.processApproval.mapper.ProcessApprovalViewMapper;
import com.app.employeePortal.task.entity.TaskDetails;

public interface ProcessApprovalService {

	ProcessApprovalViewMapper saveProcessApproval(ProcessApprovalMapper processApprovalMapper);

	ProcessApprovalViewMapper getApproval(String subProcessName);

	String ProcessApprove(String userId, String subProcessName,TaskDetails taskDetails);

	void approveStageApprovalProcess(String taskId,String userId, String taskType);

	void approveLeaveProcess(String taskId, String userId, String taskType);

	void approveMileageTaskProcess(String taskId, String userId, String taskType);

	void expenseTaskApprove(String taskId, String userId, String taskType);

	void approveContactUserCreateProcess(String taskId, String userId, String taskType);

}
