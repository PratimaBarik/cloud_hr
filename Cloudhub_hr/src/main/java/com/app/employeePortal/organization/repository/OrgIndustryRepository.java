package com.app.employeePortal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrgIndustry;

@Repository
public interface OrgIndustryRepository extends JpaRepository<OrgIndustry, String> {

	OrgIndustry findByOrgIdAndLiveInd(String orgId, boolean b);

}
