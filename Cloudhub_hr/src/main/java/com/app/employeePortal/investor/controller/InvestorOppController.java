package com.app.employeePortal.investor.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.app.employeePortal.investorleads.mapper.InvestorLeadsViewMapper;
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
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.InvestorOppFundRequest;
import com.app.employeePortal.investor.mapper.InvestorOppFundResponse;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;
import com.app.employeePortal.investor.service.InvestorOppService;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class InvestorOppController {
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	InvestorOppService investorOppService;

	@PostMapping("/api/v1/investorOpportunity")
	public ResponseEntity<?> createInvOpportunity(@RequestBody InvestorOpportunityMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws TemplateException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvestorOpportunityMapper opportunityId = investorOppService.saveInvestorOpportunity(opportunityMapper);

			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/user/{userId}/{pageNo}")
	public ResponseEntity<?> getInvOppListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (userId.equalsIgnoreCase("All")) {
//                List<InvestorOpportunityMapper> oppList = investorOppService.getAllOpportunityList(pageNo, pageSize);
//                oppList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

				List<InvestorOpportunityMapper> oppList = investorOppService
						.getOpportunityDetailsListPageWiseByOrgId(orgId, pageNo, pageSize);
				if (null != oppList && !oppList.isEmpty()) {
					return ResponseEntity.ok(oppList);
				} else {
					return new ResponseEntity<>(oppList, HttpStatus.OK);
				}
			} else {
				List<InvestorOpportunityMapper> oppList = investorOppService
						.getOpportunityDetailsListPageWiseByUserId(userId, pageNo, pageSize);
				if (null != oppList && !oppList.isEmpty()) {
					oppList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
					return new ResponseEntity<>(oppList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(oppList, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/{invOpportunityId}")

	public ResponseEntity<?> getInvOpportunityById(@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvestorOpportunityMapper opportunityMapper = investorOppService.getOpportunityDetails(invOpportunityId);

			System.out.println("get opportunityMapper" + opportunityMapper.toString());
			return ResponseEntity.ok(opportunityMapper);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/investorOpportunity/{invOpportunityId}")
	public ResponseEntity<?> updateInvOpportunityDetails(@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestBody InvestorOpportunityMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws TemplateException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			opportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			InvestorOpportunityMapper opportunityMapperr = investorOppService
					.updateInvOpportunityDetails(invOpportunityId, opportunityMapper);

			return new ResponseEntity<InvestorOpportunityMapper>(opportunityMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/investorOpportunity/notes")
	public ResponseEntity<?> createInvOpportunityNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = investorOppService.saveInvOpportunityNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/notes/investorOpportunity/{invOpportunityId}")

	public ResponseEntity<?> getNotesByInvOpportunityId(@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = investorOppService.getNoteListByInvOpportunityId(invOpportunityId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				notesMapper
						.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}
			// notesMapper.sort((NotesMapper m1, NotesMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			// return new ResponseEntity<>(notesMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/contact/{contactId}")

	public ResponseEntity<?> getInvOppListByContactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> oppList = investorOppService
					.getInvOpportunityDetailsListByContactId(contactId);
			oppList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/document/{invOpportunityId}")

	public ResponseEntity<?> getInvOppListByDocumentId(@PathVariable("invOpportunityId") String invOpportunityId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> oppList = investorOppService
					.getDocumentDetailsListByinvOpportunityId(invOpportunityId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/investorOpportunity/document/{documentId}")
	public ResponseEntity<?> deleteInvestorOpportunityDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			investorOppService.deleteDocumentById(documentId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/details/{opportunityName}")
	public ResponseEntity<?> getOpportunityDetailsByName(@PathVariable("opportunityName") String opportunityName,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<InvestorOpportunityMapper> oppertunity = investorOppService
					.getInvOpportunityDetailsByName(opportunityName);

			oppertunity.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppertunity, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/investorOpportunity/contact")
	public ResponseEntity<?> createInvOPPContact(@RequestBody ContactMapper contactMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			ContactViewMapper opportunityId = investorOppService.saveInvOpportunityContact(contactMapper);
			return new ResponseEntity<>(opportunityId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//
	@GetMapping("/api/v1/investorOpportunity/contact/details/{invOpportunityId}")

	public ResponseEntity<?> getContactListByPartnerId(@PathVariable("invOpportunityId") String invOpportunityId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ContactViewMapper> contactList = investorOppService.getContactListByInvOpportunityId(invOpportunityId);
			contactList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/investorOpportunity/delete/{invOpportunityId}")
	public ResponseEntity<?> deleteInvOpportunityDetails(@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws TemplateException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.deleteOpportunityDetailsById(invOpportunityId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}


    @GetMapping("/api/v1/investorOpportunity/deleteHistory/{pageNo}")
    public ResponseEntity<?> getAllOpportunitDeleteHistory(@RequestHeader("Authorization") String authorization,
                                                           @PathVariable("pageNo") int pageNo, HttpServletRequest request) {
        int pageSize = 20;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String loggeduserId = jwtTokenUtil.getUserIdFromToken(authToken);
            List<InvestorOpportunityMapper> opportunityMapper = investorOppService
                    .getDeletedInnOpportunityDetails(loggeduserId, pageNo, pageSize);

            System.out.println("get opportunityMapper" + opportunityMapper.toString());
            Collections.sort(opportunityMapper, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
            return new ResponseEntity<>(opportunityMapper, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

//    @GetMapping("/api/v1/recruiter/opportunity/{recruiterId}")
//    public List<OpportunityViewMapper> getOpportunityOfRecruiter(@PathVariable("recruiterId") String recruiterId,
//                                                                 @RequestHeader("Authorization") String authorization, HttpServletRequest request)
//            throws JsonGenerationException, JsonMappingException, Exception {
//
//        List<OpportunityViewMapper> mapperList = null;
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//            mapperList = oppertunityService.getOpprtunityByRecruiterId(recruiterId);
//            // Collections.sort(mapperList, (v1, v2) ->
//            // v2.getCreationDate().compareTo(v1.getCreationDate()));
//
//        }
//
//        return mapperList;
//
//    }
//
	@GetMapping("/api/v1/salesUser/investorOpportunity/{userId}")

	public ResponseEntity<List<InvestorOpportunityMapper>> getOpportunityOfASalesUser(
			@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> oppList = investorOppService.getInvOpportunityOfASalesUser(userId);

			oppList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//
//    /*
//     * fetch note list by customerId
//     *
//     * @GetMapping("/api/v1/customer/note/{opportunityId}")
//     *
//     * public ResponseEntity<?>
//     * getNoteListByCustomerId(@PathVariable("opportunityId") String opportunityId,
//     *
//     * @RequestHeader("Authorization") String authorization ,HttpServletRequest
//     * request) {
//     *
//     *
//     * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//     * List<NotesMapper> notesMapper =
//     * oppertunityService.getNoteListByOpportunityId(opportunityId);
//     * Collections.sort(notesMapper, (NotesMapper m1, NotesMapper m2) ->
//     * m2.getCreationDate() .compareTo(m1.getCreationDate())); return new
//     * ResponseEntity<>(notesMapper,HttpStatus.OK);
//     *
//     * }
//     *
//     * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
//     *
//     */
//
	@GetMapping("/api/v1/investorOpportunity/record/count/{userId}")
	public ResponseEntity<?> getInvestorOpportunityCountByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getInvestorOpportunityCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/all/record/count/{orgId}")
	public ResponseEntity<?> getInvestorOpportunityCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getInvestorOpportunityCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
//
//    @PostMapping("/api/v1/opportunity/tagContact")
//    public ResponseEntity<?> tagContact(@RequestBody ContactMapper contactMapper,
//                                        @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            contactMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//            contactMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
//
//            String opportunityId = oppertunityService.saveTagContact(contactMapper);
//            return new ResponseEntity<>(opportunityId, HttpStatus.OK);
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }

	@GetMapping("/api/v1/investorOpportunities/{userId}")
	public ResponseEntity<?> getOpportunityOfUser(@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> userOppList = investorOppService
					.getInvOpportunityDetailsListByUserId(userId);
//            List<InvestorOpportunityMapper> recruiterOppList = investorOppService.getInvOpprtunityByRecruiterId(userId);
			List<InvestorOpportunityMapper> salesUserList = investorOppService.getInvOpportunityOfASalesUser(userId);

			if (null != userOppList && !userOppList.isEmpty()) {

				userOppList.sort((v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(userOppList, HttpStatus.OK);

			}
//            if (null != recruiterOppList && !recruiterOppList.isEmpty()) {
//
//                // Collections.sort(recruiterOppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//                return new ResponseEntity<>(recruiterOppList, HttpStatus.OK);
//            }
			if (null != salesUserList && !salesUserList.isEmpty()) {

				// Collections.sort(salesUserList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(salesUserList, HttpStatus.OK);
			}

			return new ResponseEntity<>(salesUserList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//
//    @GetMapping("api/v1/opportunities/all-opportunities/{pageNo}")
//    public ResponseEntity<List<OpportunityViewMapper>> getAllOpportunityList(@PathVariable("pageNo") int pageNo,
//                                                                             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//        int pageSize = 20;
//        List<OpportunityViewMapper> oppList = oppertunityService.getAllOpportunityList(pageNo, pageSize);
//        Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
//        return ResponseEntity.ok(oppList);
//    }
//
	@PutMapping("/api/v1/investorOpportunity/reinstate/{invOpportunityId}")
	public ResponseEntity<?> reinstateOpportunity(@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.reinstateOpportunityByOppId(invOpportunityId);

			return new ResponseEntity<>("Reinstate successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//
//    @GetMapping("/api/v1/opportunity/recruiter/record/count/{userId}")
//    public ResponseEntity<?> getRecruitrtCountByuserId(@RequestHeader("Authorization") String authorization,
//                                                       @PathVariable("userId") String userId) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//            return ResponseEntity.ok(oppertunityService.getRecruitrtCountByuserId(userId));
//        }
//        return null;
//
//    }
//
//    @GetMapping("/api/v1/opportunity/jobOrderName/{jobOrder}")
//    public ResponseEntity<?> getOpportunityListByJobOrder(@PathVariable("jobOrder") String jobOrder,
//                                                          @RequestHeader("Authorization") String authorization, HttpServletRequest request)
//            throws JsonGenerationException, JsonMappingException, IOException {
//
//        List<RecruitmentOpportunityMapper> typeList = null;
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//            typeList = oppertunityService.getOpportunityListByJobOrder(jobOrder);
//
//            if (null != typeList) {
//
//                return new ResponseEntity<>(typeList, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("This job ID is not available", HttpStatus.OK);
//            }
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    /*
//     * @GetMapping("/api/v1/opportunity/count") public ResponseEntity<?>
//     * getNoOfOpportunityBycreationDate(@RequestHeader("Authorization") String
//     * authorization, HttpServletRequest request) {
//     *
//     * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) { String
//     * authToken = authorization.replace(TOKEN_PREFIX, ""); return
//     * ResponseEntity.ok(oppertunityService.getNoOfOpportunityBycreationDate());
//     *
//     * }
//     *
//     * return null;
//     *
//     * }
//     */
//    @PostMapping("/api/v1/opportunity/tag/contact")
//    public ResponseEntity<?> createTagContactWithOppertunity(@RequestBody OpportunityMapper OpportunityMapper,
//                                                             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String opportunityId = oppertunityService.saveTagContactWithOppertunity(OpportunityMapper);
//            return new ResponseEntity<>(opportunityId, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
	@PutMapping("/api/v1/investorOpportunity/transfer/{userId}")
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody TransferMapper transferMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> oppList = investorOppService.updateTransferOneUserToAnother(userId, transferMapper);

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//
//    @PutMapping("/api/v1/opportunity/update/contact/Role/{contactId}")
//    public ResponseEntity<?> updateContactRoleByContactId(@RequestBody ContactViewMapper contactViewMapper,
//                                                          @PathVariable("contactId") String contactId, HttpServletRequest request,
//                                                          @RequestHeader("Authorization") String authorization) {
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            ContactViewMapper contactList = oppertunityService.updateContactRoleByContactId(contactId,
//                    contactViewMapper);
//
//            return new ResponseEntity<>(contactList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @PutMapping("/api/v1/opportunity/update/tagCustomer/{opportunityId}")
//    public ResponseEntity<?> updateCustomerByOpportunityId(@RequestBody OpportunityViewMapper opportunityViewMapper,
//                                                           @PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
//                                                           HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            OpportunityViewMapper opportunityViewMapperr = oppertunityService
//                    .updateCustomerByOpportunityId(opportunityId, opportunityViewMapper);
//
//            return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @PostMapping("/api/v1/opportunity/Innitiative/skill/number")
//    public ResponseEntity<?> saveInnitiativeSkillAndNumber(@RequestBody OpportunityMapper opportunityMapper,
//                                                           @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//        List<OpportunitySkillLinkMapper> opportunitySkillLinkMapper = null;
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            opportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//            opportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//            opportunitySkillLinkMapper = oppertunityService.saveInnitiativeSkillAndNumber(opportunityMapper);
//
//            return new ResponseEntity<>(opportunitySkillLinkMapper, HttpStatus.OK);
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @GetMapping("api/v1/opportunities/Innitiative/skill/number/{opportunityId}")
//    public ResponseEntity<?> getSkillAndNumberByOppotunityId(@PathVariable("opportunityId") String opportunityId,
//                                                             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            List<OpportunitySkillLinkMapper> opportunitySkillLinkMapper = oppertunityService
//                    .getSkillAndNumberByOppotunityId(opportunityId);
//            // Collections.sort(oppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//            return new ResponseEntity<>(opportunitySkillLinkMapper, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
	@GetMapping("/api/v1/investorOpportunity/deleteHistory/record/count/{userId}")
	public ResponseEntity<?> getDeleteHistoryCountList(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorOppService.getDeleteCountList(userId));
		}
		return null;
	}

	@PutMapping("/api/v1/investorOpportunities/update/wonInd/{invOpportunityId}")
	public ResponseEntity<?> updateOpportunityWonIndByOpportunityId(
			@RequestBody InvestorOpportunityMapper opportunityMapper,
			@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.updateOpportunityWonIndByInvOpportunityId(invOpportunityId, opportunityMapper);
			Map map = new HashMap();
			if (opportunityMapper.isWonInd()) {
				map.put("message", "Opportunity Successfully Won");
			} else {
				map.put("message", "Opportunity not Won");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/investorOpportunity/update/lostInd/{invOpportunityId}")
	public ResponseEntity<?> updateOpportunityLostIndByOpportunityId(
			@RequestBody InvestorOpportunityMapper opportunityMapper,
			@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.updateOpportunityLostIndByInOpportunityId(invOpportunityId, opportunityMapper);
			if (opportunityMapper.isLostInd()) {
				map.put("message", "Opportunity Successfully Lost");
			} else {
				map.put("message", "Opportunity not Lost");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/investorOpportunity/update/closeInd/{invOpportunityId}")
	public ResponseEntity<?> updateOpportunityCloseIndByOpportunityId(
			@RequestBody InvestorOpportunityMapper opportunityMapper,
			@PathVariable("invOpportunityId") String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		HashMap map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.updateOpportunityCloseIndByInOpportunityId(invOpportunityId, opportunityMapper);
			if (opportunityMapper.isCloseInd()) {
				map.put("message", "Opportunity Succesfully Closed");
			} else {
				map.put("message", "Opportunity Got Opened");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/closeInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndCloseInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> opportunityList = investorOppService
					.getOpportunityDetailByUserIdAndCloseInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/lostInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndLostInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> opportunityList = investorOppService
					.getOpportunityDetailByUserIdAndLostInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/WonInd/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndWonInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> opportunityList = investorOppService
					.getOpportunityDetailByUserIdAndWonInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunity/Deleted/{userId}/{pageNo}")

	public ResponseEntity<?> getOpportunityDetailByUserIdAndDeleteInd(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> opportunityList = investorOppService
					.getOpportunityDetailByUserIdAndDeleteInd(userId, pageNo, pageSize);

			return new ResponseEntity<>(opportunityList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

//
//    @PostMapping("/api/v1/opportunity/forecast")
//    public ResponseEntity<?> saveForecast(@RequestBody OpportunityForecastLinkMapper opportunityMapper,
//                                          @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//        OpportunityForecastLinkMapper opportunityForecastLinkMapper = null;
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            //String authToken = authorization.replace(TOKEN_PREFIX, "");
//            opportunityForecastLinkMapper = oppertunityService.saveForecast(opportunityMapper);
//
//            return new ResponseEntity<>(opportunityForecastLinkMapper, HttpStatus.OK);
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @GetMapping("api/v1/opportunities/forecast/skill/number/{opportunityId}")
//    public ResponseEntity<?> getForecastSkillAndNumberByOppotunityId(
//            @PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
//            HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            List<OpportunityForecastLinkMapper> opportunityForecastLinkMapper = oppertunityService
//                    .getForecastSkillAndNumberByOppotunityId(opportunityId);
//            // Collections.sort(oppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//            return new ResponseEntity<>(opportunityForecastLinkMapper, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @PutMapping("/api/v1/opportunity/update/forecast/{opportunityId}")
//    public ResponseEntity<?> updateOpportunityForecastOpportunityId(@RequestBody OpportunityMapper opportunityMapper,
//                                                                    @PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
//                                                                    HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            List<OpportunityForecastLinkMapper> opportunityForecastMapper = oppertunityService
//                    .updateOpportunityForecastOpportunityId(opportunityId, opportunityMapper);
//
//            return new ResponseEntity<>(opportunityForecastMapper, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
	@GetMapping("/api/v1/InvestorOpportunity/CloseInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByCloseInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getOpportunityListByCloseInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/LostInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByLostInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorOppService.getOpportunityListByLostInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/WonInd/record/count/{userId}")
	public ResponseEntity<?> getOpportunityListByWonInd(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorOppService.getOpportunityListByWonInd(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//
//    @GetMapping("/api/v1/opportunity/employee/create/all-employees")
//
//    public ResponseEntity<?> getEmployeeListByOrgIdForOpportunityCreate(HttpServletRequest request,
//                                                                        @RequestHeader("Authorization") String authorization) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);
//
//            List<EmployeeViewMapper> empList = employeeService
//                    .getEmployeeListByOrgIdForOpportunityCreate(organizationId);
//            // Collections.sort(empList, ( m1, m2) ->
//            // m2.getCreationDate().compareTo(m1.getCreationDate()));
//            return new ResponseEntity<>(empList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @GetMapping("/api/v1/opportunity/open-recruitment/open-position/count/{opportunityId}")
//
//    public ResponseEntity<?> getOpenRecruitmentAndOpenPositionCountByOpportunityId(HttpServletRequest request,
//                                                                                   @PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);
//
//            return ResponseEntity
//                    .ok(oppertunityService.getOpenRecruitmentAndOpenPositionCountByOpportunityId(opportunityId));
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @DeleteMapping("/api/v1/opportunity/update/ReinstateInd/True/only/{opportunityId}")
//    public ResponseEntity<?> updateOpportunityReinstateIndOnlyTrue(@PathVariable("opportunityId") String opportunityId,
//                                                                   @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            OpportunityViewMapper opportunityViewMapperr = oppertunityService
//                    .updateOpportunityReinstateIndOnlyTrue(opportunityId);
//
//            return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @DeleteMapping("/api/v1/opportunity/update/closeInd/True/only/{opportunityId}")
//    public ResponseEntity<?> updateOpportunityCloseIndOnlyTrue(@PathVariable("opportunityId") String opportunityId,
//                                                               @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            OpportunityViewMapper opportunityViewMapperr = oppertunityService
//                    .updateOpportunityCloseIndOnlyTrue(opportunityId);
//
//            return new ResponseEntity<>(opportunityViewMapperr, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
	@PutMapping("/api/v1/investorOpportunity/update/stage")
	public ResponseEntity<?> updateStage(@RequestBody InvestorOpportunityMapper opportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		InvestorOpportunityMapper opportunityMapperNew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			opportunityMapperNew = investorOppService.updateStage(opportunityMapper);

			return new ResponseEntity<>(opportunityMapperNew, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
//
//    @GetMapping("/api/v1/opportunity/forecast/Lists/{orgId}")
//    public ResponseEntity<?> getforecastByOrgrIdMonthWise(@PathVariable("orgId") String orgId,
//                                                          @RequestHeader("Authorization") String authorization
//            , HttpServletRequest request) throws JsonGenerationException,
//            JsonMappingException, IOException {
//
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//            Map<String, List<OpportunityForecastLinkMapper>> mapperList = oppertunityService.getforecastByOrgrIdMonthWise(orgId);
//
//            return new ResponseEntity<>(mapperList, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @GetMapping("/api/v1/opportunityList/{userId}")
//    public ResponseEntity<List<OpportunityViewMapper>> opportunityList(@PathVariable("userId") String userId,
//                                                                       HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            List<OpportunityViewMapper> userOppList = oppertunityService.getOpportunityDetailsListByUserId(userId);
//            userOppList.sort((v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//            return new ResponseEntity<>(userOppList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @GetMapping("/api/v1/opportunity/contact/count/{opportunityId}")
//    public ResponseEntity<?> getOpportunityContactCountByCustomerId(@RequestHeader("Authorization") String authorization,
//                                                                    @PathVariable("opportunityId") String opportunityId) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//            return ResponseEntity.ok(oppertunityService.getOpportunityContactCountByCustomerId(opportunityId));
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//
//    @GetMapping("/api/v1/opportunity/document/count/{opportunityId}")
//    public ResponseEntity<?> getOpportunityDocumentCountByCustomerId(@RequestHeader("Authorization") String authorization,
//                                                                     @PathVariable("opportunityId") String opportunityId) {
//
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//            return ResponseEntity.ok(oppertunityService.getOpportunityDocumentCountByCustomerId(opportunityId));
//        }
//        return null;
//    }

	@GetMapping("/api/v1/InvestorOpportunity/Close/record/count/date-range/{userId}")
	public ResponseEntity<?> getCloseOpportunityCountByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getCloseOpportunityCountByUserIdAndDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/added/record/count/date-range/{userId}")
	public ResponseEntity<?> getAddedOpportunityCountByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getAddedOpportunityCountByUserIdAndDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/ClosedList/date-range/{userId}")
	public ResponseEntity<?> getClosedOpportunitiesByUserIdDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getClosedOpportunitiesByUserIdDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/added/date-range/{userId}")
	public ResponseEntity<?> getOpenOpportunitiesByUserIdDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getOpenOpportunitiesByUserIdDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/InvestorOpportunity/note/update")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = investorOppService.updateNoteDetails(notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/InvestorOpportunity/note/{notesId}")
	public ResponseEntity<?> deleteInvestorOpportunityNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			investorOppService.deleteInvestorOpportunityNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunit/details/{investorOppWorkflowId}")
	public List<?> getInvestorOpportunitByInvOppWorkFlowId(
			@PathVariable("investorOppWorkflowId") String investorOppWorkflowId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<InvestorOpportunityMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			mapperList = investorOppService.getInvestorOpportunitByInvOppWorkFlowId(investorOppWorkflowId);

		}

		return mapperList;

	}

	@GetMapping("/api/v1/investorOpportunit/team/{userId}/{pageNo}/{filter}")
	public ResponseEntity<?> getTeamInnvestorOppDetailsByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @PathVariable("filter") String filter,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<InvestorOpportunityMapper> oppViewMapper = investorOppService.getTeamInnvestorOppDetailsByUserId(userId,
					pageNo, pageSize, filter);
			return new ResponseEntity<>(oppViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunit/contact/team/count/{userId}")
	public ResponseEntity<?> getTeamInvestorOppContactCountByUserId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(investorOppService.getTeamInvestorOppContactCountByUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/details/investor/{investorId}")
	public ResponseEntity<?> getInvestorOppByinvestorId(@RequestHeader("Authorization") String authorization,
			@PathVariable("investorId") String investorId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> mapperList = investorOppService.getInvestorOppByinvestorId(investorId);
			if (null != mapperList && !mapperList.isEmpty()) {
				mapperList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return ResponseEntity.ok(mapperList);
		}
		return null;

	}

	@GetMapping("/api/v1/investorOpportunity/included/user/{userId}/{pageNo}")
	public ResponseEntity<?> getInvestorOppDetailsListPageWiseByIncludedUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> oppList = investorOppService
					.getInvestorOppDetailsListPageWiseByIncludedUserId(userId, pageNo, pageSize);
			if (null != oppList && !oppList.isEmpty()) {
				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			}
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunity/included/record/count/{userId}")
	public ResponseEntity<?> getCountListByIncludedUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getCountListByIncludedUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/closedList/date-range/{orgId}")
	public ResponseEntity<?> getClosedOpportunitiesByOrgIdDateRange(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getClosedOpportunitiesByOrgIdDateRange(orgId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/openList/date-range/{orgId}")
	public ResponseEntity<?> getOpenOpportunitiesByOrgIdDateRange(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity
					.ok(investorOppService.getOpenOpportunitiesByOrgIdDateRange(orgId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/List/date-range/{orgId}")
	public ResponseEntity<?> getOpportunitiesByOrgIdDateRange(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getOpportunitiesByOrgIdDateRange(orgId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/InvestorOpportunity/List/date-range/self/{userId}")
	public ResponseEntity<?> getOpportunitiesByUserIdDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getOpportunitiesByUserIdDateRange(userId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getTeamInnvestorOppDetailsByUnderAUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Set<InvestorOpportunityMapper> oppViewMapper = investorOppService
					.getTeamInnvestorOppDetailsByUnderAUserId(userId, pageNo, pageSize);
			return new ResponseEntity<>(oppViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunit/teams/count/{userId}")
	public ResponseEntity<?> getTeamInvestorOppContactCountByUnderAUserId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(investorOppService.getTeamInvestorOppContactCountByUnderAUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/open/count/contact/{contactId}")
	public ResponseEntity<?> getOpenInvOppCountBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(investorOppService.getOpenInvOppCountByContactId(contactId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/investorOpportunit/open/List/contact/{contactId}")
	public ResponseEntity<?> getOpenInvOppListBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> oppList = investorOppService.getOpenInvOppListBycontactId(contactId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/investorOpportunit/won/count/contact/{contactId}")
	public ResponseEntity<?> getWonInvOppCountBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(investorOppService.getWonInvOppCountBycontactId(contactId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/investorOpportunit/won/List/contact/{contactId}")
	public ResponseEntity<?> getWonInvOppListBycontactId(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> oppList = investorOppService.getWonInvOppListBycontactId(contactId);
			Collections.sort(oppList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/investorOpportunit/proposal/value/count/contact/{contactId}")
	public ResponseEntity<?> getInvestorOppProposalValueCountByContactId(@RequestHeader("Authorization") String authorization,
			@PathVariable("contactId") String contactId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(investorOppService.getInvestorOppProposalValueCountByContactId(contactId, userId, orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/weighted/value/count/contact/{contactId}")
	public ResponseEntity<?> getInvestorOppWeigthedValueCountByContactId(@RequestHeader("Authorization") String authorization,
			@PathVariable("contactId") String contactId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			return ResponseEntity.ok(investorOppService.getInvestorOppWeigthedValueCountByContactId(contactId,userId,orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/investorOpportunit/count/{country}")
	public ResponseEntity<?> getInvestorOppCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getInvestorOppCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/list/{country}")
	public ResponseEntity<?> getInvestorOppListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> list = investorOppService.getInvestorOppListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/investorOpportunit/open/count/{country}")
	public ResponseEntity<?> getOpenInvestorOppCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getOpenInvestorOppCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/open/list/{country}")
	public ResponseEntity<?> getOpenInvestorOppListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> list = investorOppService.getOpenInvestorOppListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/year/count/{country}")
	public ResponseEntity<?> getInvestorOppYearlyCountByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(investorOppService.getInvestorOppYearlyCountByCountry(country));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/investorOpportunit/year/list/{country}")
	public ResponseEntity<?> getInvestorOppYearlyListByCountry(@RequestHeader("Authorization") String authorization,
			@PathVariable("country") String country) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<InvestorOpportunityMapper> list = investorOppService.getInvestorOppYearlyListByCountry(country);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/investorOpportunit/fund/{invOpportunityId}")
	public ResponseEntity<?> getInvestorOppFundByInvOpportunityId(@PathVariable String invOpportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			List<InvestorOppFundResponse> response = investorOppService.getInvestorOppFundByInvOpportunityId(invOpportunityId,userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	  }
	
	@PostMapping("/api/v1/investorOpportunit/fund/update")
	public ResponseEntity<?> updateInvestorOppFund(@RequestBody InvestorOppFundRequest requestBody,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			InvestorOppFundResponse response = investorOppService.updateInvestorOppFund(requestBody);
			return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	  }
	
	@PostMapping("/api/v1/investorOpportunit/fund/toggle/update")
	public ResponseEntity<?> updateInvestorOppFundToggle(@RequestBody InvestorOppFundRequest requestBody,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			InvestorOppFundResponse response = investorOppService.updateInvestorOppFundToggle(requestBody);
			return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	  }

	@GetMapping("/api/v1/investorOpportunity/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
													  @PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<InvestorOpportunityMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = investorOppService.getInvestorOppByNameByOrgLevel(name, orgId);
				if (null == list || list.isEmpty()) {
					list = investorOppService.getOpportunityDetailsByNewOppIdAndTypeOrgLevel(name, orgId);
				}
			} else if (type.equalsIgnoreCase("team")) {
				list = investorOppService.getInvestorOppBByNameForTeam(name, userId);
				if (null == list || list.isEmpty()) {
					list = investorOppService.getOpportunityDetailsByNewOppIdAndTypeForTeam(name, userId);
				}
			} else {
				list = investorOppService.getInvestorOppBByNameByUserIdl(name, userId);
				if (null == list || list.isEmpty()) {
					list = investorOppService.getOpportunityDetailsByNewOppIdAndTypeAndUserId(name, userId);
				}
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}