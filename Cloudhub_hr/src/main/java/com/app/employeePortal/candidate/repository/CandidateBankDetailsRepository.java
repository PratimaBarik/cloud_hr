package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.candidate.entity.CandidateBankDetails;
@Repository
public interface CandidateBankDetailsRepository extends JpaRepository<CandidateBankDetails, String> {
	
	@Query(value = "select a  from CandidateBankDetails a  where a.candidateId=:canId and liveInd=true" )
	List<CandidateBankDetails> getBankDetailsById(@Param(value="canId")String candidateId);

	@Query(value = "select a  from CandidateBankDetails a  where a.candidateId=:canId" )
	List<CandidateBankDetails> getBankDetailsByIdWithOutLiveInd(@Param(value="canId")String candidateId);

	@Query(value = "select a  from CandidateBankDetails a  where a.candidateId=:candidateId and defaultInd=true" )
	CandidateBankDetails getByCandidateIdAndDefaultInd(@Param(value="candidateId")String candidateId);

}
