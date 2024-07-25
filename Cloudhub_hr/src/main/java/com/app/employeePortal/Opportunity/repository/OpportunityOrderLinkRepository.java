package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityOrderLink;

@Repository
public interface OpportunityOrderLinkRepository extends JpaRepository<OpportunityOrderLink, String> {
	
	List<OpportunityOrderLink> findByOpportunityId(String opportunityId);
	
	@Query(value = "select a  from OpportunityOrderLink a  where a.opportunityId=:opportunityId and a.liveInd=true" )
	OpportunityOrderLink getByOpportunityId(@Param(value="opportunityId")String opportunityId);
}
