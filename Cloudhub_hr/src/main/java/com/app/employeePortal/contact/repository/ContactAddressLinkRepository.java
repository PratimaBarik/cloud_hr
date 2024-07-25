package com.app.employeePortal.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactAddressLink;

@Repository
public interface ContactAddressLinkRepository extends JpaRepository<ContactAddressLink, String> {
	
	@Query(value = "select a  from ContactAddressLink a  where a.contact_id=:contactId" )
    public List<ContactAddressLink> getAddressListByContactId(@Param(value="contactId")String contactId);

}
