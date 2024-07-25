package com.app.employeePortal.contact.controller;

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
import org.springframework.cache.annotation.Cacheable;
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

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.contact.mapper.ContactDesignationMapper;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactTypeMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.service.ContactDesignationService;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.partner.service.PartnerService;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "contacts" })

public class ContactController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	ContactService contactService;
	@Autowired
	ContactDesignationService contactDesignationService;
	@Autowired
	PartnerService partnerService;

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/api/v1/contact")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> saveContact(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(contactMapper.getEmailId())) {
				boolean b = contactService.contactExistsByEmail(contactMapper);
				if (b) {
					map.put("contactInd", true);
					map.put("message", "Contact with same mailId already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					ContactViewMapper id = contactService.saveContact(contactMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
//			}else{
//				boolean b = contactService.CustomerContactExistsByFirstNameAndMiddleNameAndLastName(contactMapper.getFirstName(),contactMapper.getMiddleName(),contactMapper.getLastName());
//				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb"+b);
//				if (b == true) {
//					map.put("contactNameInd", true);
//					map.put("message", "Contact with same name already exists....");
//					return new ResponseEntity<>(map, HttpStatus.OK);
//				} else {
//					
//					ContactViewMapper id = contactService.saveContact(contactMapper);
//					return new ResponseEntity<>(id, HttpStatus.OK);
//
//				}
//			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/{contactId}")

	public ResponseEntity<?> getContactById(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ContactViewMapper contactMapper = contactService.getContactDetailsById(contactId);

			return new ResponseEntity<ContactViewMapper>(contactMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/user/{userId}/{pageNo}/{filter}")
//    @Cacheable( key = "#userId")
	public ResponseEntity<List<ContactViewMapper>> getContactListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			if(userId.equalsIgnoreCase("All")) {
//				List<ContactViewMapper> contactList  = contactService.getAllContatList(pageNo, pageSize);
//		    	Collections.sort(contactList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//		        return ResponseEntity.ok(contactList);
//			}else {

			List<ContactViewMapper> contactList = contactService.getFilterContactListByUserId(userId, pageNo, pageSize,
					filter);

			return ResponseEntity.ok(contactList);
//		   }
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/* fetch note list by contactId */
	@GetMapping("/api/v1/contact/notes/{contactId}")

	public ResponseEntity<?> getNoteListByContactId(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = contactService.getNoteListByContactId(contactId);
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

	@PutMapping("/api/v1/contact/{contactId}")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> updateContact(@PathVariable("contactId") String contactId,
			@RequestBody ContactMapper contactMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		ContactViewMapper newContactMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			newContactMapper = contactService.updateContact(contactId, contactMapper);
			return new ResponseEntity<ContactViewMapper>(newContactMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/contact/contact-type")
	public ResponseEntity<?> addContactType(@RequestBody ContactTypeMapper contactTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		System.out.println("inside add document controller...... ");

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			contactTypeMapper.setOrganizationId(loggedInUserOrgId);
			contactTypeMapper.setCreatorId(loggedInUserId);

			String contactid = contactService.addContactType(contactTypeMapper);

			return new ResponseEntity<>(contactid, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/contact-type")
	public ResponseEntity<?> getAllContactType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<ContactTypeMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			typeList = contactService.getContactTypesByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (null != typeList && !typeList.isEmpty()) {

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/contact/designation")
	public ResponseEntity<?> addContactDesignation(@RequestBody ContactDesignationMapper contactDesignationMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactDesignationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactDesignationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = contactDesignationService.addContactDesignation(contactDesignationMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* For getting contact designation in list */

	@GetMapping("/api/v1/contact/designation/{contactDesignationId}")

	public ResponseEntity<?> getContactDesignationById(
			@PathVariable("contactDesignationId") String contactDesignationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ContactDesignationMapper contactDesignationMapper = contactDesignationService
					.getContactDesignationByDesignationId(contactDesignationId);

			return new ResponseEntity<ContactDesignationMapper>(contactDesignationMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* For updating ContactDesignation */
	@PutMapping("/api/v1/contact/designation")

	public ResponseEntity<?> updateContactDesignation(@RequestBody ContactDesignationMapper contactDesignationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactDesignationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactDesignationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ContactDesignationMapper newDesginationMapper = contactDesignationService
					.updateContactDesignation(contactDesignationMapper);

			return new ResponseEntity<ContactDesignationMapper>(newDesginationMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/*
	 * For Deleting Contact Designation
	 */

	@DeleteMapping("/api/v1/contact/designation")
	public ResponseEntity<?> deleteContactDesignation(@PathVariable("contactDesignationId") String contactDesignationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = contactDesignationService.deleteContactDesignation(contactDesignationId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/contact/notes")
	public ResponseEntity<?> createContactNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = contactService.saveContactNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/document/{contactId}")

	public ResponseEntity<?> getContactListByContactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> contactList = contactService.getContactDocumentListByContactId(contactId);
			if (null != contactList && !contactList.isEmpty()) {
				contactList.sort(
						(DocumentMapper m1, DocumentMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(contactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/contact/document/{documentId}")
	public ResponseEntity<?> deleteContactDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			contactService.deleteDocumentById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/Name/{name}")
	public ResponseEntity<?> getContactListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		List<ContactViewMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = contactService.getContactListByName(name);

			if (null != list && !list.isEmpty()) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}

		}
		return null;

	}

	@GetMapping("/api/v1/contact/Partner/name/{name}")
	public ResponseEntity<?> getPartnerContactListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		List<ContactViewMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			list = contactService.getPartnerContactListByName(name);

			if (null != list && !list.isEmpty()) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("contFact list is empty", HttpStatus.NOT_FOUND);
			}

		}
		return null;

	}

//	 @GetMapping("api/v1/contact/all-contacts/{pageNo}")
//	   // @Cacheable( key = "#all")
//	    public ResponseEntity<List<ContactViewMapper>> getAllContatList(@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		    int pageSize = 20;
//	    	List<ContactViewMapper> contactList  = contactService.getAllContatList(pageNo, pageSize);
//	    	Collections.sort(contactList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//	        return ResponseEntity.ok(contactList);
//	    }

	@GetMapping("api/v1/contact/all-Partnercontacts/{pageNo}")
	// @Cacheable( key = "#allPartner")
	public ResponseEntity<List<ContactViewMapper>> getAllPartnerContatList(@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		List<ContactViewMapper> contactList = contactService.getAllPartnerContatList(pageNo, pageSize);
		Collections.sort(contactList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(contactList);
	}

	@GetMapping("/api/v1/contact/record/count/{userId}/{type}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId, @PathVariable("type") String type) {

		/*
		 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) { String
		 * authToken = authorization.replace(TOKEN_PREFIX, "");
		 */
		if (type.equalsIgnoreCase("customer")) {
			return ResponseEntity.ok(contactService.getCountListByuserId(userId));
		} else if (type.equalsIgnoreCase("partner")) {

			return ResponseEntity.ok(partnerService.getPartnerContactCountByuserId(userId));
		}

		return null;
	}

	/*
	 * @PostMapping("/api/v1/contact/convert/user") public ResponseEntity<?>
	 * contactConvertToUser( @RequestBody UserPasswordRq userPasswordRq,
	 * 
	 * @RequestHeader("Authorization") String authorization) {
	 * 
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 * 
	 * String authToken = authorization.replace(TOKEN_PREFIX,""); String userId =
	 * jwtTokenUtil.getUserIdFromToken(authToken); String orgId =
	 * jwtTokenUtil.getOrgIdFromToken(authToken);
	 * userPasswordRq.setOrganizationId(orgId);
	 * userPasswordRq.setEmployeeId(userId);
	 * 
	 * String id = contactService.contactConvertToUser(userPasswordRq); return new
	 * ResponseEntity<>(id, HttpStatus.OK);
	 * 
	 * }
	 * 
	 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 * 
	 * }
	 */

	// @GetMapping("/api/v1/contact/")

	@PutMapping("/api/v1/contact/transfer/{userId}")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> contactList = contactService.updateTransferOneUserToAnother(userId, transferMapper);
			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/closer/{contactId}")
	public ResponseEntity<?> getContactCloserByContactId(@RequestHeader("Authorization") String authorization,
			@PathVariable("contactId") String contactId, HttpServletRequest request) throws Exception {

		ContactViewMapper resultMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			resultMapper = contactService.getContactCloserByContactId(contactId);

			if (null != resultMapper) {

				return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
			}

		}
		return null;

	}

	@GetMapping("/api/v1/contact/Email/user/{userId}")
	@Cacheable(key = "#userId")
	public ResponseEntity<List<ContactViewMapper>> getContactListByUserIdWhichEmailPresent(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapper> contactList = contactService.getContactListByUserIdWhichEmailPresent(userId);
			if (null != contactList && !contactList.isEmpty()) {
				Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return ResponseEntity.ok(contactList);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/user/{userId}")
	@Cacheable(key = "#userId")
	public ResponseEntity<?> getAllCustomerContactListByUserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapperForDropDown> contactList = contactService
					.getAllCustomerContactListByUserIdForDropDown(userId);
			if (null != contactList && !contactList.isEmpty()) {
				Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return ResponseEntity.ok(contactList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/contact/save")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> saveContactDirectly(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			ContactViewMapper id = contactService.saveContact(contactMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/partner/all-contact/user/{userId}")
	// @Cacheable( key = "#userId")
	public ResponseEntity<?> getPartnerContactListByUserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapperForDropDown> contactList = contactService
					.getPartnerContactListByUserIdForDropDown(userId);
			Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return ResponseEntity.ok(contactList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/partner/all-contact/list/organization/{orgId}")
	// @Cacheable( key = "#userId")
	public ResponseEntity<?> getAllPartnerContactListByOrgIdForDropDown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapperForDropDown> contactList = contactService
					.getAllPartnerContactListByOrgIdForDropDown(orgId);
			Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return ResponseEntity.ok(contactList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contactCreate/employee/all-employees")

	public ResponseEntity<?> getEmployeeListByOrgIdForContactCreate(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			List<EmployeeViewMapper> empList = employeeService.getEmployeeListByOrgIdForContactCreate(organizationId);
			// Collections.sort(empList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/added/count/date-wise/{userId}")
	public ResponseEntity<?> getAddedContactListCountByUserIdWithDateWise(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Double>> map = contactService.getAddedContactListCountByUserIdWithDateWise(userId,
					startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/Invester/all-contact/user/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getInvestorContactListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapper> contactList = contactService.getInvestorContactListByUserId(userId, pageNo,
					pageSize, filter);
			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/added/investor/count/date-wise/{userId}")
	public ResponseEntity<?> getInvestorAddedContactCountByUserIdWithDateWise(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Double>> map = contactService.getInvestorAddedContactCountByUserIdWithDateWise(userId,
					startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/investor/user/{userId}")
	@Cacheable(key = "#userId")
	public ResponseEntity<?> getAllInvestorContactListByUserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapperForDropDown> contactList = contactService
					.getAllInvestorContactListByUserIdForDropDown(userId);
			if (null != contactList && !contactList.isEmpty()) {
				Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return ResponseEntity.ok(contactList);
			} else {
				return ResponseEntity.ok(contactList);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/customer/record/count/{userId}")
	public ResponseEntity<?> getCustomerContactCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(contactService.getCustomerContactCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/Invester/search/{name}")
	public ResponseEntity<?> getInvestorContactListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("name") String name) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> list = new ArrayList<>();
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);

			list = contactService.getInvestorContactListByName(name, userId);
			System.out.println("search by name ==" + list.size());
			if (null == list || list.isEmpty()) {
				System.out.println("search by company ==" + list.size());
				list = contactService.getInvestorContactByCompany(name, userId);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	 @PutMapping("/api/v1/contact/note/update")
//		public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,@RequestHeader("Authorization") String authorization,
//				HttpServletRequest request) {
//			NotesMapper notesMapperr = null;
//			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//				String authToken = authorization.replace(TOKEN_PREFIX, "");
//				notesMapperr =contactService.updateNoteDetails(notesMapper);
//				return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//			}
//
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//		}

	@DeleteMapping("/api/v1/contact/note/{notesId}")
	public ResponseEntity<?> deleteContactNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			contactService.deleteContactNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/all/{pageNo}/{type}")
	public ResponseEntity<List<ContactViewMapper>> getContactListByOrgId(@PathVariable("pageNo") int pageNo,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ContactViewMapper> contactList = contactService.getContactListByOrgId(orgId, pageNo, pageSize, type);
			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/all/record/count/{orgId}/{type}")
	public ResponseEntity<?> getContactListCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId, @PathVariable("type") String type) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(contactService.getContactListCountByOrgId(orgId, type));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/activity/list/{contactId}")
	public ResponseEntity<?> getActivityListByInvestorId(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ActivityMapper> activityMapper = contactService.getActivityListByContactId(contactId);
			if (null != activityMapper && !activityMapper.isEmpty()) {
				Collections.sort(activityMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<Set<ContactViewMapper>> getTeamContactListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<ContactViewMapper> contactList = contactService.getTeamContactListByUserId(userId, pageNo, pageSize,
					filter);

			return ResponseEntity.ok(contactList);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/team/count/{userId}")
	public ResponseEntity<?> getTeamInvestorCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(contactService.getTeamContactCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/activity/record/{contactId}")
	public ResponseEntity<?> getActivityRecordByContactId(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(contactService.getActivityRecordByContactId(contactId));

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/api/v1/contact/teams/{userId}/{pageNo}")
	public ResponseEntity<List<ContactViewMapper>> getTeamContactListByUnderUserId(
			@PathVariable("userId") String userId, @PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ContactViewMapper> contactList = contactService.getTeamContactListByUnderUserId(userId, pageNo,
					pageSize);

			return ResponseEntity.ok(contactList);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/teams/count/{userId}")
	public ResponseEntity<?> getTeamContactCountByUnderUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(contactService.getTeamContactCountByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/contact/delete/{contactId}")
	public ResponseEntity<?> deleteContact(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			contactService.deleteContact(contactId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/contact/reinitiate/{contactId}")
	public ResponseEntity<?> reinitiateDeletedContactId(@PathVariable("contactId") String contactId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			return ResponseEntity.ok(contactService.reinitiateDeletedContactId(contactId, userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/contact/search/alltype/{name}/{type}/{contactType}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @PathVariable("contactType") String contactType,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<ContactViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = contactService.getContactDetailsByNameByOrgLevel(name, orgId, contactType);
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySectorInOrgLevel(name, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySourceInOrgLevel(name, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactByOwnerNameInOrgLevel(name, orgId, contactType);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = contactService.getContactDetailsByNameForTeam(name, userId, contactType);
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySectorForTeam(name, userId, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySourceForTeam(name, userId, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactByOwnerNameForTeam(name, userId, orgId, contactType);
				}
				return ResponseEntity.ok(list);
			} else if (type.equalsIgnoreCase("user")) {
				list = contactService.getContactDetailsByNameByUserId(name, userId, contactType);
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySectorAndByUserId(name, userId, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactBySourceAndByUserId(name, userId, orgId, contactType);
				}
				if (null == list || list.isEmpty()) {
					list = contactService.getContactByOwnerNameAndByUserId(name, userId, orgId, contactType);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
