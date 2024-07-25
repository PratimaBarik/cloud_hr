package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.BankDetails;

@Repository
public interface BankRepository extends JpaRepository<BankDetails, String> {

	@Query(value = "select a  from BankDetails a  where a.employeeId=:employeeId" )

	public	List<BankDetails> getBankDetailsById(@Param(value="employeeId") String employeeId);

	@Query(value = "select a  from BankDetails a  where a.employeeId=:employeeId and defaultInd=true " )
	public BankDetails getByCandidateIdAndDefaultInd(@Param(value="employeeId") String employeeId);

}
