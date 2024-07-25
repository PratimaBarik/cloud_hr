package com.app.employeePortal.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.document.entity.DocumentDetails;

@Repository
public interface DocumentDetailsRepository extends JpaRepository<DocumentDetails, String>{

	
	
	@Query(value = "select a  from DocumentDetails a  where a.document_id=:documentId  and a.live_ind=true" )
	public DocumentDetails getDocumentDetailsById(@Param(value="documentId") String documentId);

	@Query(value = "select a  from DocumentDetails a  where a.document_id=:documentId" )
	public DocumentDetails getDocumentDetailsByIdWithOutLiveInd(@Param(value="documentId") String documentId);

}
