package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployementHistory;

@Repository
public interface EmployementHistoryRepository extends JpaRepository<EmployementHistory, String>{

	@Query(value = "select a  from EmployementHistory a  where a.employeeId=:empId and a.liveInd= true" )
	public List<EmployementHistory> getEmploymentHistoryByEmployeeId(@Param(value="empId") String employeeId);


}
