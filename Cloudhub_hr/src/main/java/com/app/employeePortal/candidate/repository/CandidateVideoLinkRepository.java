package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.candidate.entity.CandidateVideoLink;

public interface CandidateVideoLinkRepository extends JpaRepository<CandidateVideoLink, String> {

	@Query(value = "select a  from CandidateVideoLink a  where a.candidateId=:candidateId")
	List<CandidateVideoLink> getVedioByCandidateId(@Param(value = "candidateId") String candidateId);

	@Query(value = "select a  from CandidateVideoLink a  where a.candidateId=:candidateId" )
	CandidateVideoLink getVidioDetailsByCandidateId(@Param(value="candidateId") String candidateId);

}
