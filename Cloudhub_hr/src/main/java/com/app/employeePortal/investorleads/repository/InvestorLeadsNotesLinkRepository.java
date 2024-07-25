package com.app.employeePortal.investorleads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeadsNotesLink;
@Repository
public interface InvestorLeadsNotesLinkRepository  extends JpaRepository<InvestorLeadsNotesLink, String>{

	@Query(value = "select a  from InvestorLeadsNotesLink a  where a.investorLeadsId=:investorLeadsId and a.liveInd = true" )
	List<InvestorLeadsNotesLink> getNotesIdByInvestorLeadsId(@Param(value="investorLeadsId")String investorLeadsId);

	InvestorLeadsNotesLink findByNoteId(String noteId);

	

}
