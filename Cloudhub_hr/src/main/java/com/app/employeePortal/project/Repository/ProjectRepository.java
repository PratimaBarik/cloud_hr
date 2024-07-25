package com.app.employeePortal.project.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.project.Entity.ProjectDetails;
@Repository

public interface ProjectRepository extends JpaRepository<ProjectDetails, String>{
	
	@Query(value = "select a  from ProjectDetails a  where a.projectId=:projectId" )
    public ProjectDetails getProjectDetailsById(@Param(value="projectId")String projectId);
	
	@Query(value = "select a  from ProjectDetails a  where a.userId=:userId" )
	public List<ProjectDetails> getProjectListByUserId(@Param(value="userId") String userId);

	List<ProjectDetails> findByOrgId(String orgId);

	ProjectDetails findByProjectId(String projectId);

	List<ProjectDetails> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<ProjectDetails> findByCustomerIdAndLiveInd(String customerId, boolean b);
}
