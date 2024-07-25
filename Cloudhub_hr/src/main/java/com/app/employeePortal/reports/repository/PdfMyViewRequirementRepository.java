package com.app.employeePortal.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.reports.entity.PdfMyViewRequirement;

@Repository
public interface PdfMyViewRequirementRepository extends JpaRepository<PdfMyViewRequirement, String> {

	PdfMyViewRequirement findByUserId(String userId);

	PdfMyViewRequirement findByPdfMyViewRequirementId(String pdfMyViewRequirementId);

	@Query(value = "select a  from PdfMyViewRequirement a  where a.userId=:userId" )
	List<PdfMyViewRequirement> getRequirementListByUserId(@Param(value="userId")String userId);
	


}
