package com.app.employeePortal.immport.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.Opportunity.mapper.FieldDetailsDTO;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.immport.mapper.ExcelMapping;
import com.app.employeePortal.immport.service.ExcelImportService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@CrossOrigin(maxAge = 3600)
//@RequestMapping("excel")
public class ExcelImportController {

	@Autowired
	ExcelImportService excelImportService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("api/v1/import")
	public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile uploadfile,
			@RequestHeader("Authorization") String authorization) throws IOException, FileNotFoundException {
		String excelImportId = null;

		if (null != uploadfile && uploadfile.getSize() > 0) {

			File file = excelImportService.convert(uploadfile);

			long fileSize = file.length();

			System.out.println("file sizeeeee" + fileSize);
			if (fileSize <= 307200) {

				excelImportId = excelImportService.insertToExcelImport(file);

				return new ResponseEntity<String>(excelImportId, HttpStatus.OK);
			}

			else {

				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<String>("File does not exist", HttpStatus.OK);

		}

	}

	@GetMapping("/api/v1/header")
	/*
	 * public ResponseEntity<List<String>>
	 * getExcelHeaderKeys(@RequestHeader("Authorization") String authorization,
	 * 
	 * @RequestParam("excelId") String excelId)throws Exception{ return
	 * ResponseEntity.ok(excelImportService.getExcelSheetHeaderContent(excelId));
	 */
	public List<String> getExcelHeaderKeys(@RequestHeader("Authorization") String authorization,
			@RequestParam("excelId") String excelId) throws JSONException {
		List<String> headers = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			headers = excelImportService.getExcelSheetHeaderContent(excelId);

		}
		return headers;

	}

	@PostMapping("api/v1/candidate-excel-mapping")
	public int mapExcel(@RequestHeader("Authorization") String authorization,
			@RequestBody List<ExcelMapping> mappingList, @RequestParam("excelId") String excelId) throws Exception {

		String excelJson = excelImportService.getJsonMessageFromDb(excelId);
		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != excelJson) {

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

				List<String> keys = excelImportService.getAllKeys(jsonObj.toString());
				JsonElement jsonElement = jsonObj.get(keys.get(0));
				List<CandidateMapper> mapperList = new ArrayList<>();

				for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
					CandidateMapper candidateMapper = new CandidateMapper();
					AddressMapper addressMapper = new AddressMapper();
					// NoteMapper noteMapper = new NoteMapper();
					for (ExcelMapping excelMapping : mappingList) {
						String header = excelMapping.getExcelHeader();
						String mappingField = excelMapping.getMappingField();

						JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

						if (mappingField.equals("firstName")) {

							candidateMapper.setFirstName(value.getAsString());

						}
						if (mappingField.equals("salutation")) {
							candidateMapper.setSalutation(value.getAsString());
						}
						if (mappingField.equals("middleName")) {

							candidateMapper.setMiddleName(value.getAsString());

						}
						if (mappingField.equals("lastName")) {

							candidateMapper.setLastName(value.getAsString());

						}
						if (mappingField.equals("phoneNo")) {
							candidateMapper.setPhoneNumber(value.getAsString());

						}
						if (mappingField.equals("mobileNo")) {

							candidateMapper.setMobileNumber(value.getAsString());

						}
						if (mappingField.equals("emailId")) {

							candidateMapper.setEmailId(value.getAsString());

						}
						if (mappingField.equals("linkedinPublicUrl")) {

							candidateMapper.setLinkedin_public_url(value.getAsString());

						}
						// if (mappingField.equals("skypeId")) {

						// candidateMapper.setSkypeId(value.getAsString());

						// }
						if (mappingField.equals("designation")) {

							candidateMapper.setDesignation(value.getAsString());

						}
						// if (mappingField.equals("description")) {

						// candidateMapper.setDescription(value.getAsString());

						// }
						if (mappingField.equals("department")) {

							candidateMapper.setDepartmentId(value.getAsString());

						}
						if (mappingField.equals("departmentDetails")) {

							candidateMapper.setLanguage(value.getAsString());
						}

						if (mappingField.equals("language")) {

							candidateMapper.setLanguage(value.getAsString());

						}
						// if (mappingField.equals("contactOwner")) {

						// contactMapper.setContactOwner(jwtTokenUtil.getUserIdFromToken(authToken));

						// }
						if (mappingField.equals("dob")) {

							candidateMapper.setDateOfBirth(value.getAsString());

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
						// if (mappingField.equals("notes")) {

						// noteMapper.setDescription(value.getAsString());
						// }
					}
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					addressList.add(addressMapper);
					candidateMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					candidateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					// candidateMapper.setCreatorId(jwtTokenUtil.getUserIdFromToken(authToken));
					// candidateMapper.setContactOwner(jwtTokenUtil.getUserIdFromToken(authToken));
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

	@PostMapping("api/v1/import/task")
	public ResponseEntity<String> mapTask(@RequestBody FieldDetailsDTO fieldDetailsDTO,
			@RequestHeader("Authorization") String authorization) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				String id = excelImportService.insertTask(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		} else {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("api/v1/excel/import/leads")
	public ResponseEntity<String> insertLeads(@RequestHeader("Authorization") String authorization,
			@RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.insertLeads(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/category")
	public ResponseEntity<String> insertCategory(@RequestHeader("Authorization") String authorization,
			@RequestBody FieldDetailsDTO fieldDetailsDTO, @RequestParam(value = "type", required = true) String type)
			throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				if (type.equalsIgnoreCase("sector")) {
					String id = excelImportService.insertSector(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("source")) {
					String id = excelImportService.insertSource(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("taskType")) {
					String id = excelImportService.insertTaskType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("eventType")) {
					String id = excelImportService.insertEventType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("roleTypeExternal")) {
					String id = excelImportService.insertRoleTypeExternal(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("educationType")) {
					String id = excelImportService.insertEducationType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("documentType")) {
					String id = excelImportService.insertDocumentType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("designation")) {
					String id = excelImportService.insertDesignation(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("department")) {
					String id = excelImportService.insertDepartment(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("roleType")) {
					String id = excelImportService.insertRoleType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("libraryType")) {
					String id = excelImportService.insertLibraryType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("expenseType")) {
					String id = excelImportService.insertExpenseType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("shipBy")) {
					String id = excelImportService.insertShipBy(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("performanceManagement")) {
					String id = excelImportService.insertPerformanceManagement(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("payment")) {
					String id = excelImportService.insertPayment(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("nav")) {
					String id = excelImportService.insertNav(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("itemTask")) {
					String id = excelImportService.insertItemTask(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("investorCategory")) {
					String id = excelImportService.insertInvestorCategory(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("idProofType")) {
					String id = excelImportService.insertIdProofType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("customerType")) {
					String id = excelImportService.insertCustomerType(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("region")) {
					String id = excelImportService.insertRegion(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("serviceLine")) {
					String id = excelImportService.insertServiceLine(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				} else if (type.equalsIgnoreCase("lob")) {
					String id = excelImportService.insertLob(fieldDetailsDTO);
					return new ResponseEntity<String>(id, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/leadscategory")
	public ResponseEntity<String> insertLeadscategory(@RequestHeader("Authorization") String authorization,
			@RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.insertLeadsCategory(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/import/category")
	public ResponseEntity<String> importExcelForCategory(@RequestParam("file") MultipartFile uploadfile,
			@RequestParam String type, @RequestHeader("Authorization") String authorization)
			throws IOException, FileNotFoundException {
		String excelJson = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (null != uploadfile && uploadfile.getSize() > 0) {

				File file = excelImportService.convert(uploadfile);

				long fileSize = file.length();

				System.out.println("file sizeeeee" + fileSize);
				if (fileSize <= 307200) {

					excelJson = excelImportService.getExcelJsonData(file);

					if (null != type && !type.isEmpty()) {
						FieldDetailsDTO fieldDetailsDTO = new FieldDetailsDTO();
						fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
						fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
						fieldDetailsDTO.setExcelJson(excelJson);
						if (type.equalsIgnoreCase("sector")) {
							String id = excelImportService.insertSector(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("source")) {
							String id = excelImportService.insertSource(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("taskType")) {
							String id = excelImportService.insertTaskType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("eventType")) {
							String id = excelImportService.insertEventType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("roleTypeExternal")) {
							String id = excelImportService.insertRoleTypeExternal(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("educationType")) {
							String id = excelImportService.insertEducationType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("documentType")) {
							String id = excelImportService.insertDocumentType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("designation")) {
							String id = excelImportService.insertDesignation(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("department")) {
							String id = excelImportService.insertDepartment(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("roleType")) {
							String id = excelImportService.insertRoleType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("libraryType")) {
							String id = excelImportService.insertLibraryType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("expenseType")) {
							String id = excelImportService.insertExpenseType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("shipBy")) {
							String id = excelImportService.insertShipBy(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("performanceManagement")) {
							String id = excelImportService.insertPerformanceManagement(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("payment")) {
							String id = excelImportService.insertPayment(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("nav")) {
							String id = excelImportService.insertNav(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("itemTask")) {
							String id = excelImportService.insertItemTask(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("investorCategory")) {
							String id = excelImportService.insertInvestorCategory(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("idProofType")) {
							String id = excelImportService.insertIdProofType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("customerType")) {
							String id = excelImportService.insertCustomerType(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("region")) {
							String id = excelImportService.insertRegion(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("serviceLine")) {
							String id = excelImportService.insertServiceLine(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						} else if (type.equalsIgnoreCase("lob")) {
							String id = excelImportService.insertLob(fieldDetailsDTO);
							return new ResponseEntity<String>(id, HttpStatus.OK);
						}
					}
				}

				else {

					return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
				}

			} else {
				return new ResponseEntity<String>("File does not exist", HttpStatus.OK);

			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/customer")
	public ResponseEntity<String> importCustomer(@RequestHeader("Authorization") String authorization,
			@RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.importCustomer(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/Contact/{type}")
	public ResponseEntity<String> importContact(@PathVariable("type") String type,
			@RequestHeader("Authorization") String authorization, @RequestBody FieldDetailsDTO fieldDetailsDTO)
			throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.importContact(type, fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/investor")
	public ResponseEntity<String> insertInvestor(@RequestHeader("Authorization") String authorization,
			@RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.insertInvestor(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/investorLeads")
	public ResponseEntity<String> insertInvestorLeads(@RequestHeader("Authorization") String authorization,
												 @RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.insertInvestorLeads(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("api/v1/excel/import/Users/Employee")
	public ResponseEntity<String> insertEmployee(@RequestHeader("Authorization") String authorization,
													  @RequestBody FieldDetailsDTO fieldDetailsDTO) throws Exception {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (null != fieldDetailsDTO.getExcelId() && !fieldDetailsDTO.getExcelId().isEmpty()) {
				fieldDetailsDTO.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				fieldDetailsDTO.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				String id = excelImportService.insertEmployee(fieldDetailsDTO);
				return new ResponseEntity<String>(id, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("File size exceeds 300KB", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}