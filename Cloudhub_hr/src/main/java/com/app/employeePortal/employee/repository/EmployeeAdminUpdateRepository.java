package com.app.employeePortal.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeAdminUpdate;

@Repository
public interface EmployeeAdminUpdateRepository extends JpaRepository<EmployeeAdminUpdate, String> {

	EmployeeAdminUpdate findByEmployeeId(String employeeId);

//	@Query(value = "select a  from BankDetails a  where a.employeeId=:employeeId" )
//
//	public	List<BankDetails> getBankDetailsById(@Param(value="employeeId") String employeeId);
//
//	@Query(value = "select a  from BankDetails a  where a.employeeId=:employeeId and defaultInd=true " )
//	public BankDetails getByCandidateIdAndDefaultInd(@Param(value="employeeId") String employeeId);

}
