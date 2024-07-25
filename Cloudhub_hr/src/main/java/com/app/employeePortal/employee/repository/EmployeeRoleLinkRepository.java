package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeRoleLink;

@Repository
public interface EmployeeRoleLinkRepository extends JpaRepository<EmployeeRoleLink, String>{

	EmployeeRoleLink findByEmployeeIdAndLiveInd(String employeeId, boolean b);

	List<EmployeeRoleLink> findByLiveInd(boolean b);

}
