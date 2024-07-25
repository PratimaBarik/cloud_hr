package com.app.employeePortal.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeInfo;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, String> {

}
