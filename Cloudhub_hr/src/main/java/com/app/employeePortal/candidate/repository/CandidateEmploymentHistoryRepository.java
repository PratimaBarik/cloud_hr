package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateEmploymentHistory;
@Repository
public interface CandidateEmploymentHistoryRepository extends JpaRepository<CandidateEmploymentHistory,String> {
	
	@Query(value = "select a  from CandidateEmploymentHistory a  where a.candidateId=:canId and liveInd=true" )
	public List<CandidateEmploymentHistory> getCandidateEmploymentHistoryById(@Param(value="canId") String candidateId);

	@Query(value = "select a  from CandidateEmploymentHistory a  where a.candidateId=:canId" )
	public List<CandidateEmploymentHistory> getCandidateEmploymentHistoryByIdWithOutLiveInd(@Param(value="canId") String candidateId);

}
