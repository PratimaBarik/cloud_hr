package com.app.employeePortal.customer.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CampaignReqMapper;
import com.app.employeePortal.customer.mapper.CampaignRespMapper;
import com.app.employeePortal.customer.mapper.CustomerInvoiceMapper;
import com.app.employeePortal.customer.mapper.CustomerKeySkillMapper;
import com.app.employeePortal.customer.mapper.CustomerLobLinkMapper;
import com.app.employeePortal.customer.mapper.CustomerMapper;
import com.app.employeePortal.customer.mapper.CustomerNetflixMapper;
import com.app.employeePortal.customer.mapper.CustomerRecruitmentMapper;
import com.app.employeePortal.customer.mapper.CustomerReportMapper;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.CustomerSkillLinkMapper;
import com.app.employeePortal.customer.mapper.CustomerViewMapperForDropDown;
import com.app.employeePortal.customer.mapper.CustomerViewMapperrForMap;
import com.app.employeePortal.customer.mapper.InitiativeDetailsMapper;
import com.app.employeePortal.customer.mapper.InitiativeSkillMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.service.RecruitmentService;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "customers" })
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	RecruitmentService recruitmentService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	ContactService contactService;

	@Autowired
	EmployeeService employeeService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AesEncryptor encryptor;

	@PostMapping("/api/v1/customer")
	@CacheEvict(value = "customers", allEntries = true)
	public ResponseEntity<?> createCustomer(@RequestBody CustomerMapper customerMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			customerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			customerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(customerMapper.getUrl())) {
				boolean b = customerService.customerByUrl(customerMapper.getUrl());
				if (b == true) {
					map.put("customerInd", true);
					map.put("message", "Customer with same url already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					CustomerResponseMapper id = customerService.saveCustomer(customerMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				CustomerResponseMapper id = customerService.saveCustomer(customerMapper);
				return new ResponseEntity<>(id, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/{customerId}")
	public ResponseEntity<?> getCustomerDetailsById(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CustomerResponseMapper customerDetailsMapper = customerService.getCustomerDetailsById(customerId);

			return new ResponseEntity<CustomerResponseMapper>(customerDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/user/{userId}/{pageNo}/{filter}")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getCustomerDetailsByuserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<CustomerResponseMapper> customerDetailsList;
			if (userId.equalsIgnoreCase("All")) {
				String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
				customerDetailsList = customerService.getAllCustomerList(pageNo, pageSize, orgId);
//                Collections.sort(customerDetailsList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			} else if (userId.equalsIgnoreCase("team")) {
				String loggedUserId = jwtTokenUtil.getUserIdFromToken(authToken);
				customerDetailsList = customerService.getCustomerDetailsByUnderUserId(loggedUserId, pageNo, pageSize);
			} else {
				customerDetailsList = customerService.getFilterCustomerDetailsPageWiseByuserId(userId, pageNo, pageSize,
						filter);
			}
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/* fetch note list by customerId */
	@GetMapping("/api/v1/customer/note/{customerId}")

	public ResponseEntity<?> getNoteListByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = customerService.getNoteListByCustomerId(customerId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				notesMapper
						.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/contacts/{customerId}")
	public ResponseEntity<?> getContactListByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> customerDetailsList = customerService.getContactListByCustomerId(customerId);
			if (null != customerDetailsList && !customerDetailsList.isEmpty()) {
				Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/customer/{customerId}")
	@CacheEvict(value = "customers", allEntries = true)
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") String customerId,
			@RequestBody CustomerMapper customerMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			customerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			customerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			CustomerResponseMapper newCustomerMapper = customerService.updateCustomer(customerId, customerMapper);

			return new ResponseEntity<CustomerResponseMapper>(newCustomerMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/customer/notes")
	public ResponseEntity<?> createCustomerNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = customerService.saveCustomerNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/customer/contact")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> createCustomerNotes(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(contactMapper.getEmailId())) {
				boolean b = contactService.contactExistsByEmail(contactMapper);
				if (b == true) {
					map.put("contactInd", true);
					map.put("message", "Contact with same mailId already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					ContactViewMapper id = customerService.saveCustomerContact(contactMapper);

					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/document/{customerId}")

	public ResponseEntity<?> getContactListByContactId(@PathVariable("customerId") String customerId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = customerService.getDocumentListByCustomerId(customerId);
			if (null != documentList && !documentList.isEmpty()) {
				documentList.sort(
						(DocumentMapper m1, DocumentMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(documentList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(documentList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/customer/document/{documentId}")

	public ResponseEntity<?> deleteCustomerDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			customerService.deleteDocumentsById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/customer/opportunity")
	public ResponseEntity<?> createOpportunity(@RequestBody OpportunityMapper OpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			OpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityViewMapper opportunityId = customerService.saveCustomerOpportunity(OpportunityMapper);

			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/opportunity/{customerId}")

	public ResponseEntity<List<OpportunityViewMapper>> getOpportunityListByCustomerId(
			@PathVariable("customerId") String customerId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = customerService.getOpportunityListByCustomerId(customerId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/Name/{name}")
	public ResponseEntity<?> getCustomerListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		List<CustomerResponseMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			list = customerService.getCustomerListByName(name);

			if (null != list && !list.isEmpty()) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}

		}
		return null;

	}
//
//    @GetMapping("api/v1/customer/all-customer/{pageNo}")
//    // @Cacheable( key = "#all-customer")
//    public ResponseEntity<List<CustomerResponseMapper>> getCustomerList(@PathVariable("pageNo") int pageNo,
//                                                                        @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//        int pageSize = 20;
//        
//        String authToken = authorization.replace(TOKEN_PREFIX, "");
//        String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
//        
//        List<CustomerResponseMapper> customerList = customerService.getAllCustomerList(pageNo, pageSize,orgId);
//        Collections.sort(customerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
//        return ResponseEntity.ok(customerList);
//    }

	@GetMapping("/api/v1/customer/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId,HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity.ok(customerService.getCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/all/record/count/{orgId}")
	public ResponseEntity<?> getCustomerListCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId,HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(customerService.getCustomerListCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/customer/opportunity/{opportunityId}")

	public ResponseEntity<?> updateCustomerOpportunity(@PathVariable("opportunityId") String opportunityId,
			@RequestBody OpportunityMapper opportunityViewMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			opportunityViewMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// opportunityViewMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			OpportunityViewMapper newOpportunityViewMapper = customerService.updateCustomerOpportunity(opportunityId,
					opportunityViewMapper);

			return new ResponseEntity<OpportunityViewMapper>(newOpportunityViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/link/recruitment/customer")
	public ResponseEntity<String> requirementCustomer(@RequestBody CustomerRecruitmentMapper customerRecruitmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			customerRecruitmentMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			customerRecruitmentMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			String recruitmentId = customerService.saverequirementCustomer(customerRecruitmentMapper);

			return new ResponseEntity<String>(recruitmentId, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/link/recruitment/customer/{customerId}")
	public List<CustomerRecruitmentMapper> getRecruitmentOfCustomer(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<CustomerRecruitmentMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			list = customerService.getCustomerRecriutmentListByCustomerId(customerId);

			System.out.println("opportunity list is......." + list);

		}

		return list;

	}

	@PutMapping("/api/v1/link/recruitment/customer")
	public ResponseEntity<?> updateRequirementCustomer(@RequestBody CustomerRecruitmentMapper customerRecruitmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			customerRecruitmentMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			CustomerRecruitmentMapper customerRecruitmentMapperNew = customerService
					.updateRequirementCustomer(customerRecruitmentMapper);

			return new ResponseEntity<CustomerRecruitmentMapper>(customerRecruitmentMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/categoryName/{category}")
	public ResponseEntity<?> getCustomerListByCategory(@PathVariable("category") String category,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<CustomerResponseMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);

			typeList = customerService.getCustomerListByCategory(category, userId);

			if (null != typeList && !typeList.isEmpty()) {

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(typeList, HttpStatus.OK);
	}

	@GetMapping("/api/v1/customer/record/count/categoryName/{category}")
	public ResponseEntity<?> getCountListByCategory(@RequestHeader("Authorization") String authorization,
			@PathVariable("category") String category) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			return ResponseEntity.ok(customerService.getCountListByCategory(category, userId));

		}
		return null;

	}

	@GetMapping("/api/v1/contact/record/count/customer/{userId}")
	public ResponseEntity<?> getCountNoOfCustomerByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity.ok(customerService.getCountNoOfCustomerByUserId(userId));

		}

		return null;

	}

	@PostMapping("/api/v1/invoice/customer")
	public ResponseEntity<String> invoiceCustomer(@RequestBody CustomerInvoiceMapper customerInvoiceMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			// customerInvoiceMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// customerInvoiceMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			String id = customerService.saveinvoiceCustomer(customerInvoiceMapper);

			return new ResponseEntity<String>(id, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/invoice/{customerId}")

	public ResponseEntity<List<CustomerInvoiceMapper>> getInvoiceListByCustomerId(
			@PathVariable("customerId") String customerId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CustomerInvoiceMapper> oppList = customerService.getInvoiceListByCustomerId(customerId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/customer/transfer/{userId}")
	@CacheEvict(value = "customers", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> customerList = customerService.updateTransferOneUserToAnother(userId, transferMapper);
			return new ResponseEntity<>(customerList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/search/{name}")
	public ResponseEntity<?> getCustomerByNameAndSector(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization) {

		List<CustomerResponseMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = customerService.getCustomerDetailsByName(name);

			if (null == list || list.isEmpty()) {
				System.out.println("insert to the if");
				list = customerService.getCustomerDetailsBySector(name);
			}
			if (null == list || list.isEmpty()) {
				list = customerService.getCustomerDetailsByCountry(name);
			}
			if (null == list || list.isEmpty()) {
				list = customerService.getCustomerDetailsByOwnerName(name);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/customer/initiative")
	public ResponseEntity<?> createCustomerInitiative(@RequestBody InitiativeDetailsMapper initiativeDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			initiativeDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			initiativeDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = customerService.saveCustomerInitiative(initiativeDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/initiative/{customerId}")

	public ResponseEntity<?> getInitiativeListByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InitiativeDetailsMapper> initiativeDetailsMapper = customerService
					.getInitiativeListByCustomerId(customerId);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(initiativeDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/KeySkil/{customerId}")

	public ResponseEntity<?> getKeySkilListByCustomerIdAndDateRange(@PathVariable("customerId") String customerId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			CustomerKeySkillMapper customerKeySkillMapper = customerService
					.getKeySkilListByCustomerIdAndDateRange(customerId, orgId, startDate, endDate);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerKeySkillMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/open/recuitment/{customerId}")
	public ResponseEntity<?> getOpenRecuitmentByCustomerId(@RequestHeader("Authorization") String authorization,
			@PathVariable("customerId") String customerId) {

		List<RecruitmentOpportunityMapper> customerList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			customerList = recruitmentService.getOpenRecuitmentByCustomerId(customerId, orgId);
			Collections.sort(customerList, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2
					.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/initiative/skill/{initiativeDetailsId}")

	public ResponseEntity<?> getSkillListByInitiativeDetailsId(
			@PathVariable("initiativeDetailsId") String initiativeDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InitiativeSkillMapper> initiativeDetailsMapper = customerService
					.getSkillListByInitiativeDetailsId(initiativeDetailsId);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(initiativeDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/initiative/skill/search/{userId}")
	public ResponseEntity<?> getInitiativeSkillListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InitiativeDetailsMapper> initiativeDetailsMapper = customerService
					.getInitiativeSkillListByUserId(userId);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(initiativeDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/initiative/name/{userId}")
	public ResponseEntity<?> getInitiativeNameByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InitiativeDetailsMapper> initiativeDetailsMapper = customerService.getInitiativeNameByUserId(userId);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(initiativeDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/customer/initiative/{initiativeDetailsId}")

	public ResponseEntity<?> updateInitiativeDetails(@RequestBody InitiativeDetailsMapper initiativeDetailsMapper,
			@PathVariable("initiativeDetailsId") String initiativeDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		InitiativeDetailsMapper initiativeDetailsMapperr = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			initiativeDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			initiativeDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			initiativeDetailsMapperr = customerService.updateInitiativeDetails(initiativeDetailsId,
					initiativeDetailsMapper);
			// customerMapperNew = customerService.getCustomerDetailsByuserId(loggeduserId);
			return new ResponseEntity<>(initiativeDetailsMapperr, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/sort/alphabet/{userId}")
	public ResponseEntity<?> getTopCustomerByAlphabet(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		List<CustomerNetflixMapper> customerList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			customerList = recruitmentService.getAlphabetOrderCustomersByUserId(userId);

			return new ResponseEntity<>(customerList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/sort/position/{userId}")
	public ResponseEntity<?> getTopCustomerByPosition(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		List<CustomerNetflixMapper> customerList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			customerList = recruitmentService.getCustomersPositionUserId(userId);
			return new ResponseEntity<>(customerList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/sort/closer/{userId}")
	public ResponseEntity<?> getTopCustomerByCloser(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws Exception {

		List<CustomerNetflixMapper> customerList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			customerList = recruitmentService.getCustomerCloserByUserIdAndDateRange(userId, startDate, endDate);
			return new ResponseEntity<>(customerList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/all/sort/{userId}")
	public ResponseEntity<?> getAllsorttedCustomers(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "sort", defaultValue = "alphabet", required = false) String sort,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws Exception {

		List<CustomerNetflixMapper> customerList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (null != startDate && null != endDate && !startDate.isEmpty() && !endDate.isEmpty()) {

				customerList = recruitmentService.getAllCustomerCloserByUserIdAndDateRange(userId, startDate, endDate);

				return new ResponseEntity<>(customerList, HttpStatus.OK);

			} else {
				if (sort.equalsIgnoreCase("alphabet")) {
					customerList = recruitmentService.getAllCustomerAlphabetByUserId(userId);
					return new ResponseEntity<>(customerList, HttpStatus.OK);
				} else if (sort.equalsIgnoreCase("position")) {
					customerList = recruitmentService.getAllCustomersPositonByUserId(userId);
					return new ResponseEntity<>(customerList, HttpStatus.OK);
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/customer/skillSet")
	// @CacheEvict(value = "customer", allEntries = true)
	public ResponseEntity<?> saveSkillSet(@RequestBody CustomerSkillLinkMapper customerSkillLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(customerSkillLinkMapper.getSkillName())) {
				boolean b = customerService.checkSkillInCustomerSkillSet(customerSkillLinkMapper.getSkillName(),
						customerSkillLinkMapper.getCustomerId());
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			customerSkillLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			customerSkillLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = customerService.saveSkillSet(customerSkillLinkMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/skillSet/{customerId}")

	public ResponseEntity<?> getSkillSetByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerSkillLinkMapper> customerSkillLinkMapper = customerService.getSkillSetByCustomerId(customerId);
			Collections.sort(customerSkillLinkMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerSkillLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/customer/skillsset/{customerSkillLinkId}")
	public ResponseEntity<?> deleteSkillsSet(@PathVariable("customerSkillLinkId") String customerSkillLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			customerService.deleteSkillsSet(customerSkillLinkId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);

		}

		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/employee/create/all-employees")

	public ResponseEntity<?> getEmployeeListByOrgIdForCustomerCreate(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			List<EmployeeViewMapper> empList = employeeService.getEmployeeListByOrgIdForCustomerCreate(organizationId);
			// Collections.sort(empList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/customer/initiative/{initiativeDetailsId}")
	public ResponseEntity<?> deleteCustomerInnitiative(@PathVariable("initiativeDetailsId") String initiativeDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			customerService.deleteCustomerInnitiative(initiativeDetailsId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);

		}

		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/user/{userId}")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getCustomerDetailsByuserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerViewMapperForDropDown> customerDetailsList = customerService
					.getCustomerDetailsByuserIdForDropDown(userId);
			if (null != customerDetailsList && !customerDetailsList.isEmpty()) {
				Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/map/user/{userId}")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getCustomerDetailsByuserIdForMap(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerViewMapperrForMap> customerDetailsList = customerService
					.getCustomerDetailsByuserIdForMap(userId);
			Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/customer-list/{orgId}")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getCustomerDetailsByOrgIdForDropDown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerViewMapperForDropDown> customerDetailsList = customerService
					.getCustomerDetailsByOrgIdForDropDown(orgId);
			Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/oppertunity/count/{customerId}")
	public ResponseEntity<?> getCustomerOppertunityCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerService.getCustomerOppertunityCountByCustomerId(customerId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/contact/count/{customerId}")
	public ResponseEntity<?> getCustomerContactCountByCustomerId(@RequestHeader("Authorization") String authorization,
			@PathVariable("customerId") String customerId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerService.getCustomerContactCountByCustomerId(customerId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/document/count/{customerId}")
	public ResponseEntity<?> getCustomerDocumentCountByCustomerId(@RequestHeader("Authorization") String authorization,
			@PathVariable("customerId") String customerId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerService.getCustomerDocumentCountByCustomerId(customerId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/source-wise/count/{userId}")
	public ResponseEntity<?> getCustomerCountSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, Double>> map = customerService.getCustomerCountSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/source-wise/list/{userId}")
	public ResponseEntity<?> getCustomerListSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, List<CustomerResponseMapper>>> map = customerService
					.getCustomerListSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/drop/customer-list/{userId}")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getCustomerDetailsByUserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerViewMapperForDropDown> customerDetailsList = customerService
					.getCustomerDetailsByUserIdForDropDown(userId);
			if (null != customerDetailsList && !customerDetailsList.isEmpty()) {
				Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/activity/list/{customerId}")
	public ResponseEntity<?> getActivityListByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ActivityMapper> activityMapper = customerService.getActivityListByCustomerId(customerId);
			if (null != activityMapper && !activityMapper.isEmpty()) {
				Collections.sort(activityMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//    @PutMapping("/api/v1/customer/note/update")
//    public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper, @RequestHeader("Authorization") String authorization,
//                                               HttpServletRequest request) {
//        NotesMapper notesMapperr = null;
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            notesMapperr = customerService.updateNoteDetails(notesMapper);
//            return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }

	@DeleteMapping("/api/v1/customer/note/{notesId}")
	public ResponseEntity<?> deleteCustomerNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			customerService.deleteCustomerNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/all/{pageNo}/{filter}")
	public ResponseEntity<?> getCustomerListByOrgId(@PathVariable("pageNo") int pageNo,
			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CustomerResponseMapper> customerViewMapper = customerService.getCustomerListByOrgId(orgId, pageNo,
					pageSize, filter);
			if (null != customerViewMapper && !customerViewMapper.isEmpty()) {
//                investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(customerViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getTeamCustomerDetailsByuserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            if (userId.equalsIgnoreCase("All")) {
//                List<CustomerResponseMapper> customerList = customerService.getAllCustomerList(pageNo, pageSize);
//                Collections.sort(customerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
//                return ResponseEntity.ok(customerList);
//            } else {
			Set<CustomerResponseMapper> customerDetailsList = customerService
					.getTeamCustomerDetailsPageWiseByuserId(userId, pageNo, pageSize, filter);
			// Collections.sort(customerDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
//            }
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/team/count/{userId}")
	public ResponseEntity<?> getTeamCustomerCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(customerService.getTeamCustomerCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/customer/document/contract/update")
	public ResponseEntity<?> updateContractIndForDocument(@RequestBody DocumentMapper documentMapperr,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			DocumentMapper documentMapper = customerService.updateContractIndForDocument(documentMapperr);
			return new ResponseEntity<>(documentMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/oppertunity/proposal-value/count/{customerId}")
	public ResponseEntity<?> getCustomerOppertunityProposalValueCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(
					customerService.getCustomerOppertunityProposalValueCountByCustomerId(customerId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/oppertunity/weighted-value/count/{customerId}")
	public ResponseEntity<?> getCustomerOppertunityWeightedValueCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(
					customerService.getCustomerOppertunityWeightedValueCountByCustomerId(customerId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/activity/record/{customerId}")
	public ResponseEntity<?> getActivityRecordByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(customerService.getActivityRecordByCustomerId(customerId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/won-oppertunity/count/{customerId}")
	public ResponseEntity<?> getCustomerWonOppertunityCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerService.getCustomerWonOppertunityCountByCustomerId(customerId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/won-oppertunity/proposal-value/count/{customerId}")
	public ResponseEntity<?> getCustomerWonOppertunityProposalValueCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(
					customerService.getCustomerWonOppertunityProposalValueCountByCustomerId(customerId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/won-oppertunity/weighted-value/count/{customerId}")
	public ResponseEntity<?> getCustomerWonOppertunityWeightedValueCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(
					customerService.getCustomerWonOppertunityWeightedValueCountByCustomerId(customerId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/report/all-customer/enterprise/{orgId}")
	public ResponseEntity<?> getAllInvestorByOrgIdForReport(@PathVariable("orgId") String orgId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CustomerReportMapper> investorList = customerService.getAllCustomerByOrgIdForReport(orgId, startDate,
					endDate);
			if (null != investorList && !investorList.isEmpty()) {
				investorList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/report/all-customer/self/{userId}")
	public ResponseEntity<?> getAllInvestorByUserIdForReport(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CustomerReportMapper> investorList = customerService.getAllCustomerByUserIdForReport(userId, startDate,
					endDate);
			if (null != investorList && !investorList.isEmpty()) {
				investorList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/report/all-customer-contact/enterprise/{orgId}")
	public ResponseEntity<?> getAllCustomerContactListByOrgIdForReport(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactReportMapper> contactList = customerService.getAllCustomerContactListByOrgIdForReport(orgId,
					startDate, endDate);
			if (null != contactList && !contactList.isEmpty()) {
				contactList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/report/all-customer-contact/self/{userId}")
	public ResponseEntity<?> getAllCustomerContactListByUserIdForReport(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactReportMapper> contactList = customerService.getAllCustomerContactListByUserIdForReport(userId,
					startDate, endDate);
			if (null != contactList && !contactList.isEmpty()) {
				contactList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/count/{country}")
	public ResponseEntity<?> getCustomerCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(customerService.getCustomerCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/country/list/{country}")
	public ResponseEntity<?> getCustomerListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<CustomerResponseMapper> list = customerService.getCustomerListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/campaign/{customerId}")
	public ResponseEntity<?> getCampaign(@RequestHeader("Authorization") String authorization,
			@PathVariable("customerId") String customerId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CampaignRespMapper> list = customerService.getCampaign(customerId);
			if (null != list && !list.isEmpty()) {
				list.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/customer/campaign/save")
	public ResponseEntity<?> createCampaign(@RequestBody CampaignReqMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			CampaignRespMapper responseMapper = customerService.createCampaign(requestMapper);

			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getCustomerDetailsByUnderUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerResponseMapper> customerDetailsList = customerService.getCustomerDetailsByUnderUserId(userId,
					pageNo, pageSize);
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/teams/count/{userId}")
	public ResponseEntity<?> getCountCustomerDetailsByUnderUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(customerService.getCountCustomerDetailsByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/contact/drop/{customerId}")
	public ResponseEntity<?> getAllCustomerContactListByCustomerIdForDropDown(
			@PathVariable("customerId") String customerId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapperForDropDown> contactList = customerService
					.getAllCustomerContactListByCustomerIdForDropDown(customerId);

			return ResponseEntity.ok(contactList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/won/opportunity/{customerId}")

	public ResponseEntity<List<OpportunityViewMapper>> getWonOpportunityListByCustomerId(
			@PathVariable("customerId") String customerId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = customerService.getWonOpportunityListByCustomerId(customerId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/customer/lob/{customerId}")
	public ResponseEntity<?> getDistributorLObListByDistributorId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CustomerLobLinkMapper> resultList = customerService.getDistributorLObListByDistributorId(customerId,
					loggedInOrgId);
			return ResponseEntity.ok(resultList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/customer/lob/createOrUpdate")
	public ResponseEntity<CustomerLobLinkMapper> createOrUpdateLOb(
			@RequestBody CustomerLobLinkMapper customerLobLinkMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			CustomerLobLinkMapper result = customerService.createOrUpdateLOb(customerLobLinkMapper);
			return ResponseEntity.ok(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/customer/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CustomerResponseMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = customerService.getCustomerDetailsByNameByOrgLevel(name, orgId);
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySectorInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySourceInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerByOwnerNameInOrgLevel(name, orgId);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = customerService.getCustomerDetailsByNameForTeam(name, userId);
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySectorForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySourceForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerByOwnerNameForTeam(name, userId, orgId);
				}
				return ResponseEntity.ok(list);
			} else {
				list = customerService.getCustomerDetailsByNameByUserId(name, userId);
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySectorAndByUserId(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerBySourceAndByUserId(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = customerService.getCustomerByOwnerNameAndByUserId(name, userId, orgId);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
