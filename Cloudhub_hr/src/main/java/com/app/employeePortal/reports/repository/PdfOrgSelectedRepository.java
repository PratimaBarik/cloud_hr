package com.app.employeePortal.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.reports.entity.PdfOrgSelected;

@Repository
public interface PdfOrgSelectedRepository extends JpaRepository<PdfOrgSelected, String> {

	PdfOrgSelected findByUserId(String userId);

	@Query(value = "select a  from PdfOrgSelected a  where a.pdfOrgSelectedId=:pdfOrgSelectedId" )
	PdfOrgSelected getByPdfOrgSelectedId(@Param(value="pdfOrgSelectedId")String pdfOrgSelectedId);

	@Query(value = "select a  from PdfOrgSelected a  where a.userId=:userId" )
	List<PdfOrgSelected> getSelectedListByUserId(@Param(value="userId")String userId);
	
}
