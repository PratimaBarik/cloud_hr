package com.app.employeePortal.project.service;

import java.util.List;

import com.app.employeePortal.project.mapper.ProjectMapper;

public interface ProjectService {

	String saveProject(ProjectMapper projectMapper);

	List<ProjectMapper> getAllProjectTypeByOrgId(String orgId);

	ProjectMapper updateProject(ProjectMapper projectMapper);

	ProjectMapper getProjectDetailsById(String projectId);

	void deleteProjectById(String projectId);

	List<ProjectMapper> getProjectDetailsByCustomerId(String customerId);
	
	
//	public String saveProject(ProjectMapper projectMapper);
//	
//	public ProjectMapper getProject(String projectId);
//	
//	
//	
//	public String updateProject(String projectId ,ProjectMapper projectMapper);
//
//	public List<ProjectMapper> getProjectByUserId(String userId);

}
