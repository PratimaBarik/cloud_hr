package com.app.employeePortal.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.DefaultDepartment;

@Repository
public interface DefaultDepartmentRepository extends JpaRepository<DefaultDepartment, String> {

	DefaultDepartment findByDepartmentIdAndUserId(String departmentId, String userId);

	DefaultDepartment findByUserId(String userId);

}
