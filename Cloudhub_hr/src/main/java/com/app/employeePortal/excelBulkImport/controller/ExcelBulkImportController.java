package com.app.employeePortal.excelBulkImport.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.customer.mapper.CustomerMapper;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.excelBulkImport.mapper.ExcelBulkMapping;
import com.app.employeePortal.excelBulkImport.mapper.FieldDetailss;
import com.app.employeePortal.excelBulkImport.service.ExcelBulkImportService;
import com.app.employeePortal.leads.mapper.LeadsMapper;
import com.app.employeePortal.leads.service.LeadsService;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@CrossOrigin(maxAge = 3600)

public class ExcelBulkImportController {

	@Autowired
	ExcelBulkImportService excelBulkImportService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	LeadsService leadsService;
	@Autowired
	PartnerService partnerService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	

	@PostMapping("/api/v1/excel-bulk-import")
	public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile uploadfile,
			@RequestHeader("Authorization") String authorization) throws IOException, FileNotFoundException {

		String excelBulkImportId = null;

		if (null != uploadfile && uploadfile.getSize() > 0) {

			File file = excelBulkImportService.convert(uploadfile);

			long fileSize = file.length();

			System.out.println("file sizeeeee" + fileSize);

			if (fileSize <= 307200) {

				excelBulkImportId = excelBulkImportService.insertToExcelImport(file);

				return new ResponseEntity<String>(excelBulkImportId, HttpStatus.OK);
			} else {

				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<String>("File does not exist", HttpStatus.OK);

		}
	}

	@GetMapping("/api/v1/excel-bulk-header")
	public List<String> getExcelHeaderKeys(@RequestHeader("Authorization") String authorization,
			@RequestParam("excelId") String excelId) throws JSONException {
		List<String> headers = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			headers = excelBulkImportService.getExcelSheetHeaderContent(excelId);

		}
		return headers;

	}

	@GetMapping("/api/v1/excelBulkImport/customer-fields")

	public List<FieldDetailss> getAllcustomerFields(@RequestHeader("Authorization") String authorization) {

		ArrayList<FieldDetailss> detailsList = new ArrayList<FieldDetailss>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			FieldDetailss name = new FieldDetailss(" Name", "name");

			FieldDetailss url = new FieldDetailss(" Url", "url");

			FieldDetailss notes = new FieldDetailss("Notes", "notes");

			FieldDetailss phoneNumber = new FieldDetailss("Phone Number ", "phoneNumber");

			FieldDetailss email = new FieldDetailss("Email", "email");

			FieldDetailss vatNo = new FieldDetailss("Vat No ", "vatNo");

			FieldDetailss country = new FieldDetailss("Country", "country");

			FieldDetailss sector = new FieldDetailss("Sector", "sector");

			FieldDetailss zipcode = new FieldDetailss("Zipcode ", "zipcode");

			FieldDetailss countryDialCode = new FieldDetailss("Country Dial Code", "countryDialCode");

			FieldDetailss category = new FieldDetailss("Category", "category");

			FieldDetailss imageURL = new FieldDetailss("Image URL ", "imageURL");

			FieldDetailss assignedTo = new FieldDetailss("AssignedTo", "assignedTo");

			FieldDetailss businessRegistration = new FieldDetailss("Business Registration ", "businessRegistration");

			// FieldDetailss categoryyyy = new FieldDetailss("Categoryyyy ", "categoryyyy");

			detailsList.add(name);
			detailsList.add(url);
			detailsList.add(notes);
			detailsList.add(phoneNumber);
			detailsList.add(email);
			detailsList.add(vatNo);
			detailsList.add(country);
			detailsList.add(sector);
			detailsList.add(zipcode);
			detailsList.add(countryDialCode);
			detailsList.add(category);
			detailsList.add(imageURL);
			detailsList.add(assignedTo);
			detailsList.add(businessRegistration);
			// detailsList.add(categoryyyy);

		}
		return detailsList;
	}

	@PostMapping("/api/v1/excelBulkImport/customer-excel-mapping")

	public int mapExcel(@RequestHeader("Authorization") String authorization,
			@RequestBody List<ExcelBulkMapping> mappingList, @RequestParam("excelId") String excelId) throws Exception {

		String excelJson = excelBulkImportService.getJsonMessageFromDb(excelId);
		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != excelJson) {

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

				List<String> keys = excelBulkImportService.getAllKeys(jsonObj.toString());
				JsonElement jsonElement = jsonObj.get(keys.get(0));
				List<CustomerMapper> mapperList = new ArrayList<>();

				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
					CustomerMapper customerMapper = new CustomerMapper();
					AddressMapper addressMapper = new AddressMapper();
					// NotesMapper noteMapper = new NotesMapper();
					for (ExcelBulkMapping excelBulkMapping : mappingList) {
						String header = excelBulkMapping.getExcelHeader();
						String mappingField = excelBulkMapping.getMappingField();

						JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

						if (mappingField.equals("name")) {

							customerMapper.setName(value.getAsString());

						}
						if (mappingField.equals("url")) {
							customerMapper.setUrl(value.getAsString());
						}
						if (mappingField.equals("notes")) {

							customerMapper.setNotes(value.getAsString());

						}
						if (mappingField.equals("phoneNumber")) {

							customerMapper.setPhoneNumber(value.getAsString());

						}
						if (mappingField.equals("email")) {
							customerMapper.setEmail(value.getAsString());

						}
						if (mappingField.equals("vatNo")) {

							customerMapper.setVatNo(value.getAsString());

						}
						if (mappingField.equals("country")) {

							customerMapper.setCountry(value.getAsString());

						}
						if (mappingField.equals("sector")) {

							customerMapper.setSector(value.getAsString());

						}
						if (mappingField.equals("zipcode")) {

							customerMapper.setZipcode(value.getAsString());

						}
						if (mappingField.equals("countryDialCode")) {

							customerMapper.setCountryDialCode(value.getAsString());

						}
						if (mappingField.equals("category")) {

							customerMapper.setCategory(value.getAsString());

						}
						if (mappingField.equals("imageURL")) {

							customerMapper.setImageURL(value.getAsString());

						}
						if (mappingField.equals("assignedTo")) {

							customerMapper.setAssignedTo(value.getAsString());
						}

						if (mappingField.equals("businessRegistration")) {

							customerMapper.setBusinessRegistration(value.getAsString());

						}

						if (mappingField.equals("addressType")) {

							addressMapper.setAddressType(value.getAsString());

						}
						if (mappingField.equals("address1")) {

							addressMapper.setAddress1(value.getAsString());

						}
						if (mappingField.equals("address2")) {

							addressMapper.setAddress2(value.getAsString());

						}
						if (mappingField.equals("postalCode")) {

							addressMapper.setPostalCode(value.getAsString());

						}
						if (mappingField.equals("street")) {

							addressMapper.setStreet(value.getAsString());

						}
						if (mappingField.equals("city")) {

							addressMapper.setCity(value.getAsString());

						}
						if (mappingField.equals("town")) {

							addressMapper.setTown(value.getAsString());

						}
						if (mappingField.equals("country")) {

							addressMapper.setCountry(value.getAsString());

						}

						if (mappingField.equals("xlAddress")) {

							addressMapper.setXlAddress(value.getAsString());

						}
						/*
						 * if (mappingField.equals("notes")) {
						 * 
						 * noteMapper.setDescription(value.getAsString()); }
						 */
					}
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					addressList.add(addressMapper);
					customerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					customerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setSectorId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setDocumentId(jwtTokenUtil.getUserIdFromToken(authToken));
					customerMapper.setAddress(addressList);
					// List<NoteMapper> noteMapperList = new ArrayList<>();
					// noteMapperList.add(noteMapper);

					// contactMapper.setNoteMapper(noteMapperList);
					mapperList.add(customerMapper);

				}

				if (null != mapperList && !mapperList.isEmpty()) {

					for (CustomerMapper customerMapper : mapperList) {

						if (!StringUtils.isEmpty(customerMapper.getEmail())) {

							customerService.saveCustomer(customerMapper);
							count++;

						}
					}

				}

			}

		}

		return count;
	}

	@GetMapping("/api/v1/excelBulkImport/candidate-fields")

	public List<FieldDetailss> getAllcandidateFields(@RequestHeader("Authorization") String authorization) {

		ArrayList<FieldDetailss> detailsList = new ArrayList<FieldDetailss>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			FieldDetailss salutation = new FieldDetailss("Salutation", "salutation");
			
			FieldDetailss firstName = new FieldDetailss("First Name", "firstName", true);

			FieldDetailss middleName = new FieldDetailss("Middle Name", "middleName");

			FieldDetailss lastName = new FieldDetailss("Last Name", "lastName", true);
			
			FieldDetailss mobileNumber = new FieldDetailss("Mobile Number", "mobileNumber");
			
			FieldDetailss emailId = new FieldDetailss("EmailId", "emailId");
			
			FieldDetailss LinkedIn = new FieldDetailss("LinkedIn", "LinkedIn");

			//FieldDetailss name = new FieldDetailss(" Name", "name");

			FieldDetailss linkedinPublicUrl = new FieldDetailss("linkedinPublicUrl", "linkedinPublicUrl");

			FieldDetailss notes = new FieldDetailss("Notes", "notes");

			FieldDetailss department = new FieldDetailss("Department ", "department");

			FieldDetailss designation = new FieldDetailss("Designation", "designation");
			
			FieldDetailss countryDialCode = new FieldDetailss("Country Dial Code", "countryDialCode");

			FieldDetailss countryDialcode1 = new FieldDetailss("Country Dial Code1 ", "countryDialcode1");

			FieldDetailss currency = new FieldDetailss("Currency", "currency");

			FieldDetailss dateOfBirth = new FieldDetailss("Date Of Birth", "dateOfBirth");

			FieldDetailss gender = new FieldDetailss("Gender ", "gender");

			FieldDetailss nationality = new FieldDetailss("Nationality", "nationality");

			FieldDetailss idProof = new FieldDetailss("Id Proof ", "idProof");

			FieldDetailss educatioin = new FieldDetailss("Educatioin", "educatioin");

			FieldDetailss experience = new FieldDetailss("Experience", "experience");

		    FieldDetailss skill = new FieldDetailss("Skill ", "skill");
		    
		    FieldDetailss language = new FieldDetailss("Language ", "language");
		    
		    FieldDetailss workLocation = new FieldDetailss("Work Location", "workLocation");
		    
		    FieldDetailss workType = new FieldDetailss("Work Type ", "workType");
		    
		    FieldDetailss fullName = new FieldDetailss("Full Name ", "fullName");
		    
		    FieldDetailss country = new FieldDetailss("Country ", "country");
		    
		    FieldDetailss currentCtc = new FieldDetailss("Current Ctc ", "currentCtc");
		    
		    FieldDetailss roleType = new FieldDetailss("Role Type ", "roleType");
		    
		    FieldDetailss noticePeriod = new FieldDetailss("NoticePeriod ", "noticePeriod");
		    
		    FieldDetailss category = new FieldDetailss("Category ", "category");
		    
		    FieldDetailss benifit = new FieldDetailss("Benifit ", "benifit");
		    
		    FieldDetailss noticeDetail = new FieldDetailss("Notice Detail ", "noticeDetail");
		    
		    FieldDetailss whatsApp = new FieldDetailss("Whats App ", "whatsApp");
		    
		    FieldDetailss workPreferance = new FieldDetailss("Work Preferance ", "workPreferance");
		    
		    FieldDetailss channel = new FieldDetailss("Channel ", "channel");

			detailsList.add(salutation);
			detailsList.add(firstName);
			detailsList.add(middleName);
			detailsList.add(lastName);
			detailsList.add(mobileNumber);
			detailsList.add(emailId);
			detailsList.add(LinkedIn);
			detailsList.add(linkedinPublicUrl);
			detailsList.add(notes);
			detailsList.add(department);
			detailsList.add(designation);
			detailsList.add(countryDialCode);
			detailsList.add(countryDialcode1);
		    detailsList.add(currency);
		    detailsList.add(dateOfBirth);
		    detailsList.add(gender);
		    detailsList.add(nationality);
		    detailsList.add(idProof);
		    detailsList.add(educatioin);
		    detailsList.add(experience);
		    detailsList.add(skill);
		    detailsList.add(language);
		    detailsList.add(workLocation);
		    detailsList.add(workType);
		    detailsList.add(fullName);
		    detailsList.add(country);
		    detailsList.add(currentCtc);
		    detailsList.add(roleType);
		    detailsList.add(noticePeriod);
		    detailsList.add(category);
		    detailsList.add(benifit);
		    detailsList.add(noticeDetail);
		    detailsList.add(whatsApp);
		    detailsList.add(workPreferance);
		    detailsList.add(channel);


		}
		return detailsList;
	}
	@PostMapping("/api/v1/excelBulkImport/candidateExcelMapping")

	public int mapExcell(@RequestHeader("Authorization") String authorization,
			@RequestBody List<ExcelBulkMapping> mappingList, @RequestParam("excelId") String excelId) throws Exception {

		String excelJson = excelBulkImportService.getJsonMessageFromDb(excelId);
		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != excelJson) {

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

				List<String> keys = excelBulkImportService.getAllKeys(jsonObj.toString());
				JsonElement jsonElement = jsonObj.get(keys.get(0));
				List<CandidateMapper> mapperList = new ArrayList<>();

				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
					CandidateMapper candidateMapper = new CandidateMapper();
					AddressMapper addressMapper = new AddressMapper();
					// NotesMapper noteMapper = new NotesMapper();
					for (ExcelBulkMapping excelBulkMapping : mappingList) {
						String header = excelBulkMapping.getExcelHeader();
						String mappingField = excelBulkMapping.getMappingField();

						JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

						if (mappingField.equals("salutation")) {

							candidateMapper.setSalutation(value.getAsString());

						}
						
						if (mappingField.equals("firstName")) {

							candidateMapper.setFirstName(value.getAsString());

						}
						if (mappingField.equals("middleName")) {

							candidateMapper.setMiddleName(value.getAsString());

						}
						if (mappingField.equals("lastName")) {
							candidateMapper.setLastName(value.getAsString());

						}
						if (mappingField.equals("mobileNumber")) {

							candidateMapper.setMobileNumber(value.getAsString());

						}
						if (mappingField.equals("emailId")) {

							candidateMapper.setEmailId(value.getAsString());

						}
						if (mappingField.equals("LinkedIn")) {

							candidateMapper.setLinkedin(value.getAsString());

						}
						if (mappingField.equals("linkedinPublicUrl")) {

							candidateMapper.setLinkedin_public_url(value.getAsString());

						}
						if (mappingField.equals("notes")) {

							candidateMapper.setNotes(value.getAsString());

						}
						if (mappingField.equals("department")) {

							candidateMapper.setDepartmentName(value.getAsString());

						}
						if (mappingField.equals("designation")) {

							candidateMapper.setDesignation(value.getAsString());

						}
						if (mappingField.equals("countryDialCode")) {

							candidateMapper.setCountryDialCode(value.getAsString());
						}

						if (mappingField.equals("countryDialcode1")) {

							candidateMapper.setCountryDialCode1(value.getAsString());

						}

						if (mappingField.equals("currency")) {

							candidateMapper.setCurrency(value.getAsString());

						}
						if (mappingField.equals("dateOfBirth")) {

							candidateMapper.setDateOfBirth(value.getAsString());

						}
						if (mappingField.equals("gender")) {

							candidateMapper.setGender(value.getAsString());

						}
						if (mappingField.equals("nationality")) {

							candidateMapper.setNationality(value.getAsString());

						}
						if (mappingField.equals("idProof")) {

							candidateMapper.setIdProof(value.getAsString());

						}
						if (mappingField.equals("educatioin")) {

							candidateMapper.setEducation(value.getAsString());

						}
						if (mappingField.equals("experience")) {

							candidateMapper.setExperience(value.getAsFloat());

						}
						/*if (mappingField.equals("skill")) {

							candidateMapper.setSkills(value.getAsFloat());
						}*/
						if (mappingField.equals("language")) {

							candidateMapper.setLanguage(value.getAsString());

						}
						if (mappingField.equals("workLocation")) {

							candidateMapper.setWorkLocation(value.getAsString());

						}
						if (mappingField.equals("workType")) {

							candidateMapper.setWorkType(value.getAsString());

						}
						if (mappingField.equals("fullName")) {

							candidateMapper.setFullName(value.getAsString());

						}
						if (mappingField.equals("country")) {

							candidateMapper.setCountry(value.getAsString());

						}

						if (mappingField.equals("currentCtc")) {

							candidateMapper.setCurrentCtc(value.getAsString());

						}

						if (mappingField.equals("roleType")) {

							candidateMapper.setRoleType(value.getAsString());

						}

						if (mappingField.equals("noticePeriod")) {

							candidateMapper.setNoticePeriod(value.getAsLong());

						}

						if (mappingField.equals("benifit")) {

							candidateMapper.setBenifit(value.getAsString());

						}
						if (mappingField.equals("noticeDetail")) {

							candidateMapper.setNoticeDetail(value.getAsString());

						}if (mappingField.equals("whatsApp")) {

							candidateMapper.setWhatsApp(value.getAsString());

						}if (mappingField.equals("workPreferance")) {

							candidateMapper.setWorkPreference(value.getAsString());

						}if (mappingField.equals("category")) {

							candidateMapper.setCategory(value.getAsString());

						}if (mappingField.equals("channel")) {

							candidateMapper.setChannel(value.getAsString());

						}

						if (mappingField.equals("addressType")) {

							addressMapper.setAddressType(value.getAsString());

						}
						if (mappingField.equals("address1")) {

							addressMapper.setAddress1(value.getAsString());

						}
						if (mappingField.equals("address2")) {

							addressMapper.setAddress2(value.getAsString());

						}
						if (mappingField.equals("postalCode")) {

							addressMapper.setPostalCode(value.getAsString());

						}
						if (mappingField.equals("street")) {

							addressMapper.setStreet(value.getAsString());

						}
						if (mappingField.equals("city")) {

							addressMapper.setCity(value.getAsString());

						}
						if (mappingField.equals("town")) {

							addressMapper.setTown(value.getAsString());

						}
						if (mappingField.equals("country")) {

							addressMapper.setCountry(value.getAsString());

						}
						/*
						 * if (mappingField.equals("notes")) {
						 * 
						 * noteMapper.setDescription(value.getAsString()); }
						 */
					}
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					addressList.add(addressMapper);
					candidateMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					candidateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setSectorId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setDocumentId(jwtTokenUtil.getUserIdFromToken(authToken));
					candidateMapper.setAddress(addressList);
					// List<NoteMapper> noteMapperList = new ArrayList<>();
					// noteMapperList.add(noteMapper);

					// contactMapper.setNoteMapper(noteMapperList);
					mapperList.add(candidateMapper);

				}

				if (null != mapperList && !mapperList.isEmpty()) {

					for (CandidateMapper candidateMapper : mapperList) {

						if (!StringUtils.isEmpty(candidateMapper.getFirstName())) {

							candidateService.saveCandidate(candidateMapper);
							count++;

						}
					}

				}

			}

		}

		return count;
	}
	@GetMapping("/api/v1/excelBulkImport/leads-fields")

	public List<FieldDetailss> getAllleadsFields(@RequestHeader("Authorization") String authorization) {

		ArrayList<FieldDetailss> detailsList = new ArrayList<FieldDetailss>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			FieldDetailss name = new FieldDetailss("Name", "name");
			
			FieldDetailss url = new FieldDetailss("Url", "url");

			FieldDetailss notes = new FieldDetailss("Notes", "notes");

			FieldDetailss phoneNumber = new FieldDetailss("Phone Number ", "phoneNumber");
			
			FieldDetailss email = new FieldDetailss("Email ", "email");
			
			FieldDetailss vatNo = new FieldDetailss("Vat No", "vatNo");
			
			FieldDetailss country = new FieldDetailss("Country ", "country");
			
			FieldDetailss sector = new FieldDetailss("Sector", "sector");

			FieldDetailss zipcode = new FieldDetailss("Zipcode ", "zipcode");

			FieldDetailss countryDialCode = new FieldDetailss("Country Dial Code", "countryDialCode");
			
			FieldDetailss category = new FieldDetailss("Category", "category");

			FieldDetailss imageURL = new FieldDetailss(" Image URL", "imageURL");

			FieldDetailss assignedTo = new FieldDetailss("Assigned To", "assignedTo");

			FieldDetailss businessRegistration = new FieldDetailss("Business Registration", "businessRegistration");

			//FieldDetailss categoryyyy = new FieldDetailss("Categoryyyy ", "categoryyyy");

			

			detailsList.add(name);
			detailsList.add(url);
			detailsList.add(notes);
			detailsList.add(phoneNumber);
			detailsList.add(email);
			detailsList.add(vatNo);
			detailsList.add(country);
			detailsList.add(sector);
			detailsList.add(zipcode);
			detailsList.add(countryDialCode);
			detailsList.add(category);
			detailsList.add(imageURL);
			detailsList.add(assignedTo);
		    detailsList.add(businessRegistration);
		   // detailsList.add(categoryyyy);
		   


		}
		return detailsList;
	}
	
	
	@PostMapping("/api/v1/excelBulkImport/leadsExcelMapping")

	public int mapExcelll(@RequestHeader("Authorization") String authorization,
			@RequestBody List<ExcelBulkMapping> mappingList, @RequestParam("excelId") String excelId) throws Exception {

		String excelJson = excelBulkImportService.getJsonMessageFromDb(excelId);
		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != excelJson) {

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

				List<String> keys = excelBulkImportService.getAllKeys(jsonObj.toString());
				JsonElement jsonElement = jsonObj.get(keys.get(0));
				List<LeadsMapper> mapperList = new ArrayList<>();

				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
					LeadsMapper leadsMapper = new LeadsMapper();
					AddressMapper addressMapper = new AddressMapper();
					// NotesMapper noteMapper = new NotesMapper();
					for (ExcelBulkMapping excelBulkMapping : mappingList) {
						String header = excelBulkMapping.getExcelHeader();
						String mappingField = excelBulkMapping.getMappingField();

						JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

						if (mappingField.equals("name")) {

							leadsMapper.setName(value.getAsString());

						}
						
						if (mappingField.equals("url")) {

							leadsMapper.setUrl(value.getAsString());

						}
						if (mappingField.equals("notes")) {

							leadsMapper.setNotes(value.getAsString());

						}
						if (mappingField.equals("phoneNumber")) {
							leadsMapper.setPhoneNumber(value.getAsString());

						}
						if (mappingField.equals("email")) {

							leadsMapper.setEmail(value.getAsString());

						}
						if (mappingField.equals("vatNo")) {

							leadsMapper.setVatNo(value.getAsString());

						}
						if (mappingField.equals("country")) {

							leadsMapper.setCountry(value.getAsString());

						}
						if (mappingField.equals("sector")) {

							leadsMapper.setSector(value.getAsString());

						}
						if (mappingField.equals("notes")) {

							leadsMapper.setNotes(value.getAsString());

						}
						if (mappingField.equals("zipcode")) {

							leadsMapper.setZipcode(value.getAsString());

						}
						if (mappingField.equals("countryDialCode")) {

							leadsMapper.setCountryDialCode(value.getAsString());

						}
						if (mappingField.equals("category")) {

							leadsMapper.setCategory(value.getAsString());
						}

						if (mappingField.equals("imageURL")) {

							leadsMapper.setImageURL(value.getAsString());

						}

						if (mappingField.equals("assignedTo")) {

							leadsMapper.setAssignedTo(value.getAsString());

						}
						if (mappingField.equals("businessRegistration")) {

							leadsMapper.setBusinessRegistration(value.getAsString());

						}
						

						if (mappingField.equals("addressType")) {

							addressMapper.setAddressType(value.getAsString());

						}
						if (mappingField.equals("address1")) {

							addressMapper.setAddress1(value.getAsString());

						}
						if (mappingField.equals("address2")) {

							addressMapper.setAddress2(value.getAsString());

						}
						if (mappingField.equals("postalCode")) {

							addressMapper.setPostalCode(value.getAsString());

						}
						if (mappingField.equals("street")) {

							addressMapper.setStreet(value.getAsString());

						}
						if (mappingField.equals("city")) {

							addressMapper.setCity(value.getAsString());

						}
						if (mappingField.equals("town")) {

							addressMapper.setTown(value.getAsString());

						}
						if (mappingField.equals("country")) {

							addressMapper.setCountry(value.getAsString());

						}
						/*
						 * if (mappingField.equals("notes")) {
						 * 
						 * noteMapper.setDescription(value.getAsString()); }
						 */
					}
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					addressList.add(addressMapper);
					leadsMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					leadsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setSectorId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setDocumentId(jwtTokenUtil.getUserIdFromToken(authToken));
					leadsMapper.setAddress(addressList);
					// List<NoteMapper> noteMapperList = new ArrayList<>();
					// noteMapperList.add(noteMapper);

					// contactMapper.setNoteMapper(noteMapperList);
					mapperList.add(leadsMapper);

				}

				if (null != mapperList && !mapperList.isEmpty()) {

					for (LeadsMapper leadsMapper : mapperList) {

						if (!StringUtils.isEmpty(leadsMapper.getName())) {

							leadsService.saveLeads(leadsMapper);
							count++;

						}
					}

				}

			}

		}

		return count;
	}
	@GetMapping("/api/v1/excelBulkImport/partner-fields")

	public List<FieldDetailss> getAllPartnerFields(@RequestHeader("Authorization") String authorization) {

		ArrayList<FieldDetailss> detailsList = new ArrayList<FieldDetailss>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			FieldDetailss partnerName = new FieldDetailss("Partner Name", "partnerName");
			
			FieldDetailss url = new FieldDetailss("Url", "url", true);

			FieldDetailss country = new FieldDetailss("Country", "country");

			FieldDetailss sector = new FieldDetailss("Sector", "sector", true);
			
			FieldDetailss phoneNo = new FieldDetailss("Phone No", "phoneNo");
			
			FieldDetailss email = new FieldDetailss("Email", "email");
			
			FieldDetailss taxRegistrationNumber = new FieldDetailss("Tax Registration Number", "taxRegistrationNumber");

			FieldDetailss businessRegistrationNumber = new FieldDetailss("Business Registration Number", "businessRegistrationNumber");

			FieldDetailss accountNumber = new FieldDetailss("Account Number", "accountNumber");

			FieldDetailss bankName = new FieldDetailss("Bank Name ", "bankName");

			FieldDetailss note = new FieldDetailss("Note", "note");
			
			FieldDetailss tncInd = new FieldDetailss("Tnc Ind", "tncInd");

			FieldDetailss status = new FieldDetailss("Status", "status");

			FieldDetailss ownerName = new FieldDetailss("Owner Name", "ownerName");

			FieldDetailss countryDialCode = new FieldDetailss("Country Dial Code", "countryDialCode");

			FieldDetailss name = new FieldDetailss("Name ", "name");

			FieldDetailss imageURL = new FieldDetailss("Image URL", "imageURL");

			FieldDetailss assignedTo = new FieldDetailss("Assigned To ", "assignedTo");

			FieldDetailss document = new FieldDetailss("Document", "document");

			
			detailsList.add(partnerName);
			detailsList.add(url);
			detailsList.add(country);
			detailsList.add(sector);
			detailsList.add(phoneNo);
			detailsList.add(email);
			detailsList.add(taxRegistrationNumber);
			detailsList.add(businessRegistrationNumber);
			detailsList.add(accountNumber);
			detailsList.add(bankName);
			detailsList.add(note);
			detailsList.add(tncInd);
		    detailsList.add(status);
		    detailsList.add(ownerName);
		    detailsList.add(countryDialCode);
		    detailsList.add(name);
		    detailsList.add(imageURL);
		    detailsList.add(assignedTo);
		    detailsList.add(document);
		}
		return detailsList;
	}
	
	@PostMapping("/api/v1/excelBulkImport/partnerExcelMapping")

	public int mapPartnerExcel(@RequestHeader("Authorization") String authorization,
			@RequestBody List<ExcelBulkMapping> mappingList, @RequestParam("excelId") String excelId) throws Exception {

		String excelJson = excelBulkImportService.getJsonMessageFromDb(excelId);
		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != excelJson) {

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

				List<String> keys = excelBulkImportService.getAllKeys(jsonObj.toString());
				JsonElement jsonElement = jsonObj.get(keys.get(0));
				List<PartnerMapper> mapperList = new ArrayList<>();

				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
					PartnerMapper partnerMapper = new PartnerMapper();
					AddressMapper addressMapper = new AddressMapper();
					// NotesMapper noteMapper = new NotesMapper();
					for (ExcelBulkMapping excelBulkMapping : mappingList) {
						String header = excelBulkMapping.getExcelHeader();
						String mappingField = excelBulkMapping.getMappingField();

						JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

						if (mappingField.equals("partnerName")) {

							partnerMapper.setPartnerName(value.getAsString());

						}
						
						if (mappingField.equals("url")) {

							partnerMapper.setUrl(value.getAsString());

						}
						if (mappingField.equals("country")) {

							partnerMapper.setCountry(value.getAsString());

						}
						if (mappingField.equals("sector")) {
							partnerMapper.setSector(value.getAsString());

						}
						if (mappingField.equals("phoneNo")) {

							partnerMapper.setPhoneNo(value.getAsString());

						}
						if (mappingField.equals("email")) {

							partnerMapper.setEmail(value.getAsString());

						}
						if (mappingField.equals("taxRegistrationNumber")) {

							partnerMapper.setTaxRegistrationNumber(value.getAsString());

						}
						if (mappingField.equals("businessRegistrationNumber")) {

							partnerMapper.setBusinessRegistrationNumber(value.getAsString());

						}
						if (mappingField.equals("accountNumber")) {

							partnerMapper.setAccountNumber(value.getAsString());

						}
						if (mappingField.equals("bankName")) {

							partnerMapper.setBankName(value.getAsString());

						}
						if (mappingField.equals("note")) {

							partnerMapper.setNote(value.getAsString());

						}
						if (mappingField.equals("countryDialCode")) {

							partnerMapper.setCountryDialCode(value.getAsString());
						}

						if (mappingField.equals("tncInd")) {

							partnerMapper.setTncInd(value.getAsString());

						}

						if (mappingField.equals("status")) {

							partnerMapper.setStatus(value.getAsBoolean());

						}
						if (mappingField.equals("ownerName")) {

							partnerMapper.setOwnerName(value.getAsString());

						}
						if (mappingField.equals("name")) {

							partnerMapper.setName(value.getAsString());

						}
						if (mappingField.equals("imageURL")) {

							partnerMapper.setImageURL(value.getAsString());

						}
						if (mappingField.equals("assignedTo")) {

							partnerMapper.setAssignedTo(value.getAsString());

						}
						if (mappingField.equals("document")) {

							partnerMapper.setDocument(value.getAsString());

						}
						

						if (mappingField.equals("addressType")) {

							addressMapper.setAddressType(value.getAsString());

						}
						if (mappingField.equals("address1")) {

							addressMapper.setAddress1(value.getAsString());

						}
						if (mappingField.equals("address2")) {

							addressMapper.setAddress2(value.getAsString());

						}
						if (mappingField.equals("postalCode")) {

							addressMapper.setPostalCode(value.getAsString());

						}
						if (mappingField.equals("street")) {

							addressMapper.setStreet(value.getAsString());

						}
						if (mappingField.equals("city")) {

							addressMapper.setCity(value.getAsString());

						}
						if (mappingField.equals("town")) {

							addressMapper.setTown(value.getAsString());

						}
						if (mappingField.equals("country")) {

							addressMapper.setCountry(value.getAsString());

						}
						/*
						 * if (mappingField.equals("notes")) {
						 * 
						 * noteMapper.setDescription(value.getAsString()); }
						 */
					}
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					addressList.add(addressMapper);
					partnerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					partnerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setSectorId(jwtTokenUtil.getUserIdFromToken(authToken));
					// customerMapper.setDocumentId(jwtTokenUtil.getUserIdFromToken(authToken));
					partnerMapper.setAddress(addressList);
					// List<NoteMapper> noteMapperList = new ArrayList<>();
					// noteMapperList.add(noteMapper);

					// contactMapper.setNoteMapper(noteMapperList);
					mapperList.add(partnerMapper);

				}

				if (null != mapperList && !mapperList.isEmpty()) {

					for (PartnerMapper partnerMapper : mapperList) {

						if (!StringUtils.isEmpty(partnerMapper.getName())) {

							partnerService.saveToPartnerProcess(partnerMapper); 
							count++;

						}
					}

				}

			}

		}

		return count;
	}
	

}
