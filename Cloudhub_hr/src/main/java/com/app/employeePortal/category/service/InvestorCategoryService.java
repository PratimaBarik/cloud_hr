package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.InvestorCategoryMapper;

public interface InvestorCategoryService {
	
	public InvestorCategoryMapper saveInvestorCategory(InvestorCategoryMapper investorCategoryMapper);

	public InvestorCategoryMapper getInvestorCategoryById(String investorCategoryId);

	public List<InvestorCategoryMapper> getInvestorCategoryByOrgId(String orgId);

	public InvestorCategoryMapper updateInvestorCategory(String investorCategoryId,
			InvestorCategoryMapper investorCategoryMapper);

	public void deleteInvestorCategory(String investorCategoryId, String userId);

	public List<InvestorCategoryMapper> getInvestorCategoryByName(String name);

	public HashMap getInvestorCategoryCountByOrgId(String orgId);

	public ByteArrayInputStream exportInvestorCategoryListToExcel(List<InvestorCategoryMapper> list);

	public boolean checkNameInInvestorCategoryByOrgLevel(String name, String orgId);


}
