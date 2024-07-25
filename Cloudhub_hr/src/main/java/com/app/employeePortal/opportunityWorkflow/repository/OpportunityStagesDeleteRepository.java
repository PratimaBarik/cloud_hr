package com.app.employeePortal.opportunityWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStagesDelete;

@Repository
public interface OpportunityStagesDeleteRepository extends JpaRepository<OpportunityStagesDelete, String>{

	OpportunityStagesDelete findByOpportunityStagesId(String opportunityStagesId);

	List<OpportunityStagesDelete> findByOrgId(String orgId);

	List<OpportunityStagesDelete> findByOpportunityWorkflowDetailsId(String oppworkFlowId);

	
}
