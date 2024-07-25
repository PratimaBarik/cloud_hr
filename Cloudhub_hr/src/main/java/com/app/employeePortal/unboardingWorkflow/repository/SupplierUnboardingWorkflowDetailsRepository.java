package com.app.employeePortal.unboardingWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingWorkflowDetails;

@Repository
public interface SupplierUnboardingWorkflowDetailsRepository extends JpaRepository<SupplierUnboardingWorkflowDetails, String>{

	List<SupplierUnboardingWorkflowDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	@Query(value = "select exp  from SupplierUnboardingWorkflowDetails exp  where exp.supplierUnboardingWorkflowDetailsId=:supplierUnboardingWorkflowDetailsId")
	SupplierUnboardingWorkflowDetails getBySupplierUnboardingWorkflowDetailsId(@Param(value="supplierUnboardingWorkflowDetailsId")
			String supplierUnboardingWorkflowDetailsId);

	List<SupplierUnboardingWorkflowDetails> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);
}
