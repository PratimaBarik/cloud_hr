package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.AssessmentDetails;

@Repository
public interface AssessmentDetailsRepository extends JpaRepository<AssessmentDetails, String> {

	AssessmentDetails findByOrgId(String orgId);

}
