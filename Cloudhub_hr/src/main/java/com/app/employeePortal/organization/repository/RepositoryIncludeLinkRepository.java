package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.RepositoryIncludeLink;
@Repository
public interface RepositoryIncludeLinkRepository extends JpaRepository<RepositoryIncludeLink, String>{

	List<String> findByOrgId(String orgId);

	List<RepositoryIncludeLink> findByUserIdAndLiveInd(String userId , boolean b);

	List<RepositoryIncludeLink> findByOrganizationDocumentLinkId(String organizationDocumentLinkId);
	
	List<RepositoryIncludeLink> findByOrganizationDocumentLinkIdAndLiveInd(String organizationDocumentLinkId, boolean b);
}
