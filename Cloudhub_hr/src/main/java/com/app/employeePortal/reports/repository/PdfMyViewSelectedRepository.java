package com.app.employeePortal.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.reports.entity.PdfMyViewSelected;

@Repository
public interface PdfMyViewSelectedRepository extends JpaRepository<PdfMyViewSelected, String> {

	PdfMyViewSelected findByUserId(String orgId);

	PdfMyViewSelected findByPdfMyViewSelectedId(String pdfMyViewSelectedId);
	
	@Query(value = "select a  from PdfMyViewSelected a  where a.userId=:userId" )
	List<PdfMyViewSelected> getSelectedListByUserId(String userId);
	

}
