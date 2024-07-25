package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkRespMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtDropDownMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtReqMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtRespMapper;

public interface PerformanceManagementService {

	public PerformanceMgmtRespMapper savePerformanceManagement(PerformanceMgmtReqMapper requestMapper);

	public PerformanceMgmtRespMapper getPerformanceManagementById(String performanceManagementId);

	public List<PerformanceMgmtRespMapper> getPerformanceManagementByOrgId(String orgId);

	public PerformanceMgmtRespMapper updatePerformanceManagement(String performanceManagementId,
			PerformanceMgmtReqMapper requestMapper);

	public void deletePerformanceManagement(String performanceManagementId, String userId);

	public List<PerformanceMgmtRespMapper> getPerformanceManagementByKpiByOrgLevel(String kpi, String orgId);

//	public List<PerformanceMgmtRespMapper> getPerformanceManagementByDepartmentId(String departmentId);

	public HashMap getPerformanceManagementCountByOrgId(String orgId);

	public ByteArrayInputStream exportPerformanceManagementListToExcel(List<PerformanceMgmtRespMapper> list);

	public List<PerformanceMgmntDeptLinkRespMapper> saveDepartmentPerformanceMgmt(PerformanceMgmntDeptLinkMapper requestMapper);

	public List<PerformanceMgmntDeptLinkRespMapper> getDepartmentPerformanceMgmtByDepartmentIdAndRoleTypeId(String departmentId,
			String roleTypeId);

	public List<PerformanceMgmtDropDownMapper> getPerformanceManagementByOrgIdForDropDown(String orgId);

	public PerformanceMgmtRespMapper updatePerformanceManagementForCurrencyInd(String performanceManagementId,
			PerformanceMgmtReqMapper requestMapper);

	public boolean checkKpiInPerformanceMgmtByOrgLevel(String kpi, String orgId);

}
