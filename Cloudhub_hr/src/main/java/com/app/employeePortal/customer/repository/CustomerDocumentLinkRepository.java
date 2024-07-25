package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.customer.entity.CustomerDocumentLink;

public interface CustomerDocumentLinkRepository extends JpaRepository<CustomerDocumentLink, String>{
	@Query(value = "select a  from CustomerDocumentLink a  where a.customerId=:customerId" )
	List<CustomerDocumentLink> getDocumentByCustomerId(@Param(value="customerId") String customerId);

	@Query(value = "select a  from CustomerDocumentLink a  where a.documentId=:documentId" )
	public CustomerDocumentLink getDocumentByDocumentId(@Param(value="documentId") String documentId);
}
