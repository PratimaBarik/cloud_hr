package com.app.employeePortal.productionWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.productionWorkflow.entity.ProductionWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingWorkflowDetails;

@Repository
public interface ProductionWorkflowDetailsRepository extends JpaRepository<ProductionWorkflowDetails, String> {

	@Query(value = "select exp  from ProductionWorkflowDetails exp  where exp.productionWorkflowDetailsId=:productionWorkflowDetailsId")
	ProductionWorkflowDetails getByProductionWorkflowDetailsId(@Param(value = "productionWorkflowDetailsId") String productionWorkflowDetailsId);

	List<ProductionWorkflowDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<ProductionWorkflowDetails> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);


}
