package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorNoteLink;

public interface InvestorNotesLinkRepo extends JpaRepository<InvestorNoteLink,String> {
    @Query(value = "select a  from InvestorNoteLink a  where a.investorId=:investorId and a.liveInd = true" )
    List<InvestorNoteLink> getNotesIdByInvestorId(@Param("investorId") String investorId);

	public InvestorNoteLink findByNoteId(String noteId);
}
