package com.app.employeePortal.excelBulkImport.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

public interface ExcelBulkImportService {

	File convert(MultipartFile file) throws IOException;

	
	String insertToExcelImport(File file) throws  IOException, FileNotFoundException;


	List<String> getExcelSheetHeaderContent(String excelBulkImportId)throws JSONException;


	String getJsonMessageFromDb(String excelBulkImportId);

	List<String> getAllKeys(String jsonstring);


	JsonObject getCsvDataAsJsonObject(File csvFile) throws IOException;


	JsonObject getExcelDataAsJsonObject(File excelFile);
	
	

}
