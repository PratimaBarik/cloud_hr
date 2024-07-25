package com.app.employeePortal.opportunityWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;

@Repository
public interface OpportunityWorkflowDetailsRepository extends JpaRepository<OpportunityWorkflowDetails, String>{

	List<OpportunityWorkflowDetails> findByOrgId(String orgId);
	
	@Query(value = "select exp  from OpportunityWorkflowDetails exp  where exp.opportunityWorkflowDetailsId=:opportunityWorkflowDetailsId")
	public OpportunityWorkflowDetails getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(@Param(value="opportunityWorkflowDetailsId")String opportunityWorkflowDetailsId);

	List<OpportunityWorkflowDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<OpportunityWorkflowDetails> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);
	
	
	
	
}
