package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppNotesLink;

public interface InvestorOppNoteLinkRepo extends JpaRepository<InvestorOppNotesLink,String> {
    @Query(value = "select a  from InvestorOppNotesLink a  where a.invOpportunityId=:invOpportunityId and a.liveInd = true" )
    List<InvestorOppNotesLink> getNoteListByInvOpportunityId(@Param("invOpportunityId") String invOpportunityId);

	InvestorOppNotesLink findByNoteId(String notesId);
}
