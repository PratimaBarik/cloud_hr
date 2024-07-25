package com.app.employeePortal.unboardingWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingStages;

@Repository
public interface SupplierUnboardingStagesRepository extends JpaRepository<SupplierUnboardingStages, String> {

	List<SupplierUnboardingStages> findBySupplierUnboardingWorkflowDetailsIdAndLiveInd(
			String supplierUnboardingWorkflowDetailsId, boolean b);
	
	@Query(value = "select exp  from SupplierUnboardingStages exp  where exp.supplierUnboardingStagesId=:supplierUnboardingStagesId and liveInd=true")
	public SupplierUnboardingStages getBySupplierUnboardingStagesId(@Param(value="supplierUnboardingStagesId")String supplierUnboardingStagesId);

	List<SupplierUnboardingStages> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<SupplierUnboardingStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);

}
