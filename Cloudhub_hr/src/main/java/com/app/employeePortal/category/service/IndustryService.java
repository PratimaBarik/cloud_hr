package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.IndustryMapper;

public interface IndustryService {

	IndustryMapper saveIndustry(IndustryMapper industryMapper);

	List<IndustryMapper> getIndustryMapperByOrgId(String orgId);

	IndustryMapper updateIndustry(String industryId, IndustryMapper industryMapper);

	void deleteIndustry(String industryId, String userId);

	List<IndustryMapper> getIndustryByName(String name, String orgId);

	HashMap getIndustryCountByOrgId(String orgId);

	boolean checkNameInIndustry(String name, String orgIdFromToken);
	
}
