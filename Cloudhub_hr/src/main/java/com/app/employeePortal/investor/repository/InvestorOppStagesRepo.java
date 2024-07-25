package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;

public interface InvestorOppStagesRepo extends JpaRepository<InvestorOppStages,String> {
    List<InvestorOppStages> findByInvestorOppStagesIdAndLiveInd(String investorOppStagesId, boolean b);

    List<InvestorOppStages> findByInvestorOppWorkflowIdAndLiveInd(String investorOppWorkflowId, boolean b);

    List<InvestorOppStages> findByOrgIdAndLiveInd(String orgId, boolean b);
    
    List<InvestorOppStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);

	@Query(value = "select exp  from InvestorOppStages exp  where exp.investorOppStagesId=:investorOppStagesId and liveInd=true")
	public InvestorOppStages getInvestorOppStagesByInvestorOppStagesId(@Param(value="investorOppStagesId")String investorOppStagesId);
}
