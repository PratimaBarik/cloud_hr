package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationSubsriptionDetails;
@Repository
public interface OrganizationSubsriptionDetailsRepository extends JpaRepository<OrganizationSubsriptionDetails, String>{

    OrganizationSubsriptionDetails findByOrganizationId(String orgId);
}
