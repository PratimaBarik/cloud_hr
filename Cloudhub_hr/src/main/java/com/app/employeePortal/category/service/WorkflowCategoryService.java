package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.WorkflowCategoryMapper;

import java.util.HashMap;
import java.util.List;

public interface WorkflowCategoryService {

	WorkflowCategoryMapper saveWorkflowCategory(WorkflowCategoryMapper mapper);

	boolean checkNameInWorkflowCategory(String name, String orgIdFromToken);

	List<WorkflowCategoryMapper> getWorkflowCategoryByOrgId(String orgIdFromToken);

	WorkflowCategoryMapper updateWorkflowCategory(String equipmentId, WorkflowCategoryMapper mapper);

	void deleteWorkflowCategory(String workflowCategoryId, String userIdFromToken);

	List<WorkflowCategoryMapper> getWorkflowCategoryByName(String name, String orgIdFromToken);

	HashMap getWorkflowCategoryCountByOrgId(String orgIdFromToken);

	boolean checkNameInWorkflowCategoryInUpdate(String name, String orgIdFromToken,String workflowCategoryId);
	
}
