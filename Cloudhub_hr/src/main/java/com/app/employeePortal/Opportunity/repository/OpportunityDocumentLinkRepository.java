package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityDocumentLink;

@Repository
public interface OpportunityDocumentLinkRepository extends JpaRepository<OpportunityDocumentLink, String> {
	
	@Query(value = "select a  from OpportunityDocumentLink a  where a.opportunity_id=:opportunityId" )
	public List<OpportunityDocumentLink> getDocumentByOpportunityId(@Param(value="opportunityId") String opportunityId);

	@Query(value = "select a  from OpportunityDocumentLink a  where a.document_id=:documentId" )
	public OpportunityDocumentLink getDocumentByDocumentId(@Param(value="documentId") String documentId);

}
