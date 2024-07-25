package com.app.employeePortal.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactNotesLink;
@Repository
public interface ContactNotesLinkRepository extends JpaRepository<ContactNotesLink, String>{

	@Query(value = "select a  from ContactNotesLink a  where a.contact_id=:contactId and a.liveInd = true" )
	public List<ContactNotesLink> getNoteListByContactId(@Param(value="contactId") String contactId);

	public ContactNotesLink findByNotesId(String notesId);	
	
	
}
