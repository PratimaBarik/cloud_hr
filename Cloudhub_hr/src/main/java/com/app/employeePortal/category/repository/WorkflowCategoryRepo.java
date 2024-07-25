package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.WorkflowCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowCategoryRepo extends JpaRepository<WorkflowCategory, String> {

	WorkflowCategory findByWorkflowCategoryIdAndLiveInd(String WorkflowCategory, boolean b);

	List<WorkflowCategory> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<WorkflowCategory> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
	
	List<WorkflowCategory> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

    List<WorkflowCategory> findByNameContainingAndWorkflowCategoryIdNotAndLiveIndAndOrgId(String name,String SupplierCatId, boolean b, String orgId);
}
