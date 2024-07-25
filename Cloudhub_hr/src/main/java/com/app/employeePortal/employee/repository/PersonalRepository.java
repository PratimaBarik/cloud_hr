
package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.employee.entity.PersonalDetails;


public interface PersonalRepository extends JpaRepository<PersonalDetails, String>{

	@Query(value = "select a  from PersonalDetails a  where a.employeeId=:empId and a.liveInd= true" )
	public List<PersonalDetails> getPersonalDetailsById(@Param(value="empId") String employeeId);
	
	
}




