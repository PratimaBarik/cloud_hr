package com.app.employeePortal.repairWorkflow.service;

import java.util.List;

import com.app.employeePortal.repairWorkflow.mapper.RepairStagesRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairStagesResponseMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowResponseMapper;

public interface RepairWorkflowService {

	RepairWorkflowResponseMapper saveRepairWorkflow(RepairWorkflowRequestMapper requestMapper);

	List<RepairWorkflowResponseMapper> getRepairWorkflowListByOrgId(String orgId);

	RepairWorkflowResponseMapper updateRepairWorkflowDetails(String RepairWorkflowDetailsId,
			RepairWorkflowRequestMapper requestMapper);

	void deleteRepairWorkflowDetails(String RepairWorkflowDetailsId);

	boolean stageExistsByWeightage(double probability, String RepairWorkflowDetailsId);

	RepairStagesResponseMapper saveRepairStages(RepairStagesRequestMapper requestMapper);

	List<RepairStagesResponseMapper> getStagesByRepairWorkflowDetailsId(String RepairWorkflowDetailsId);

	RepairStagesResponseMapper updateRepairStagesId(String RepairStagesId,
			RepairStagesRequestMapper requestMapper);

	void deleteRepairStagesById(String RepairStagesId);

	RepairStagesResponseMapper updateRepairStagesPubliahInd(RepairStagesRequestMapper requestMapper);

	RepairWorkflowResponseMapper updateRepairWorkflowDetailsPublishInd(
			RepairStagesRequestMapper requestMapper);

	List<RepairWorkflowResponseMapper> getRepairWorkflowListByOrgIdForDropdown(String orgId);

	List<RepairStagesResponseMapper> getRepairStagesByOrgIdForDropdown(String orgId);
}
