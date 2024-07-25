package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.call.entity.CallCandidateLink;

@Repository
public interface CallCandidateLinkRepository extends JpaRepository<CallCandidateLink, String>{

	@Query(value = "select a  from CallCandidateLink a  where a.candidateId=:candidateId and liveInd=true" )
	public List<CallCandidateLink> getCallListByCandidateId(@Param(value="candidateId")String candidateId);

	@Query(value = "select a  from CallCandidateLink a  where a.candidateId=:candidateId" )
	public List<CallCandidateLink> getCallListByCandidateIdWithOutLiveInd(@Param(value="candidateId")String candidateId);

	@Query(value = "select a  from CallCandidateLink a  where a.callId=:callId and liveInd=true" )
	public CallCandidateLink getcallOfCandidateByCallId(@Param(value="callId")String callId);

}
