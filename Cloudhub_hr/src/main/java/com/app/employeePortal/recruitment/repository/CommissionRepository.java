package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.Commission;
@Repository
public interface CommissionRepository extends JpaRepository<Commission, String>{

	List<Commission> findByOrgId(String orgId);

	Commission findByUserId(String userId);

	List<Commission> findByOrgIdAndType(String orgId, String type);
}