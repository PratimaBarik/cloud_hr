package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityContactLink;

@Repository
public interface OpportunityContactLinkRepository extends JpaRepository<OpportunityContactLink, String> {

	
	@Query(value = "select a  from OpportunityContactLink a  where a.opportunityId=:opportunityId" )
	List<OpportunityContactLink> getContactByOpportunityId(@Param(value="opportunityId")String opportunityId);

	public OpportunityContactLink findByContactIdAndOpportunityId(String contactId, String opportunityId);

}
