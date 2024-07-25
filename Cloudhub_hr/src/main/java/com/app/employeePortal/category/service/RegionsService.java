package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.RegionsDropDownMapper;
import com.app.employeePortal.category.mapper.RegionsMapper;
import com.app.employeePortal.category.mapper.RegionsTargetDashBoardMapper;
import com.app.employeePortal.category.mapper.RegionsTargetMapper;

public interface RegionsService {

	public RegionsMapper createRegions(RegionsMapper requestMapper);

	public RegionsMapper getRegionsByRegionsId(String regionsId);

	public List<RegionsMapper> getRegionsByOrgId(String orgId);

	public RegionsMapper updateRegions(String regionsId, RegionsMapper requestMapper);

	public void deleteRegionsByRegionsId(String regionsId, String userId);

	public List<RegionsDropDownMapper> getRegionsByOrgIdForDropDown(String orgId);

	public HashMap getRegionsCountByOrgId(String orgId);

	public RegionsTargetMapper createRegionsTarget(RegionsTargetMapper requestMapper);

	public RegionsTargetMapper getRegionsTargetByRegionsId(String regionsId, double year, String quarter);

	public List<RegionsTargetMapper> getRegionsTargetByYearAndQuarterForDashBoard(double year, String quarter, String orgId);

	public List<RegionsTargetDashBoardMapper> getRegionsTargetByYearAndQuarterByRegionsIdForDashBoard(int year,
			String quarter, String orgId, String regionsId, String type);

	public ByteArrayInputStream exportRegionsListToExcel(List<RegionsMapper> list);

	public boolean checkRegionInRegionsByOrgLevel(String regions, String orgId);

	public List<RegionsMapper> getRegionsByRegionsorgLevel(String regions, String orgId);
}
