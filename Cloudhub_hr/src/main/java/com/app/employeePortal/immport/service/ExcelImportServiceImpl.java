package com.app.employeePortal.immport.service;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsMapper;
import com.app.employeePortal.investorleads.service.InvestorLeadsService;
import com.app.employeePortal.location.controller.LocationDetailsController;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.registration.service.RegistrationService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.Opportunity.mapper.FieldDetailsDTO;
import com.app.employeePortal.RoleTypeExternal.mapper.RoleTypeExternalMapper;
import com.app.employeePortal.RoleTypeExternal.service.RoleTypeExternalService;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.category.mapper.CustomerTypeMapper;
import com.app.employeePortal.category.mapper.IdProofTypeMapper;
import com.app.employeePortal.category.mapper.InvestorCategoryMapper;
import com.app.employeePortal.category.mapper.ItemTaskMapper;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.mapper.LobMapper;
import com.app.employeePortal.category.mapper.NavRequestMapper;
import com.app.employeePortal.category.mapper.PaymentMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtReqMapper;
import com.app.employeePortal.category.mapper.RegionsMapper;
import com.app.employeePortal.category.mapper.RoleMapper;
import com.app.employeePortal.category.mapper.ServiceLineReqMapper;
import com.app.employeePortal.category.mapper.ShipByMapper;
import com.app.employeePortal.category.service.CategoryService;
import com.app.employeePortal.category.service.CustomerTypeService;
import com.app.employeePortal.category.service.IdProofService;
import com.app.employeePortal.category.service.InvestorCategoryService;
import com.app.employeePortal.category.service.ItemTaskService;
import com.app.employeePortal.category.service.LeadsCategoryService;
import com.app.employeePortal.category.service.LobService;
import com.app.employeePortal.category.service.NavService;
import com.app.employeePortal.category.service.PaymentService;
import com.app.employeePortal.category.service.PerformanceManagementService;
import com.app.employeePortal.category.service.RegionsService;
import com.app.employeePortal.category.service.ServiceLineService;
import com.app.employeePortal.category.service.ShipByService;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.mapper.CustomerMapper;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.document.mapper.DocumentTypeMapper;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.education.mapper.EducationTypeMapper;
import com.app.employeePortal.education.service.EducationService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.event.service.EventService;
import com.app.employeePortal.expense.mapper.ExpenseMapper;
import com.app.employeePortal.expense.service.ExpenseService;
import com.app.employeePortal.immport.entity.ExcelImport;
import com.app.employeePortal.immport.mapper.FieldDetails;
import com.app.employeePortal.immport.repository.ExcelImportRepository;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.mapper.InvestorMapper;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investor.service.InvestorService;
import com.app.employeePortal.leads.mapper.LeadsMapper;
import com.app.employeePortal.leads.service.LeadsService;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.Designation;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.DesignationMapper;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.registration.service.DepartmentService;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.sector.service.SectorService;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.mapper.SourceMapper;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.source.service.SourceService;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.mapper.TaskMapper;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.task.service.TaskService;
import com.app.employeePortal.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import freemarker.template.TemplateException;

@Service
@Transactional
public class ExcelImportServiceImpl implements ExcelImportService {

	@Autowired
	ExcelImportRepository excelimportRepository;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	SourceService sourceService;
	@Autowired
	LeadsService leadsService;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	SectorService sectorService;
	@Autowired
	EventService eventService;
	@Autowired
	RoleTypeExternalService roleTypeExternalService;
	@Autowired
	EducationService educationService;
	@Autowired
	DocumentService documentService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ExpenseService expenseService;
	@Autowired
	ShipByService shipByService;
	@Autowired
	PerformanceManagementService performanceManagementService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	NavService navService;
	@Autowired
	LeadsCategoryService leadsCategoryService;
	@Autowired
	ItemTaskService itemTaskService;
	@Autowired
	IdProofService idProofTypeService;
	@Autowired
	InvestorCategoryService investorCategoryService;
	@Autowired
	CustomerTypeService customerTypeService;
	@Autowired
	RegionsService regionsService;
	@Autowired
	ServiceLineService serviceLineService;
	@Autowired
	LobService lobService;
	@Autowired
	CustomerService customerService;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ContactService contactService;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	InvestorRepository investorRepository;
	@Autowired
	InvestorService investorService;
	@Autowired
	InvestorLeadsService investorLeadsService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RegistrationService registrationService;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	LocationDetailsRepository locationDetailsRepository;

	public File convert(MultipartFile file) throws IOException {

		File convFile = new File(System.getProperty("java.io.tmpdir") + file.getOriginalFilename());

		FileOutputStream fos = new FileOutputStream(convFile);

		fos.write(file.getBytes());

		fos.close();

		return convFile;
	}

