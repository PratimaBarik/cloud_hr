package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerNotesLink;
@Repository
public interface CustomerNotesLinkRepository  extends JpaRepository<CustomerNotesLink, String>{
	
	@Query(value = "select a  from CustomerNotesLink a  where a.customerId=:customerId and a.liveInd = true" )
	public	List<CustomerNotesLink> getNotesIdByCustomerId(@Param(value="customerId") String customerId);

	public CustomerNotesLink findByNotesId(String notesId);

}
