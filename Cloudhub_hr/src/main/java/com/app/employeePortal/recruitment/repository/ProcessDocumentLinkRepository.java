package com.app.employeePortal.recruitment.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.ProcessDocumentLink;

@Repository
public interface ProcessDocumentLinkRepository extends JpaRepository<ProcessDocumentLink, String>{

	ProcessDocumentLink findByProcessId(String processId);

	 @Query(value = "select a  from ProcessDocumentLink a  where a.processId=:processId and a.mandatoryInd=true " )
		List<ProcessDocumentLink> getProcessDocumentListByProcessId(@Param(value="processId")String processId);

	ProcessDocumentLink findByProcessDocumentLinkId(String processDocumentLinkId);

	ProcessDocumentLink findByDocumentTypeIdAndProcessId(String documentTypeId, String processId);
} 