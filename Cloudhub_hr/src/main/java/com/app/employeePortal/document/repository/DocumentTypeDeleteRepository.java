package com.app.employeePortal.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.document.entity.DocumentTypeDelete;

@Repository
public interface DocumentTypeDeleteRepository extends JpaRepository<DocumentTypeDelete, String> {

	List<DocumentTypeDelete> findByOrgId(String orgId);
	
	@Query(value = "select a  from DocumentTypeDelete a  where a.document_type_id=:document_type_id")
	public DocumentTypeDelete getByDocumentTypeId(@Param(value = "document_type_id") String document_type_id);
	
}