	public List<String> getExcelSheetHeaderContent(String excelId) {

		List<String> headerKeys = null;
		JsonParser jsonParser = new JsonParser();

		if (null != excelId) {

			ExcelImport excelImport = excelimportRepository.getById(excelId);

			if (excelImport.getFileExtension().equalsIgnoreCase("xls")
					|| excelImport.getFileExtension().equalsIgnoreCase("xlsx")
					|| excelImport.getFileExtension().equalsIgnoreCase("csv")) {
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelImport.getExcelJson());

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

	public List<String> getAllKeys(String jsonstring) {

		JsonParser parser = new JsonParser();
		JsonObject jObj = (JsonObject) parser.parse(jsonstring);

		List<String> keys = new ArrayList<String>();
		for (java.util.Map.Entry<String, JsonElement> e : jObj.entrySet()) {

			keys.add(e.getKey());
		}
		return keys;

	}

	public String getJsonMessageFromDb(String excelId) {
		String excelJson = null;
		if (null != excelId) {

			Optional<ExcelImport> excelImport = excelimportRepository.findById(excelId);

			excelJson = excelImport.get().getExcelJson();
		}

		return excelJson;
	}

	public String insertToExcelImport(File file)
			throws JsonProcessingException, IOException, java.io.FileNotFoundException {
		String excelImportId = null;
		JsonObject object = null;
		if (file.exists()) {

			if (getFileExtension(file).equalsIgnoreCase("xlsx") || getFileExtension(file).equalsIgnoreCase("xls")) {
				System.out.println("excelImportId 1=" + excelImportId);
				object = getExcelDataAsJsonObject(file);

			} else if (getFileExtension(file).equalsIgnoreCase("csv")) {
				object = getCsvDataAsJsonObject(file);

			} else {
			}

			if (null != object) {
				System.out.println("object=" + object.toString());
				ExcelImport excelImport = new ExcelImport();

				excelImport.setExcelJson(object.toString());
				excelImport.setImportDate(new Date());
				excelImport.setFileExtension(getFileExtension(file));
				excelImportId = excelimportRepository.save(excelImport).getId();

				System.out.println("excelImportId 2=" + excelImportId);
			}
		} else {
			throw new java.io.FileNotFoundException("Could not find file: " + file);
		}
		return excelImportId;
	}

	// get file extension//
	private String getFileExtension(File file) {

		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {

			return "";
		}
		/*
		 * String name = file.getName(); int lastIndexOf = name.lastIndexOf("."); if
		 * (lastIndexOf == -1) { return ""; // empty extension } return
		 * name.substring(lastIndexOf);
		 */

	}

	public JsonObject getCsvDataAsJsonObject(File csvFile) throws JsonProcessingException, IOException {

		List<Map<?, ?>> list = null;

		JSONObject res = new JSONObject();
		JsonObject gsonObject = null;
		try {
			CsvSchema csv = CsvSchema.emptySchema().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
					.readValues(csvFile);

			list = mappingIterator.readAll();

			for (int i = 0; i < list.size(); i++) {

				res.put("csvData", list);

			}

			JsonParser jsonParser = new JsonParser();
			gsonObject = (JsonObject) jsonParser.parse(res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gsonObject;
	}

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

	public List<FieldDetails> getAllCandidateFields(@RequestHeader("Authorization") String authorization) {

		ArrayList<FieldDetails> detailsList = new ArrayList<FieldDetails>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			FieldDetails salutation = new FieldDetails("Salutation", "salutation");

			FieldDetails firstName = new FieldDetails("First Name", "firstName", true);

			FieldDetails middleName = new FieldDetails("Middle Name", "middleName");

			FieldDetails lastName = new FieldDetails("Last Name", "lastName");

			FieldDetails phoneNo = new FieldDetails("Work Phone", "phoneNo");

			FieldDetails mobileNo = new FieldDetails("Mobile Number", "mobileNo");

			FieldDetails emailId = new FieldDetails("EmailId", "emailId");

			FieldDetails linkedinPublicUrl = new FieldDetails("LinkedIn", "linkedinPublicUrl");

			// FieldDetails skypeId = new FieldDetails("Skype Id", "skypeId");

			FieldDetails designation = new FieldDetails("Designation", "designation");

			FieldDetails department = new FieldDetails("Function", "department");

			// FieldDetails departmentDetails = new FieldDetails("Function Details",
			// "departmentDetails");

			// FieldDetails description = new FieldDetails("Description", "description");

			FieldDetails dob = new FieldDetails("Birth Date", "dob");

			FieldDetails address1 = new FieldDetails("Address Line1", "address1");

			FieldDetails street = new FieldDetails("Street", "street");

			FieldDetails city = new FieldDetails("City", "city");

			FieldDetails postalCode = new FieldDetails("Post Code", "postalCode");

			FieldDetails country = new FieldDetails("Country", "country");

			// FieldDetails notes = new FieldDetails("Notes", "notes");

			// FieldDetails contactOwner = new FieldDetails("Contact Owner", "contactOwner",
			// true);

			// detailsList.add(contactType);
			// detailsList.add(contactSubType);
			detailsList.add(salutation);
			detailsList.add(firstName);
			detailsList.add(middleName);
			detailsList.add(lastName);
			detailsList.add(phoneNo);
			detailsList.add(mobileNo);
			detailsList.add(emailId);
			detailsList.add(linkedinPublicUrl);
			// detailsList.add(skypeId);
			detailsList.add(designation);
			detailsList.add(department);
			// detailsList.add(departmentDetails);
			// detailsList.add(description);
			// detailsList.add(language);
			detailsList.add(dob);
			detailsList.add(address1);
			// detailsList.add(address2);
			// detailsList.add(town);
			detailsList.add(street);
			detailsList.add(city);
			detailsList.add(postalCode);
			detailsList.add(country);
			// detailsList.add(notes);
			// detailsList.add(contactOwner);

		}
		return detailsList;
	}

	@Override
	public String insertTask(FieldDetailsDTO fieldDetailsDTO) throws IOException, TemplateException {
		List<FieldDetailsDTO> list = getAllMappingEntityFields();
		mapTask(list, fieldDetailsDTO);
		return fieldDetailsDTO.getExcelId();
	}

	private int mapTask(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO)
			throws IOException, TemplateException {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		System.out.println("excelJson==" + excelJson);
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<TaskMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {

				TaskMapper dto = new TaskMapper();
				// AddressDTO addressDTO = new AddressDTO();
//					EmployeeDetails user = new EmployeeDetails();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("endDate")) {

						dto.setEndDate(Utility.convertExcelDateToString(value.getAsString()));
						System.out.println("Date ISO =" + Utility.convertExcelDateToString(value.getAsString()));
					}
					if (mappingField.equals("taskName")) {

						dto.setTaskName(value.getAsString());
//							System.out.println("taskName=="+value.getAsString());
					}
					if (mappingField.equals("taskType")) {
						if (value.getAsString() != null && value.getAsString().trim().length() > 0) {
							TaskType taskType = taskTypeRepository.getActiveTaskTypeByTaskType(value.getAsString());
							if (null != taskType) {
								dto.setTaskTypeId(taskType.getTaskTypeId());
//								System.out.println("taskType==" + value.getAsString());
							} else {
								TaskType newTaskType = new TaskType();

								newTaskType.setTaskType(value.getAsString());
								newTaskType.setCreationDate(new Date());
								newTaskType.setUserId(fieldDetailsDTO.getUserId());
								newTaskType.setOrgId(fieldDetailsDTO.getOrgId());
								newTaskType.setLiveInd(true);
								newTaskType.setEditInd(true);

								TaskType dbTaskType = taskTypeRepository.save(newTaskType);
								String taskTypeId = dbTaskType.getTaskTypeId();
								dto.setTaskTypeId(taskTypeId);
							}
						}
					}
					if (mappingField.equals("assignedTo")) {
						String assignToUser = fieldDetailsDTO.getUserId();
						if (!StringUtils.isEmpty(value.getAsString())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeByMailId(value.getAsString());
							if (null != employeeDetails) {
								assignToUser = employeeDetails.getUserId();
							}
						}
						dto.setAssignedTo(assignToUser);
					}

					if (mappingField.equals("priority")) {

						dto.setPriority(value.getAsString());

					}
//					if (mappingField.equals("taskStatus")) {
//
//						dto.setTaskStatus(value.getAsString());
//
//					}

				}

				mapperList.add(dto);
			}
			if (null != mapperList && !mapperList.isEmpty()) {

				for (TaskMapper dto1 : mapperList) {
					dto1.setUserId(fieldDetailsDTO.getUserId());
					dto1.setOrganizationId(fieldDetailsDTO.getOrgId());
//							System.out.println("userId=="+fieldDetailsDTO.getUserId()+"||orgId=="+fieldDetailsDTO.getOrgId());
					taskService.saveTaskProcess(dto1, "import");

				}
			}
		}
		return count;

	}

	private List<FieldDetailsDTO> getAllMappingEntityFields() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO endDate = new FieldDetailsDTO("Date", "endDate");

		FieldDetailsDTO taskName = new FieldDetailsDTO("Name", "taskName");

		FieldDetailsDTO taskType = new FieldDetailsDTO("Type", "taskType");

		FieldDetailsDTO priority = new FieldDetailsDTO("Priority", "priority");

		FieldDetailsDTO assignedTo = new FieldDetailsDTO("AssignedTo", "assignedTo");

//			FieldDetailsDTO taskStatus = new FieldDetailsDTO("Completed", "taskStatus");

		list.add(endDate);
		list.add(taskName);
		list.add(taskType);
		list.add(priority);
		list.add(assignedTo);
//			list.add(taskStatus);
		return list;
	}

	@Override
	public String insertLeads(FieldDetailsDTO fieldDetailsDTO) throws IOException, TemplateException {
		List<FieldDetailsDTO> list = getAllMappingEntityFields1();
		int count = mapLeads(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	public List<FieldDetailsDTO> getAllMappingEntityFields1() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO firstName = new FieldDetailsDTO("Person", "firstName");

		FieldDetailsDTO url = new FieldDetailsDTO("Client Url", "url");

		FieldDetailsDTO notes = new FieldDetailsDTO("Discussion Points", "notes");

		FieldDetailsDTO name = new FieldDetailsDTO("Client", "name");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone Number ", "phoneNumber");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO vatNo = new FieldDetailsDTO("Vat No", "vatNo");

		FieldDetailsDTO country = new FieldDetailsDTO("Country ", "country");

		FieldDetailsDTO sector = new FieldDetailsDTO("Sector", "sector");

		FieldDetailsDTO source = new FieldDetailsDTO("Source", "source");

		// FieldDetailsDTO zipcode = new FieldDetailsDTO("Zipcode ", "zipcode");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO clientLocation = new FieldDetailsDTO("Client Location", "clientLocation");

		// FieldDetailsDTO imageURL = new FieldDetailsDTO(" Image URL", "imageURL");

		FieldDetailsDTO assignedTo = new FieldDetailsDTO("Nuboxed Team", "assignedTo");

		FieldDetailsDTO businessRegistration = new FieldDetailsDTO("Business Registration", "businessRegistration");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		FieldDetailsDTO type = new FieldDetailsDTO("Type", "type");
		// FieldDetailss categoryyyy = new FieldDetailss("Categoryyyy ", "categoryyyy");

		list.add(firstName);
		list.add(url);
		list.add(notes);
		list.add(name);
		list.add(phoneNumber);
		list.add(email);
		list.add(vatNo);
		list.add(country);
		list.add(sector);
		list.add(source);
		// list.add(zipcode);
		list.add(countryDialCode);
		list.add(clientLocation);
		// list.add(imageURL);
		list.add(assignedTo);
		list.add(businessRegistration);
		list.add(xlAddress);
		list.add(type);
		// detailsList.add(categoryyyy);

		return list;
	}

	public int mapLeads(List<FieldDetailsDTO> mappingList, FieldDetailsDTO fieldDetailsDTO)
			throws IOException, TemplateException {

		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<LeadsMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				LeadsMapper leadsMapper = new LeadsMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelMapping : mappingList) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							leadsMapper.setName(value.getAsString());
						}

					}
					if (mappingField.equals("firstName")) {

						leadsMapper.setFirstName(value.getAsString());

					}

					if (mappingField.equals("url")) {

						leadsMapper.setUrl(value.getAsString());

					}
					if (mappingField.equals("notes")) {

						leadsMapper.setNotes(value.getAsString());

					}
//							if (mappingField.equals("phoneNumber")) {
//								leadsMapper.setPhoneNumber(value.getAsString());
//
//							}
					if (mappingField.equals("email")) {

						leadsMapper.setEmail(value.getAsString());

					}
