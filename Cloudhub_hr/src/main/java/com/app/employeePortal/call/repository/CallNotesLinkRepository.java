package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.call.entity.CallNotesLink;

@Repository
public interface CallNotesLinkRepository  extends JpaRepository<CallNotesLink, String>{

	@Query(value = "select a  from CallNotesLink a  where a.callId=:callId and a.liveInd = true" )
	List<CallNotesLink> getNoteByCallId(@Param(value="callId")String callId);

	public CallNotesLink findByNotesId(String notesId);

	CallNotesLink findByCallIdAndNotesId(String callId, String notesId);


}
