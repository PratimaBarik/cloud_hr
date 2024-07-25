package com.app.employeePortal.investorleads.controller;

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
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsOpportunityMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsReportMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsViewMapper;
import com.app.employeePortal.investorleads.service.InvestorLeadsService;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)

public class InvestorLeadsController {

	@Autowired
	InvestorLeadsService investorLeadsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ContactService contactService;

	@PostMapping("/api/v1/investorleads")
	public ResponseEntity<?> createInvestorLeads(@RequestBody InvestorLeadsMapper investorleadsMapper,
			@RequestHeader("Authorization") String authorization) throws IOException, TemplateException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorleadsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorleadsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(investorleadsMapper.getEmail())) {
				boolean b = investorLeadsService.getInvestorLeadsByEmail(investorleadsMapper.getEmail());
				if (b == true) {
					map.put("LeadInd", true);
					map.put("message", "Lead with same Email already exists....");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					if (!StringUtils.isEmpty(investorleadsMapper.getUrl())) {
						boolean b1 = investorLeadsService.getInvestorLeadsByUrl(investorleadsMapper.getUrl());
						if (b1 == true) {
							map.put("LeadInd", true);
							map.put("message", "Lead with same url already exists....");
							return new ResponseEntity<>(map, HttpStatus.OK);
						} else {
							InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
							return new ResponseEntity<>(id, HttpStatus.OK);
						}
					} else {
						InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
						return new ResponseEntity<>(id, HttpStatus.OK);
					}
				}

			} else {
				if (!StringUtils.isEmpty(investorleadsMapper.getUrl())) {
					boolean b1 = investorLeadsService.getInvestorLeadsByUrl(investorleadsMapper.getUrl());
					if (b1 == true) {
						map.put("LeadInd", true);
						map.put("message", "Lead with same url already exists....");
						return new ResponseEntity<>(map, HttpStatus.OK);
					} else {
						InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
						return new ResponseEntity<>(id, HttpStatus.OK);
					}
				} else {
					InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);
				}
			}

