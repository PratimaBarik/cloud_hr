package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateDocumentLink;

@Repository
public interface CandidateDocumentLinkRepository extends JpaRepository<CandidateDocumentLink, String>{
	@Query(value = "select a  from CandidateDocumentLink a  where a.candidateId=:candidateId" )
	List<CandidateDocumentLink> getDocumentByCandidateId(@Param(value="candidateId") String candidateId);

	List<CandidateDocumentLink> findByCandidateId(String candidateId);

	/*
	 * @Query(value =
	 * "select a  from CandidateDocumentLink a  where a.candidateId=:candidateId" )
	 * CandidateDocumentLink getDocumentsByCandidateId(@Param(value="candidateId")
	 * String candidateId);
	 */

}
