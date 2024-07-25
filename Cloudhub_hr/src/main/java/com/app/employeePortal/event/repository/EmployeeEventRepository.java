package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EmployeeEventLink;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEventLink, String>{

	@Query(value = "select a  from EmployeeEventLink a  where a.employee_id=:empId and a.event_id=:eventId and live_ind=true" )
	public EmployeeEventLink getEmployeeEventLink(@Param(value="empId") String empId,
			                                          @Param(value="eventId") String eventId);
	
	
	
	@Query(value = "select a  from EmployeeEventLink a  where a.employee_id=:empId and live_ind=true" )
	public Page<EmployeeEventLink> getEventListByEmpId(@Param(value="empId") String empId, Pageable paging);
	
	@Query(value = "select a  from EmployeeEventLink a  where a.event_id=:eventId and live_ind=true" )
	public List<EmployeeEventLink> getEmpListByEventId(@Param(value="eventId") String eventId);

	//public EmployeeEventLink findByEmployeeId(String employeeId);

	@Query(value = "select a  from EmployeeEventLink a  where a.employee_id=:employeeId and live_ind=true" )
	public List<EmployeeEventLink> getByEmployeeId(@Param(value="employeeId") String employeeId);

}
