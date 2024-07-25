package com.app.employeePortal.unboardingWorkflow.service;

import java.util.List;

import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesRequestMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesResponseMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfReqMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfRespMapper;

public interface UnboardingWorkflowService {

	UnboardingWfRespMapper saveUnboardingWorkflow(UnboardingWfReqMapper requestMapper);

	List<UnboardingWfRespMapper> getUnboardingWorkflowListByOrgId(String orgId);

	UnboardingWfRespMapper updateUnboardingWorkflowDetails(String unboardingWorkflowDetailsId,
			UnboardingWfReqMapper requestMapper);

	void deleteUnboardingWorkflow(String unboardingWorkflowDetailsId);

	boolean stageExistsByWeightage(double probability, String unboardingWorkflowDetailsId);

	UnboardingStagesResponseMapper saveOpportunityStages(UnboardingStagesRequestMapper requestMapper);

	List<UnboardingStagesResponseMapper> getStagesByUnboardingWorkflowId(String unboardingWorkflowId);

	UnboardingStagesResponseMapper updateUnboardingStages(String unboardingStagesId,
			UnboardingStagesRequestMapper requestMapper);

	void deleteUnboardingStages(String unboardingStagesId);

	List<UnboardingStagesResponseMapper> getStagesByOrgId(String orgId);

	UnboardingStagesResponseMapper updateUnboardingStagesPubliahInd(UnboardingStagesRequestMapper requestMapper);

	UnboardingWfRespMapper updateUnboardingWorkflowDetailsPublishInd(UnboardingWfReqMapper requestMapper);

	List<UnboardingWfRespMapper> getUnboardingWorkflowListByOrgIdForDropdown(String orgId);

	List<UnboardingStagesResponseMapper> getStagesByOrgIdForDropdown(String orgId);

}
