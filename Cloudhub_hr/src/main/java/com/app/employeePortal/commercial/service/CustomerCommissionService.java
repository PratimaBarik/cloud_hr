package com.app.employeePortal.commercial.service;

import java.util.List;

import com.app.employeePortal.commercial.mapper.CustomerCommissionMapper;

public interface CustomerCommissionService {

	String saveCustomerCommission(CustomerCommissionMapper customerCommissionMapper);

	List<CustomerCommissionMapper> getCustomerCommissionListByCustomerId(String customerId);

	//CustomerCommissionMapper getCustomerCommissionListByOrgrId(String orgId);

	
	

}
