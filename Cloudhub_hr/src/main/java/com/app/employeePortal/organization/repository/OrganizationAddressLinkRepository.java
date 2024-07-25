package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.OrganizationAddressLink;

@Repository
public interface OrganizationAddressLinkRepository extends JpaRepository<OrganizationAddressLink, String> {
	
	@Query(value = "select a  from OrganizationAddressLink a  where a.organization_id=:orgId and a.live_ind=true" )
    public List<OrganizationAddressLink> getAddressListByOrgId(@Param(value="orgId")String orgId);
	
	@Query(value = "select a  from OrganizationAddressLink a  where a.organization_id=:orgId and a.live_ind=true" )
    public OrganizationAddressLink getAddressByOrgId(@Param(value="orgId")String orgId);
}
