package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventNotesLink;

@Repository
public interface EventNotesLinkRepository  extends JpaRepository<EventNotesLink, String>{

	@Query(value = "select a  from EventNotesLink a  where a.eventId=:eventId and a.liveInd = true" )
	List<EventNotesLink> getNoteByEventId(@Param(value="eventId")String eventId);

	EventNotesLink findByNotesId(String notesId);


}