//							if (mappingField.equals("vatNo")) {
//
//								leadsMapper.setVatNo(value.getAsString());
//
//							}
//							if (mappingField.equals("country")) {
//
//								leadsMapper.setCountry(value.getAsString());
//
//							}
					if (mappingField.equals("sector")) {

						SectorDetails sector = sectorDetailsRepository
								.getSectorDetailsBySectorName(value.getAsString());
						if (sector != null) {
							leadsMapper.setSectorId(sector.getSectorId());
						} else {
							SectorMapper sectorMapper = new SectorMapper();
							sectorMapper.setName(value.getAsString());
							sectorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							sectorMapper.setUserId(fieldDetailsDTO.getUserId());
							List<SectorMapper> sector1 = sectorService.saveToSectorDetails(sectorMapper);
							if (sector1 != null) {
								leadsMapper.setSectorId(sector1.get(0).getSectorId());
							}
						}

					}
					if (mappingField.equals("source")) {
						Source source = sourceRepository.findBySourceName(value.getAsString());
						if (null != source) {
							leadsMapper.setSource(source.getSourceId());
						} else {
							SourceMapper sourceMapper = new SourceMapper();
							sourceMapper.setName(value.getAsString());
							sourceMapper.setOrgId(fieldDetailsDTO.getOrgId());
							sourceMapper.setUserId(fieldDetailsDTO.getUserId());

							SourceMapper source1 = sourceService.saveSource(sourceMapper);
							if (null != source1) {
								leadsMapper.setSource(source1.getSourceId());
							}
						}
					}
					if (mappingField.equals("assignedTo")) {

						EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(value.getAsString());
						if (null != employeeDetails) {

							leadsMapper.setAssignedTo(employeeDetails.getEmployeeId());
						}

					}

//							if (mappingField.equals("zipcode")) {
//
//								leadsMapper.setZipcode(value.getAsString());
//
//							}
//							if (mappingField.equals("countryDialCode")) {
//
//								leadsMapper.setCountryDialCode(value.getAsString());
//
//							}

//							if (mappingField.equals("imageURL")) {
//
//								leadsMapper.setImageURL(value.getAsString());
//
//							}

//							if (mappingField.equals("businessRegistration")) {
//
//								leadsMapper.setBusinessRegistration(value.getAsString());
//
//							}

					if (mappingField.equals("clientLocation")) {

						leadsMapper.setCategory(value.getAsString());
					}

					if (mappingField.equals("xlAddress")) {
						addressMapper.setXlAddress(value.getAsString());
					}
					if (mappingField.equals("type")) {
						leadsMapper.setType(value.getAsString());
					}

