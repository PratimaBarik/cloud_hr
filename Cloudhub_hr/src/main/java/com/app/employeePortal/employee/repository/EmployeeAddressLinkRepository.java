
package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeAddressLink;

@Repository
public interface EmployeeAddressLinkRepository extends JpaRepository<EmployeeAddressLink, String> {

	@Query(value = "select a  from EmployeeAddressLink a  where a.employeeId=:empId and a.liveInd= true" )
    public List<EmployeeAddressLink> getAddressListByEmpId(@Param(value="empId")String empId);

	
}

