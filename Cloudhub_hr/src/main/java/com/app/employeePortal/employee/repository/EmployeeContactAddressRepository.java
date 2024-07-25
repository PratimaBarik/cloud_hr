package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeContactAddressLink;

@Repository
public interface EmployeeContactAddressRepository extends JpaRepository<EmployeeContactAddressLink, String>{

	@Query(value = "select a  from EmployeeContactAddressLink a  where a.employeeId=:empId and a.contactPersonId=:contactId and a.liveInd=true" )
    public List<EmployeeContactAddressLink> getEmployeeContactAddressById(@Param(value="empId")String empId,@Param(value="contactId")String contactId);
}
