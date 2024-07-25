package com.app.employeePortal.partner.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.mapper.PartnerMapperForDropDown;
import com.app.employeePortal.partner.mapper.PartnerSkillSetMapper;
import com.app.employeePortal.partner.mapper.PartnerWebsiteMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class PartnerController {

	@Autowired
	PartnerService partnerService;
	@Autowired
	ContactService contactService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	WebsiteRepository websiteRepository;

	@PostMapping("/api/v1/partner")
	public ResponseEntity<?> createPartner(@RequestBody PartnerMapper partnerMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
	
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) { 
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(partnerMapper.getEmail())) {
				boolean c = partnerService.emailExistsByWebsite(partnerMapper.getEmail());
				if (c == true) {
					map.put("partnerInd", true);
					map.put("message", "Partner with same mail already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
			partnerMapper.setChannel("Self");
			partnerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			partnerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			PartnerMapper id = partnerService.saveToPartnerProcess(partnerMapper);			
			 return new ResponseEntity<PartnerMapper>(id, HttpStatus.OK);
				}
			}else {
				map.put("message", "Please provide a mail ID");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/user/{userId}/{pageNo}")

	public ResponseEntity<?> getPartnerDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {

		int pageSize = 20;
		//List<PartnerMapper> partnerMapperList = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if(userId.equalsIgnoreCase("All")) {
				List<PartnerMapper> partnerMapperList  = partnerService.getAllPartnerList( pageNo, pageSize);
				Collections.sort(partnerMapperList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
			    //return ResponseEntity.ok(partnerList);
			}else {
				List<PartnerMapper>	partnerMapperList = partnerService.getPartnerDetailsListByUserId(userId, pageNo, pageSize);

			//if (null != partnerMapperList && !partnerMapperList.isEmpty()) {

				Collections.sort(partnerMapperList,
						(PartnerMapper p1, PartnerMapper p2) -> p2.getCreationDate().compareTo(p1.getCreationDate()));
				 return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
			//}
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/{partnerId}")

	public ResponseEntity<?> getPartnerById(@PathVariable("partnerId") String partnerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PartnerMapper partnerMapper = partnerService.getPartnerDetailsById(partnerId);

			return new ResponseEntity<PartnerMapper>(partnerMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/document/{partnerId}")

	public ResponseEntity<?> getDocumentListByPartnerId(@PathVariable("partnerId") String partnerId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = partnerService.getDocumentListByPartnerId(partnerId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(documentList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/partner/document/{documentId}")

	public ResponseEntity<?> deletePartnerDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			partnerService.deleteDocumentById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/partner/contact")
	public ResponseEntity<?> createPartnerNotes(@RequestBody ContactMapper contactMapper,
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
					ContactViewMapper mapper = partnerService.savePartnerContact(contactMapper);

					return new ResponseEntity<>(mapper, HttpStatus.OK);
				}
			}else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
//			}else {
//				boolean b = contactService.PartnerContactExistsByFirstNameAndMiddleNameAndLastName(contactMapper.getFirstName(),contactMapper.getMiddleName(),contactMapper.getLastName());
//				if (b == true) {
//					map.put("contactNameInd", true);
//					map.put("message", "Contact with same name already exists....");
//					return new ResponseEntity<>(map, HttpStatus.OK);
//				} else {
//					ContactViewMapper mapper = partnerService.savePartnerContact(contactMapper);
//
//					return new ResponseEntity<>(mapper, HttpStatus.OK);
//				}
//				}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/partner/notes")
	public ResponseEntity<?> createCustomerNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			// notesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = partnerService.savePartnerNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/contact/{partnerId}")

	public ResponseEntity<?> getContactListByPartnerId(@PathVariable("partnerId") String partnerId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> contactList = partnerService.getContactListByPartnerId(partnerId);

			Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/note/{partnerId}")

	public ResponseEntity<?> getNoteListByPartnerId(@PathVariable("partnerId") String partnerId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> noteList = partnerService.getNoteListByPartnerId(partnerId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(noteList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/partner/oppotunity")
	public ResponseEntity<?> createPartnerOppotunity(@RequestBody OpportunityMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			String id = partnerService.savePartnerOppotunity(opportunityMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/oppotunity/{partnerId}")

	public ResponseEntity<?> getOpportunityListByPartnerId(@PathVariable("partnerId") String partnerId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> opportunityList = partnerService.getOpportunityListByPartnerId(partnerId);
			Collections.sort(opportunityList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/Name/{partnerName}")
	public ResponseEntity<?> getPartnerListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("partnerName") String partnerName, HttpServletRequest request) throws Exception {

		List<PartnerMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			list = partnerService.getPartnerName(partnerName, userId);

			if (null == list || list.isEmpty()) {
				list = partnerService.getPartnerByBusinessRegistrationNumber(partnerName, userId);
			}

			if (null == list || list.isEmpty()) {
				list = partnerService.getPartnerByTaxRegistrationNumber(partnerName, userId);
			}
			if (null != list && !list.isEmpty()) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Partner list is empty", HttpStatus.NOT_FOUND);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/partner/{partnerId}")

	public ResponseEntity<?> updatePartner(@PathVariable("partnerId") String partnerId,
			@RequestBody PartnerMapper partnerMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		PartnerMapper partnerMapper1 = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			partnerMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			partnerMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			partnerMapper1 = partnerService.updatePartner(partnerId, partnerMapper);

			return new ResponseEntity<PartnerMapper>(partnerMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/partner/contact/{partnerId}")

	public ResponseEntity<?> updateContactListByPartnerId(@PathVariable("partnerId") String partnerId,
			HttpServletRequest request, @RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		ContactViewMapper contactViewMapper1 = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			contactViewMapper1 = partnerService.updateContactListByPartnerId(partnerId, contactMapper);
			// Collections.sort(contactList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactViewMapper1, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/all-contacts/{userId}/{pageNo}")
	// @Cacheable( key = "#userId")
	public ResponseEntity<List<ContactViewMapper>> getAllPartnerContatList(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization) {
		
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(userId.equalsIgnoreCase("All")) {
				List<ContactViewMapper> contactList  = contactService.getAllPartnerContatList(pageNo, pageSize);
		    	Collections.sort(contactList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
		        return ResponseEntity.ok(contactList);
			}else {
			List<ContactViewMapper> contactList = partnerService.getAllPartnerContatList(userId,pageNo, pageSize);
			 Collections.sort(contactList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return ResponseEntity.ok(contactList);
		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/partner/skillSet")
	public ResponseEntity<?> saveSkillSet(@RequestBody PartnerSkillSetMapper skillSetMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(skillSetMapper.getSkillName())) {
				boolean b = partnerService.checkSkillInCustomerSkillSet(skillSetMapper.getSkillName(),skillSetMapper.getPartnerId());
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String id = partnerService.saveSkillSet(skillSetMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/skill-set/{partnerId}")

	public ResponseEntity<?> getSkillSetById(@PathVariable("partnerId") String partnerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PartnerSkillSetMapper> skillSetMapper = partnerService.getSkillSetDetails(partnerId);
			Collections.sort(skillSetMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(skillSetMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/partner/all-partner/{pageNo}")

	public ResponseEntity<List<PartnerMapper>> getAllPartnerList(@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		List<PartnerMapper> partnerList = partnerService.getAllPartnerList(pageNo, pageSize);
		Collections.sort(partnerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(partnerList);
	}

	@DeleteMapping("/api/v1/partner/skilsset/{id}")
	public ResponseEntity<?> deleteSkilsset(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			partnerService.deleteSkilsset(id);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/partner/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(partnerService.getCountListByuserId(userId));
		}
		return null;

	}

	/*
	 * @GetMapping("/api/v1/partner/candidate/{partnerId}")
	 * 
	 * public ResponseEntity<?>
	 * getCandidateListByPartnerId(@PathVariable("partnerId") String
	 * partnerId,HttpServletRequest request,
	 * 
	 * @RequestHeader("Authorization") String authorization){
	 * 
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 * List<CandidateMapper> candidateList =
	 * partnerService.getCandidateListByPartnerId(partnerId); //
	 * Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
	 * m2.getCreationDate().compareTo(m1.getCreationDate()));
	 * 
	 * return new ResponseEntity<>(candidateList, HttpStatus.OK); } return new
	 * ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 * 
	 * 
	 * 
	 * }
	 */

	@PutMapping("/api/v1/partner/transfer/{userId}")
	@CacheEvict(value = "partner", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody PartnerMapper partnerMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		// List<PartnerMapper> partnerMapperNew = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			// String loggeduserId = jwtTokenUtil.getUserIdFromToken(authToken);

			List<String> partnerList = partnerService.updateTransferOneUserToAnother(userId, partnerMapper);
			// partnerMapperNew =
			// partnerService.getPartnerDetailsListByUserId(loggeduserId);
			return new ResponseEntity<>(partnerList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/partner/website")
	@CacheEvict(value = "partner", allEntries = true)
	public ResponseEntity<?> savePartnerThroughWebsite(@RequestBody PartnerWebsiteMapper partnerMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			
			if (!StringUtils.isEmpty(partnerMapper.getEmail())) {
				boolean c = partnerService.emailExistsByWebsite(partnerMapper.getEmail());
				if (c == true) {
					map.put("partnerInd", true);
					map.put("message", "Partner with same mail already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					partnerMapper.setChannel("Website");
					partnerMapper.setUserId(web.getUser_id());
					partnerMapper.setAssignedTo(web.getAssignToUserId());
					String id = partnerService.saveToPartnerProcessForWebsite(partnerMapper);
					map.put("ID", id);
					map.put("message", "Thank you for Registering .");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}else {
				map.put("message", "Please provide a mail ID");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@PutMapping("/api/v1/partner/acess/contact/{contactId}")
	public ResponseEntity<?> giveAccessContactToUser(@PathVariable("contactId") String contactId,
			@RequestBody ContactViewMapper contactMapper, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ContactViewMapper contactMapper1 = partnerService.giveAccessContactToUser(contactId, contactMapper);
			return new ResponseEntity<>(contactMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/partner/employee/create/all-employees")

	public ResponseEntity<?> getEmployeeListByOrgIdForPartnerCreate(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			List<EmployeeViewMapper> empList = employeeService.getEmployeeListByOrgIdForPartnerCreate(organizationId);
			// Collections.sort(empList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/partner/all/partner")
	public ResponseEntity<?> getAllPartnerListByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<PartnerMapper> partnerList = partnerService.getAllPartnerListByOrgId(loggedOrgId);
			Collections.sort(partnerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(partnerList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("api/v1/partner/all-partner")

	public ResponseEntity<List<PartnerMapperForDropDown>> getAllPartnerListForDropDown(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		List<PartnerMapperForDropDown> partnerList = partnerService.getAllPartnerListForDropDown();
		Collections.sort(partnerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(partnerList);
	}

	@GetMapping("/api/v1/partner/all-contacts/{userId}")
	// @Cacheable( key = "#userId")
	public ResponseEntity<List<ContactViewMapperForDropDown>> getAllPartnerContatListForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			List<ContactViewMapperForDropDown> contactList = partnerService.getAllPartnerContatListForDropDown(userId);
			 Collections.sort(contactList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return ResponseEntity.ok(contactList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/partner/delete/{partnerId}")
	public ResponseEntity<?> deleteAndReinstatePartnerByPartnerId( @RequestBody PartnerMapper partnerMapper,
			                                       @PathVariable("partnerId") String partnerId ,
			                                       @RequestHeader("Authorization") String authorization,
			                                       HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
		return ResponseEntity.ok(partnerService.deleteAndReinstatePartnerByPartnerId(partnerId,partnerMapper));
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
    
    @GetMapping("/api/v1/partner/deleted/partner/list/{userId}/{pageNo}")

	public ResponseEntity<?> getDeletedPartnerDetailsByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		int pageSize = 20;
		
		//List<PartnerMapper> partnerMapperList = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//			if(userId.equalsIgnoreCase("All")) {
//				List<PartnerMapper> partnerMapperList  = partnerService.getAllPartnerList( pageNo, pageSize);
//				Collections.sort(partnerMapperList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//				return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
//			    //return ResponseEntity.ok(partnerList);
//			}else {
			
				List<PartnerMapper> partnerMapperList = partnerService.getDeletedPartnerDetailsByUserId(userId, pageNo, pageSize);

			//if (null != partnerMapperList && !partnerMapperList.isEmpty()) {

				
				  Collections.sort(partnerMapperList, (PartnerMapper p1, PartnerMapper p2) ->
				  p2.getCreationDate().compareTo(p1.getCreationDate()));
				  return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
			//}
			//}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
    
    @GetMapping("api/v1/partner/all/partner/contact")

	public ResponseEntity<List<ContactViewMapperForDropDown>> getAllPartnerContactListForDropDown(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
    	if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
    	
		List<ContactViewMapperForDropDown> partnerContactList = partnerService.getAllPartnerContactListForDropDown();
		if(null!=partnerContactList) {
		Collections.sort(partnerContactList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(partnerContactList);
		}else {
			return ResponseEntity.ok(partnerContactList);
		}
    	}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
