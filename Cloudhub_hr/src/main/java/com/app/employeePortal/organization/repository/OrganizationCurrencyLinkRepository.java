package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationCurrencyLink;

@Repository
public interface OrganizationCurrencyLinkRepository extends JpaRepository<OrganizationCurrencyLink, String> {

	List<OrganizationCurrencyLink> findByOrgIdAndLiveInd(String orgId, boolean b);

	OrganizationCurrencyLink findByOrgCurrencyLinkId(String orgCurrencyLinkId);
	
}
