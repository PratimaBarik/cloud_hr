package com.app.employeePortal.commercial.service;

import java.util.List;

import com.app.employeePortal.commercial.mapper.PartnerCommissionMapper;

public interface PartnerCommissionService {

	String savePartnerCommission(PartnerCommissionMapper partnerCommissionMapper);

	List<PartnerCommissionMapper> getPartnerCommissionListByPartnerId(String partnerId);
}
