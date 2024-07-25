package com.app.employeePortal.employee.service;

import java.util.List;

import com.app.employeePortal.employee.mapper.EmployeeCertificationLinkMapper;

public interface EmployeeCertificationLinkService {

	public String saveEmployeeCertification(EmployeeCertificationLinkMapper employeeCertificationLinkMapper);

	public boolean checkCertificationInCertificationSet(String employeeCertificationName, String employeeId);

	public List<EmployeeCertificationLinkMapper> getEmployeeCertificationDetails(String employeeId);

	public String deleteEmployeeCertification(String employeeCertificationLinkId);
	
	
}
