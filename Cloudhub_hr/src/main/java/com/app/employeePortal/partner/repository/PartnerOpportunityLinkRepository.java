package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerOpportunityLink;
@Repository
public interface PartnerOpportunityLinkRepository extends JpaRepository<PartnerOpportunityLink, String> {
	
	
	@Query(value = "select a  from PartnerOpportunityLink a  where a.partner_id=:partnerId" )
    public List<PartnerOpportunityLink>  getOpportintyByPartnerId(@Param(value="partnerId")String partnerId);


}
