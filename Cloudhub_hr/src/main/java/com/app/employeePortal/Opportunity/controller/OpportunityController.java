package com.app.employeePortal.Opportunity.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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

import com.app.employeePortal.Opportunity.mapper.OpportunityDropdownMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityForecastLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderViewMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityProductMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityReportMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunitySkillLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.organization.mapper.OrganizationValueMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class OpportunityController {

	@Autowired
	OpportunityService oppertunityService;
	@Autowired
	ContactService contactService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/opportunity") // Steper -1
	public ResponseEntity<?> createOpportunity(@RequestBody OpportunityMapper OpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			OpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityViewMapper opportunityId = oppertunityService.saveOpportunity(OpportunityMapper);

			return new ResponseEntity<>(opportunityId, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/opportunity/link-product") // Steper -2
	public ResponseEntity<?> savePoSupplier(@RequestBody OpportunityProductMapper mapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));

			return ResponseEntity.ok(oppertunityService.linkOppToProduct(mapper));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/link-product/{opportunityId}")
	public ResponseEntity<?> getOpportunityLinkProductListByOpportunityId(@PathVariable String opportunityId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(oppertunityService.getOpportunityLinkProductListByOpportunityId(opportunityId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/user/{userId}/{pageNo}")
	public ResponseEntity<List<OpportunityViewMapper>> getOppListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			if (userId.equalsIgnoreCase("All")) {
//				List<OpportunityViewMapper> oppList = oppertunityService.getAllOpportunityList(pageNo, pageSize);
//				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				List<OpportunityViewMapper> oppList = oppertunityService.getOpportunityDetailsListPageWiseByOrgId(orgId,
						pageNo, pageSize);
				return ResponseEntity.ok(oppList);
			} else {
				List<OpportunityViewMapper> oppList = oppertunityService
						.getOpportunityDetailsListPageWiseByUserId(userId, pageNo, pageSize);

				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(oppList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/{opportunityId}")

	public ResponseEntity<?> getOpportunityById(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OpportunityViewMapper opportunityMapper = oppertunityService.getOpportunityDetails(opportunityId);

			System.out.println("get opportunityMapper" + opportunityMapper.toString());
			return new ResponseEntity<OpportunityViewMapper>(opportunityMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/opportunity/{opportunityId}")
	public ResponseEntity<?> updateOpportunityDetails(@PathVariable("opportunityId") String opportunityId,
			@RequestBody OpportunityMapper opportunityMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			OpportunityViewMapper opportunityMapperr = oppertunityService.updateOpportunityDetails(opportunityId,
					opportunityMapper);

			return new ResponseEntity<OpportunityViewMapper>(opportunityMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/opportunity/notes")
	public ResponseEntity<?> createOpportunityNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = oppertunityService.saveOpportunityNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/notes/opportunity/{opportunityId}")

	public ResponseEntity<?> getOpportunityListByOpportunityId(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = oppertunityService.getNoteListByOpportunityId(opportunityId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				notesMapper
						.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}
//            Collections.sort(notesMapper,
//                    (NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
//            return new ResponseEntity<>(notesMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/contact/{contactId}")

	public ResponseEntity<?> getOppListBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService.getOpportunityDetailsListByContactId(contactId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/document/{opportunityId}")

	public ResponseEntity<?> getOppListByDocumentId(@PathVariable("opportunityId") String opportunityId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> oppList = oppertunityService.getOpportunityDetailsListBydocumentId(opportunityId);
			if (null != oppList && !oppList.isEmpty()) {
				oppList.sort(
						(DocumentMapper m1, DocumentMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(oppList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(oppList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/opportunity/document/{documentId}")

	public ResponseEntity<?> deleteOpportunityDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.deleteDocumentById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/details/{opportunityName}")
	public ResponseEntity<List<OpportunityViewMapper>> getOpportunityDetailsByName(
			@PathVariable("opportunityName") String opportunityName,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OpportunityViewMapper> oppertunity = oppertunityService.getOpportunityDetailsByName(opportunityName);

			Collections.sort(oppertunity, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppertunity, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/opportunity/contact")
	public ResponseEntity<?> createContact(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			ContactViewMapper opportunityId = oppertunityService.saveOpportunityContact(contactMapper);
			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/contact/details/{opportunityId}")

	public ResponseEntity<?> getContactListByPartnerId(@PathVariable("opportunityId") String opportunityId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> contactList = oppertunityService.getContactListByOpportunityId(opportunityId);
			Collections.sort(contactList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/opportunity/delete/{id}")
	public ResponseEntity<?> deleteOpportunitydetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.deleteOppertunityDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/deleteHistory/{pageNo}")
	public ResponseEntity<?> getAllOpportunitDeleteHistory(@RequestHeader("Authorization") String authorization,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggeduserId = jwtTokenUtil.getUserIdFromToken(authToken);
			List<OpportunityViewMapper> opportunityMapper = oppertunityService.getDeleteOpportunityDetails(loggeduserId,
					pageNo, pageSize);

			System.out.println("get opportunityMapper" + opportunityMapper.toString());
			Collections.sort(opportunityMapper, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(opportunityMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruiter/opportunity/{recruiterId}")
	public ResponseEntity<?> getOpportunityOfRecruiter(@PathVariable("recruiterId") String recruiterId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<OpportunityViewMapper> mapperList = oppertunityService.getOpprtunityByRecruiterId(recruiterId);
			// Collections.sort(mapperList, (v1, v2) ->
			// v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/salesUser/opportunity/{userId}")

	public ResponseEntity<List<OpportunityViewMapper>> getOpportunityOfASalesUser(@PathVariable("userId") String userId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService.getopportunityOfASalesUser(userId);

			Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/*
	 * fetch note list by customerId
	 *
	 * @GetMapping("/api/v1/customer/note/{opportunityId}")
	 *
	 * public ResponseEntity<?>
	 * getNoteListByCustomerId(@PathVariable("opportunityId") String opportunityId,
	 *
	 * @RequestHeader("Authorization") String authorization ,HttpServletRequest
	 * request) {
	 *
	 *
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 * List<NotesMapper> notesMapper =
	 * oppertunityService.getNoteListByOpportunityId(opportunityId);
	 * Collections.sort(notesMapper, (NotesMapper m1, NotesMapper m2) ->
	 * m2.getCreationDate() .compareTo(m1.getCreationDate())); return new
	 * ResponseEntity<>(notesMapper,HttpStatus.OK);
	 *
	 * }
	 *
	 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
	 *
	 */

	@GetMapping("/api/v1/opportunity/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/opportunity/tagContact")
	public ResponseEntity<?> tagContact(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			String opportunityId = oppertunityService.saveTagContact(contactMapper);
			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunities/{userId}")

	public ResponseEntity<List<OpportunityViewMapper>> getOpportunityOfUser(@PathVariable("userId") String userId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> userOppList = oppertunityService.getOpportunityDetailsListByUserId(userId);
			List<OpportunityViewMapper> recruiterOppList = oppertunityService.getOpprtunityByRecruiterId(userId);
			List<OpportunityViewMapper> salesUserList = oppertunityService.getopportunityOfASalesUser(userId);

			if (null != userOppList && !userOppList.isEmpty()) {

				Collections.sort(userOppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(userOppList, HttpStatus.OK);

			}
			if (null != recruiterOppList && !recruiterOppList.isEmpty()) {

				// Collections.sort(recruiterOppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(recruiterOppList, HttpStatus.OK);
			}
			if (null != salesUserList && !salesUserList.isEmpty()) {

				// Collections.sort(salesUserList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(salesUserList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/opportunities/all-opportunities/{pageNo}")
	public ResponseEntity<List<OpportunityViewMapper>> getAllOpportunityList(@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		String authToken = authorization.replace(TOKEN_PREFIX, "");
		String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
		List<OpportunityViewMapper> oppList = oppertunityService.getOpportunityDetailsListPageWiseByOrgId(orgId, pageNo,
				pageSize);
		Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(oppList);
	}

	@PutMapping("/api/v1/opportunity/reinstate/{opportunityId}")
	public ResponseEntity<?> reinstateOpportunity(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.reinstateOpportunityByOppId(opportunityId);

			return new ResponseEntity<>("Reinstate successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/recruiter/record/count/{userId}")
	public ResponseEntity<?> getRecruitrtCountByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getRecruitrtCountByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/jobOrderName/{jobOrder}")
	public ResponseEntity<?> getOpportunityListByJobOrder(@PathVariable("jobOrder") String jobOrder,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<RecruitmentOpportunityMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = oppertunityService.getOpportunityListByJobOrder(jobOrder);

			if (null != typeList) {

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("This job ID is not available", HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/*
	 * @GetMapping("/api/v1/opportunity/count") public ResponseEntity<?>
	 * getNoOfOpportunityBycreationDate(@RequestHeader("Authorization") String
	 * authorization, HttpServletRequest request) {
	 *
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) { String
	 * authToken = authorization.replace(TOKEN_PREFIX, ""); return
	 * ResponseEntity.ok(oppertunityService.getNoOfOpportunityBycreationDate());
	 *
	 * }
	 *
	 * return null;
	 *
	 * }
	 */
	@PostMapping("/api/v1/opportunity/tag/contact")
	public ResponseEntity<?> createTagContactWithOppertunity(@RequestBody OpportunityMapper OpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String opportunityId = oppertunityService.saveTagContactWithOppertunity(OpportunityMapper);
			return new ResponseEntity<>(opportunityId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/transfer/{userId}")
	@CacheEvict(value = "opportunity", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> oppList = oppertunityService.updateTransferOneUserToAnother(userId, transferMapper);

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/opportunity/update/contact/Role/{contactId}")
	public ResponseEntity<?> updateContactRoleByContactId(@RequestBody ContactViewMapper contactViewMapper,
			@PathVariable("contactId") String contactId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			ContactViewMapper contactList = oppertunityService.updateContactRoleByContactId(contactId,
					contactViewMapper);

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/opportunity/update/tagCustomer/{opportunityId}")
	public ResponseEntity<?> updateCustomerByOpportunityId(@RequestBody OpportunityViewMapper opportunityViewMapper,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OpportunityViewMapper opportunityViewMapperr = oppertunityService
					.updateCustomerByOpportunityId(opportunityId, opportunityViewMapper);

			return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/opportunity/Innitiative/skill/number")
	public ResponseEntity<?> saveInnitiativeSkillAndNumber(@RequestBody OpportunityMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<OpportunitySkillLinkMapper> opportunitySkillLinkMapper = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			opportunitySkillLinkMapper = oppertunityService.saveInnitiativeSkillAndNumber(opportunityMapper);

			return new ResponseEntity<>(opportunitySkillLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("api/v1/opportunities/Innitiative/skill/number/{opportunityId}")
	public ResponseEntity<?> getSkillAndNumberByOppotunityId(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OpportunitySkillLinkMapper> opportunitySkillLinkMapper = oppertunityService
					.getSkillAndNumberByOppotunityId(opportunityId);
			// Collections.sort(oppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(opportunitySkillLinkMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/deleteHistory/record/count/{userId}")
	public ResponseEntity<?> getDeleteHistoryCountList(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getDeleteHistoryCountList(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/update/wonInd/{opportunityId}")
	public ResponseEntity<?> updateOpportunityWonIndByOpportunityId(@RequestBody OpportunityMapper opportunityMapper,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		HashMap map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.updateOpportunityWonIndByOpportunityId(opportunityId, opportunityMapper);

			if (opportunityMapper.isWonInd() == true) {
				map.put("message", "Opportunity Succesfully Won");
			} else {
				map.put("message", "Opportunity Reinstate Succesfully");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/update/lostInd/{opportunityId}")
	public ResponseEntity<?> updateOpportunityLostIndByOpportunityId(@RequestBody OpportunityMapper opportunityMapper,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		HashMap map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.updateOpportunityLostIndByOpportunityId(opportunityId, opportunityMapper);
			if (opportunityMapper.isLostInd() == true) {
				map.put("message", "Opportunity Succesfully Lost");
			} else {
				map.put("message", "Opportunity Reinstate Succesfully");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/update/closeInd/{opportunityId}")
	public ResponseEntity<?> updateOpportunityCloseIndByOpportunityId(@RequestBody OpportunityMapper opportunityMapper,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		HashMap map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.updateOpportunityCloseIndByOpportunityId(opportunityId, opportunityMapper);
			if (opportunityMapper.isCloseInd() == true) {
				map.put("message", "Opportunity Succesfully Closed");
			} else {
				map.put("message", "Opportunity Got Opened");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/closeInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndCloseInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> opportunityList = oppertunityService
					.getOpportunityDetailByUserIdAndCloseInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/lostInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndLostInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> opportunityList = oppertunityService
					.getOpportunityDetailByUserIdAndLostInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/wonInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndWonInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> opportunityList = oppertunityService
					.getOpportunityDetailByUserIdAndWonInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/opportunity/forecast")
	public ResponseEntity<?> saveForecast(@RequestBody OpportunityForecastLinkMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		OpportunityForecastLinkMapper opportunityForecastLinkMapper = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityForecastLinkMapper = oppertunityService.saveForecast(opportunityMapper);

			return new ResponseEntity<>(opportunityForecastLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("api/v1/opportunities/forecast/skill/number/{opportunityId}")
	public ResponseEntity<?> getForecastSkillAndNumberByOppotunityId(
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OpportunityForecastLinkMapper> opportunityForecastLinkMapper = oppertunityService
					.getForecastSkillAndNumberByOppotunityId(opportunityId);
			// Collections.sort(oppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(opportunityForecastLinkMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/update/forecast/{opportunityId}")
	public ResponseEntity<?> updateOpportunityForecastOpportunityId(@RequestBody OpportunityMapper opportunityMapper,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OpportunityForecastLinkMapper> opportunityForecastMapper = oppertunityService
					.updateOpportunityForecastOpportunityId(opportunityId, opportunityMapper);

			return new ResponseEntity<>(opportunityForecastMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/CloseInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByCloseInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getOpportunityListByCloseInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/LostInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByLostInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getOpportunityListByLostInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/wonInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByWonInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getOpportunityListByWonInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/employee/create/all-employees")

	public ResponseEntity<?> getEmployeeListByOrgIdForOpportunityCreate(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			List<EmployeeViewMapper> empList = employeeService
					.getEmployeeListByOrgIdForOpportunityCreate(organizationId);
			// Collections.sort(empList, ( m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(empList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/open-recruitment/open-position/count/{opportunityId}")

	public ResponseEntity<?> getOpenRecruitmentAndOpenPositionCountByOpportunityId(HttpServletRequest request,
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

			return ResponseEntity
					.ok(oppertunityService.getOpenRecruitmentAndOpenPositionCountByOpportunityId(opportunityId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/opportunity/update/ReinstateInd/True/only/{opportunityId}")
	public ResponseEntity<?> updateOpportunityReinstateIndOnlyTrue(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OpportunityViewMapper opportunityViewMapperr = oppertunityService
					.updateOpportunityReinstateIndOnlyTrue(opportunityId);

			return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/opportunity/update/closeInd/True/only/{opportunityId}")
	public ResponseEntity<?> updateOpportunityCloseIndOnlyTrue(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OpportunityViewMapper opportunityViewMapperr = oppertunityService
					.updateOpportunityCloseIndOnlyTrue(opportunityId);

			return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/opportunity/update/stage")
	public ResponseEntity<?> updateStage(@RequestBody OpportunityViewMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		OpportunityViewMapper opportunityMapperNew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapperNew = oppertunityService.updateStage(opportunityMapper);

			return new ResponseEntity<>(opportunityMapperNew, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/forecast/Lists/{orgId}")
	public ResponseEntity<?> getforecastByOrgrIdMonthWise(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Map<String, List<OpportunityForecastLinkMapper>> mapperList = oppertunityService
					.getforecastByOrgrIdMonthWise(orgId);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunityList/{userId}")
	public ResponseEntity<List<OpportunityViewMapper>> opportunityList(@PathVariable("userId") String userId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> userOppList = oppertunityService.getOpportunityDetailsListByUserId(userId);
			userOppList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(userOppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/contact/count/{opportunityId}")
	public ResponseEntity<?> getOpportunityContactCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("opportunityId") String opportunityId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getOpportunityContactCountByCustomerId(opportunityId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/document/count/{opportunityId}")
	public ResponseEntity<?> getOpportunityDocumentCountByCustomerId(
			@RequestHeader("Authorization") String authorization, @PathVariable("opportunityId") String opportunityId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(oppertunityService.getOpportunityDocumentCountByCustomerId(opportunityId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/Close/record/count/date-range/{userId}")
	public ResponseEntity<?> getCloseOpportunityListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(oppertunityService.getCloseOpportunityListByUserIdAndDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/added/record/count/date-range/{userId}")
	public ResponseEntity<?> getAddedOpportunityListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(oppertunityService.getAddedOpportunityListByUserIdAndDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/ClosedList/date-range/{userId}")
	public ResponseEntity<?> getClosedOpportunitiesByUserIdDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(oppertunityService.getClosedOpportunitiesByUserIdDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/added/date-range/{userId}")
	public ResponseEntity<?> getAddedOpportunitiesByUserIdDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(oppertunityService.getAddedOpportunitiesByUserIdDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/drop-opportunityList/{userId}")
	public ResponseEntity<List<OpportunityDropdownMapper>> getDropDownOpportunityList(
			@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityDropdownMapper> userOppList = oppertunityService.getDropDownOpportunityList(userId);
			return new ResponseEntity<>(userOppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/oppertunity/note/update")
	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		NotesMapper notesMapperr = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapperr = oppertunityService.updateNoteDetails(notesMapper);
			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/oppertunity/note/{notesId}")
	public ResponseEntity<?> deleteOppertunityNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			oppertunityService.deleteCustomerNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/all/{pageNo}")
	public ResponseEntity<List<OpportunityViewMapper>> getAllOpportunitylist(@PathVariable("pageNo") int pageNo,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<OpportunityViewMapper> oppList = oppertunityService.getOpportunityDetailsListPageWiseByOrgId(orgId,
					pageNo, pageSize);
			return ResponseEntity.ok(oppList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/all/record/count/{orgId}")
	public ResponseEntity<?> getOpportunityListCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getOpportunityListCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunit/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getTeamInnvestorOppDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<OpportunityViewMapper> oppViewMapper = oppertunityService.getTeamOpportunityDetailsByUserId(userId,
					pageNo, pageSize, filter);
			return new ResponseEntity<>(oppViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/oppertunity/contact/team/count/{userId}")
	public ResponseEntity<?> getTeamOppertunityContactCountByUserId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getTeamOppertunityContactCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/oppertunity/document/contract/update")
	public ResponseEntity<?> updateContractIndForDocument(@RequestBody DocumentMapper documentMapperr,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			DocumentMapper documentMapper = oppertunityService.updateContractIndForDocument(documentMapperr);
			return new ResponseEntity<>(documentMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/opportunity/order")
	public ResponseEntity<?> createOpportunity(@RequestBody OpportunityOrderMapper opportunityOrderMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityOrderMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityOrderMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			List<OpportunityOrderViewMapper> id = oppertunityService.saveOpportunityOrder(opportunityOrderMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/order/list/{OpportunityId}")
	public ResponseEntity<?> getOpportunityOrderListByOpportunityId(@PathVariable("OpportunityId") String OpportunityId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityOrderViewMapper> resultList = oppertunityService
					.getOpportunityOrderListByOpportunityId(OpportunityId);
			return ResponseEntity.ok(resultList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/opportunity/order/delete/{opportunityOrderId}")
	public ResponseEntity<?> deleteOpportunityOrder(@PathVariable("opportunityOrderId") String opportunityOrderId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String msg = oppertunityService.deleteOpportunityOrder(opportunityOrderId);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/included/user/{userId}/{pageNo}")
	public ResponseEntity<?> getOpportunityDetailsListPageWiseByIncludedUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService
					.getOpportunityDetailsListPageWiseByIncludedUserId(userId, pageNo, pageSize);
			if (null != oppList && !oppList.isEmpty()) {
				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			}
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/included/record/count/{userId}")
	public ResponseEntity<?> getCountListByIncludedUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getCountListByIncludedUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/action-required/record/today/{userId}")

	public ResponseEntity<?> getActionRequiredRecordByToday(@PathVariable("userId") String userId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity.ok(oppertunityService.getActionRequiredRecordByToday(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/closedList/date-range/enterprise/{orgId}")
	public ResponseEntity<?> getClosedOpportunitiesByOrgIdDateRangeforReport(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityReportMapper> resultMapper = oppertunityService
				.getClosedOpportunitiesByOrgIdDateRangeforReport(orgId, startDate, endDate);
		if (null != resultMapper) {
			Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
	}

	@GetMapping("/api/v1/opportunity/closedList/date-range/self/{userId}")
	public ResponseEntity<?> getClosedOpportunitiesByUserIdDateRangeforReport(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityReportMapper> resultMapper = oppertunityService
				.getClosedOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
		if (null != resultMapper) {
			Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
	}

	@GetMapping("/api/v1/opportunity/openList/date-range/enterprise/{orgId}")
	public ResponseEntity<?> getOpenOpportunitiesByOrgIdDateRangeforReport(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityReportMapper> resultMapper = oppertunityService
				.getOpenOpportunitiesByOrgIdDateRangeforReport(orgId, startDate, endDate);
		if (null != resultMapper) {
			Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
	}

	@GetMapping("/api/v1/opportunity/openList/date-range/self/{userId}")
	public ResponseEntity<?> getOpenOpportunitiesByUserIdDateRangeforReport(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityReportMapper> resultMapper = oppertunityService
				.getOpenOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
		if (null != resultMapper) {
			Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
	}

	@GetMapping("/api/v1/opportunity/List/date-range/{orgId}")
	public ResponseEntity<?> getOpportunitiesByOrgIdDateRangeforReport(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<OpportunityReportMapper> resultMapper = oppertunityService.getOpportunitiesByOrgIdDateRangeforReport(orgId,
				startDate, endDate);
		if (null != resultMapper) {
			Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
	}

	@GetMapping("/api/v1/opportunity/List/date-range/self/{userId}")
	public ResponseEntity<?> getOpportunitiesByUserIdDateRangeforReport(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityReportMapper> resultMapper = oppertunityService
					.getOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
			if (null != resultMapper) {
				Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/count/{country}")
	public ResponseEntity<?> getOpportunityCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getOpportunityCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/list/{country}")
	public ResponseEntity<?> getOpportunityListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> list = oppertunityService.getOpportunityListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/open/count/{country}")
	public ResponseEntity<?> getOpenOpportunityCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getOpenOpportunityCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/open/list/{country}")
	public ResponseEntity<?> getOpenOpportunityListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> list = oppertunityService.getOpenOpportunityListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/year/count/{country}")
	public ResponseEntity<?> getOpportunityYearlyCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(oppertunityService.getOpportunityYearlyCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/year/list/{country}")
	public ResponseEntity<?> getOpportunityYearlyListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> list = oppertunityService.getOpportunityYearlyListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunit/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getTeamOppDetailsByUnderUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<OpportunityViewMapper> oppViewMapper = oppertunityService.getTeamOppDetailsByUnderUserId(userId, pageNo,
					pageSize);
			return new ResponseEntity<>(oppViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/oppertunity/teams/count/{userId}")
	public ResponseEntity<?> getTeamOppertunityCountByUnderUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(oppertunityService.getTeamOppertunityCountByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/open/count/contact/{contactId}")
	public ResponseEntity<?> getOpenOppCountBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(oppertunityService.getOpenOppCountBycontactId(contactId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/open/List/contact/{contactId}")
	public ResponseEntity<?> getOpenOppListBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService.getOpenOppListBycontactId(contactId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/won/count/contact/{contactId}")
	public ResponseEntity<?> getWonOppCountBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(oppertunityService.getWonOppCountBycontactId(contactId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/won/List/contact/{contactId}")
	public ResponseEntity<?> getWonOppListBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService.getWonOppListBycontactId(contactId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/proposal/value/count/contact/{contactId}")
	public ResponseEntity<?> getOppProposalValueCountByContactId(@RequestHeader("Authorization") String authorization,
			@PathVariable("contactId") String contactId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(oppertunityService.getOppProposalValueCountByContactId(contactId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/weighted/value/count/contact/{contactId}")
	public ResponseEntity<?> getOppWeigthedValueCountByContactId(@RequestHeader("Authorization") String authorization,
			@PathVariable("contactId") String contactId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(oppertunityService.getOppWeigthedValueCountByContactId(contactId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/drop-opportunityList/customer/{customerId}")
	public ResponseEntity<List<OpportunityDropdownMapper>> getDropDownOpportunityListByCustomerId(
			@PathVariable("customerId") String customerId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityDropdownMapper> userOppList = oppertunityService
					.getDropDownOpportunityListByCustomerId(customerId);
			return new ResponseEntity<>(userOppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunityList/{userId}/{quarter}/{year}")
	public ResponseEntity<?> getOpportunityListByUserIdAndQuarterAndYear(@PathVariable("userId") String userId,
			@PathVariable("quarter") String quarter, @PathVariable("year") int year, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> userOppList = oppertunityService
					.getOpportunityListByUserIdAndQuarterAndYear(userId, quarter, year);
			if (null != userOppList && !userOppList.isEmpty()) {
				userOppList.sort((v1, v2) -> v2.getEndDate().compareTo(v1.getEndDate()));
			}
			return new ResponseEntity<>(userOppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/self/report/{userId}/{type}")
	public ResponseEntity<?> getSelfOpportunityListByDateRange(@PathVariable String userId, @PathVariable String type,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<OpportunityReportMapper> result = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("inprogess")) {
				result = oppertunityService.getOpenOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
			} else if (type.equalsIgnoreCase("won")) {
				result = oppertunityService.getWonOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
			} else if (type.equalsIgnoreCase("lost")) {
				result = oppertunityService.getLostOpportunitiesByUserIdDateRangeforReport(userId, startDate, endDate);
			} else {
				result = oppertunityService.getClosedOpportunitiesByUserIdDateRangeforReport(userId, startDate,
						endDate);
			}

			return ResponseEntity.ok(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/multi-org/dash-board/{emailId}/{year}/{quarter}")
	public ResponseEntity<?> getMultyOrgWonOppValueByYearAndQuarterForDashBoard(@PathVariable("emailId") String emailId,
			@PathVariable("year") int year, @PathVariable("quarter") String quarter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			List<OrganizationValueMapper> responseMapper = oppertunityService
					.getMultyOrgWonOppValueByYearAndQuarterForDashBoard(emailId, year, quarter, userId, orgId);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/opportunity/multi-org/won-list/dash-board/{orgId}/{year}/{quarter}")
	public ResponseEntity<?> getAllWonOppList(@PathVariable("orgId") String orgId, @PathVariable("year") int year,
			@PathVariable("quarter") String quarter, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<OpportunityViewMapper> oppList = oppertunityService.getAllWonOppList(orgId, year, quarter);
			if (null != oppList && !oppList.isEmpty()) {
				Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/opportunity/search/type/{name}/{type}")
	public ResponseEntity<List<OpportunityViewMapper>> getOpportunityDetailsByNameAndType(
			@PathVariable("name") String name, @PathVariable("type") String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<OpportunityViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = oppertunityService.getOpportunityDetailsByNameAndTypeOrgLevel(name, orgId);
				if (null == list || list.isEmpty()) {
					list = oppertunityService.getOpportunityDetailsByNewOppIdAndTypeOrgLevel(name, orgId);
				}
			} else if (type.equalsIgnoreCase("Team")) {
				list = oppertunityService.getOpportunityDetailsByNameAndTypeForTeam(name, userId);
				if (null == list || list.isEmpty()) {
					list = oppertunityService.getOpportunityDetailsByNewOppIdAndTypeForTeam(name, userId);
				}
			} else {
				list = oppertunityService.getOpportunityDetailsByNameAndTypeAndUserId(name, userId);
				if (null == list || list.isEmpty()) {
					list = oppertunityService.getOpportunityDetailsByNewOppIdAndTypeAndUserId(name, userId);
				}
			}
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}