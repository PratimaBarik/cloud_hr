package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.SalaryDetails;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryDetails, String>{
	
	@Query(value = "select a  from SalaryDetails a  where a.employeeId=:empId and  liveInd=true" )

	public	List<SalaryDetails> getSalaryDetailsById(@Param(value="empId") String employeeId);
	

}
