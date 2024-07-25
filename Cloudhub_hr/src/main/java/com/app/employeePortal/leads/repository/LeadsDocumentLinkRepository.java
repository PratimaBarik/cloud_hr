package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsDocumentLink;
@Repository
public interface LeadsDocumentLinkRepository  extends JpaRepository<LeadsDocumentLink, String>{

	
	@Query(value = "select a  from LeadsDocumentLink a  where a.leadsId=:leadsId" )
	List<LeadsDocumentLink> getDocumentByLeadsId(@Param(value="leadsId") String leadsId);

	@Query(value = "select a  from LeadsDocumentLink a  where a.documentId=:documentId" )
	LeadsDocumentLink getDocumentDetailsByDocumentId(@Param(value="documentId") String documentId);


	
}
