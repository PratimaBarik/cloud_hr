package com.app.employeePortal.ruleEngine.service;

import com.app.employeePortal.ruleEngine.mapper.OrganizationLeadsAgingRuleMapper;

public interface AgeingService {
	
	public String saveOrganizationColdLeadsAgingRule(OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper);
	
	public String saveOrganizationHotLeadsAgingRule(OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper);
	
	public String saveOrganizationWarmLeadsAgingRule(OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper);
	
	public String saveOrganizationOtherLeadsAgingRule(OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper);

	public OrganizationLeadsAgingRuleMapper saveLeadsAgingRule(
			OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper);

	public OrganizationLeadsAgingRuleMapper getLeadsAgingRule(String orgId);
}
