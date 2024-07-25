package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.DepartmentDelete;

@Repository
public interface DepartmentDeleteRepository extends JpaRepository< DepartmentDelete, String> {

	DepartmentDelete findByDepartmentId(String departmentId);

	List<DepartmentDelete> findByOrgId(String orgId);

}
