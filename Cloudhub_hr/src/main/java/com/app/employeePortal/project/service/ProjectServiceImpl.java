package com.app.employeePortal.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.project.Entity.ProjectDetails;
import com.app.employeePortal.project.Repository.ProjectRepository;
import com.app.employeePortal.project.mapper.ProjectMapper;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {


	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	CustomerRepository customerRepository;
	
//
//	@Override
//	public String saveProject(ProjectMapper projectMapper) {
//		String productId = null;
//		if (null != projectMapper) {
//
//			ProjectDetails project = new ProjectDetails();
//			project.setProject_name(projectMapper.getProject());
//			project.setNotes_Field(projectMapper.getNotesField());
//
//			project.setUser_id(projectMapper.getUserId());
//			project.setOrganization_id(projectMapper.getOrganizationId());
//			project.setCreation_date(projectMapper.getCreationDate());
//
//			try {
//				project.setStart_time(Utility.getDateFromISOString(projectMapper.getStartTime()));
//				project.setEnd_time(Utility.getDateFromISOString(projectMapper.getEndTime()));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			ProjectDetails details = projectRepository.save(project);
//			productId = details.getProject_id();
//		}
//		return productId;
//	}
//
//	@Override
//	public String updateProject(String projectId, ProjectMapper projectMapper) {
//
//		if (null != projectId) {
//			ProjectDetails project = projectRepository.getProjectDetailsById(projectId);
//			project.setLive_ind(false);
//			projectRepository.save(project);
//
//			ProjectDetails projectt = new ProjectDetails();
//
//			if (null != project) {
//
//				if (projectMapper.getProject() != null) {
//					projectt.setProject_name(projectMapper.getProject());
//				} else {
//					projectt.setProject_name(project.getProject_name());
//				}
//
//				if (projectMapper.getNotesField() != null) {
//					projectt.setNotes_Field(projectMapper.getNotesField());
//
//				} else {
//					projectt.setNotes_Field(project.getNotes_Field());
//				}
//
//				if (projectMapper.getStartTime() != null) {
//					try {
//						projectt.setStart_time(Utility.getDateFromISOString(projectMapper.getStartTime()));
//					} catch (Exception e) {
//
//						e.printStackTrace();
//					}
//
//				} else {
//					projectt.setStart_time(project.getStart_time());
//				}
//
//				if (projectMapper.getEndTime() != null) {
//					try {
//						projectt.setEnd_time(Utility.getDateFromISOString(projectMapper.getEndTime()));
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				} else {
//					projectt.setEnd_time(project.getEnd_time());
//				}
//
//				if (projectMapper.getCreationDate() != null) {
//					projectt.setCreation_date(projectMapper.getCreationDate());
//
//				} else {
//					projectt.setCreation_date(project.getCreation_date());
//				}
//
//				projectt.setProject_id(projectId);
//				projectt.setUser_id(project.getUser_id());
//				projectt.setCreation_date(project.getCreation_date());
//				projectt.setOrganization_id(project.getOrganization_id());
//				projectt.setLive_ind(false);
//
//
//				projectRepository.save(projectt);
//
//
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public ProjectMapper getProject(String projectId) {
//
//		ProjectDetails project = projectRepository.getProjectDetailsById(projectId);
//		ProjectMapper projectMapper = new ProjectMapper();
//
//		if (null != project) {
//
//			projectMapper.setProjectId(project.getProject_id());
//			projectMapper.setUserId(project.getUser_id());
//			projectMapper.setNotesField(project.getNotes_Field());
//			projectMapper.setStartTime(Utility.getISOFromDate(project.getStart_time()));
//			projectMapper.setEndTime(Utility.getISOFromDate(project.getEnd_time()));
//			projectMapper.setCreationDate(project.getCreation_date());
//
//		}
//
//		return projectMapper;
//	}
//
//	@Override
//	public List<ProjectMapper> getProjectByUserId(String userId) {
//		return projectRepository.getProjectListByUserId(userId).stream().map(c -> getProject(c.getProject_id())).collect(Collectors.toList());
//	}
	
	
	
	
	
	
	@Override
	public String saveProject(ProjectMapper projectMapper) {
		String projectId = null;
		if (projectMapper != null) {
			ProjectDetails project = new ProjectDetails();
			project.setProjectName(projectMapper.getProjectName());
			project.setUserId(projectMapper.getUserId());
			project.setOrgId(projectMapper.getOrgId());
			project.setCustomerId(projectMapper.getCustomerId());
			project.setCreationDate(new Date());
			project.setLiveInd(true);
			project.setEditInd(true);
			projectId = projectRepository.save(project).getProjectId();
		}
		return projectId;
	}

	@Override
	public List<ProjectMapper> getAllProjectTypeByOrgId(String orgId) {
		List<ProjectMapper> resultList = new ArrayList<ProjectMapper>();
		List<ProjectDetails> projectList = projectRepository.findByOrgIdAndLiveInd(orgId,true);

		if (null != projectList && !projectList.isEmpty()) {
			projectList.stream().map(project-> {
				ProjectMapper projectMapper = new ProjectMapper();
				projectMapper.setProjectId(project.getProjectId());
				projectMapper.setCreationDate(project.getCreationDate());
				projectMapper.setProjectName(project.getProjectName());
				projectMapper.setEditInd(project.isEditInd());
				projectMapper.setLiveInd(project.isLiveInd());
				projectMapper.setOrgId(project.getOrgId());
				projectMapper.setUserId(project.getUserId());
				projectMapper.setCreatorName(employeeService.getEmployeeFullName(project.getUserId()));
				
				if (!StringUtils.isEmpty(project.getCustomerId())) {
					projectMapper.setCustomerId(project.getCustomerId());
					System.out.println("project.getCustomerId()======="+project.getCustomerId());
					Customer customer = customerRepository.getCustomerByIdAndLiveInd(project.getCustomerId());
					if(null!=customer) {
						if (!StringUtils.isEmpty(customer.getName())) {
							projectMapper.setCustomerName(customer.getName());
					}
					}
					}
				
				resultList.add(projectMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public ProjectMapper updateProject(ProjectMapper projectMapper) {
		ProjectMapper resultMapper = null;
		String projectId = projectMapper.getProjectId();
		if (null != projectId) {
			ProjectDetails project = projectRepository.getById(projectId);

			if (null != project.getProjectId()) {
				//unit.setCreationDate(new Date());
				//unit.setOrgId(unitMapper.getOrgId());
				project.setEditInd(projectMapper.isEditInd());
				//unit.setLiveInd(unitMapper.isLiveInd());
				project.setProjectName(projectMapper.getProjectName());
				//unit.setUserId(unitMapper.getUserId());
				projectRepository.save(project);

			}
			resultMapper = getProjectDetailsById(projectId);
		}
		return resultMapper;
	}
	@Override
	public ProjectMapper getProjectDetailsById(String projectId) {
		ProjectDetails project = projectRepository.getById(projectId);
		ProjectMapper resultMapper=new ProjectMapper();
		
		if(null!=project){
			resultMapper.setCreationDate(project.getCreationDate());
			resultMapper.setEditInd(project.isEditInd());
			resultMapper.setLiveInd(project.isLiveInd());
			resultMapper.setOrgId(project.getOrgId());
			resultMapper.setProjectName(project.getProjectName());
			resultMapper.setUserId(project.getUserId());
			resultMapper.setProjectId(project.getProjectId());
			resultMapper.setCreatorName(employeeService.getEmployeeFullName(project.getUserId()));
			if (!StringUtils.isEmpty(project.getCustomerId())) {
				resultMapper.setCustomerId(project.getCustomerId());
				Customer customer = customerRepository.getCustomerByIdAndLiveInd(project.getCustomerId());
				if(null!=customer) {
					if (!StringUtils.isEmpty(customer.getName())) {
						resultMapper.setCustomerName(customer.getName());
				}
				}
				}
		}
		return resultMapper;
	}

	@Override
	public void deleteProjectById(String projectId) {
		if (null != projectId) {
			ProjectDetails project = projectRepository.findByProjectId(projectId);
			if(null!=project) {
				project.setLiveInd(false);
			projectRepository.save(project);
			}
		}
		
	}

	@Override
	public List<ProjectMapper> getProjectDetailsByCustomerId(String customerId) {
		List<ProjectMapper> resultList = new ArrayList<ProjectMapper>();
		List<ProjectDetails> projectList = projectRepository.findByCustomerIdAndLiveInd(customerId,true);

		if (null != projectList && !projectList.isEmpty()) {
			projectList.stream().map(project-> {
				ProjectMapper projectMapper = new ProjectMapper();
				projectMapper.setProjectId(project.getProjectId());
				projectMapper.setCreationDate(project.getCreationDate());
				projectMapper.setProjectName(project.getProjectName());
				projectMapper.setEditInd(project.isEditInd());
				projectMapper.setLiveInd(project.isLiveInd());
				projectMapper.setOrgId(project.getOrgId());
				projectMapper.setUserId(project.getUserId());
				projectMapper.setCreatorName(employeeService.getEmployeeFullName(project.getUserId()));
				
				if (!StringUtils.isEmpty(project.getCustomerId())) {
					projectMapper.setCustomerId(project.getCustomerId());
					Customer customer = customerRepository.getCustomerByIdAndLiveInd(project.getCustomerId());
					if(null!=customer) {
						if (!StringUtils.isEmpty(customer.getName())) {
							projectMapper.setCustomerName(customer.getName());
					}
					}
					}
				
				resultList.add(projectMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}
	
	
	
}

		

	 


