package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.TrainingDetails;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingDetails, String> {

	@Query(value = "select a  from TrainingDetails a  where a.employeeId=:empId and a.liveInd= true" )
	public List<TrainingDetails> getEmployeeTrainingDetailsByEmployeeId(@Param(value="empId") String employeeId);



}
