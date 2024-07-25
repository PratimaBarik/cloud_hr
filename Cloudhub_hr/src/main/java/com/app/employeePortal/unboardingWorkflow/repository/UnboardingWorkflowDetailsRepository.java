package com.app.employeePortal.unboardingWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.unboardingWorkflow.entity.UnboardingWorkflowDetails;

@Repository
public interface UnboardingWorkflowDetailsRepository extends JpaRepository<UnboardingWorkflowDetails, String>{

	List<UnboardingWorkflowDetails> findByOrgId(String orgId);
	
	@Query(value = "select exp  from UnboardingWorkflowDetails exp  where exp.unboardingWorkflowDetailsId=:UnboardingWorkflowDetailsId")
	public UnboardingWorkflowDetails getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(@Param(value="UnboardingWorkflowDetailsId")String UnboardingWorkflowDetailsId);

	List<UnboardingWorkflowDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<UnboardingWorkflowDetails> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);
	
	
	
	
}
