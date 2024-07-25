package com.app.employeePortal.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactDesignation;

@Repository
public interface ContactDesignationRepository extends JpaRepository<ContactDesignation , String> {
	@Query(value = "select a  from ContactDesignation a  where a.contact_designation_id=:contactDesignationId" )
	
	public ContactDesignation getContactDesignationById(@Param(value="contactDesignationId") String contactDesignationId);


}
