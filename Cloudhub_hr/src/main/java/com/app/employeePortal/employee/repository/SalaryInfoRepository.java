package com.app.employeePortal.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.employee.entity.SalaryInfo;

public interface SalaryInfoRepository extends JpaRepository<SalaryInfo, String> {

}
