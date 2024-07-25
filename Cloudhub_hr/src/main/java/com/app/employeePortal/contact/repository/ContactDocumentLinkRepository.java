package com.app.employeePortal.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactDocumentLink;

@Repository
public interface ContactDocumentLinkRepository extends JpaRepository<ContactDocumentLink, String>{

	
	@Query(value = "select a  from ContactDocumentLink a  where a.contact_id=:contactId" )
	public List<ContactDocumentLink> getDocumentByContactId(@Param(value="contactId") String contactId);

}
