package com.app.employeePortal.opportunityWorkflow.service;

import java.util.List;

import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityStagesMapper;
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityWorkflowMapper;

public interface OpportunityWorkflowService {

	OpportunityWorkflowMapper saveOpportunityWorkflow(OpportunityWorkflowMapper opportunityWorkflowMapper);

	List<OpportunityWorkflowMapper> getWorkflowListByOrgId(String orgId);

	OpportunityWorkflowMapper updateOpportunityWorkflowDetails(String opportunityWorkflowDetailsId,
			OpportunityWorkflowMapper opportunityWorkflowMapper);

	public void deleteOpportunityWorkflowById(String opportunityWorkflowDetailsId);

	OpportunityStagesMapper saveOpportunityStages(OpportunityStagesMapper opportunityStagesMapper);

	List<OpportunityStagesMapper> getStagesByOppworkFlowId(String oppworkFlowId);

	OpportunityStagesMapper updateOpportunityStages(String opportunityStagesId,
			OpportunityStagesMapper opportunityStagesMapper);

	public void deleteOpportunityStagesById(String opportunityStagesId);

	List<OpportunityStagesMapper> getStagesByOrgId(String orgId);

	boolean stageExistsByWeightage(double probability, String opportunityWorkflowDetailsId);

	OpportunityWorkflowMapper updateOpportunityWorkflowDetailsPublishInd(OpportunityWorkflowMapper opportunityWorkflowMapper);

	OpportunityStagesMapper updateOpportunityStagesPubliahInd(OpportunityStagesMapper opportunityStagesMapper);

	List<OpportunityWorkflowMapper> getWorkflowListByOrgIdForDropdown(String orgId);

	List<OpportunityStagesMapper> getStagesByOrgIdForDropdown(String orgId);

}
