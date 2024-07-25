package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.DistributionAutomationMapper;

public interface DistributionAutomationService {

	DistributionAutomationMapper saveDistributionAutomation(DistributionAutomationMapper distributionAutomationMapper);

	DistributionAutomationMapper getDistributionAutomationByDistributionAutomationId(String distributionAutomationId);

	DistributionAutomationMapper getDistributionAutomationByOrgIdAndType(String orgId, String type);

	


}
