package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.candidate.entity.CandidateNotesLink;

public interface CandidateNotesLinkRepository extends JpaRepository<CandidateNotesLink, String>{

	//CandidateNotesLink getnotesLinkById(String candidateId);
	
	@Query(value = "select a  from CandidateNotesLink a  where a.candidate_id=:candidateId and a.liveInd = true" )
	public List<CandidateNotesLink>getNoteListByCandidateId(@Param(value="candidateId") String candidateId);

	public CandidateNotesLink findByNotesId(String notesId);

}
