package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeEmailLink;

@Repository
public interface EmployeeEmailLinkRepository extends JpaRepository<EmployeeEmailLink, String>{
	
	@Query(value = "select exp  from EmployeeEmailLink exp  where (exp.primaryEmailId = :emailId OR exp.secondaryEmailId = :emailId) and exp.liveInd=true")
	List<EmployeeEmailLink> findByEmail(@Param(value = "emailId") String emailId);
	
	@Query(value = "select exp  from EmployeeEmailLink exp  where (exp.primaryEmailId = :emailId OR exp.secondaryEmailId = :emailId) and exp.liveInd=true")
	EmployeeEmailLink findUserLinkByEmail(@Param(value = "emailId") String emailId);

//	EmployeeEmailLink findByEmployeeIdAndLiveInd(String employeeId, boolean b);
//
//	List<EmployeeEmailLink> findByLiveInd(boolean b);

}
