package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.ServiceLineReqMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.registration.mapper.DepartmentMapper;

public interface ServiceLineService {

	public ServiceLineRespMapper saveServiceLine(ServiceLineReqMapper requestMapper);

	public	List<ServiceLineRespMapper> getServiceLineByOrgId(String orgId);

	public	ServiceLineRespMapper updateServiceLine(String serviceLineId, ServiceLineReqMapper requestMapper);

	public void deleteServiceLine(String serviceLineId, String userId);

//	public List<ServiceLineRespMapper> getServiceLineByName(String name);

	public List<ServiceLineRespMapper> getServiceLineByDepartmentId(String departmentId);

	public HashMap getServiceLineCountByOrgId(String orgId);

	public DepartmentMapper updateServiceLineDepartment(String orgId, String departmentId, boolean liveInd);

	public ByteArrayInputStream exportServiceLineListToExcel(List<ServiceLineRespMapper> list);

	public boolean checkServiceLineNameInServiceLineByOrgLevel(String serviceLineName, String orgId);

	public List<ServiceLineRespMapper> getServiceLineByNameByOrgLevel(String name, String orgId);
}
