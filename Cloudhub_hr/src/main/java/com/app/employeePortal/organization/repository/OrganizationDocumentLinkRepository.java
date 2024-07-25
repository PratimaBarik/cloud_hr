package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationDocumentLink;

@Repository
public interface OrganizationDocumentLinkRepository extends JpaRepository<OrganizationDocumentLink, String> {

	List<OrganizationDocumentLink> findByOrgIdAndLiveInd(String orgId, boolean b);

	@Query(value = "select a  from OrganizationDocumentLink a  where a.organizationDocumentLinkId=:organizationDocumentLinkId and a.liveInd=true ")
	public OrganizationDocumentLink getByOrganizationDocumentLinkIdAndLiveInd(@Param(value = "organizationDocumentLinkId") String organizationDocumentLinkId);

	List<OrganizationDocumentLink> findByShareInd(boolean b);
	
}
