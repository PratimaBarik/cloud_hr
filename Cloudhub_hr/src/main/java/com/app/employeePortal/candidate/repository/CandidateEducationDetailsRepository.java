package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateEducationDetails;
@Repository
public interface CandidateEducationDetailsRepository extends JpaRepository<CandidateEducationDetails, String> {
	
	@Query(value = "select a  from CandidateEducationDetails a  where a.candidateId=:canId" )
	public List<CandidateEducationDetails> getCandidateEducationDetailsById(@Param(value="canId") String candidateId);
	
	
	@Query(value = "select a  from CandidateEducationDetails a  where a.userId=:userId" )
	public List<CandidateEducationDetails> getCandidateEducationDetailsByuserId(@Param(value="userId") String userId);

}
