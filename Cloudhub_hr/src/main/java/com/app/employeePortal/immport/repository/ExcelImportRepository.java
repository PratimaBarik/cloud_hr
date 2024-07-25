package com.app.employeePortal.immport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.immport.entity.ExcelImport;

@Repository
public interface ExcelImportRepository extends JpaRepository<ExcelImport, String>{

	

}
