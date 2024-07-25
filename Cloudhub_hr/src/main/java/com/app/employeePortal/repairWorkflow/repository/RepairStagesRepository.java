package com.app.employeePortal.repairWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.repairWorkflow.entity.RepairStages;

@Repository
public interface RepairStagesRepository extends JpaRepository<RepairStages, String> {

	List<RepairStages> findByRepairWorkflowDetailsIdAndLiveInd(String repairWorkflowDetailsId, boolean b);

	@Query(value = "select exp  from RepairStages exp  where exp.repairStagesId=:repairStagesId and liveInd=true")
	public RepairStages getByRepairStagesId(@Param(value = "repairStagesId") String productionStagesId);

	List<RepairStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);


}
