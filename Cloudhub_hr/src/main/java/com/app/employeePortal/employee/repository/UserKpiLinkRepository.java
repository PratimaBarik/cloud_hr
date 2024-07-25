package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.UserKpiLink;

@Repository
public interface UserKpiLinkRepository extends JpaRepository<UserKpiLink, String> {

	List<UserKpiLink> findByEmployeeIdAndLiveInd(String employeeId, boolean b);

	List<UserKpiLink> findByYearAndQuarterAndEmployeeIdAndLiveInd(double year, String quarter, String employeeId,
			boolean b);

	List<UserKpiLink> findByYearAndQuarterAndEmployeeIdAndPerformanceManagementIdAndLiveInd(double year, String quarter,
			String employeeId, String kpiSales, boolean b);
	
}
