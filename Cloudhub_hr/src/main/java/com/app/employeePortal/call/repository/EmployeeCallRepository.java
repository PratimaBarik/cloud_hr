package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.call.entity.EmployeeCallLink;

@Repository
public interface EmployeeCallRepository extends JpaRepository<EmployeeCallLink, String>{

	
	@Query(value = "select a  from EmployeeCallLink a  where a.employee_id=:empId and a.call_id=:callId and live_ind=true" )
	public EmployeeCallLink getEmployeeCallLink(@Param(value="empId") String empId,
			                                          @Param(value="callId") String callId);
	
	
	
	@Query(value = "select a  from EmployeeCallLink a  where a.employee_id=:empId and live_ind=true" )
	public Page<EmployeeCallLink> getCallListByEmpId(@Param(value="empId") String empId, Pageable paging1);
	
	@Query(value = "select a  from EmployeeCallLink a  where a.call_id=:callId and live_ind=true" )
	public List<EmployeeCallLink> getEmpListByCallId(@Param(value="callId") String callId);

	@Query(value = "select a  from EmployeeCallLink a  where a.employee_id=:userId and live_ind=true" )
	public List<EmployeeCallLink> getByEmployeeId(@Param(value="userId") String userId);
	
	
			                                          
}
