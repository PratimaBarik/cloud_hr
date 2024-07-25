package com.app.employeePortal.excelBulkImport.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.excelBulkImport.entity.ExcelBulkImport;
import com.app.employeePortal.excelBulkImport.repository.ExcelBulkImportRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Transactional

public class ExcelBulkImportServiceImpl implements ExcelBulkImportService {
	@Autowired
	ExcelBulkImportRepository excelBulkImportRepository;

	@Override
	public File convert(MultipartFile file) throws IOException {
		

	        
	        File convFile = new File(System.getProperty("java.io.tmpdir") + file.getOriginalFilename());


	        FileOutputStream fos = new FileOutputStream(convFile);

	        fos.write(file.getBytes());

	        fos.close();

	        return convFile;
	    }


	@Override
	public String insertToExcelImport(File file) throws IOException, FileNotFoundException {
		  String excelBulkImportId = null;
	        JsonObject object = null;
	        if (file.exists()) {

	            if (getFileExtension(file).equalsIgnoreCase("xlsx") || getFileExtension(file).equalsIgnoreCase("xls")) {

	                object = getExcelDataAsJsonObject(file);

	            } else if (getFileExtension(file).equalsIgnoreCase("csv")) {
	                object = getCsvDataAsJsonObject(file);

	            } else {
	            }


	            if (null != object) {


	                ExcelBulkImport excelImport = new ExcelBulkImport();


	                excelImport.setExcel_json(object.toString());
	                excelImport.setImport_date(new Date());
	                excelImport.setFile_extension(getFileExtension(file));

	                ExcelBulkImport excelImport1 = excelBulkImportRepository.save(excelImport);
	                excelBulkImportId = excelImport1.getExcel_bulk_import_id();


	            }
	        } else {
	            throw new FileNotFoundException("Could not find file: " + file);
	        }
	        return excelBulkImportId;
	    }


	    @Override
	    public JsonObject getCsvDataAsJsonObject(File csvFile) throws IOException {
		List<Map<?, ?>> list = null;
		
		  JSONObject res = new JSONObject();
		   JsonObject gsonObject = null;
		try {
			CsvSchema csv = CsvSchema.emptySchema().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
					.readValues(csvFile);

			list = mappingIterator.readAll();
			
		        for(int i=0;i<list.size();i++){
		         
		        	res.put("csvData", list);
		        	
		        }
		        
		        JsonParser jsonParser = new JsonParser();
		         gsonObject = (JsonObject)jsonParser.parse(res.toString());
		      
		        


		} catch (Exception e) {
			e.printStackTrace();
		}
		return gsonObject;
	}

    @Override
	public JsonObject getExcelDataAsJsonObject(File excelFile) {
		JsonObject sheetsJsonObject = new JsonObject();
		Workbook workbook = null;

		try {

			workbook = WorkbookFactory.create(excelFile);
			// workbook = new XSSFWorkbook(excelFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

			JsonArray sheetArray = new JsonArray();
			ArrayList<String> columnNames = new ArrayList<String>();
			Sheet sheet = workbook.getSheetAt(i);
			Iterator<Row> sheetIterator = sheet.iterator();

			while (sheetIterator.hasNext()) {

				Row currentRow = sheetIterator.next();
				JsonObject jsonObject = new JsonObject();

				if (currentRow.getRowNum() != 0) {

					for (int j = 0; j < columnNames.size(); j++) {

						if (currentRow.getCell(j) != null) {
							if (currentRow.getCell(j).getCellTypeEnum() == CellType.STRING) {
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getStringCellValue());
							} else if (currentRow.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getNumericCellValue());
							} else if (currentRow.getCell(j).getCellTypeEnum() == CellType.BOOLEAN) {
								jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getBooleanCellValue());
							} else if (currentRow.getCell(j).getCellTypeEnum() == CellType.BLANK) {
								jsonObject.addProperty(columnNames.get(j), "");
							}
						} else {
							jsonObject.addProperty(columnNames.get(j), "");
						}

					}

					sheetArray.add(jsonObject);

				} else {
					// store column names
					for (int k = 0; k < currentRow.getPhysicalNumberOfCells(); k++) {
						columnNames.add(currentRow.getCell(k).getStringCellValue());
					}
				}

			}

			sheetsJsonObject.add(workbook.getSheetName(i), sheetArray);

		}

		return sheetsJsonObject;

	}


	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {

			return "";
		}
		/* String name = file.getName(); int lastIndexOf = name.lastIndexOf("."); if
		 * (lastIndexOf == -1) { return ""; // empty extension } return
		 * name.substring(lastIndexOf);
		 */

	}


	@Override
	public List<String> getExcelSheetHeaderContent(String excelBulkImportId)throws JSONException {
		 List<String> headerKeys = null;
	        JsonParser jsonParser = new JsonParser();

	        if (null != excelBulkImportId) {

	     
	            ExcelBulkImport excelImport = excelBulkImportRepository.getExcelJson(excelBulkImportId);

	            if (excelImport.getFile_extension().equalsIgnoreCase("xls")
	                    || excelImport.getFile_extension().equalsIgnoreCase("xlsx") || excelImport.getFile_extension().equalsIgnoreCase("csv")) {
	                JsonObject jsonObj = (JsonObject) jsonParser.parse(excelImport.getExcel_json());

	                if (null != jsonObj) {

	                    List<String> keys = getAllKeys(jsonObj.toString());
	                    JsonElement jsonElement = jsonObj.get(keys.get(0));

	                    System.out.println("jsonElementttt" + jsonElement);

	                    headerKeys = getAllKeys(jsonElement.getAsJsonArray().get(0).toString());


	                }
	            }
	        }
	        return headerKeys;

	    }


	@Override
    public List<String> getAllKeys(String jsonstring) {
		JsonParser parser = new JsonParser();
        JsonObject jObj = (JsonObject) parser.parse(jsonstring);

        List<String> keys = new ArrayList<String>();
        for (Entry<String, JsonElement> e : jObj.entrySet()) {
        
        	keys.add(e.getKey());
        }
        return keys;

    }


	@Override
	public String getJsonMessageFromDb(String excelBulkImportId) {
		 String excelJson = null;
	        if (null != excelBulkImportId) {

	          // ExcelImport excelImport = excelImportDao.getExcelJson(excelImportId);
	            ExcelBulkImport excelImport = excelBulkImportRepository.getExcelJson(excelBulkImportId);

	            System.out.println("excellllImportttt" + excelImport);

	            excelJson = excelImport.getExcel_json();

	            System.out.println("excelJson$$$$$$$$$$$" + excelJson);

	        }
	        return excelJson;

	    }

}