//			if (!StringUtils.isEmpty(investorleadsMapper.getUrl())) {
//                boolean b = investorLeadsService.getInvestorLeadsByUrl(investorleadsMapper.getUrl());
//                if (b == true) {
//                    map.put("investorLeadsInd", true);
//                    map.put("message", "Pitch with same url already exists....");
//                    return new ResponseEntity<>(map, HttpStatus.OK);
//                } else {
//					InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
//					return new ResponseEntity<>(id, HttpStatus.OK);
//                }
//            }else {
//            	InvestorLeadsViewMapper id = investorLeadsService.saveInvestorLeads(investorleadsMapper);
//				return new ResponseEntity<>(id, HttpStatus.OK);
//            }
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/{investorleadsId}")
	public ResponseEntity<?> getInvestorLeadsDetailsById(@PathVariable("investorleadsId") String investorleadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvestorLeadsViewMapper investorLeadsViewMapper = investorLeadsService
					.getInvestorLeadsDetailsById(investorleadsId);

			return new ResponseEntity<InvestorLeadsViewMapper>(investorLeadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorleads/{investorleadsId}")
	public ResponseEntity<?> updateLeads(@PathVariable("investorleadsId") String investorleadsId,
			@RequestBody InvestorLeadsMapper investorleadsMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			investorleadsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorleadsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			InvestorLeadsViewMapper investorLeadsViewMapper = investorLeadsService.updateLeads(investorleadsId,
					investorleadsMapper);

			return new ResponseEntity<InvestorLeadsViewMapper>(investorLeadsViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/investorleads/{investorleadsId}")
	public ResponseEntity<?> deleteLeads(@PathVariable("investorleadsId") String investorleadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			investorLeadsService.deleteInvestorLeads(investorleadsId, userId, orgId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/user/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getInvestorLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (userId.equalsIgnoreCase("all")) {
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
						.getInvestorLeadsListByOrgId(orgId, pageNo, pageSize, filter);
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);

			} else if (userId.equalsIgnoreCase("team")) {
				String loggedUserId = (jwtTokenUtil.getUserIdFromToken(authToken));
				Set<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
						.getTeamInvestorLeadsDetailsUnderAyUserId(loggedUserId, pageNo, pageSize);
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			} else {
				List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
						.getInvestorLeadsDetailsByUserId(userId, pageNo, pageSize, filter);

				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/contacts/{investorLeadsId}")
	public ResponseEntity<?> getContactListByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> customerDetailsList = investorLeadsService
					.getContactListByInvestorLeadsId(investorLeadsId);
			if (null != customerDetailsList && !customerDetailsList.isEmpty()) {
				customerDetailsList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
			}
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/document/{investorLeadsId}")
	public ResponseEntity<?> getDocumentListByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = investorLeadsService.getDocumentListByInvestorLeadsId(investorLeadsId);
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

	@DeleteMapping("/api/v1/investorLeads/document/{documentId}")
	public ResponseEntity<?> deleteInvestorLeadsDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorLeadsService.deleteDocumentsById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/investorLeads/opportunity")
	public ResponseEntity<?> createOpportunity(
			@RequestBody InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			investorLeadsOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorLeadsOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvestorLeadsOpportunityMapper id = investorLeadsService
					.saveInvestorLeadsOpportunity(investorLeadsOpportunityMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/opportunity/{investorLeadsId}")
	public ResponseEntity<?> getOpportunityListByInvestorLeadsId(
			@PathVariable("investorLeadsId") String investorLeadsId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsOpportunityMapper> opportunityList = investorLeadsService
					.getOpportunityListByInvestorLeadsId(investorLeadsId);
			if (null != opportunityList && !opportunityList.isEmpty()) {
				opportunityList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(opportunityList, HttpStatus.OK);
			}
			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorLeads/opportunity/update/{investorLeadsOppId}")
	public ResponseEntity<?> updateLeadsOpportunity(@PathVariable("investorLeadsOppId") String investorLeadsOppId,
			@RequestBody InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			investorLeadsOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			investorLeadsOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper1 = investorLeadsService
					.updateInvestorLeadsOpportunity(investorLeadsOppId, investorLeadsOpportunityMapper);

			return new ResponseEntity<>(investorLeadsOpportunityMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/investorLeads/opportunity/delete/{investorLeadOppId}")
	public ResponseEntity<?> deleteLeadsOppertunity(@PathVariable("investorLeadOppId") String investorLeadOppId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorLeadsService.deleteLeadsOppertunity(investorLeadOppId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/investorLeads/type/update/{investorLeadsId}/{type}")
	public ResponseEntity<?> updateInvestorLeadsType(@PathVariable("investorLeadsId") String investorLeadsId,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = investorLeadsService.updateInvestorLeadsType(investorLeadsId, type);
			return new ResponseEntity<>(msg, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorLeads/convert/{investorLeadsId}/{assignedToId}")
	public ResponseEntity<?> convertInvestorLeadsById(@PathVariable("investorLeadsId") String investorLeadsId,
			@PathVariable("assignedToId") String assignedToId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws TemplateException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String msg = investorLeadsService.convertInvestorLeadsById(investorLeadsId, assignedToId);

			return new ResponseEntity<String>(msg, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorLeadsService.getCountListByuserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/qualified-investorLeads/count/{userId}")
	public ResponseEntity<?> getQualifiedInvestorLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = investorLeadsService.getQualifiedInvestorLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/qualified-investorLeads/list/{userId}")
	public ResponseEntity<?> getQualifiedInvestorLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> investorleadsViewMapper = investorLeadsService
					.getQualifiedInvestorLeadsDetailsByUserId(userId, startDate, endDate);
			if (null != investorleadsViewMapper && !investorleadsViewMapper.isEmpty()) {
				investorleadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorleadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorleadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorLeads/reinstate/transfer/{userId}")
	public ResponseEntity<?> ReinstateInvestorLeadsToJunk(@PathVariable("userId") String userId,
			@RequestBody TransferMapper transferMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String message = investorLeadsService.ReinstateInvestorLeadsToJunk(userId, transferMapper);

			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/createded-investorLeads/count/{userId}")
	public ResponseEntity<?> getCreatededInvestorLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = investorLeadsService.getInvestorCreatededLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/createded-investorLeads/list/{userId}")
	public ResponseEntity<?> getCreatededInvestorLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getCreatededInvestorLeadsDetailsByUserId(userId, startDate, endDate);
			if (null != investorLeadsViewMapper && !investorLeadsViewMapper.isEmpty()) {
				investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/junked/count/{userId}")
	public ResponseEntity<?> getJunkedInvestorLeadsCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = investorLeadsService.getJunkedInvestorLeadsCountByUserId(userId);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/junked/list/{userId}")
	public ResponseEntity<?> getJunkedInvestorLeadsListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getJunkedInvestorLeadsListByUserId(userId);
			if (null != investorLeadsViewMapper && !investorLeadsViewMapper.isEmpty()) {
				investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/junked-investorleadsleads/count/{userId}")
	public ResponseEntity<?> getJunkedInvestorLeadsListCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = investorLeadsService.getJunkedInvestorLeadsListCountByUserId(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/junked-investorleadsleads/list/{userId}")
	public ResponseEntity<?> getJunkedInvestorLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getJunkedInvestorLeadsDetailsByUserId(userId, startDate, endDate);
			if (null != investorLeadsViewMapper && !investorLeadsViewMapper.isEmpty()) {
				investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorLeads/transfer/one-user-to-another/{userId}")
	public ResponseEntity<?> transferInvestorLeadsOneUserToAnother(@RequestBody TransferMapper TransferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> investorleadsList = investorLeadsService.transferInvestorLeadsOneUserToAnother(userId,
					TransferMapper);

			return new ResponseEntity<>(investorleadsList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/type/hot-warm-cold/count/date-range/{userId}")
	public ResponseEntity<?> getInvestorLeadsListCountByUserIdAndtypeAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = investorLeadsService.getInvestorLeadsListCountByUserIdAndtypeAndDateRange(userId, startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/added/count/date-wise/{userId}")
	public ResponseEntity<?> getAddedInvestorLeadsListCountByUserIdWithDateWise(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Double>> map = investorLeadsService
					.getAddedInvestorLeadsListCountByUserIdWithDateWise(userId, startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/source-wise/count/{userId}")
	public ResponseEntity<?> getLeadsCountSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, Double>> map = investorLeadsService.getInvestorLeadsCountSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/source-wise/list/{userId}")
	public ResponseEntity<?> getLeadsListSourceWiseByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<Map<String, List<InvestorLeadsViewMapper>>> map = investorLeadsService
					.getInvestorLeadsListSourceWiseByUserId(userId, orgId);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorLeads/type-hot/date-range/{userId}")
	public ResponseEntity<?> getInvestorLeadsHotByUserIdAndTypeAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsViewMapper> list = investorLeadsService
					.getInvestorLeadsHotByUserIdAndTypeAndDateRange(userId, startDate, endDate);
			if (null != list && !list.isEmpty()) {
				list.sort((c1, c2) -> c2.getCreationDate().compareTo(c1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/type-cold/date-range/{userId}")
	public ResponseEntity<?> getInvestorLeadsColdByUserIdAndTypeAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsViewMapper> list = investorLeadsService
					.getInvestorLeadsColdByUserIdAndTypeAndDateRange(userId, startDate, endDate);
			if (null != list && !list.isEmpty()) {
				list.sort((c1, c2) -> c2.getCreationDate().compareTo(c1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/type-warm/date-range/{userId}")
	public ResponseEntity<?> getInvestorLeadsWarmByUserIdAndTypeAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsViewMapper> list = investorLeadsService
					.getInvestorLeadsWarmByUserIdAndTypeAndDateRange(userId, startDate, endDate);
			if (null != list && !list.isEmpty()) {
				list.sort((c1, c2) -> c2.getCreationDate().compareTo(c1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/activity/list/{investorLeadsId}")
	public ResponseEntity<?> getActivityListByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ActivityMapper> activityMapper = investorLeadsService
					.getActivityListByInvestorLeadsId(investorLeadsId);
			if (null != activityMapper && !activityMapper.isEmpty()) {
				Collections.sort(activityMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(activityMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/search/{name}")
	public ResponseEntity<?> getCustomerByNameAndSector(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization) {

		List<InvestorLeadsViewMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = investorLeadsService.getInvestorLeadsListByName(name);

			if (null == list || list.isEmpty()) {
				list = investorLeadsService.getInvestorLeadsBySector(name);
			}
			if (null == list || list.isEmpty()) {
				list = investorLeadsService.getInvestorLeadsByOwnerName(name);
			}

			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/investorleads/notes")
	public ResponseEntity<?> createLeadsNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = investorLeadsService.saveInvestorLeadsNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/note/{investorLeadsId}")

	public ResponseEntity<?> getNoteListByLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = investorLeadsService.getNoteListByInvestorLeadsId(investorLeadsId);
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

//	@PutMapping("/api/v1/investorleads/note/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = investorLeadsService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/investorleads/note/{notesId}")
	public ResponseEntity<?> deleteCustomerNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorLeadsService.deleteInvestorLeadsNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/all/{pageNo}/{filter}")
	public ResponseEntity<?> getInvestorLeadsDetailsByUserId(@PathVariable("pageNo") int pageNo,
			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getInvestorLeadsListByOrgId(orgId, pageNo, pageSize, filter);
			if (null != investorLeadsViewMapper && !investorLeadsViewMapper.isEmpty()) {
//	                investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/all/record/count/{orgId}")
	public ResponseEntity<?> getInvestorLeadsCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorLeadsService.getInvestorLeadsCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getTeamInvestorLeadsDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getTeamInvestorLeadsDetailsByUserId(userId, pageNo, pageSize, filter);
			if (null != investorLeadsViewMapper && !investorLeadsViewMapper.isEmpty()) {
//	                investorLeadsViewMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/activity/record/{investorLeadsId}")
	public ResponseEntity<?> getActivityRecordByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorLeadsService.getActivityRecordByInvestorLeadsId(investorLeadsId));

		} else {

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/api/v1/investorleads/all/count")
	public ResponseEntity<?> getInvestorLeadsAllCount(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return new ResponseEntity<>(investorLeadsService.getInvestorLeadsAllCount(orgId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/report/all-investorleads/enterprise/{orgId}")
	public ResponseEntity<?> getAllInvestorByOrgIdForReport(@PathVariable("orgId") String orgId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsReportMapper> list = investorLeadsService.getAllInvestorLeadsByOrgIdForReport(orgId,
					startDate, endDate);
			if (null != list && !list.isEmpty()) {
				list.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/report/all-investorleads/self/{userId}")
	public ResponseEntity<?> getAllInvestorLeadsByUserIdForReport(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsReportMapper> list = investorLeadsService.getAllInvestorLeadsByUserIdForReport(userId,
					startDate, endDate);
			if (null != list && !list.isEmpty()) {
				list.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorLeads/report/all-qualified-investorLeads/enterprise/{orgId}")
	public ResponseEntity<?> getAllInvestorQualifiedLeadsListByOrgIdForReport(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@PathVariable("orgId") String orgId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorLeadsReportMapper> investorLeadsContactList = investorLeadsService
					.getAllInvestorQualifiedLeadsListByOrgIdForReport(orgId, startDate, endDate);
			if (null != investorLeadsContactList && !investorLeadsContactList.isEmpty()) {
				investorLeadsContactList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(investorLeadsContactList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(investorLeadsContactList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getTeamInvestorLeadsDetailsUnderAyUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<InvestorLeadsViewMapper> investorLeadsViewMapper = investorLeadsService
					.getTeamInvestorLeadsDetailsUnderAyUserId(userId, pageNo, pageSize);

			return new ResponseEntity<>(investorLeadsViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/teams/count/{userId}")
	public ResponseEntity<?> getTeamInvestorLeadsCountUnderAyUserId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorLeadsService.getTeamInvestorLeadsCountUnderAyUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/User/{userId}/{pageNo}/{filter}/{type}")
	public ResponseEntity<?> getInvLeadsDetailsByUserIdAndType(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("type") String type,
			@PathVariable("filter") String filter, @RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> viewMapper = investorLeadsService.getInvLeadsDetailsByUserIdAndType(userId,
					pageNo, pageSize, filter, type);

			if (null != viewMapper) {
				return new ResponseEntity<>(viewMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(viewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorleads/teams/{userId}/{pageNo}/{type}")
	public ResponseEntity<?> getTeamInvLeadsDetailsByUnderAUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable String type,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorLeadsViewMapper> viewMapper = investorLeadsService.getTeamInvLeadsDetailsByUnderAUserId(userId,
					pageNo, pageSize, type);

			return new ResponseEntity<>(viewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/all/{pageNo}/{filter}/{type}")
	public ResponseEntity<?> getInvLeadsListByOrgId(@PathVariable("pageNo") int pageNo,
			@PathVariable("filter") String filter, @PathVariable String type,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 5;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorLeadsViewMapper> viewMapper = investorLeadsService.getInvLeadsListByOrgId(orgId, pageNo,
					pageSize, filter, type);
			return new ResponseEntity<>(viewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/investorleads/delete/user/{userId}")
	public ResponseEntity<?> deletePitchUserLevel(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorLeadsService.deletePitchUserLevel(userId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorleads/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorLeadsViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = investorLeadsService.getInvLeadsDetailsByNameByOrgLevel(name, orgId);
				System.out.println("By Name");
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsBySectorInOrgLevel(name, orgId);
					System.out.println("By Sector");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsBySourceInOrgLevel(name, orgId);
					System.out.println("By Source");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsByOwnerNameInOrgLevel(name, orgId);
					System.out.println("By Owner");
				}

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = investorLeadsService.getInvLeadsDetailsByNameForTeam(name, userId);
				System.out.println("By Name");
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsBySectorForTeam(name, userId, orgId);
					System.out.println("By Sector");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsBySourceForTeam(name, userId, orgId);
					System.out.println("By Source");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsDetailsByOwnerNameForTeam(name, userId, orgId);
					System.out.println("By Owner");
				}
				return ResponseEntity.ok(list);
			} else {
				list = investorLeadsService.getInvLeadsDetailsByNameByUserId(name, userId);
				System.out.println("By Name");
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsBySectorInUserLevel(name, userId, orgId);
					System.out.println("By Sector");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsBySourceInUserLevel(name, userId, orgId);
					System.out.println("By Source");
				}
				if (null == list || list.isEmpty()) {
					list = investorLeadsService.getInvLeadsByOwnerNameInUserLevel(name, userId, orgId);
					System.out.println("By Owner");
				}
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}