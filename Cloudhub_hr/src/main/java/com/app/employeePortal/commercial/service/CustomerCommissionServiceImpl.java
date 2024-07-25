package com.app.employeePortal.commercial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.commercial.entity.CustomerCommission;
import com.app.employeePortal.commercial.mapper.CustomerCommissionMapper;
import com.app.employeePortal.commercial.repository.CustomerCommissionRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional

public class CustomerCommissionServiceImpl implements CustomerCommissionService {
	
	@Autowired
	CustomerCommissionRepository customerCommissionRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
    public String saveCustomerCommission(CustomerCommissionMapper customerCommissionMapper) {
		String Id = null;
			CustomerCommission dbCustomerCommission = customerCommissionRepository.findByCustomerId(customerCommissionMapper.getCustomerId());
			
	        if (null != dbCustomerCommission) {
	            dbCustomerCommission.setRequirementType(customerCommissionMapper.getRequirementType());
	            dbCustomerCommission.setCommissionDeal(customerCommissionMapper.getCommissionDeal());
	            dbCustomerCommission.setPaymentDate(customerCommissionMapper.getPaymentDate());
	            dbCustomerCommission.setCommissionAmount(customerCommissionMapper.getCommissionAmount());
	            dbCustomerCommission.setCurrency(customerCommissionMapper.getCurrency());
	            dbCustomerCommission.setUserId(customerCommissionMapper.getUserId());
	            dbCustomerCommission.setOrgId(customerCommissionMapper.getOrgId());
	            dbCustomerCommission.setLastUpdatedOn(new Date());
	            dbCustomerCommission.setCustomerId(customerCommissionMapper.getCustomerId());
	            
	            Id = customerCommissionRepository.save(dbCustomerCommission).getCustomerCommissionId();
	        	}else { 
		
        CustomerCommission newCustomerCommission = new CustomerCommission();
        newCustomerCommission.setCustomerId(customerCommissionMapper.getCustomerId());
        newCustomerCommission.setOrgId(customerCommissionMapper.getOrgId());
        newCustomerCommission.setRequirementType(customerCommissionMapper.getRequirementType());
        newCustomerCommission.setCommissionDeal(customerCommissionMapper.getCommissionDeal());
        newCustomerCommission.setPaymentDate(customerCommissionMapper.getPaymentDate());
        newCustomerCommission.setCommissionAmount(customerCommissionMapper.getCommissionAmount());
        newCustomerCommission.setCurrency(customerCommissionMapper.getCurrency());
        newCustomerCommission.setLastUpdatedOn(new Date());
        newCustomerCommission.setUserId(customerCommissionMapper.getUserId());
        
        Id = customerCommissionRepository.save(newCustomerCommission).getCustomerCommissionId();
	        	}
        
        return Id;
		
		
    }

	

	
	
	@Override
	public List<CustomerCommissionMapper> getCustomerCommissionListByCustomerId(String customerId) {


		List<CustomerCommission> customerCommissionList = customerCommissionRepository.getByCustomerId(customerId);
		List<CustomerCommissionMapper> mapperList = new ArrayList<>();
		if (null != customerCommissionList && !customerCommissionList.isEmpty()) {

			customerCommissionList.stream().map(li -> {

				CustomerCommissionMapper customerCommissionMapper = new CustomerCommissionMapper();

				customerCommissionMapper.setCustomerCommissionId(li.getCustomerCommissionId());
				customerCommissionMapper.setCustomerId(li.getCustomerId());
				customerCommissionMapper.setOrgId(li.getOrgId());
				customerCommissionMapper.setRequirementType(li.getRequirementType());
				customerCommissionMapper.setCommissionDeal(li.getCommissionDeal());
				customerCommissionMapper.setPaymentDate(li.getPaymentDate());
				customerCommissionMapper.setCommissionAmount(li.getCommissionAmount());
				customerCommissionMapper.setCurrency(li.getCurrency());
				customerCommissionMapper.setLastUpdatedOn(Utility.getISOFromDate(li.getLastUpdatedOn()));
				customerCommissionMapper.setUserId(li.getUserId());
				
				EmployeeDetails employeeDetails = employeeRepository
		                .getEmployeeDetailsByEmployeeId(li.getUserId());
		        if (null != employeeDetails) {
		            String middleName = " ";
		            String lastName = "";

		            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

		                lastName = employeeDetails.getLastName();
		            }

		            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

		                middleName = employeeDetails.getMiddleName();
		                customerCommissionMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
		            } else {

		            	customerCommissionMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
		            }
		        }
				mapperList.add(customerCommissionMapper);

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}
	


}		
	

