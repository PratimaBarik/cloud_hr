package com.app.employeePortal.employee.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.employee.entity.EmployeeIDDetails;


public interface EmployeeIDRepository extends JpaRepository<EmployeeIDDetails, String>{
	
	@Query(value = "select a  from EmployeeIDDetails a  where a.employeeId=:empId" )
	public EmployeeIDDetails getEmployeeIdDetailsByEmployeeId(@Param(value="empId") String employeeId);
	
	
	@Query(value = "select a  from EmployeeIDDetails a  where a.employeeId=:empId" )
	public List<EmployeeIDDetails> getEmployeeIdDetailsListByEmployeeId(@Param(value="empId") String employeeId);
}
