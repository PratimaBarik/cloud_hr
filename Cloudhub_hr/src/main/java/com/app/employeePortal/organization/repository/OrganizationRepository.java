package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationDetails;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationDetails, String> {

	@Query(value = "select a  from OrganizationDetails a  where a.organization_id=:orgId" )
	public OrganizationDetails getOrganizationDetailsById(@Param(value="orgId") String orgId);

	@Query(value = "select a  from OrganizationDetails a  where a.organization_id=:orgId" )
	public List<OrganizationDetails> getOrganizationDetailsByOrgId(@Param(value="orgId") String orgId);
	
	@Query(value = "select a  from OrganizationDetails a  where a.live_ind=:b" )
	public List<OrganizationDetails> getOrganizationDetailsByLiveInd(@Param(value="b") boolean b);

    OrganizationDetails findByType(String parent);

	@Query(value = "select a  from OrganizationDetails a  where a.organization_id=:orgId" )
    OrganizationDetails findByOrgId(String orgId);
}
