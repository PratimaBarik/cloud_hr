package com.app.employeePortal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationInfo;

@Repository
public interface OrganizationInfoRepository extends JpaRepository<OrganizationInfo, String> {

}
