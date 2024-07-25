package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Development;
@Repository
public interface DevelopmentRepository extends JpaRepository<Development, String>{

	public List<Development> findByOrgIdAndLiveInd(String orgId, boolean b);

	public Development findByDevelopmentIdAndLiveInd(String developmentId, boolean b);

	public Development findByDepartmentIdAndRoletypeId(String departmentId, String roletypeId);

	public List<Development> findByTaskTypeIdAndValueAndOrgIdAndLiveInd(String taskTypeId, String value, String orgId,
			boolean b);

	public Development findByDepartmentIdAndRoletypeIdAndTaskTypeIdAndOrgIdAndLiveInd(String department,
			String roleType, String taskType, String orgId, boolean b);
}