//							
//							if (mappingField.equals("addressType")) {
//
//								addressMapper.setAddressType(value.getAsString());
//
//							}
//							if (mappingField.equals("address1")) {
//
//								addressMapper.setAddress1(value.getAsString());
//
//							}
//							if (mappingField.equals("address2")) {
//
//								addressMapper.setAddress2(value.getAsString());
//
//							}
//							if (mappingField.equals("postalCode")) {
//
//								addressMapper.setPostalCode(value.getAsString());
//
//							}
//							if (mappingField.equals("street")) {
//
//								addressMapper.setStreet(value.getAsString());
//
//							}
//							if (mappingField.equals("city")) {
//
//								addressMapper.setCity(value.getAsString());
//
//							}
//							if (mappingField.equals("town")) {
//
//								addressMapper.setTown(value.getAsString());
//
//							}
//							if (mappingField.equals("country")) {
//
//								addressMapper.setCountry(value.getAsString());
//
//							}
					/*
					 * if (mappingField.equals("notes")) {
					 *
					 * noteMapper.setDescription(value.getAsString()); }
					 */
				}
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				addressList.add(addressMapper);
				leadsMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
				leadsMapper.setUserId(fieldDetailsDTO.getUserId());
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

					if (!StringUtils.isEmpty(leadsMapper.getUrl())) {
						boolean b1 = leadsService.getLeadsByUrl(leadsMapper.getUrl());
						if (!b1) {
							leadsService.saveLeads(leadsMapper);
							count++;
						}
					}
				}

			}

		}

		return count;

	}

	@Override
	public String insertTaskType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsforTaskType();
		int count = mapTaskType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsforTaskType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "taskType");

		list.add(name);

		return list;
	}

	private int mapTaskType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<TaskMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				TaskMapper taskMapper = new TaskMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("taskType")) {
						if (null != value.getAsString()) {
							taskMapper.setTaskType(value.getAsString());
						}

					}

					taskMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
					taskMapper.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(taskMapper);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (TaskMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getTaskType())) {
						boolean b = taskService.checkTaskNameInTaskTypeByOrgLevel(mapper.getTaskType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							taskService.saveTaskType(mapper);
							count++;
						}

					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertEventType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForEventType();
		int count = mapEventType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapEventType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<EventMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				EventMapper mapper1 = new EventMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("eventType")) {
						if (null != value.getAsString()) {
							mapper1.setEventType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (EventMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getEventType())) {
						boolean b = eventService.checkEventNameInEventTypeByOrgLevel(mapper.getEventType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							eventService.saveEventType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForEventType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "eventType");

		list.add(name);

		return list;
	}

	@Override
	public String insertSector(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForSector();
		int count = mapSector(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapSector(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<SectorMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				SectorMapper mapper1 = new SectorMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("sectorName")) {
						if (null != value.getAsString()) {
							mapper1.setSectorName(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (SectorMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getSectorName())) {
						boolean b = sectorService.checkSectorNameInSectorDetailsByOrgLevel(mapper.getSectorName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							sectorService.saveToSectorDetails(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForSector() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "sectorName");

		list.add(name);

		return list;
	}

	@Override
	public String insertSource(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForSource();
		int count = mapSource(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForSource() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapSource(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<SourceMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				SourceMapper mapper1 = new SourceMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (SourceMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = sourceService.checkSourceByNameByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							sourceService.saveSource(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertRoleTypeExternal(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForRoleTypeExternal();
		int count = mapRoleTypeExternal(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForRoleTypeExternal() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "roleType");

		list.add(name);

		return list;
	}

	private int mapRoleTypeExternal(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<RoleTypeExternalMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				RoleTypeExternalMapper mapper1 = new RoleTypeExternalMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("roleType")) {
						if (null != value.getAsString()) {
							mapper1.setRoleType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (RoleTypeExternalMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getRoleType())) {
						boolean b = roleTypeExternalService.checkRoleNameInRoleTypeByOrgLevel(mapper.getRoleType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							roleTypeExternalService.createRoleTypeExternal(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertEducationType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForEducationType();
		int count = mapEducationType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForEducationType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "educationType");

		list.add(name);

		return list;
	}

	private int mapEducationType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<EducationTypeMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				EducationTypeMapper mapper1 = new EducationTypeMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("educationType")) {
						if (null != value.getAsString()) {
							mapper1.setEducationType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (EducationTypeMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getEducationType())) {
						boolean b = educationService.checkEducationNameInEducationTypeByOrgLevel(
								mapper.getEducationType(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							educationService.saveEducationType(mapper);
							count++;
						}

					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertDocumentType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForDocumentType();
		int count = mapDocumentType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForDocumentType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "documentTypeName");

		list.add(name);

		return list;
	}

	private int mapDocumentType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<DocumentTypeMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				DocumentTypeMapper mapper1 = new DocumentTypeMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("documentTypeName")) {
						if (null != value.getAsString()) {
							mapper1.setDocumentTypeName(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setCreatorId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (DocumentTypeMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getDocumentTypeName())) {
						boolean b = documentService.checkDocumentNameInDocumentTypeByOrgLevel(
								mapper.getDocumentTypeName(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							documentService.addDocumentType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertDesignation(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForDesignation();
		int count = mapDesignation(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForDesignation() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "designationType");

		list.add(name);

		return list;
	}

	private int mapDesignation(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<DesignationMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				DesignationMapper mapper1 = new DesignationMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("designationType")) {
						if (null != value.getAsString()) {
							mapper1.setDesignationType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (DesignationMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getDesignationType())) {
						boolean b = departmentService.checkDesignationInDesignationTypeByOrgLevel(
								mapper.getDesignationType(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							departmentService.saveDesignation(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertDepartment(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForDepartment();
		int count = mapDepartment(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForDepartment() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "departmentName");

		list.add(name);

		return list;
	}

	private int mapDepartment(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<DepartmentMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				DepartmentMapper mapper1 = new DepartmentMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("departmentName")) {
						if (null != value.getAsString()) {
							mapper1.setDepartmentName(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (DepartmentMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getDepartmentName())) {
						boolean b = departmentService.checkDepartmentNameInDepartmentByOrgLevel(
								mapper.getDepartmentName(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							departmentService.saveDepartment(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertRoleType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForRoleType();
		int count = mapRoleType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForRoleType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "roleType");

		list.add(name);

		return list;
	}

	private int mapRoleType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<RoleMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				RoleMapper mapper1 = new RoleMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("roleType")) {
						if (null != value.getAsString()) {
							mapper1.setRoleType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (RoleMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getRoleType())) {
						boolean b = categoryService.checkRoleNameInRoleTypeByorgLevel(mapper.getRoleType(),
								mapper.getDepartmentId(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							categoryService.CreateRoleType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertShipBy(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForShipBy();
		int count = mapShipBy(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForShipBy() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapShipBy(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<ShipByMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				ShipByMapper mapper1 = new ShipByMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (ShipByMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = shipByService.checkNameInShipByByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							shipByService.saveShipBy(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertLibraryType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForLibraryType();
		int count = mapLibraryType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForLibraryType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "libraryType");

		list.add(name);

		return list;
	}

	private int mapLibraryType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<RoleMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				RoleMapper mapper1 = new RoleMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("libraryType")) {
						if (null != value.getAsString()) {
							mapper1.setLibraryType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (RoleMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getLibraryType())) {
						boolean b = categoryService.checkLibraryNameInLibraryTypeInOrgLevel(mapper.getLibraryType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							categoryService.CreateLibraryType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertExpenseType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForExpenseType();
		int count = mapExpenseType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForExpenseType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "expenseType");

		list.add(name);

		return list;
	}

	private int mapExpenseType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<ExpenseMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				ExpenseMapper mapper1 = new ExpenseMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("expenseType")) {
						if (null != value.getAsString()) {
							mapper1.setExpenseType(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (ExpenseMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getExpenseType())) {
						boolean b = expenseService.checkExpenseNameInExpenseTypeByOrgLevel(mapper.getExpenseType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							expenseService.saveExpenseType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertPerformanceManagement(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForPerformanceManagement();
		int count = mapPerformanceManagement(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForPerformanceManagement() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "kpi");

		list.add(name);

		return list;
	}

	private int mapPerformanceManagement(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<PerformanceMgmtReqMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				PerformanceMgmtReqMapper mapper1 = new PerformanceMgmtReqMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("kpi")) {
						if (null != value.getAsString()) {
							mapper1.setKpi(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (PerformanceMgmtReqMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getKpi())) {
						boolean b = performanceManagementService.checkKpiInPerformanceMgmtByOrgLevel(mapper.getKpi(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							performanceManagementService.savePerformanceManagement(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertPayment(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForPayment();
		int count = mapPayment(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForPayment() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapPayment(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<PaymentMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				PaymentMapper mapper1 = new PaymentMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (PaymentMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = paymentService.checkNameInPaymentByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							paymentService.savePayment(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertLeadsCategory(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForLeadsCategory();
		int count = mapLeadsCategory(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForLeadsCategory() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO cold = new FieldDetailsDTO("Cold", "cold");
		FieldDetailsDTO hot = new FieldDetailsDTO("Hot", "hot");
		FieldDetailsDTO worm = new FieldDetailsDTO("Worm", "worm");
		list.add(cold);
		list.add(hot);
		list.add(worm);

		return list;
	}

	private int mapLeadsCategory(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<LeadsCategoryMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				LeadsCategoryMapper mapper1 = new LeadsCategoryMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("hot")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}
					if (mappingField.equals("cold")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}
					if (mappingField.equals("worm")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (LeadsCategoryMapper mapper : mapperList) {

					leadsCategoryService.CreateLeadsCategory(mapper);
					count++;

				}

			}
		}
		return count;

	}

	@Override
	public String insertNav(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForNav();
		int count = mapNav(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForNav() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "navName");

		list.add(name);

		return list;
	}

	private int mapNav(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<NavRequestMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				NavRequestMapper mapper1 = new NavRequestMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("navName")) {
						if (null != value.getAsString()) {
							mapper1.setNavName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (NavRequestMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getNavName())) {
						boolean b = navService.checkNavNameInNavDetailsByOrgLevel(mapper.getNavName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							navService.createNav(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertItemTask(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForItemTask();
		int count = mapItemTask(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForItemTask() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapItemTask(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<ItemTaskMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				ItemTaskMapper mapper1 = new ItemTaskMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (ItemTaskMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = itemTaskService.checkNameInItemTaskByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							itemTaskService.saveItemTask(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertInvestorCategory(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForInvestorCategory();
		int count = mapInvestorCategory(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForInvestorCategory() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapInvestorCategory(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<InvestorCategoryMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				InvestorCategoryMapper mapper1 = new InvestorCategoryMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (InvestorCategoryMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = investorCategoryService.checkNameInInvestorCategoryByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							investorCategoryService.saveInvestorCategory(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertIdProofType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForIdProofType();
		int count = mapIdProofType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForIdProofType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "idProofType");

		list.add(name);

		return list;
	}

	private int mapIdProofType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<IdProofTypeMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				IdProofTypeMapper mapper1 = new IdProofTypeMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("idProofType")) {
						if (null != value.getAsString()) {
							mapper1.setIdProofType(value.getAsString());
						}

					}

					mapper1.setOrganizationId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (IdProofTypeMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getIdProofType())) {
						boolean b = idProofTypeService.checkIdProofNameInIdProofTypebyOrgLevel(mapper.getIdProofType(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							idProofTypeService.saveIdProofType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertCustomerType(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForCustomerType();
		int count = mapCustomerType(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForCustomerType() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapCustomerType(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<CustomerTypeMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				CustomerTypeMapper mapper1 = new CustomerTypeMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (CustomerTypeMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = customerTypeService.checkNameInCustomerTypeByOrgLevel(mapper.getName(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							customerTypeService.saveCustomerType(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertRegion(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForRegion();
		int count = mapRegion(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForRegion() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "regions");

		list.add(name);

		return list;
	}

	private int mapRegion(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<RegionsMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				RegionsMapper mapper1 = new RegionsMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("regions")) {
						if (null != value.getAsString()) {
							mapper1.setRegions(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (RegionsMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getRegions())) {
						boolean b = regionsService.checkRegionInRegionsByOrgLevel(mapper.getRegions(),
								fieldDetailsDTO.getOrgId());
						if (b != true) {
							regionsService.createRegions(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertServiceLine(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForServiceLine();
		int count = mapServiceLine(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForServiceLine() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "serviceLineName");

		list.add(name);

		return list;
	}

	private int mapServiceLine(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<ServiceLineReqMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				ServiceLineReqMapper mapper1 = new ServiceLineReqMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("serviceLineName")) {
						if (null != value.getAsString()) {
							mapper1.setServiceLineName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (ServiceLineReqMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getServiceLineName())) {
						boolean b = serviceLineService.checkServiceLineNameInServiceLineByOrgLevel(
								mapper.getServiceLineName(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							serviceLineService.saveServiceLine(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String insertLob(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForLob();
		int count = mapLob(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForLob() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Name", "name");

		list.add(name);

		return list;
	}

	private int mapLob(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {

//		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		String excelJson = fieldDetailsDTO.getExcelJson();
		int count = 0;

		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<LobMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				LobMapper mapper1 = new LobMapper();
				for (FieldDetailsDTO excelMapping : list) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {
						if (null != value.getAsString()) {
							mapper1.setName(value.getAsString());
						}

					}

					mapper1.setOrgId(fieldDetailsDTO.getOrgId());
					mapper1.setUserId(fieldDetailsDTO.getUserId());

					mapperList.add(mapper1);

				}
			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (LobMapper mapper : mapperList) {

					if (!StringUtils.isEmpty(mapper.getName())) {
						boolean b = lobService.checkNameInLobByOrgLevel(mapper.getName(), fieldDetailsDTO.getOrgId());
						if (b != true) {
							lobService.saveLob(mapper);
							count++;
						}
					}
				}

			}
		}
		return count;

	}

	@Override
	public String getExcelJsonData(File file) throws JsonProcessingException, IOException {
		String excelJsonData = null;
		JsonObject object = null;
		if (file.exists()) {

			if (getFileExtension(file).equalsIgnoreCase("xlsx") || getFileExtension(file).equalsIgnoreCase("xls")) {
				object = getExcelDataAsJsonObject(file);

			} else if (getFileExtension(file).equalsIgnoreCase("csv")) {
				object = getCsvDataAsJsonObject(file);

			} else {
				throw new java.io.FileNotFoundException("Please upload xls or  csv file !!!");
			}

			if (null != object) {
				excelJsonData = object.toString();
			}
		} else {
			throw new java.io.FileNotFoundException("Could not find file: " + file);
		}
		return excelJsonData;
	}

	@Override
	public String importCustomer(FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsCustomer();
		int count = mapCustomer(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapCustomer(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO)
			throws TemplateException, IOException {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<CustomerMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				CustomerMapper customerMapper = new CustomerMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelBulkMapping : list) {
					String header = excelBulkMapping.getFieldViewName();
					String mappingField = excelBulkMapping.getFieldKey();

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

						SectorDetails sector = sectorDetailsRepository
								.getSectorDetailsBySectorName(value.getAsString());
						if (sector != null) {
							customerMapper.setSectorId(sector.getSectorId());
						} else {
							SectorMapper sectorMapper = new SectorMapper();
							sectorMapper.setName(value.getAsString());
							sectorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							sectorMapper.setUserId(fieldDetailsDTO.getUserId());
							List<SectorMapper> sector1 = sectorService.saveToSectorDetails(sectorMapper);
							if (sector1 != null) {
								customerMapper.setSectorId(sector1.get(0).getSectorId());
							}
						}

					}
					if (mappingField.equals("source")) {
						Source source = sourceRepository.findBySourceName(value.getAsString());
						if (null != source) {
							customerMapper.setSource(source.getSourceId());
						} else {
							SourceMapper sourceMapper = new SourceMapper();
							sourceMapper.setName(value.getAsString());
							sourceMapper.setOrgId(fieldDetailsDTO.getOrgId());
							sourceMapper.setUserId(fieldDetailsDTO.getUserId());

							SourceMapper source1 = sourceService.saveSource(sourceMapper);
							if (null != source1) {
								customerMapper.setSource(source1.getSourceId());
							}
						}
					}
					if (mappingField.equals("assignedTo")) {

						EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(value.getAsString());
						if (null != employeeDetails) {

							customerMapper.setAssignedTo(employeeDetails.getEmployeeId());
						}

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

					if (mappingField.equals("businessRegistration")) {

						customerMapper.setBusinessRegistration(value.getAsString());

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
				customerMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
				customerMapper.setUserId(fieldDetailsDTO.getUserId());
				customerMapper.setAddress(addressList);
				mapperList.add(customerMapper);

			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (CustomerMapper customerMapper : mapperList) {

					if (!StringUtils.isEmpty(customerMapper.getUrl())) {
						boolean b = customerService.customerByUrl(customerMapper.getUrl());
						if (!b) {
							customerService.saveCustomer(customerMapper);
							count++;
						}
					}
				}

			}
		}
		return count;
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsCustomer() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Company Name", "name");

		FieldDetailsDTO url = new FieldDetailsDTO("Url", "url");

		FieldDetailsDTO notes = new FieldDetailsDTO("Note", "notes");

//		FieldDetailsDTO name = new FieldDetailsDTO("Client", "name");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone Number", "phoneNumber");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO vatNo = new FieldDetailsDTO("Vat No", "vatNo");

		FieldDetailsDTO country = new FieldDetailsDTO("Country", "country");

		FieldDetailsDTO sector = new FieldDetailsDTO("Sector", "sector");

		FieldDetailsDTO source = new FieldDetailsDTO("Source", "source");

		FieldDetailsDTO zipcode = new FieldDetailsDTO("Zipcode", "zipcode");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO category = new FieldDetailsDTO("Category", "category");

		FieldDetailsDTO assignedTo = new FieldDetailsDTO("Nuboxed Team", "assignedTo");

		FieldDetailsDTO businessRegistration = new FieldDetailsDTO("Business Registration", "businessRegistration");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		list.add(name);
		list.add(url);
		list.add(notes);
		list.add(phoneNumber);
		list.add(email);
		list.add(vatNo);
		list.add(country);
		list.add(sector);
		list.add(source);
		list.add(zipcode);
		list.add(countryDialCode);
		list.add(assignedTo);
		list.add(businessRegistration);
		list.add(xlAddress);
		list.add(category);

		return list;
	}

	@Override
	public String importContact(String type, FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsContact();
		int count = mapContact(type, list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsContact() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO salutation = new FieldDetailsDTO("Salutation", "salutation");

		FieldDetailsDTO firstName = new FieldDetailsDTO("First Name", "firstName");

		FieldDetailsDTO middleName = new FieldDetailsDTO("Middle Name", "middleName");

		FieldDetailsDTO lastName = new FieldDetailsDTO("Last Name", "lastName");

		FieldDetailsDTO notes = new FieldDetailsDTO("Notes", "notes");

		FieldDetailsDTO mobileNumber = new FieldDetailsDTO("Mobile No", "mobileNumber");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone No", "phoneNumber");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO department = new FieldDetailsDTO("Department", "department");

		FieldDetailsDTO designation = new FieldDetailsDTO("Designation", "designation");

		FieldDetailsDTO country = new FieldDetailsDTO("Country", "country");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO sector = new FieldDetailsDTO("Sector", "sector");

		FieldDetailsDTO source = new FieldDetailsDTO("Source", "source");

//        FieldDetailsDTO zipcode = new FieldDetailsDTO("Zipcode", "zipcode");

		FieldDetailsDTO company = new FieldDetailsDTO("Company", "company");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		list.add(salutation);
		list.add(firstName);
		list.add(middleName);
		list.add(lastName);
		list.add(notes);
		list.add(phoneNumber);
		list.add(email);
		list.add(mobileNumber);
		list.add(country);
		list.add(sector);
		list.add(source);
		list.add(countryDialCode);
		list.add(designation);
		list.add(department);
		list.add(company);
		list.add(xlAddress);
		return list;
	}

	private int mapContact(String type, List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO)
			throws TemplateException, IOException {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<ContactMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				ContactMapper contactMapper = new ContactMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelBulkMapping : list) {
					String header = excelBulkMapping.getFieldViewName();
					String mappingField = excelBulkMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("salutation")) {

						contactMapper.setSalutation(value.getAsString());

					}
					if (mappingField.equals("firstName")) {
						contactMapper.setFirstName(value.getAsString());
					}
					if (mappingField.equals("middleName")) {
						contactMapper.setMiddleName(value.getAsString());
					}

					if (mappingField.equals("lastName")) {
						contactMapper.setLastName(value.getAsString());
					}

					if (mappingField.equals("notes")) {

						contactMapper.setNotes(value.getAsString());

					}

					if (mappingField.equals("mobileNumber")) {

						contactMapper.setMobileNumber(value.getAsString());

					}
					if (mappingField.equals("phoneNumber")) {

						contactMapper.setPhoneNumber(value.getAsString());

					}
					if (mappingField.equals("email")) {
						contactMapper.setEmailId(value.getAsString());

					}
					if (mappingField.equals("department")) {

						Department department = departmentRepository.getByDepartmentNameAndOrgId(value.getAsString(),
								fieldDetailsDTO.getOrgId());
						if (null != department) {
							contactMapper.setDepartmentId(department.getDepartment_id());
						} else {
							DepartmentMapper departmentMapper = new DepartmentMapper();
							departmentMapper.setDepartmentName(value.getAsString());
							departmentMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							departmentMapper.setUserId(fieldDetailsDTO.getUserId());
							DepartmentMapper department1 = departmentService.saveDepartment(departmentMapper);
							if (null != department1) {
								contactMapper.setDepartmentId(department1.getDepartmentId());
							}
						}

					}

					if (mappingField.equals("designation")) {

						Designation designation = designationRepository.findByDesignationTypeIdAndLiveIndAndOrgId(
								value.getAsString(), true, fieldDetailsDTO.getOrgId());
						if (null != designation) {
							contactMapper.setDesignationTypeId(designation.getDesignationTypeId());
						} else {
							DesignationMapper designationMapper = new DesignationMapper();
							designationMapper.setDesignationType(value.getAsString());
							designationMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							designationMapper.setUserId(fieldDetailsDTO.getUserId());
							DesignationMapper designation1 = departmentService.saveDesignation(designationMapper);
							if (null != designation1) {
								contactMapper.setDesignationTypeId(designation1.getDesignationTypeId());
							}
						}

					}

					if (mappingField.equals("country")) {

						contactMapper.setCountry(value.getAsString());

					}

					if (mappingField.equals("sector")) {

						SectorDetails sector = sectorDetailsRepository
								.getSectorDetailsBySectorName(value.getAsString());
						if (sector != null) {
							contactMapper.setSectorId(sector.getSectorId());
						} else {
							SectorMapper sectorMapper = new SectorMapper();
							sectorMapper.setName(value.getAsString());
							sectorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							sectorMapper.setUserId(fieldDetailsDTO.getUserId());
							List<SectorMapper> sector1 = sectorService.saveToSectorDetails(sectorMapper);
							if (sector1 != null) {
								contactMapper.setSectorId(sector1.get(0).getSectorId());
							}
						}

					}
					if (mappingField.equals("source")) {
						Source source = sourceRepository.findBySourceName(value.getAsString());
						if (null != source) {
							contactMapper.setSource(source.getSourceId());
						} else {
							SourceMapper sourceMapper = new SourceMapper();
							sourceMapper.setName(value.getAsString());
							sourceMapper.setOrgId(fieldDetailsDTO.getOrgId());
							sourceMapper.setUserId(fieldDetailsDTO.getUserId());

							SourceMapper source1 = sourceService.saveSource(sourceMapper);
							if (null != source1) {
								contactMapper.setSource(source1.getSourceId());
							}
						}
					}

					if (mappingField.equals("countryDialCode")) {

						contactMapper.setCountryDialCode(value.getAsString());

					}

					if (mappingField.equals("xlAddress")) {

						addressMapper.setXlAddress(value.getAsString());

					}
					/*
					 * if (mappingField.equals("notes")) {
					 *
					 * noteMapper.setDescription(value.getAsString()); }
					 */
					if (mappingField.equals("company")) {
						if (type.equalsIgnoreCase("Investor")) {
							contactMapper.setContactType("Investor");
							Investor investor = investorRepository.findByNameAndLiveInd(value.getAsString(), true);
							if (null != investor) {
								contactMapper.setInvestorId(investor.getInvestorId());
							}
						} else {
							contactMapper.setContactType("Customer");
							Customer customer = customerRepository.getByName(value.getAsString());
							if (null != customer) {
								contactMapper.setCustomerId(customer.getCustomerId());
							}
						}
					}
				}

				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				addressList.add(addressMapper);
				contactMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
				contactMapper.setUserId(fieldDetailsDTO.getUserId());
				contactMapper.setAddress(addressList);
				mapperList.add(contactMapper);

			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (ContactMapper contactMapper : mapperList) {

					if (!StringUtils.isEmpty(contactMapper.getEmailId())) {
						boolean b = contactService.contactExistsByEmail(contactMapper);
						if (!b) {
							contactService.saveContact(contactMapper);
							count++;
						}
					}
				}

			}
		}
		return count;
	}

	@Override
	public String insertInvestor(FieldDetailsDTO fieldDetailsDTO) throws IOException, TemplateException {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsForInvestor();
		int count = mapInvestor(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapInvestor(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO)
			throws IOException, TemplateException {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<InvestorMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				InvestorMapper investorMapper = new InvestorMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelBulkMapping : list) {
					String header = excelBulkMapping.getFieldViewName();
					String mappingField = excelBulkMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {

						investorMapper.setName(value.getAsString());

					}
					if (mappingField.equals("url")) {
						investorMapper.setUrl(value.getAsString());
					}
					if (mappingField.equals("notes")) {

						investorMapper.setNotes(value.getAsString());

					}
					if (mappingField.equals("phoneNumber")) {

						investorMapper.setPhoneNumber(value.getAsString());

					}
					if (mappingField.equals("email")) {
						investorMapper.setEmail(value.getAsString());

					}
					if (mappingField.equals("vatNo")) {

						investorMapper.setVatNo(value.getAsString());

					}
					if (mappingField.equals("country")) {

						investorMapper.setCountry(value.getAsString());

					}

					if (mappingField.equals("sector")) {

						SectorDetails sector = sectorDetailsRepository
								.getSectorDetailsBySectorName(value.getAsString());
						if (sector != null) {
							investorMapper.setSectorId(sector.getSectorId());
						} else {
							SectorMapper sectorMapper = new SectorMapper();
							sectorMapper.setName(value.getAsString());
							sectorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							sectorMapper.setUserId(fieldDetailsDTO.getUserId());
							List<SectorMapper> sector1 = sectorService.saveToSectorDetails(sectorMapper);
							if (sector1 != null) {
								investorMapper.setSectorId(sector1.get(0).getSectorId());
							}
						}

					}
					if (mappingField.equals("source")) {
						Source source = sourceRepository.findBySourceName(value.getAsString());
						if (null != source) {
							investorMapper.setSource(source.getSourceId());
						} else {
							SourceMapper sourceMapper = new SourceMapper();
							sourceMapper.setName(value.getAsString());
							sourceMapper.setOrgId(fieldDetailsDTO.getOrgId());
							sourceMapper.setUserId(fieldDetailsDTO.getUserId());

							SourceMapper source1 = sourceService.saveSource(sourceMapper);
							if (null != source1) {
								investorMapper.setSource(source1.getSourceId());
							}
						}
					}
					if (mappingField.equals("assignedTo")) {

						EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(value.getAsString());
						if (null != employeeDetails) {

							investorMapper.setAssignedTo(employeeDetails.getEmployeeId());
						}

					}
					if (mappingField.equals("zipcode")) {

						investorMapper.setZipcode(value.getAsString());

					}
					if (mappingField.equals("countryDialCode")) {

						investorMapper.setCountryDialCode(value.getAsString());

					}
					if (mappingField.equals("category")) {

						investorMapper.setCategory(value.getAsString());

					}

					if (mappingField.equals("businessRegistration")) {

						investorMapper.setBusinessRegistration(value.getAsString());

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
				investorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
				investorMapper.setUserId(fieldDetailsDTO.getUserId());
				investorMapper.setAddress(addressList);
				mapperList.add(investorMapper);

			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (InvestorMapper investorMapper : mapperList) {

					if (!StringUtils.isEmpty(investorMapper.getUrl())) {
						boolean b = investorService.investorByUrl(investorMapper.getUrl());
						if (!b) {
							investorService.saveInvestor(investorMapper);
							count++;
						}
					}
				}

			}
		}
		return count;
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsForInvestor() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Company Name", "name");

		FieldDetailsDTO url = new FieldDetailsDTO("Url", "url");

		FieldDetailsDTO notes = new FieldDetailsDTO("Note", "notes");

//		FieldDetailsDTO name = new FieldDetailsDTO("Client", "name");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone Number", "phoneNumber");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO vatNo = new FieldDetailsDTO("Vat No", "vatNo");

		FieldDetailsDTO country = new FieldDetailsDTO("Country", "country");

		FieldDetailsDTO sector = new FieldDetailsDTO("Sector", "sector");

		FieldDetailsDTO source = new FieldDetailsDTO("Source", "source");

		FieldDetailsDTO zipcode = new FieldDetailsDTO("Zipcode", "zipcode");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO category = new FieldDetailsDTO("Category", "category");

		FieldDetailsDTO assignedTo = new FieldDetailsDTO("Nuboxed Team", "assignedTo");

		FieldDetailsDTO businessRegistration = new FieldDetailsDTO("Business Registration", "businessRegistration");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		list.add(name);
		list.add(url);
		list.add(notes);
		list.add(phoneNumber);
		list.add(email);
		list.add(vatNo);
		list.add(country);
		list.add(sector);
		list.add(source);
		list.add(zipcode);
		list.add(countryDialCode);
		list.add(assignedTo);
		list.add(businessRegistration);
		list.add(xlAddress);
		list.add(category);

		return list;
	}

	@Override
	public String insertInvestorLeads(FieldDetailsDTO fieldDetailsDTO) throws TemplateException, IOException {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsInvestorLeads();
		int count = mapInvestorLeads(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapInvestorLeads(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO)
			throws TemplateException, IOException {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<InvestorLeadsMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				InvestorLeadsMapper investorMapper = new InvestorLeadsMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelBulkMapping : list) {
					String header = excelBulkMapping.getFieldViewName();
					String mappingField = excelBulkMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("name")) {

						investorMapper.setName(value.getAsString());

					}

					if (mappingField.equals("salutation")) {

						investorMapper.setSalutation(value.getAsString());

					}

					if (mappingField.equals("firstName")) {
						investorMapper.setFirstName(value.getAsString());
					}
					if (mappingField.equals("middleName")) {
						investorMapper.setMiddleName(value.getAsString());
					}

					if (mappingField.equals("lastName")) {
						investorMapper.setLastName(value.getAsString());
					}

					if (mappingField.equals("url")) {
						investorMapper.setUrl(value.getAsString());
					}
					if (mappingField.equals("notes")) {

						investorMapper.setNotes(value.getAsString());

					}
					if (mappingField.equals("phoneNumber")) {

						investorMapper.setPhoneNumber(value.getAsString());

					}
					if (mappingField.equals("email")) {
						investorMapper.setEmail(value.getAsString());

					}
					if (mappingField.equals("vatNo")) {

						investorMapper.setVatNo(value.getAsString());

					}
					if (mappingField.equals("country")) {

						investorMapper.setCountry(value.getAsString());

					}

					if (mappingField.equals("valueOfShare")) {

						investorMapper.setValueOfShare(value.getAsDouble());

					}

					if (mappingField.equals("unitOfShare")) {

						investorMapper.setUnitOfShare(value.getAsDouble());

					}

					if (mappingField.equals("sector")) {

						SectorDetails sector = sectorDetailsRepository
								.getSectorDetailsBySectorName(value.getAsString());
						if (sector != null) {
							investorMapper.setSectorId(sector.getSectorId());
						} else {
							SectorMapper sectorMapper = new SectorMapper();
							sectorMapper.setName(value.getAsString());
							sectorMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							sectorMapper.setUserId(fieldDetailsDTO.getUserId());
							List<SectorMapper> sector1 = sectorService.saveToSectorDetails(sectorMapper);
							if (sector1 != null) {
								investorMapper.setSectorId(sector1.get(0).getSectorId());
							}
						}

					}
					if (mappingField.equals("source")) {
						Source source = sourceRepository.findBySourceName(value.getAsString());
						if (null != source) {
							investorMapper.setSource(source.getSourceId());
						} else {
							SourceMapper sourceMapper = new SourceMapper();
							sourceMapper.setName(value.getAsString());
							sourceMapper.setOrgId(fieldDetailsDTO.getOrgId());
							sourceMapper.setUserId(fieldDetailsDTO.getUserId());

							SourceMapper source1 = sourceService.saveSource(sourceMapper);
							if (null != source1) {
								investorMapper.setSource(source1.getSourceId());
							}
						}
					}
					if (mappingField.equals("assignedTo")) {

						EmployeeDetails employeeDetails = employeeRepository.getEmployeeByMailId(value.getAsString());
						if (null != employeeDetails) {

							investorMapper.setAssignedTo(employeeDetails.getEmployeeId());
						}

					}
					if (mappingField.equals("zipcode")) {

						investorMapper.setZipcode(value.getAsString());

					}
					if (mappingField.equals("countryDialCode")) {

						investorMapper.setCountryDialCode(value.getAsString());

					}
					if (mappingField.equals("category")) {

						investorMapper.setCategory(value.getAsString());

					}

					if (mappingField.equals("businessRegistration")) {

						investorMapper.setBusinessRegistration(value.getAsString());

					}

					if (mappingField.equals("type")) {

						investorMapper.setType(value.getAsString());

					}

					if (mappingField.equals("group")) {

						investorMapper.setGroup(value.getAsString());

					}

					if (mappingField.equals("xlAddress")) {

						addressMapper.setXlAddress(value.getAsString());

					}
				}
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				addressList.add(addressMapper);
				investorMapper.setOrgId(fieldDetailsDTO.getOrgId());
				investorMapper.setUserId(fieldDetailsDTO.getUserId());
				investorMapper.setAddress(addressList);
				mapperList.add(investorMapper);

			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (InvestorLeadsMapper investorLeadsMapper : mapperList) {

					if (!StringUtils.isEmpty(investorLeadsMapper.getEmail())) {
						boolean b = investorLeadsService.getInvestorLeadsByEmail(investorLeadsMapper.getEmail());
						if (!b) {
							if (!StringUtils.isEmpty(investorLeadsMapper.getUrl())) {
								boolean b1 = investorLeadsService.getInvestorLeadsByUrl(investorLeadsMapper.getUrl());
								if (!b1) {
									investorLeadsService.saveInvestorLeads(investorLeadsMapper);
									count++;
								}
							} else {
								investorLeadsService.saveInvestorLeads(investorLeadsMapper);
								count++;
							}
						}
					}
				}

			}
		}
		return count;
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsInvestorLeads() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO name = new FieldDetailsDTO("Company Name", "name");

		FieldDetailsDTO salutation = new FieldDetailsDTO("Salutation", "salutation");

		FieldDetailsDTO firstName = new FieldDetailsDTO("First Name", "firstName");

		FieldDetailsDTO middleName = new FieldDetailsDTO("Middle Name", "middleName");

		FieldDetailsDTO lastName = new FieldDetailsDTO("Last Name", "lastName");

		FieldDetailsDTO url = new FieldDetailsDTO("Url", "url");

		FieldDetailsDTO notes = new FieldDetailsDTO("Notes", "notes");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone No", "phoneNumber");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO group = new FieldDetailsDTO("Group", "group");

		FieldDetailsDTO vatNo = new FieldDetailsDTO("Vat No", "vatNo");

		FieldDetailsDTO assignedTo = new FieldDetailsDTO("Nubox Team", "assignedTo");

		FieldDetailsDTO country = new FieldDetailsDTO("Country", "country");

		FieldDetailsDTO sector = new FieldDetailsDTO("Sector", "sector");

		FieldDetailsDTO source = new FieldDetailsDTO("Source", "source");

		FieldDetailsDTO zipcode = new FieldDetailsDTO("Zipcode", "zipcode");

		FieldDetailsDTO type = new FieldDetailsDTO("Type", "type");

		FieldDetailsDTO category = new FieldDetailsDTO("Category", "category");

		FieldDetailsDTO businessRegistration = new FieldDetailsDTO("Business Registration", "businessRegistration");

		FieldDetailsDTO unitOfShare = new FieldDetailsDTO("Unit Of Share", "unitOfShare");

		FieldDetailsDTO valueOfShare = new FieldDetailsDTO("Value Of Share", "valueOfShare");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		list.add(name);
		list.add(salutation);
		list.add(firstName);
		list.add(middleName);
		list.add(lastName);
		list.add(notes);
		list.add(url);
		list.add(group);
		list.add(vatNo);
		list.add(assignedTo);
		list.add(zipcode);
		list.add(type);
		list.add(phoneNumber);
		list.add(email);
		list.add(country);
		list.add(sector);
		list.add(source);
		list.add(countryDialCode);
		list.add(category);
		list.add(businessRegistration);
		list.add(unitOfShare);
		list.add(valueOfShare);
		list.add(xlAddress);
		return list;
	}

	@Override
	public String insertEmployee(FieldDetailsDTO fieldDetailsDTO) {
		List<FieldDetailsDTO> list = getAllMappingEntityFieldsUsers();
		int count = mapUsers(list, fieldDetailsDTO);
		return String.valueOf(count);
	}

	private int mapUsers(List<FieldDetailsDTO> list, FieldDetailsDTO fieldDetailsDTO) {
		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));
			List<EmployeeMapper> mapperList = new ArrayList<>();

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
				EmployeeMapper empMapper = new EmployeeMapper();
				AddressMapper addressMapper = new AddressMapper();
				// NotesMapper noteMapper = new NotesMapper();
				for (FieldDetailsDTO excelBulkMapping : list) {
					String header = excelBulkMapping.getFieldViewName();
					String mappingField = excelBulkMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("salutation")) {
						empMapper.setSalutation(value.getAsString());
					}

					if (mappingField.equals("firstName")) {
						empMapper.setFirstName(value.getAsString());
					}
					if (mappingField.equals("middleName")) {
						empMapper.setMiddleName(value.getAsString());
					}

					if (mappingField.equals("lastName")) {
						empMapper.setLastName(value.getAsString());
					}

					if (mappingField.equals("lastName")) {
						empMapper.setLastName(value.getAsString());
					}

					if (mappingField.equals("phoneNumber")) {

						empMapper.setPhoneNo(value.getAsString());

					}
					if (mappingField.equals("email")) {
						empMapper.setEmailId(value.getAsString());

					}

					if (mappingField.equals("secondaryEmail")) {
						empMapper.setSecondaryEmailId(value.getAsString());
					}

					if (mappingField.equals("mobileNumber")) {
						empMapper.setMobileNo(value.getAsString());
					}

					if (mappingField.equals("phoneNumber")) {
						empMapper.setPhoneNo(value.getAsString());
					}

					if (mappingField.equals("language")) {
						empMapper.setPreferedLanguage(value.getAsString());
					}

					if (mappingField.equals("timeZone")) {
						empMapper.setTimeZone(value.getAsString());
					}

					if (mappingField.equals("dob")) {
						empMapper.setDob(value.getAsString());
					}

					if (mappingField.equals("doj")) {
						empMapper.setDateOfJoining(value.getAsString());
					}

					if (mappingField.equals("designation")) {

						Designation designation = designationRepository.findByDesignationTypeIdAndLiveIndAndOrgId(
								value.getAsString(), true, fieldDetailsDTO.getOrgId());
						if (null != designation) {
							empMapper.setDesignationTypeId(designation.getDesignationTypeId());
						} else {
							DesignationMapper designationMapper = new DesignationMapper();
							designationMapper.setDesignationType(value.getAsString());
							designationMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							designationMapper.setUserId(fieldDetailsDTO.getUserId());
							DesignationMapper designation1 = departmentService.saveDesignation(designationMapper);
							if (null != designation1) {
								empMapper.setDesignationTypeId(designation1.getDesignationTypeId());
							}
						}

					}

					if (mappingField.equals("department")) {

						Department department = departmentRepository.getByDepartmentNameAndOrgId(value.getAsString(),
								fieldDetailsDTO.getOrgId());
						if (null != department) {
							empMapper.setDepartmentId(department.getDepartment_id());
							if (mappingField.equals("roleType")) {
								RoleType roleType = roleTypeRepository.getByRoleTypeAndLiveIndAndDepartmentIdAndOrgId(
										value.getAsString(), true, department.getDepartment_id(),
										fieldDetailsDTO.getOrgId());
								if (null != roleType) {
									empMapper.setRoleType(roleType.getRoleTypeId());
								} else {
									RoleMapper mapper = new RoleMapper();
									mapper.setRoleType(value.getAsString());
									mapper.setOrganizationId(fieldDetailsDTO.getOrgId());
									mapper.setUserId(fieldDetailsDTO.getUserId());
									mapper.setDepartmentId(department.getDepartment_id());
									RoleMapper roleMapper = categoryService.CreateRoleType(mapper);
									empMapper.setRoleType(roleMapper.getRoleTypeId());
								}
							}

						} else {
							DepartmentMapper departmentMapper = new DepartmentMapper();
							departmentMapper.setDepartmentName(value.getAsString());
							departmentMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
							departmentMapper.setUserId(fieldDetailsDTO.getUserId());
							DepartmentMapper department1 = departmentService.saveDepartment(departmentMapper);
							empMapper.setDepartmentId(department1.getDepartmentId());
							if (mappingField.equals("roleType")) {
								RoleMapper mapper = new RoleMapper();
								mapper.setRoleType(value.getAsString());
								mapper.setOrganizationId(fieldDetailsDTO.getOrgId());
								mapper.setUserId(fieldDetailsDTO.getUserId());
								mapper.setDepartmentId(department.getDepartment_id());
								RoleMapper roleMapper = categoryService.CreateRoleType(mapper);
								empMapper.setRoleType(roleMapper.getRoleTypeId());
							}

						}

					}

					if (mappingField.equals("country")) {

						empMapper.setCountry(value.getAsString());

					}

					if (mappingField.equals("countryDialCode")) {

						empMapper.setCountryDialCode(value.getAsString());

					}

					if (mappingField.equals("reportingManager")) {
						EmployeeDetails employeeDetail = employeeRepository.findByEmailId(value.getAsString());
						if (null != employeeDetail) {
							empMapper.setReportingManager(employeeDetail.getEmployeeId());
							empMapper.setReportingManagerDeptId(employeeDetail.getDepartment());
						}
					}

					if (mappingField.equals("secondaryReptManager")) {
						EmployeeDetails employeeDetail = employeeRepository.findByEmailId(value.getAsString());
						if (null != employeeDetail) {
							empMapper.setSecondaryReptManager(employeeDetail.getEmployeeId());
							empMapper.setSecondaryReptManagerDept(employeeDetail.getDepartment());
						}
					}

					if (mappingField.equals("workplace")) {

						empMapper.setWorkplace(value.getAsString());

					}

					if (mappingField.equals("currency")) {
						Currency currency = currencyRepository.findByCurrencyNameAndOrgId(value.getAsString(),
								fieldDetailsDTO.getOrgId());
						if (null != currency) {
							empMapper.setCurrency(currency.getCurrency_id());
						}
					}

					if (mappingField.equals("employeeType")) {
						empMapper.setEmployeeType(value.getAsString());
					}

					if (mappingField.equals("salary")) {
						empMapper.setSalary(value.getAsDouble());
					}

					if (mappingField.equals("location")) {
						LocationDetails locationDetails = locationDetailsRepository
								.findByLocationNameAndOrgIdAndActiveInd(value.getAsString(), fieldDetailsDTO.getOrgId(),
										true);
						if (null != locationDetails) {
							empMapper.setLocation(locationDetails.getLocationDetailsId());
						}
					}

					if (mappingField.equals("xlAddress")) {

						addressMapper.setXlAddress(value.getAsString());

					}
				}
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				addressList.add(addressMapper);
				empMapper.setOrganizationId(fieldDetailsDTO.getOrgId());
				empMapper.setUserId(fieldDetailsDTO.getUserId());
				empMapper.setAddress(addressList);
				empMapper.setUserType("User");
				mapperList.add(empMapper);

			}

			if (null != mapperList && !mapperList.isEmpty()) {

				for (EmployeeMapper employeeMapper : mapperList) {

					if (!StringUtils.isEmpty(employeeMapper.getEmailId())) {
						boolean b = registrationService.emailExist(employeeMapper.getEmailId());
						if (!b) {
							employeeService.saveToEmployeeProcess(employeeMapper, fieldDetailsDTO.getUserId());
							count++;
						}
					}
				}
			}

		}
		return count;
	}

	private List<FieldDetailsDTO> getAllMappingEntityFieldsUsers() {
		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO salutation = new FieldDetailsDTO("Salutation", "salutation");

		FieldDetailsDTO firstName = new FieldDetailsDTO("First Name", "firstName");

		FieldDetailsDTO middleName = new FieldDetailsDTO("Middle Name", "middleName");

		FieldDetailsDTO lastName = new FieldDetailsDTO("Last Name", "lastName");

		FieldDetailsDTO gender = new FieldDetailsDTO("Gender", "gender");

		FieldDetailsDTO email = new FieldDetailsDTO("Email (Mandatory)", "email");

		FieldDetailsDTO secondaryEmail = new FieldDetailsDTO("Secondary Email", "secondaryEmail");

		FieldDetailsDTO mobileNumber = new FieldDetailsDTO("Mobile No", "mobileNo");

		FieldDetailsDTO phoneNumber = new FieldDetailsDTO("Phone No", "phoneNumber");

		FieldDetailsDTO language = new FieldDetailsDTO("Language", "language");

		FieldDetailsDTO timeZone = new FieldDetailsDTO("Time Zone", "timeZone");

		FieldDetailsDTO dob = new FieldDetailsDTO("DOB", "dob");

		FieldDetailsDTO doj = new FieldDetailsDTO("DOJ", "doj");

		FieldDetailsDTO designation = new FieldDetailsDTO("Designation", "designation");

//    FieldDetailsDTO role= new FieldDetailsDTO("Role", "role");

		FieldDetailsDTO country = new FieldDetailsDTO("Country", "country");

		FieldDetailsDTO department = new FieldDetailsDTO("Department", "department");

		FieldDetailsDTO reportingManager = new FieldDetailsDTO("Reporting Manager", "reportingManager");

		FieldDetailsDTO roleType = new FieldDetailsDTO("Role Type", "roleType");

		FieldDetailsDTO countryDialCode = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO workplace = new FieldDetailsDTO("Workplace", "workplace");

		FieldDetailsDTO currency = new FieldDetailsDTO("Currency", "currency");

//    FieldDetailsDTO jobType = new FieldDetailsDTO("Country Dial Code", "countryDialCode");

		FieldDetailsDTO employeeType = new FieldDetailsDTO("Employee Type", "employeeType");

		FieldDetailsDTO location = new FieldDetailsDTO("Location", "location");

//        FieldDetailsDTO serviceLine = new FieldDetailsDTO("Service Line", "serviceLine");

//        FieldDetailsDTO secondaryReptManagerDept = new FieldDetailsDTO("Secondary Rept. Manager Dept.", "secondaryReptManagerDept");

		FieldDetailsDTO secondaryReptManager = new FieldDetailsDTO("Secondary Rept. Manager", "secondaryReptManager");

		FieldDetailsDTO salary = new FieldDetailsDTO("Salary", "salary");

		FieldDetailsDTO xlAddress = new FieldDetailsDTO("Address", "xlAddress");

		list.add(salutation);
		list.add(firstName);
		list.add(middleName);
		list.add(lastName);
		list.add(gender);
		list.add(email);
		list.add(secondaryEmail);
		list.add(mobileNumber);
		list.add(phoneNumber);
		list.add(language);
		list.add(timeZone);
		list.add(dob);
		list.add(doj);
		list.add(designation);
//    list.add(role);
		list.add(country);
		list.add(department);
		list.add(reportingManager);
		list.add(roleType);
		list.add(countryDialCode);
		list.add(workplace);
		list.add(currency);
		list.add(employeeType);
		list.add(location);
//        list.add(serviceLine);
//        list.add(secondaryReptManagerDept);
		list.add(secondaryReptManager);
		list.add(salary);
		list.add(xlAddress);
		return list;
	}

}
