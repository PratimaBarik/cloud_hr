package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.RegionsTarget;

@Repository
public interface RegionsTargetRepository extends JpaRepository<RegionsTarget, String> {

	public RegionsTarget findByRegionsId(String regionsId);

	public RegionsTarget findByRegionsIdAndYearAndQuarter(String regionsId, double year, String quarter);

//	public List<Regions> findByOrgIdAndLiveInd(String orgId, boolean b);
//
//	public List<Regions> findByRegionsContainingAndLiveInd(String name, boolean b);

//	public PerformanceManagement findByPerformanceManagementId(String performanceManagementId);
//
//	public List<PerformanceManagement> findByOrgIdAndLiveInd(String orgId, boolean b);
//
//	public List<PerformanceManagement> findByKpiContainingAndLiveInd(String name, boolean b);
//
//	public List<PerformanceManagement> findByDepartmentIdAndLiveInd(String departmentId, boolean b);

}
