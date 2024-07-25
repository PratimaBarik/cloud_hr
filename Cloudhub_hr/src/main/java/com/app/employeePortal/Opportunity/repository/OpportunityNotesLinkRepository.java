package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;

@Repository
public interface OpportunityNotesLinkRepository extends JpaRepository<OpportunityNotesLink, String> {

	//List<OpportunityNotesLink> getNoteListByOpportunityId(String opportunityId);
	
	@Query(value = "select a  from OpportunityNotesLink a  where a.opportunity_id=:opportunityId and a.liveInd=true" )
	public List<OpportunityNotesLink> getNoteListByOpportunityId(@Param(value="opportunityId") String opportunityId);

	public OpportunityNotesLink findByNotesId(String notesId);

}
