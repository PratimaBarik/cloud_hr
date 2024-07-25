package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateCertificationLink;

@Repository
public interface CandidateCertificationLinkRepository extends JpaRepository<CandidateCertificationLink, String> {

	@Query(value = "select a  from CandidateCertificationLink a  where a.candidateId=:candidateId")
	public List<CandidateCertificationLink> getCandidateCertificationById(@Param(value = "candidateId") String candidateId);

	public CandidateCertificationLink findByCandidateCertificationLinkId(String candiCertiLinkId);

}
