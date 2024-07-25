package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeDocumentLink;
@Repository
public interface EmployeeDocumentLinkRepository extends JpaRepository<EmployeeDocumentLink, String> {
	
	@Query(value = "select a  from EmployeeDocumentLink a  where a.employeeId=:employeeId ")
	List<EmployeeDocumentLink> getDocumentByEmployeeId(@Param(value="employeeId")String employeeId);

}
