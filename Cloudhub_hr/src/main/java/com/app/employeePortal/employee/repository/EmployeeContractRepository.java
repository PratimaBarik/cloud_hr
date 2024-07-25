package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeContract;

@Repository
public interface EmployeeContractRepository extends JpaRepository<EmployeeContract, String> {

//	List<EmployeeContract> getEmployeeContractById(String employeeId);
	
	@Query(value = "select a  from EmployeeContract a  where a.employeeId=:employeeId and liveInd=true" )
	public List<EmployeeContract>  getEmployeeContractListById(@Param(value="employeeId") String employeeId);

	@Query(value = "select a  from EmployeeContract a  where a.id=:id and liveInd=true" )
	public EmployeeContract  getEmployeeContractById(@Param(value="id") String id);



}
