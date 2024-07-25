package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.organization.mapper.OrganizationMapper;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminRegisterMapper {
	
	private OrganizationMapper organization;

	private EmployeeMapper employee;

	
	
	

}
