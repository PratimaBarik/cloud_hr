package com.app.employeePortal.investor.service;

import java.util.List;

import com.app.employeePortal.investor.mapper.InvestorOppStagesMapper;
import com.app.employeePortal.investor.mapper.InvestorOppWorkflowMapper;

public interface InvestorOppWorkflowService {
    InvestorOppWorkflowMapper saveInvOpportunityWorkflow(InvestorOppWorkflowMapper opportunityWorkflowMapper);

    List<InvestorOppWorkflowMapper> getInvOppWorkflowListByOrgId(String orgId);

    InvestorOppWorkflowMapper updateInvOpportunityWorkflow(String investorOppWorkflowId, InvestorOppWorkflowMapper opportunityWorkflowMapper);

    InvestorOppWorkflowMapper getInvOpportunityWorkflow(String investorOppWorkflowId);

    void deleteInvOpportunityWorkflowById(String investorOppWorkflowId,String loggedInUserId);

    boolean stageExistsByWeightage(double probability, String investorOppStagesId);

    InvestorOppStagesMapper saveInvOpportunityStages(InvestorOppStagesMapper opportunityStagesMapper);

    List<InvestorOppStagesMapper> getStagesByInvOppworkFlowId(String investorOppWorkflowId);

    InvestorOppStagesMapper updateInvOpportunityStages(String investorOppStagesId, InvestorOppStagesMapper opportunityStagesMapper);

    InvestorOppStagesMapper getOpportunityStagesDetails(String investorOppStagesId);

    void deleteInvOpportunityStagesById(String investorOppStagesId,String loggedInUserId);

    List<InvestorOppStagesMapper> getStagesByOrgId(String orgId);

    InvestorOppStagesMapper updateInvOpportunityStagesPublishInd(InvestorOppStagesMapper opportunityStagesMapper);

    InvestorOppWorkflowMapper updateInvOpportunityWorkflowDetailsPublishInd(InvestorOppWorkflowMapper opportunityWorkflowMapper);

	List<InvestorOppWorkflowMapper> getWorkflowListByOrgIdForDropDown(String orgId);

	List<InvestorOppStagesMapper> getOpportunityWorkflowStagesByOrgIdForDropDown(String orgId);
}
