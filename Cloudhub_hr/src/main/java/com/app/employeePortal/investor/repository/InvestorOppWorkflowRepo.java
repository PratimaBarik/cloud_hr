package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.investor.entity.InvestorOppWorkflow;

public interface InvestorOppWorkflowRepo extends JpaRepository<InvestorOppWorkflow,String> {
	
    List<InvestorOppWorkflow> findByOrgIdAndLiveInd(String orgId, boolean b);
    
    List<InvestorOppWorkflow> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);
}
