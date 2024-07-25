package com.app.employeePortal.opportunityWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;

@Repository
public interface OpportunityStagesRepository extends JpaRepository<OpportunityStages, String>{

	List<OpportunityStages> findByOrgId(String orgId);
	
	@Query(value = "select exp  from OpportunityStages exp  where exp.opportunityStagesId=:opportunityStagesId and liveInd=true")
	public OpportunityStages getOpportunityStagesByOpportunityStagesId(@Param(value="opportunityStagesId")String opportunityStagesId);

	//List<OpportunityStages> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<OpportunityStages> findByOpportunityWorkflowDetailsIdAndLiveInd(String oppworkFlowId, boolean b);

	List<OpportunityStages> findByOrgIdAndLiveInd(String orgId, boolean b);
	
	 List<OpportunityStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);

	

}
