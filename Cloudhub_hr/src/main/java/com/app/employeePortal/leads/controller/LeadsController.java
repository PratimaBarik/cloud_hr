package com.app.employeePortal.leads.controller;

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
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.InitiativeDetailsMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.mapper.LeadsMapper;
import com.app.employeePortal.leads.mapper.LeadsOpportunityMapper;
import com.app.employeePortal.leads.mapper.LeadsReportMapper;
import com.app.employeePortal.leads.mapper.LeadsSkillLinkMapper;
import com.app.employeePortal.leads.mapper.LeadsViewMapper;
import com.app.employeePortal.leads.service.LeadsService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
//@CacheConfig(cacheNames = { "leads" })
public class LeadsController {

	@Autowired
	LeadsService leadsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ContactService contactService;
	@Autowired
	WebsiteRepository websiteRepository;

	@PostMapping("/api/v1/leads")
	// @CacheEvict(value = "leads", allEntries = true)
	public ResponseEntity<?> createLeads(@RequestBody LeadsMapper leadsMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			leadsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			leadsMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(leadsMapper.getEmail())) {
				boolean b = leadsService.getLeadsByEmail(leadsMapper.getEmail());
				if (b == true) {
					map.put("LeadInd", true);
					map.put("message", "Lead with same Email already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					if (!StringUtils.isEmpty(leadsMapper.getUrl())) {
						boolean b1 = leadsService.getLeadsByUrl(leadsMapper.getUrl());
						if (b1 == true) {
							map.put("LeadInd", true);
							map.put("message", "Lead with same url already exists....");
							return new ResponseEntity<>(map, HttpStatus.OK);
						} else {
							LeadsViewMapper id = leadsService.saveLeads(leadsMapper);
							return new ResponseEntity<>(id, HttpStatus.OK);
						}
					} else {
						LeadsViewMapper id = leadsService.saveLeads(leadsMapper);
						return new ResponseEntity<>(id, HttpStatus.OK);
					}
				}

			} else {
				if (!StringUtils.isEmpty(leadsMapper.getUrl())) {
					boolean b1 = leadsService.getLeadsByUrl(leadsMapper.getUrl());
					if (b1 == true) {
						map.put("LeadInd", true);
						map.put("message", "Lead with same url already exists....");
						return new ResponseEntity<>(map, HttpStatus.OK);
					} else {
						LeadsViewMapper id = leadsService.saveLeads(leadsMapper);
						return new ResponseEntity<>(id, HttpStatus.OK);
					}
				} else {
					LeadsViewMapper id = leadsService.saveLeads(leadsMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/{leadsId}")
	public ResponseEntity<?> getLeadsDetailsById(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			LeadsViewMapper leadsViewMapper = leadsService.getLeadsDetailsById(leadsId);

			return new ResponseEntity<LeadsViewMapper>(leadsViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/{leadsId}")
	// @CacheEvict(value = "leads", allEntries = true)
	public ResponseEntity<?> updateLeads(@PathVariable("leadsId") String leadsId, @RequestBody LeadsMapper leadsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			leadsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			leadsMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			LeadsViewMapper leadsViewMapper = leadsService.updateLeads(leadsId, leadsMapper);

			return new ResponseEntity<LeadsViewMapper>(leadsViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/leads/{leadsId}")
	public ResponseEntity<?> deleteLeads(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			leadsService.deleteLeads(leadsId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/convert/{leadsId}/{assignedToId}")
	public ResponseEntity<?> convertLeadsById(@PathVariable("leadsId") String leadsId,
			@PathVariable("assignedToId") String assignedToId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			String msg = leadsService.convertLeadsById(leadsId, assignedToId);

			return new ResponseEntity<String>(msg, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/User/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getLeadsDetailsByUserIdPagging(userId, pageNo,
					pageSize, filter);

			if (null != leadsViewMapper) {
				// Collections.sort(leadsViewMapper, ( m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/leads/contact")
	// @CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> createLeadsContact(@RequestBody ContactMapper contactMapper,
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
					ContactViewMapper mapper = leadsService.saveleadsContact(contactMapper);

					return new ResponseEntity<>(mapper, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/contacts/{leadsId}")
	public ResponseEntity<?> getContactListByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> customerDetailsList = leadsService.getContactListByLeadsId(leadsId);
			Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/leads/contact/update/{contactId}")
	public ResponseEntity<?> updateContactDetails(@RequestBody ContactMapper contactMapper,
			@PathVariable("contactId") String contactId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {
		ContactViewMapper contactMapperr = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			contactMapperr = leadsService.updateContactDetails(contactId, contactMapper);
			return new ResponseEntity<>(contactMapperr, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/leads/notes")
	public ResponseEntity<?> createLeadsNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = leadsService.saveLeadsNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/note/{leadsId}")

	public ResponseEntity<?> getNoteListByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = leadsService.getNoteListByLeadsId(leadsId);
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

//	@PutMapping("/api/v1/leads/note/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = leadsService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@GetMapping("/api/v1/leads/document/{leadsId}")
	public ResponseEntity<?> getDocumentListByLeadsId(@PathVariable("leadsId") String leadsId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = leadsService.getDocumentListByLeadsId(leadsId);
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

	@DeleteMapping("/api/v1/leads/document/{documentId}")

	public ResponseEntity<?> deleteLeadsDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			leadsService.deleteDocumentsById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/leads/opportunity")
	public ResponseEntity<?> createOpportunity(@RequestBody LeadsOpportunityMapper leadsOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			leadsOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			leadsOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			LeadsOpportunityMapper id = leadsService.saveleadsOpportunity(leadsOpportunityMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/opportunity/{leadsId}")
	public ResponseEntity<?> getOpportunityListByLeadsId(@PathVariable("leadsId") String leadsId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeadsOpportunityMapper> opportunityList = leadsService.getOpportunityListByLeadsId(leadsId);
			if (null != opportunityList && !opportunityList.isEmpty()) {
				Collections.sort(opportunityList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(opportunityList, HttpStatus.OK);
			}
			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/opportunity/update/{opportunityId}")
	public ResponseEntity<?> updateLeadsOpportunity(@PathVariable("opportunityId") String opportunityId,
			@RequestBody LeadsOpportunityMapper leadsOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			leadsOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// opportunityViewMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			LeadsOpportunityMapper leadsOpportunityMapper1 = leadsService.updateLeadsOpportunity(opportunityId,
					leadsOpportunityMapper);

			return new ResponseEntity<>(leadsOpportunityMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/opportunity/delete/{leadOppId}")

	public ResponseEntity<?> deleteLeadsOppertunity(@PathVariable("leadOppId") String leadOppId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String message = leadsService.deleteLeadsOppertunity(leadOppId);

			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/leads/innitiative")
	public ResponseEntity<?> createLeadsInitiative(@RequestBody InitiativeDetailsMapper initiativeDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			initiativeDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			initiativeDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = leadsService.saveLeadsInitiative(initiativeDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/innitiative/{leadsId}")
	public ResponseEntity<?> getInitiativeListByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InitiativeDetailsMapper> initiativeDetailsMapper = leadsService.getInitiativeListByLeadsId(leadsId);
			// Collections.sort(notesMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(initiativeDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/leads/initiative/update/{initiativeDetailsId}")
	public ResponseEntity<?> updateInitiativeDetails(@RequestBody InitiativeDetailsMapper initiativeDetailsMapper,
			@PathVariable("initiativeDetailsId") String initiativeDetailsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		InitiativeDetailsMapper initiativeDetailsMapperr = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			initiativeDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			initiativeDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			initiativeDetailsMapperr = leadsService.updateInitiativeDetails(initiativeDetailsId,
					initiativeDetailsMapper);
			return new ResponseEntity<>(initiativeDetailsMapperr, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/leads/skillSet")
	public ResponseEntity<?> saveLeadsSkillSet(@RequestBody LeadsSkillLinkMapper leadsSkillLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			leadsSkillLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			leadsSkillLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = leadsService.saveLeadsSkillSet(leadsSkillLinkMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/skillSet/{leadsId}")

	public ResponseEntity<?> getSkillSetByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsSkillLinkMapper> leadsSkillLinkMapper = leadsService.getSkillSetByLeadsId(leadsId);
			Collections.sort(leadsSkillLinkMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(leadsSkillLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/leads/skillsset/{leadsSkillLinkId}")
	public ResponseEntity<?> deleteSkillsSet(@PathVariable("leadsSkillLinkId") String leadsSkillLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			leadsService.deleteSkillsSet(leadsSkillLinkId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);

		}

		return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/search/{name}")
	public ResponseEntity<?> getLeadsByNameAndSector(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization) {

		List<LeadsViewMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = leadsService.getLeadsDetailsByName(name);

			if (null == list || list.isEmpty()) {
				System.out.println("insert to the if");
				list = leadsService.getLeadsDetailsBySector(name);
			}
			if (null == list || list.isEmpty()) {
				list = leadsService.getLeadsDetailsByOwnerName(name);
			}
			if (null == list || list.isEmpty()) {
				list = leadsService.getLeadsDetailsByContactName(name);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/type/update/{leadsId}/{type}")
	public ResponseEntity<?> updateLeadsType(@PathVariable("leadsId") String leadsId, @PathVariable("type") String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = leadsService.updateLeadsType(leadsId, type);
			return new ResponseEntity<>(msg, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(leadsService.getCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leadsCreate/employee/all-employees")

	public ResponseEntity<?> getEmployeeListByOrgIdForLeadsCreate(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			List<EmployeeViewMapper> empList = employeeService.getEmployeeListByOrgIdForLeadsCreate(organizationId);
			if (null != empList) {
				Collections.sort(empList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(empList, HttpStatus.OK);
			}
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/qualified-leads/count/{userId}")
	public ResponseEntity<?> getQualifiedLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getQualifiedLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/qualified-leads/list/{userId}")
	public ResponseEntity<?> getQualifiedLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getQualifiedLeadsDetailsByUserId(userId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/leads/reinstate/transfer/{userId}")

	public ResponseEntity<?> ReinstateLeadToJunk(@PathVariable("userId") String userId,
			@RequestBody TransferMapper transferMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String message = leadsService.ReinstateLeadToJunk(userId, transferMapper);

			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/qualified-leads/count/for-org/{orgId}")
	public ResponseEntity<?> getQualifiedLeadsListCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getQualifiedLeadsListCountByOrgId(orgId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/qualified-leads/list/for-org/{orgId}")
	public ResponseEntity<?> getQualifiedLeadsDetailsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getQualifiedLeadsDetailsByOrgId(orgId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/createded-leads/count/{userId}")
	public ResponseEntity<?> getCreatededLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getCreatededLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/createded-leads/list/{userId}")
	public ResponseEntity<?> getCreatededLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getCreatededLeadsDetailsByUserId(userId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/Createded-leads/count/for-org/{orgId}")
	public ResponseEntity<?> getCreatededLeadsListCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getCreatededLeadsListCountByOrgId(orgId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/createded-leads/list/for-org/{orgId}")
	public ResponseEntity<?> getCreatededLeadsDetailsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getCreatededLeadsDetailsByOrgId(orgId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked/count/{userId}")
	public ResponseEntity<?> getJunkedLeadsCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getJunkedLeadsCountByUserId(userId);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked/list/{userId}")
	public ResponseEntity<?> getJunkedLeadsListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getJunkedLeadsListByUserId(userId);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked/count/for-org/{orgId}")
	public ResponseEntity<?> getJunkedLeadsCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getJunkedLeadsCountByOrgId(orgId);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked/list/for-org/{orgId}")
	public ResponseEntity<?> getJunkedLeadsListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getJunkedLeadsListByOrgId(orgId);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked-leads/count/{userId}")
	public ResponseEntity<?> getJunkedLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getJunkedLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked-leads/list/{userId}")
	public ResponseEntity<?> getJunkedLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getJunkedLeadsDetailsByUserId(userId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked-leads/count/for-org/{orgId}")
	public ResponseEntity<?> getJunkedLeadsListCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getJunkedLeadsListCountByOrgId(orgId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/junked-leads/list/for-org/{orgId}")
	public ResponseEntity<?> getJunkedLeadsDetailsByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getJunkedLeadsDetailsByOrgId(orgId, startDate,
					endDate);
			if (null != leadsViewMapper) {
				Collections.sort(leadsViewMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/hot/list/{userId}")
	public ResponseEntity<?> getHotListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getHotListByUserId(userId);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/warm/list/{userId}")
	public ResponseEntity<?> getWarmListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getWarmListByUserId(userId);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/cold/list/{userId}")
	public ResponseEntity<?> getcoldListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getcoldListByUserId(userId);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/hot/count/{userId}")
	public ResponseEntity<?> getHotListCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getHotListCountByUserId(userId);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/worm/count/{userId}")
	public ResponseEntity<?> getWormListCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getWormListCountByUserId(userId);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/cold/count/{userId}")
	public ResponseEntity<?> getColdListCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getColdListCountByUserId(userId);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/leads/transfer/one-user-to-another/{userId}")
	public ResponseEntity<?> transferLeadsOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> leadsList = leadsService.transferLeadsOneUserToAnother(userId, transferMapper);

			return new ResponseEntity<>(leadsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/hot-warm-cold/count/{userId}")
	public ResponseEntity<?> getLeadsListCountByUserIdAndtype(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getLeadsListCountByUserIdAndtype(userId);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/hot-warm-cold/count/date-range/{userId}")
	public ResponseEntity<?> getLeadsListCountByUserIdAndtypeAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		Map map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Integer> map = leadsService.getLeadsListCountByUserIdAndtypeAndDateRange(userId, startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/type/hot-warm-cold/count/{orgId}")
	public ResponseEntity<?> getLeadsListCountByOrgIdAndtype(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = leadsService.getLeadsListCountByOrgIdAndtype(orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/hot-warm-cold/count/date-range/{orgId}")
	public ResponseEntity<?> getLeadsListCountByOrgIdAndtypeAndDateRange(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Map map = leadsService.getLeadsListCountByOrgIdAndtypeAndDateRange(orgId, startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/added/count/date-wise/{userId}")
	public ResponseEntity<?> getAddedLeadsListCountByUserIdWithDateWise(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Double>> map = leadsService.getAddedLeadsListCountByUserIdWithDateWise(userId, startDate,
					endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/source-wise/count/{userId}")
	public ResponseEntity<?> getLeadsCountSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, Double>> map = leadsService.getLeadsCountSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/source-wise/list/{userId}")
	public ResponseEntity<?> getLeadsListSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, List<LeadsViewMapper>>> map = leadsService.getLeadsListSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/hot/list/date-range/{userId}")
	public ResponseEntity<?> getHotListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getHotListByUserIdAndDateRange(userId, startDate,
					endDate);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/warm/list/date-range/{userId}")
	public ResponseEntity<?> getWarmListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getWarmListByUserIdAndDateRange(userId, startDate,
					endDate);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/cold/list/date-range/{userId}")
	public ResponseEntity<?> getcoldListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getcoldListByUserIdAndDateRange(userId, startDate,
					endDate);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/activity/list/{leadsId}")
	public ResponseEntity<?> getActivityListByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ActivityMapper> activityMapper = leadsService.getActivityListByLeadsId(leadsId);
			if (null != activityMapper && !activityMapper.isEmpty()) {
				Collections.sort(activityMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(activityMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/leads/note/{notesId}")
	public ResponseEntity<?> deleteLeadsNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			leadsService.deleteLeadsNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/all/{pageNo}/{filter}/{type}")
	public ResponseEntity<?> getLeadsListByOrgId(@PathVariable("pageNo") int pageNo,
			@PathVariable("filter") String filter,@PathVariable String type, @RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<LeadsViewMapper> leadsViewMapper = leadsService.getLeadsListByOrgId(orgId, pageNo, pageSize, filter,type);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/all/record/count/{orgId}")
	public ResponseEntity<?> getLeadsListCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(leadsService.getLeadsListCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getTeamLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<LeadsViewMapper> leadsViewMapper = leadsService.getTeamLeadsDetailsByUserId(userId, pageNo, pageSize,
					filter);

			if (null != leadsViewMapper) {
				// Collections.sort(leadsViewMapper, ( m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/leads/website")
	public ResponseEntity<?> createLeadsThroughWebsite(@RequestBody LeadsMapper leadsMapper,
			@RequestParam(value = "url", required = true) String url) throws IOException, TemplateException {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			if (!StringUtils.isEmpty(leadsMapper.getEmail())) {
				boolean b = leadsService.getLeadsByEmail(leadsMapper.getEmail());
				if (b == true) {
					map.put("LeadInd", true);
					map.put("message", "Lead with same Email already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					if (!StringUtils.isEmpty(leadsMapper.getUrl())) {
						boolean b1 = leadsService.getLeadsByUrl(leadsMapper.getUrl());
						if (b1 == true) {
							map.put("LeadInd", true);
							map.put("message", "Lead with same url already exists....");
							return new ResponseEntity<>(map, HttpStatus.OK);
						} else {
							leadsMapper.setOrganizationId(web.getOrgId());
							String msg = leadsService.saveLeadsTroughWebsite(leadsMapper);
							map.put("message", msg);
							return new ResponseEntity<>(map, HttpStatus.OK);
						}
					} else {
						leadsMapper.setOrganizationId(web.getOrgId());
						String msg = leadsService.saveLeadsTroughWebsite(leadsMapper);
						map.put("message", msg);
						return new ResponseEntity<>(map, HttpStatus.OK);
					}
				}

			} else {
				if (!StringUtils.isEmpty(leadsMapper.getUrl())) {
					boolean b1 = leadsService.getLeadsByUrl(leadsMapper.getUrl());
					if (b1 == true) {
						map.put("LeadInd", true);
						map.put("message", "Lead with same url already exists....");
						return new ResponseEntity<>(map, HttpStatus.OK);
					} else {
						leadsMapper.setOrganizationId(web.getOrgId());
						String msg = leadsService.saveLeadsTroughWebsite(leadsMapper);
						map.put("message", msg);
						return new ResponseEntity<>(map, HttpStatus.OK);
					}
				} else {
					leadsMapper.setOrganizationId(web.getOrgId());
					String msg = leadsService.saveLeadsTroughWebsite(leadsMapper);
					map.put("message", msg);
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

		} else {
			map.put("message", " Something went wrong !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/leads/team/count/{userId}")
	public ResponseEntity<?> getTeamLeadsCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(leadsService.getTeamLeadsCountByUserId(userId));
		}
		return null;

	}

	@GetMapping("/api/v1/leads/activity/record/{leadsId}")
	public ResponseEntity<?> getActivityRecordByLeadsId(@PathVariable("leadsId") String leadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(leadsService.getActivityRecordByLeadsId(leadsId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/report/all-leads/enterprise/{orgId}")
	public ResponseEntity<?> getAllLeadsByOrgIdForReport(@PathVariable("orgId") String orgId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeadsReportMapper> investorList = leadsService.getAllLeadsByOrgIdForReport(orgId, startDate, endDate);
			if (null != investorList && !investorList.isEmpty()) {
				investorList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/report/all-leads/self/{userId}")
	public ResponseEntity<?> getAllLeadsByUserIdForReport(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeadsReportMapper> investorList = leadsService.getAllLeadsByUserIdForReport(userId, startDate,
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

	@GetMapping("/api/v1/leads/qualified-leads/list/for-report/self/{userId}")
	public ResponseEntity<?> getQualifiedLeadsDetailsForReportByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsReportMapper> leadsMapper = leadsService.getQualifiedLeadsDetailsForReportByUserId(userId,
					startDate, endDate);
			if (null != leadsMapper) {
				Collections.sort(leadsMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leads/qualified-leads/list/for-report/enterprise/{orgId}")
	public ResponseEntity<?> getQualifiedLeadsDetailsForReportByOrgId(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsReportMapper> leadsMapper = leadsService.getQualifiedLeadsDetailsForReportByOrgId(orgId,
					startDate, endDate);
			if (null != leadsMapper) {
				Collections.sort(leadsMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/teams/{userId}/{pageNo}/{type}")
	public ResponseEntity<?> getTeamLeadsDetailsByUnderAUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo,@PathVariable String type, @RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getTeamLeadsDetailsByUnderAUserId(userId, pageNo,
					pageSize,type);

			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/teams/count/{userId}")
	public ResponseEntity<?> getTeamLeadsCountByUnderAUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(leadsService.getTeamLeadsCountByUnderAUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/leads/User/{userId}/{pageNo}/{filter}/{type}")
	public ResponseEntity<?> getLeadsDetailsByUserIdAndType(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("type") String type,
			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<LeadsViewMapper> leadsViewMapper = leadsService.getLeadsDetailsByUserIdAndType(userId, pageNo,
					pageSize, filter,type);

			if (null != leadsViewMapper) {
				// Collections.sort(leadsViewMapper, ( m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/leads/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<LeadsViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = leadsService.getLeadsDetailsByNameByOrgLevel(name, orgId);
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsBySectorInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsBySourceInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsByOwnerNameInOrgLevel(name, orgId);
				}
				
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = leadsService.getLeadsDetailsByNameForTeam(name, userId);
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsBySectorForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsBySourceForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsDetailsByOwnerNameForTeam(name, userId, orgId);
				}
				return ResponseEntity.ok(list);
			} else {
				list = leadsService.getLeadsDetailsByNameByUserId(name, userId);
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsBySectorInUserLevel(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsBySourceInUserLevel(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = leadsService.getLeadsByOwnerNameInUserLevel(name, userId, orgId);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
