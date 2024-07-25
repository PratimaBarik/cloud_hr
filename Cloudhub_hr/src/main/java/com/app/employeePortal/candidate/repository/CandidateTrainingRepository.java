package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateTraining;

@Repository
public interface CandidateTrainingRepository extends JpaRepository<CandidateTraining, String> {
	
	@Query(value = "select a  from CandidateTraining a  where a.candidateId=:candidateId" )
	public List<CandidateTraining> getCandidateTrainingById(@Param(value="candidateId") String candidateId);

}
