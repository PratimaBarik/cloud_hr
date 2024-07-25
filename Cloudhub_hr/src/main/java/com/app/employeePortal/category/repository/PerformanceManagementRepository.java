package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.PerformanceManagement;

@Repository
public interface PerformanceManagementRepository extends JpaRepository<PerformanceManagement, String> {

	public PerformanceManagement findByPerformanceManagementId(String performanceManagementId);

	public List<PerformanceManagement> findByOrgIdAndLiveInd(String orgId, boolean b);

//	public List<PerformanceManagement> findByKpiContainingAndLiveInd(String name, boolean b);

	public List<PerformanceManagement> findByDepartmentIdAndLiveInd(String departmentId, boolean b);

	public List<PerformanceManagement> findByKpiContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

	public List<PerformanceManagement> findByKpiAndOrgIdAndLiveInd(String kpi, String orgId, boolean b);

}
