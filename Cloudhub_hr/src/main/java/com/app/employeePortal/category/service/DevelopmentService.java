package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.DevelopmentMapper;
import com.app.employeePortal.category.mapper.RoleMapper;

public interface DevelopmentService {

	public DevelopmentMapper createDevelopment(DevelopmentMapper developmentMapper);

	public void deleteDevelopment(String developmentId, String userId);

	public HashMap getDevelopmentCountByOrgId(String orgId);

	public List<DevelopmentMapper> getDevelopmentByOrgId(String orgId);

	public DevelopmentMapper updateDevelopment(String developmentId, DevelopmentMapper developmentMapper);

	public List<DevelopmentMapper> getDevelopmentByTaskTypeIdAndValue(String taskTypeId, String value,
			String orgId);
	
}
