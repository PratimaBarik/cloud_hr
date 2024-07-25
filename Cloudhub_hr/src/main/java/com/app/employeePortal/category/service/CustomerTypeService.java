package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.CustomerTypeMapper;

public interface CustomerTypeService {

	public CustomerTypeMapper saveCustomerType(CustomerTypeMapper customerTypeMapper);

	public CustomerTypeMapper getCustomerTypeByCustomerTypeId(String customerTypeId);

	public List<CustomerTypeMapper> getCustomerTypeByOrgId(String orgId);

	public CustomerTypeMapper updateCustomerType(String customerTypeId, CustomerTypeMapper customerTypeMapper);

	public void deleteCustomerType(String customerTypeId, String userId);

	public List<CustomerTypeMapper> getCustomerTypeByNameAndOrgId(String name, String orgId);

	public HashMap getCustomerTypeCountByOrgId(String orgId);

	public ByteArrayInputStream exportCustomerTypeListToExcel(List<CustomerTypeMapper> list);

	public boolean checkNameInCustomerTypeByOrgLevel(String name, String orgId);


}
