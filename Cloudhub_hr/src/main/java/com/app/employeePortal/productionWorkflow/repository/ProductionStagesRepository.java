package com.app.employeePortal.productionWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStagesDelete;
import com.app.employeePortal.productionWorkflow.entity.ProductionStages;
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingStages;

@Repository
public interface ProductionStagesRepository extends JpaRepository<ProductionStages, String> {

	List<ProductionStages> findByProductionWorkflowDetailsIdAndLiveInd(String productionWorkflowDetailsId, boolean b);

	@Query(value = "select exp  from ProductionStages exp  where exp.productionStagesId=:productionStagesId and liveInd=true")
	public ProductionStages getByProductionStagesId(@Param(value = "productionStagesId") String productionStagesId);

	List<ProductionStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);

}
