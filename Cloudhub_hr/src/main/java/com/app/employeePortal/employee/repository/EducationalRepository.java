package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EducationalDetails;

@Repository
public interface EducationalRepository extends JpaRepository<EducationalDetails, String> {

	@Query(value = "select a  from EducationalDetails a  where a.employeeId=:empId and a.liveInd=true" )
	public List<EducationalDetails> getEducationDetailsById(@Param(value="empId") String employeeId);

}
