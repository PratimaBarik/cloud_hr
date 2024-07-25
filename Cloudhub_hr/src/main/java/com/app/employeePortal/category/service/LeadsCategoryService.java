package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.LeadsCategoryMapper;

public interface LeadsCategoryService {

	public LeadsCategoryMapper CreateLeadsCategory(LeadsCategoryMapper leadsCatagoryMapper);

	public List<LeadsCategoryMapper> getLeadsCategoryListByOrgId(String orgId);

	public LeadsCategoryMapper getLeadsCatagoryById(String leadsCatagoryId);

	public HashMap getLeadsCategoryCountByOrgId(String orgId);

	public ByteArrayInputStream exportleadsCategoryListToExcel(List<LeadsCategoryMapper> list);


}
