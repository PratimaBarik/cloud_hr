package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.CandidateEventLink;

@Repository
public interface CandidateEventLinkRepository extends JpaRepository<CandidateEventLink, String>{

	@Query(value = "select a  from CandidateEventLink a  where a.candidateId=:candidateId and liveInd=true" )
	List<CandidateEventLink> getEventListByCandidateId(@Param(value="candidateId") String candidateId);

	@Query(value = "select a  from CandidateEventLink a  where a.candidateId=:candidateId" )
	List<CandidateEventLink> getEventListByCandidateIdWithOutLiveInd(@Param(value="candidateId") String candidateId);

	@Query(value = "select a  from CandidateEventLink a  where a.eventId=:eventId and liveInd=true" )
	CandidateEventLink getCandidateEventLinkByEventId(@Param(value="eventId") String eventId);

//	@Query(value = "select a  from CandidateEventLink a  where a.userId=:userId and liveInd=true" )
//	List<CandidateEventLink> getEventListByUserId(@Param(value="userId") String userId);

}
