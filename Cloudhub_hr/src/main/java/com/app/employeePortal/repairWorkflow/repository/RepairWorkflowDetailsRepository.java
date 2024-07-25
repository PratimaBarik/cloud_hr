package com.app.employeePortal.repairWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.repairWorkflow.entity.RepairWorkflowDetails;

@Repository
public interface RepairWorkflowDetailsRepository extends JpaRepository<RepairWorkflowDetails, String> {
	
	@Query(value = "select exp  from RepairWorkflowDetails exp  where exp.repairWorkflowDetailsId=:repairWorkflowDetailsId")
	RepairWorkflowDetails getByRepairWorkflowDetailsId(@Param(value = "repairWorkflowDetailsId") String repairWorkflowDetailsId);

	List<RepairWorkflowDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<RepairWorkflowDetails> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);



}
