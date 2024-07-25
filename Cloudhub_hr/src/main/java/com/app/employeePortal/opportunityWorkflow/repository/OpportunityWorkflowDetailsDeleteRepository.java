package com.app.employeePortal.opportunityWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetailsDelete;

@Repository
public interface OpportunityWorkflowDetailsDeleteRepository extends JpaRepository<OpportunityWorkflowDetailsDelete, String>{

	List<OpportunityWorkflowDetailsDelete> findByOrgId(String orgId);

	OpportunityWorkflowDetailsDelete findByOpportunityWorkflowDetailsId(String opportunityWorkflowDetailsId);

	
}
