package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.employee.entity.EmployeeNotesLink;

public interface EmployeeNotesLinkRepository extends JpaRepository<EmployeeNotesLink, String>{
	
	@Query(value = "select a  from EmployeeNotesLink a  where a.employeeId=:empId and a.liveInd = true" )

	public	List<EmployeeNotesLink> getNotesIdByEmployeeId(@Param(value="empId") String employeeId);

	public EmployeeNotesLink findByNotesId(String notesId);

}
