package com.app.employeePortal.investor.controller;

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

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.InvestorDocTypeMapper;
import com.app.employeePortal.investor.mapper.InvestorInvoiceMapper;
import com.app.employeePortal.investor.mapper.InvestorKeySkillMapper;
import com.app.employeePortal.investor.mapper.InvestorMapper;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;
import com.app.employeePortal.investor.mapper.InvestorReportMapper;
import com.app.employeePortal.investor.mapper.InvestorShareMapper;
import com.app.employeePortal.investor.mapper.InvestorSkillLinkMapper;
import com.app.employeePortal.investor.mapper.InvestorViewMapper;
import com.app.employeePortal.investor.mapper.InvestorViewMapperForDropDown;
import com.app.employeePortal.investor.service.InvestorService;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "investor" })
public class InvestorController {
	@Autowired
	ContactService contactService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private InvestorService investorService;

	@PostMapping("/api/v1/investor")
	@CacheEvict(value = "investor", allEntries = true)
	public ResponseEntity<?> createInvestor(@RequestBody InvestorMapper investorMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(investorMapper.getUrl())) {
				boolean b = investorService.investorByUrl(investorMapper.getUrl());
				if (b == true) {
					map.put("investorInd", true);
					map.put("message", "investor with same url already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					InvestorViewMapper id = investorService.saveInvestor(investorMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				InvestorViewMapper id = investorService.saveInvestor(investorMapper);
				return new ResponseEntity<>(id, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/{investorId}")
	public ResponseEntity<?> getInvestorById(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			InvestorViewMapper investorViewMapper = investorService.getInvestorDetailsById(investorId);
			return new ResponseEntity<InvestorViewMapper>(investorViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getInvestorByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (userId.equalsIgnoreCase("All")) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				List<InvestorViewMapper> investorList = investorService.getInvestorListByOrgId(orgId, pageNo, pageSize,
						filter);
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			} else if (userId.equalsIgnoreCase("team")) {
				String loggedUserId = jwtTokenUtil.getUserIdFromToken(authToken);
				Set<InvestorViewMapper> investorList = investorService.getTeamInvestorListByUnderUserId(loggedUserId,
						pageNo, pageSize);
				return ResponseEntity.ok(investorList);
			} else {
				List<InvestorViewMapper> investorList = investorService.getFilterInvestorsPageWiseByUserId(userId,
						pageNo, pageSize, filter);
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/investor/{investorId}")
	@CacheEvict(value = "investor", allEntries = true)
	public ResponseEntity<?> updateInvestor(@PathVariable("investorId") String investorId,
			@RequestBody InvestorMapper investorMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			investorMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			InvestorViewMapper investorViewMapper = investorService.updateInvestor(investorId, investorMapper);

			return ResponseEntity.ok(investorViewMapper);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/contacts/{investorId}")
	public ResponseEntity<?> getContactListByInvestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> contactViewMappers = investorService.getContactListByInvestorId(investorId);
			if (null != contactViewMappers && !contactViewMappers.isEmpty()) {
				contactViewMappers.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(contactViewMappers, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investors")
//	@Cacheable(key = "#userId")
	public ResponseEntity<?> getInvestors(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorViewMapper> investorList = investorService.getAllInvestor();
			investorList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return ResponseEntity.ok(investorList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/note/{investorId}")
	public ResponseEntity<?> getNoteListByInestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = investorService.getNoteListByInvestorId(investorId);
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

	@PostMapping("/api/v1/investor/notes")
	public ResponseEntity<?> createCustomerNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = investorService.saveInvestorNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/investor/contact")
	@CacheEvict(value = "contacts", allEntries = true)
	public ResponseEntity<?> createInvestorContact(@RequestBody ContactMapper contactMapper,
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
					ContactViewMapper id = investorService.saveInvestorContact(contactMapper);

					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/document/{investorId}")
	public ResponseEntity<?> getContactListByContactId(@PathVariable("investorId") String investorId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = investorService.getDocumentListByInvestorId(investorId);
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

	@PostMapping("/api/v1/investor/opportunity/save")
	public ResponseEntity<?> saveInvestorOpportunity(@RequestBody InvestorOpportunityMapper OpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws TemplateException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			OpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvestorOpportunityMapper opportunityId = investorService.saveInvestorOpportunity(OpportunityMapper);

			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/Name/{name}")
	public ResponseEntity<?> getInvestorListByName(@RequestHeader("Authorization") String authorization,
			@PathVariable("name") String name, HttpServletRequest request) throws Exception {

		List<InvestorViewMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			list = investorService.getInvestorListByName(name);

			if (null != list && !list.isEmpty()) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("investor is not found", HttpStatus.NOT_FOUND);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/investor/all-investor/{pageNo}")
	public ResponseEntity<?> getInvestors(@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		List<InvestorViewMapper> customerList = investorService.getInvestors(pageNo, pageSize);
		customerList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(customerList);
	}

	@GetMapping("/api/v1/investor/record/count/{userId}")
	public ResponseEntity<?> getCountListByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorService.getCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/all/record/count/{orgId}")
	public ResponseEntity<?> getInvestorCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorService.getInvestorCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/contact/record/count/investor/{userId}")
	public ResponseEntity<?> getCountNoOfContactByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorService.getCountNoOfContactByUserId(userId));
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/invoice/investor")
	public ResponseEntity<String> saveinvoiceInvestor(@RequestBody InvestorInvoiceMapper customerInvoiceMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String id = investorService.saveinvoiceInvestor(customerInvoiceMapper);

			return new ResponseEntity<String>(id, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/invoice/{investorId}")

	public ResponseEntity<?> getInvoiceListByInvestorId(@PathVariable("investorId") String investorId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorInvoiceMapper> oppList = investorService.getInvoiceListByInvestorId(investorId);
			oppList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investor/transfer/{userId}")
	@CacheEvict(value = "investor", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> investorList = investorService.updateTransferOneUserToAnother(userId, transferMapper);
			return new ResponseEntity<>(investorList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/search/{name}")
	public ResponseEntity<?> getCustomerByNameAndSector(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization) {

		List<InvestorViewMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = investorService.getInvestorListByName(name);

			if (null == list || list.isEmpty()) {
				list = investorService.getInvestorBySector(name);
			}
			if (null == list || list.isEmpty()) {
				list = investorService.getInvestorByOwnerName(name);
			}

			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/KeySkil/{investorId}")

	public ResponseEntity<?> getKeySkilListByCustomerIdAndDateRange(@PathVariable("investorId") String investorId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			InvestorKeySkillMapper customerKeySkillMapper = investorService
					.getKeySkilListByInvestorIdAndDateRange(investorId, orgId, startDate, endDate);

			return new ResponseEntity<>(customerKeySkillMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/investor/skillSet")
	public ResponseEntity<?> saveSkillSet(@RequestBody InvestorSkillLinkMapper investorSkillLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(investorSkillLinkMapper.getSkillName())) {
				boolean b = investorService.checkSkillInInvestorSkillSet(investorSkillLinkMapper.getSkillName(),
						investorSkillLinkMapper.getInvestorId());
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorSkillLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorSkillLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = investorService.saveSkillSet(investorSkillLinkMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/skillSet/{investorId}")
	public ResponseEntity<?> getSkillSetByInvestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorSkillLinkMapper> customerSkillLinkMapper = investorService.getSkillSetByInvestorId(investorId);
			if (null != customerSkillLinkMapper && !customerSkillLinkMapper.isEmpty()) {
				customerSkillLinkMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerSkillLinkMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(customerSkillLinkMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/user/{userId}")
	public ResponseEntity<?> getInvestorByUserIdForDropDown(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorViewMapperForDropDown> investorDetailsList = investorService
					.getInvestorByUserIdForDropDown(userId);
			investorDetailsList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(investorDetailsList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/contact/count/{investorId}")
	public ResponseEntity<?> getInvestorContactCountByInvestorId(@RequestHeader("Authorization") String authorization,
			@PathVariable("investorId") String investorId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorService.getInvestorContactCountByInvestorId(investorId));
		}
		return null;

	}

	@GetMapping("/api/v1/investor/document/count/{investorId}")
	public ResponseEntity<?> getDocumentCountByInvestorId(@RequestHeader("Authorization") String authorization,
			@PathVariable("investorId") String investorId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorService.getDocumentCountByInvestorId(investorId));
		}
		return null;

	}

	@GetMapping("/api/v1/investor/source-wise/count/{userId}")
	public ResponseEntity<?> getInvestorCountSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, Double>> map = investorService.getInvestorCountSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/source-wise/list/{userId}")
	public ResponseEntity<?> getInvestorListSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, List<InvestorViewMapper>>> map = investorService.getInvestorListSourceWiseByUserId(userId,
					orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/investor/note/update")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = investorService.updateNoteDetails(notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/investor/note/{notesId}")
	public ResponseEntity<?> deleteCustomerNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorService.deleteInvestorNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/activity/list/{investorId}")
	public ResponseEntity<?> getActivityListByInvestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ActivityMapper> activityMapper = investorService.getActivityListByInvestorId(investorId);
			if (null != activityMapper && !activityMapper.isEmpty()) {
				Collections.sort(activityMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	// sk
	@GetMapping("/api/v1/investor/list/{pageNo}/{filter}")
	public ResponseEntity<?> getInvestorListByOrgId(@PathVariable("pageNo") int pageNo,
			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorViewMapper> investorList = investorService.getInvestorListByOrgId(orgId, pageNo, pageSize,
					filter);
			return new ResponseEntity<>(investorList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/api/v1/investor/contact/{pageNo}/{filter}")
//	public ResponseEntity<?> getInvestorContactListByOrgId(@PathVariable("pageNo") int pageNo,
//			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		int pageSize = 20;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
//			List<ContactViewMapper> investorContactList = investorService.getInvestorContactListByOrgId(orgId, pageNo,
//					pageSize, filter);
//			return new ResponseEntity<>(investorContactList, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
//
//    @GetMapping("/api/v1/investor/team/{userId}/{pageNo}/{filter}")
//    public ResponseEntity<?> getTeamInvestorDetailsByUserId(@PathVariable("userId") String userId,
//                                                            @PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
//                                                            @RequestHeader("Authorization") String authorization) {
//        int pageSize = 20;
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            Set<InvestorViewMapper> leadsViewMapper = investorService.getTeamInvestorDetailsByUserId(userId, pageNo,
//                    pageSize, filter);
//            return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @GetMapping("/api/v1/investor/contact/team/{userId}/{pageNo}/{filter}")
//    public ResponseEntity<Set<ContactViewMapper>> getTeamInvestorContactListByUserId(
//            @PathVariable("userId") String userId, @PathVariable("pageNo") int pageNo,
//            @PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization,
//            HttpServletRequest request) {
//
//        int pageSize = 20;
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            Set<ContactViewMapper> contactList = investorService.getTeamInvestorContactListByUserId(userId, pageNo,
//                    pageSize, filter);
//
//            return ResponseEntity.ok(contactList);
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }

	@GetMapping("/api/v1/investor/team/count/{userId}")
	public ResponseEntity<?> getTeamInvestorCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorService.getTeamInvestorCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/contact/team/count/{userId}")
	public ResponseEntity<?> getTeamInvestorContactCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorService.getTeamInvestorContactCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/report/all-investor/enterprise/{orgId}")
	public ResponseEntity<?> getAllInvestorByOrgIdForReport(@PathVariable("orgId") String orgId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorReportMapper> investorList = investorService.getAllInvestorByOrgIdForReport(orgId, startDate,
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

	@GetMapping("/api/v1/investor/report/all-investor/self/{userId}")
	public ResponseEntity<?> getAllInvestorByUserIdForReport(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorReportMapper> investorList = investorService.getAllInvestorByUserIdForReport(userId, startDate,
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

	@GetMapping("/api/v1/investor/report/all-investor-contact/enterprise/{orgId}")
	public ResponseEntity<?> getAllInvestorContactListByOrgIdForReport(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactReportMapper> investorContactList = investorService
					.getAllInvestorContactListByOrgIdForReport(orgId, startDate, endDate);
			if (null != investorContactList && !investorContactList.isEmpty()) {
				investorContactList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorContactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorContactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/report/all-investor-contact/self/{userId}")
	public ResponseEntity<?> getAllInvestorContactListByUserIdForReport(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactReportMapper> investorContactList = investorService
					.getAllInvestorContactListByUserIdForReport(userId, startDate, endDate);
			if (null != investorContactList && !investorContactList.isEmpty()) {
				investorContactList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorContactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorContactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/activity/record/{investorId}")
	public ResponseEntity<?> getActivityRecordByInvestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorService.getActivityRecordByInvestorId(investorId));
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/api/v1/investor/opportunity/count/{investorId}")
	public ResponseEntity<?> getInvestorOpportunityCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorService.getInvestorOpportunityCountByInvestorId(investorId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/proposal/value/count/{investorId}")
	public ResponseEntity<?> getInvestorOppProposalValueCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity
					.ok(investorService.getInvestorOppProposalValueCountByInvestorId(investorId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/weighted/value/count/{investorId}")
	public ResponseEntity<?> getInvestorOppWeigthedValueCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity
					.ok(investorService.getInvestorOppWeigthedValueCountByInvestorId(investorId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/won/count/{investorId}")
	public ResponseEntity<?> getInvestorWonOpportunityCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity.ok(investorService.getInvestorWonOpportunityCountByInvestorId(investorId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/won/proposal/value/count/{investorId}")
	public ResponseEntity<?> getInvestorWonOppProposalValueCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity
					.ok(investorService.getInvestorWonOppProposalValueCountByInvestorId(investorId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/won/weighted/value/count/{investorId}")
	public ResponseEntity<?> getInvestorWonOppWeigthedValueCountByInvestorId(
			@RequestHeader("Authorization") String authorization, @PathVariable("investorId") String investorId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity
					.ok(investorService.getInvestorWonOppWeigthedValueCountByInvestorId(investorId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/count/{country}")
	public ResponseEntity<?> getInvestorCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorService.getInvestorCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/country/list/{country}")
	public ResponseEntity<?> getInvestorListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<InvestorViewMapper> list = investorService.getInvestorListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getTeamInvestorListByUnderUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<InvestorViewMapper> leadsViewMapper = investorService.getTeamInvestorListByUnderUserId(userId, pageNo,
					pageSize);
			return new ResponseEntity<>(leadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/teams/count/{userId}")
	public ResponseEntity<?> getTeamInvestorCountByUnderUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorService.getTeamInvestorCountByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	// sk
	@GetMapping("/api/v1/investor/contact/teams/{userId}/{pageNo}")
	public ResponseEntity<Set<ContactViewMapper>> getTeamInvestorContactListByUnderUserId(
			@PathVariable("userId") String userId, @PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<ContactViewMapper> contactList = investorService.getTeamInvestorContactListByUnderUserId(userId, pageNo,
					pageSize);

			return ResponseEntity.ok(contactList);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/contact/teams/count/{userId}")
	public ResponseEntity<?> getTeamInvestorContactCountByUnderUserId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorService.getTeamInvestorContactCountByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/opportunity/won/list/{investorId}")
	public ResponseEntity<?> getWonOpportunityListByInvestorId(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> mappers = investorService.getWonOpportunityListByInvestorId(investorId);
			if (null != mappers && !mappers.isEmpty()) {
				mappers.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(mappers, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/investor/delete/{investorId}")
	public ResponseEntity<?> deleteInvestorById(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorService.deleteInvestorById(investorId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investor/contact/deleted/list/{userId}")
	public ResponseEntity<?> getDeletedInvestorContactList(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> mappers = investorService.getDeletedInvestorContactList(userId);
			if (null != mappers && !mappers.isEmpty()) {
				mappers.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(mappers, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/investor/share/save")
	public ResponseEntity<?> saveShare(@RequestBody InvestorShareMapper investorShareMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorShareMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorShareMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvestorShareMapper id = investorService.saveShare(investorShareMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/get/all/share-list/{investorId}")
	public ResponseEntity<?> getAllInvestorShareList(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorShareMapper> mappers = investorService.getAllInvestorShareList(investorId);
			if (null != mappers && !mappers.isEmpty()) {
				mappers.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(mappers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/get/all-in-one/share/{investorId}")
	public ResponseEntity<?> getAllInvestorShare(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			InvestorShareMapper mappers = investorService.getAllInvestorShare(investorId);
			return new ResponseEntity<>(mappers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/investor/share/{investorShareId}")
	public ResponseEntity<?> deleteInvestorShareByInvestorShareId(
			@PathVariable("investorShareId") String investorShareId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorService.deleteInvestorShareByInvestorShareId(investorShareId,
					jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("Share deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/investor/share/update")
	public ResponseEntity<?> updateInvestorShare(@RequestBody InvestorShareMapper investorShareMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			InvestorShareMapper investorShareMapperr = investorService.updateInvestorShare(investorShareMapper);
			return new ResponseEntity<>(investorShareMapperr, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investor/docType/update")
	public ResponseEntity<?> createAndUpdateInvestorDocType(@RequestBody InvestorDocTypeMapper investorDocTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorDocTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorDocTypeMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(investorService.createAndUpdateInvestorDocType(investorDocTypeMapper),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/getDocTypes/{investorId}")
	public ResponseEntity<?> getDocTypesByInvestor(@PathVariable("investorId") String investorId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return new ResponseEntity<>(investorService.getDocTypesByInvestor(investorId, userId, orgId),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investor/search/alltype/{name}/{type}")
	public ResponseEntity<?> getInvestorByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = investorService.getInvestorDetailsByNameInOrgLevel(name, orgId);
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySectorInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySourceInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByOwnerNameInOrgLevel(name, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByClubInOrgLevel(name, orgId);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = investorService.getInvestorDetailsByNameForTeam(name, userId);
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySectorForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySourceForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByOwnerNameForTeam(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByClubInForTeam(name, userId, orgId);
				}
				return ResponseEntity.ok(list);
			} else {
				list = investorService.getInvestorDetailsByNameInUserLevel(name, userId);
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySectorInUserLevel(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorBySourceInUserLevel(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByOwnerNameInUserLevel(name, userId, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = investorService.getInvestorByClubInUserLevel(name, userId, orgId);
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/investor/club/{userId}/{pageNo}/{clubType}")
	public ResponseEntity<?> getInvestorByUserTypeAndClubType(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("clubType") String clubType,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (userId.equalsIgnoreCase("All")) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				List<InvestorViewMapper> investorList = investorService.getInvestorListByOrgIdAndClubType
						(orgId, pageNo, pageSize,clubType);
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			} else if (userId.equalsIgnoreCase("team")) {
				String loggedUserId = jwtTokenUtil.getUserIdFromToken(authToken);
				Set<InvestorViewMapper> investorList = investorService.getTeamInvestorListByUnderUserIdAndClubType
						(loggedUserId,pageNo, pageSize,clubType);
				return ResponseEntity.ok(investorList);
			} else {
				List<InvestorViewMapper> investorList = investorService.getFilterInvestorsPageWiseByUserIdAndClubType
						(userId,pageNo, pageSize, clubType);
				return new ResponseEntity<>(investorList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
