package com.app.employeePortal.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateEmailLink;

@Repository
public interface CandidateEmailLinkRepository extends JpaRepository<CandidateEmailLink, String>{

}
