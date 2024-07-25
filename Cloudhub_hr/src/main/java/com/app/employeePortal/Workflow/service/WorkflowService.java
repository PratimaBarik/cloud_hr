package com.app.employeePortal.Workflow.service;

import java.util.List;

import com.app.employeePortal.Workflow.mapper.StagesRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesResponseMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskResponseMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowRequestMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowResponseMapper;

public interface WorkflowService {

	WorkflowResponseMapper saveWorkflow(WorkflowRequestMapper requestMapper);

	List<WorkflowResponseMapper> getWorkflowListByOrgId(String orgId, String type);

	WorkflowResponseMapper updateWorkflowDetails(String WorkflowDetailsId,
			WorkflowRequestMapper requestMapper);

	void deleteWorkflowDetails(String WorkflowDetailsId, String userId);

	boolean stageExistsByWeightage(double probability, String WorkflowDetailsId);

	StagesResponseMapper saveStages(StagesRequestMapper requestMapper);

	List<StagesResponseMapper> getStagesByWorkflowDetailsId(String WorkflowDetailsId);

	StagesResponseMapper updateStagesId(String StagesId,
			StagesRequestMapper requestMapper);

	void deleteStagesById(String StagesId, String userId);

	StagesResponseMapper updateStagesPubliahInd(StagesRequestMapper requestMapper);

	WorkflowResponseMapper updateWorkflowDetailsPublishInd(
			StagesRequestMapper requestMapper);

	List<WorkflowResponseMapper> getWorkflowListByOrgIdForDropdown(String orgId, String type);

	List<StagesResponseMapper> getStagesByOrgIdForDropdown(String orgId);

	WorkflowResponseMapper updateGlobalIndForWorkflow(String orgId, boolean globalInd, String workflowId);

	List<WorkflowResponseMapper> listOfWorkflowWithGlobalInd(String orgId, String type);

	WorkflowResponseMapper createNewWorkflowForOrg(String orgId, String workflowId,String userId,String type);

	StagesTaskResponseMapper createStageTask(StagesTaskRequestMapper requestMapper);

	List<StagesTaskResponseMapper> getStageTaskByStageId(String orgId, String stageId);

	String moveWF(String type);

    StagesTaskResponseMapper updateMandatoryIndForStagesTask(String stagesTaskId, boolean mandatoryInd);

	boolean workflowExistsByName(String workflowName, String orgId, String type);

	StagesTaskResponseMapper updateStagesTask(String stagesTaskId, StagesTaskRequestMapper requestMapper);

	String deleteReinstateStagesTask(String stagesTaskId,boolean liveInd, String userId);

}
