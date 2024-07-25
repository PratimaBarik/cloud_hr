package com.app.employeePortal.excelBulkImport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.excelBulkImport.entity.ExcelBulkImport;
@Repository
public interface ExcelBulkImportRepository extends JpaRepository<ExcelBulkImport, String>{

	

    @Query(value = "select a  from ExcelBulkImport a  where a.excel_bulk_import_id=:excelBulkImportId" )
    ExcelBulkImport getExcelJson(@Param(value = "excelBulkImportId") String excelBulkImportId);
}
