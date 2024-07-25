package com.app.employeePortal.immport.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.Opportunity.mapper.FieldDetailsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import freemarker.template.TemplateException;

public interface ExcelImportService {

	File convert(MultipartFile uploadfile) throws IOException;

	String insertToExcelImport(File file) throws JsonProcessingException, IOException, FileNotFoundException;

	 List<String> getExcelSheetHeaderContent(String excelId);

	List<String> getAllKeys(String string);

	String getJsonMessageFromDb(String excelId);

	String insertTask(FieldDetailsDTO fieldDetailsDTO) throws IOException, TemplateException;
	
	String insertLeads(FieldDetailsDTO fieldDetailsDTO) throws IOException, TemplateException;

	String insertTaskType(FieldDetailsDTO fieldDetailsDTO);

	String insertEventType(FieldDetailsDTO fieldDetailsDTO);

	String insertSector(FieldDetailsDTO fieldDetailsDTO);

	String insertSource(FieldDetailsDTO fieldDetailsDTO);

	String insertRoleTypeExternal(FieldDetailsDTO fieldDetailsDTO);

	String insertEducationType(FieldDetailsDTO fieldDetailsDTO);

	String insertDocumentType(FieldDetailsDTO fieldDetailsDTO);

	String insertDesignation(FieldDetailsDTO fieldDetailsDTO);

	String insertDepartment(FieldDetailsDTO fieldDetailsDTO);

	String insertRoleType(FieldDetailsDTO fieldDetailsDTO);

	String insertLibraryType(FieldDetailsDTO fieldDetailsDTO);

	String insertExpenseType(FieldDetailsDTO fieldDetailsDTO);

	String insertShipBy(FieldDetailsDTO fieldDetailsDTO);

	String insertPerformanceManagement(FieldDetailsDTO fieldDetailsDTO);

	String insertPayment(FieldDetailsDTO fieldDetailsDTO);

	String insertNav(FieldDetailsDTO fieldDetailsDTO);

	String insertLeadsCategory(FieldDetailsDTO fieldDetailsDTO);

	String insertItemTask(FieldDetailsDTO fieldDetailsDTO);

	String insertInvestorCategory(FieldDetailsDTO fieldDetailsDTO);

	String insertIdProofType(FieldDetailsDTO fieldDetailsDTO);

	String insertCustomerType(FieldDetailsDTO fieldDetailsDTO);

	String insertRegion(FieldDetailsDTO fieldDetailsDTO);

	String insertServiceLine(FieldDetailsDTO fieldDetailsDTO);

	String insertLob(FieldDetailsDTO fieldDetailsDTO);

	String getExcelJsonData(File file) throws JsonProcessingException, IOException;

    String importCustomer(FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException;

    String importContact(String type,FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException;

	String insertInvestor(FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException;

    String insertInvestorLeads(FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException;

	String insertEmployee(FieldDetailsDTO fieldDetailsDTO);
}
