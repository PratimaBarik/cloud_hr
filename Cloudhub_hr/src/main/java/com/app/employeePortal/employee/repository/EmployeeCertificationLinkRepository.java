package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeCertificationLink;

@Repository
public interface EmployeeCertificationLinkRepository extends JpaRepository<EmployeeCertificationLink, String> {

	List<EmployeeCertificationLink> findByEmployeeCertificationNameAndEmployeeId(String certificationId, String employeeId);
	
	@Query(value = "select a  from EmployeeCertificationLink a  where a.employeeId=:employeeId")
	List<EmployeeCertificationLink> getEmployeeCertificationById(@Param(value = "employeeId")String employeeId);

	public EmployeeCertificationLink findByEmployeeCertificationLinkId(String employeeCertificationLinkId);

	}
