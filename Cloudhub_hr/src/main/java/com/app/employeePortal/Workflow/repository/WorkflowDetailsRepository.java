package com.app.employeePortal.Workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Workflow.entity.WorkflowDetails;

@Repository
public interface WorkflowDetailsRepository extends JpaRepository<WorkflowDetails, String> {
	
	@Query(value = "select exp  from WorkflowDetails exp  where exp.workflowDetailsId=:workflowDetailsId")
	WorkflowDetails getByWorkflowDetailsId(@Param(value = "workflowDetailsId") String workflowDetailsId);

	List<WorkflowDetails> findByOrgIdAndTypeAndLiveInd(String orgId, String type, boolean b);

	List<WorkflowDetails> findByOrgIdAndLiveIndAndPublishIndAndType(String orgId, boolean b, boolean c, String type);

    WorkflowDetails findByWorkflowDetailsIdAndOrgId(String workflowId,String orgId);
    
    WorkflowDetails findByWorkflowNameAndOrgIdAndLiveInd(String workflowName,String orgId, boolean b);

	List<WorkflowDetails> findByOrgIdAndTypeAndLiveIndAndGlobalInd(String orgId, String type, boolean b, boolean b1);

	List<WorkflowDetails> findByWorkflowNameAndOrgIdAndTypeAndLiveInd(String workflowName, String orgId, String type, boolean b);
}
