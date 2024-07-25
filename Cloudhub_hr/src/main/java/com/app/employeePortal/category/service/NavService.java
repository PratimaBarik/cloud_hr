package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.NavRequestMapper;
import com.app.employeePortal.category.mapper.NavResponseMapper;

public interface NavService {

	public NavResponseMapper createNav(NavRequestMapper requestMapper);

	public List<NavResponseMapper> getNavByOrgId(String orgId);

	public NavResponseMapper getNavById(int navDetailId);

	public NavResponseMapper updateNav(int navDetailId, NavRequestMapper requestMapper);

	public void deleteNav(int navDetailId);

	public HashMap getNavCountByOrgId(String orgId);

	public ByteArrayInputStream exportNavListToExcel(List<NavResponseMapper> list);

	public boolean checkNavNameInNavDetailsByOrgLevel(String navName, String orgId);


}
