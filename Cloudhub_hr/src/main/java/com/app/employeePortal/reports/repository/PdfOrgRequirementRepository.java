package com.app.employeePortal.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.reports.entity.PdfOrgRequirement;

@Repository
public interface PdfOrgRequirementRepository extends JpaRepository<PdfOrgRequirement, String> {

	PdfOrgRequirement findByUserId(String userId);

	@Query(value = "select a  from PdfOrgRequirement a  where a.pdfOrgRequirementId=:pdfOrgRequirementId" )
	PdfOrgRequirement getByPdfRequiredmentId(@Param(value="pdfOrgRequirementId")String pdfOrgRequirementId);

	@Query(value = "select a  from PdfOrgRequirement a  where a.userId=:userId" )
	List<PdfOrgRequirement> getRequirementListByUserId(@Param(value="userId")String userId);
	
}
