package com.app.employeePortal.permission.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.permission.mapper.AssessmentMapper;
import com.app.employeePortal.permission.mapper.CommunicationMapper;
import com.app.employeePortal.permission.mapper.ComplianceMapper;
import com.app.employeePortal.permission.mapper.DepartmentPermissionMapper;
import com.app.employeePortal.permission.mapper.NotificationPermissionMapper;
import com.app.employeePortal.permission.mapper.PermissionMapper;
import com.app.employeePortal.permission.mapper.PermissionUsersMapper;
import com.app.employeePortal.permission.mapper.SourcingMapper;
import com.app.employeePortal.permission.mapper.ThirdPartyMapper;
import com.app.employeePortal.permission.service.PermissionService;
import com.app.employeePortal.task.mapper.TaskViewMapper;

@RestController
@CrossOrigin(maxAge = 3600)
public class PermissionController {
	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	PermissionService permissionService;
	@Autowired
	PartnerService partnerService;
	@Autowired
	ContactService contactService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	OpportunityService opportunityService;
	
	@PostMapping("/api/v1/permission")
	public ResponseEntity<?> savePermission(@RequestBody PermissionMapper permissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			permissionMapper.setUserId(loggedInUserId);

			String permissionId = permissionService.savePermission(permissionMapper);

			return new ResponseEntity<>(permissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/{userId}")
	public ResponseEntity<?> getPermissiondetailByUserId(@PathVariable("userId") String userId,
			HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PermissionMapper candidateList = permissionService.getList(userId);

			return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/type")

	public ResponseEntity<?> getUserListByPermission(HttpServletRequest request,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();

		List<PermissionMapper> candidatelist = null;

		List<PermissionMapper> contactList = null;
		List<PermissionMapper> customerList = null;
		List<PermissionMapper> opportunityList = null;
		List<PermissionMapper> partnerlist = null;
		List<PermissionMapper> partnerContactlist = null;
		List<PermissionMapper> calllist = null;
		List<PermissionMapper> eventlist = null;
		List<PermissionMapper> tasklist = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (type.equalsIgnoreCase("candidate")) {
				boolean b = permissionService.candidateShareTrueByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == false) {
					map.put("adminCandidateShareInd", b);
					map.put("message", "Admin not allowed to show Candidate.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}

				candidatelist = permissionService.getUserList();
				/*
				 * Collections.sort(candidatelist, (CandidateViewMapper m1, CandidateViewMapper
				 * m2) -> m2.getCreationDate() .compareTo(m1.getCreationDate()));
				 */

				return new ResponseEntity<>(candidatelist, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("contact")) {

				contactList = permissionService.getUserListForContact();
				// Collections.sort(contactList, (ContactViewMapper m1, ContactViewMapper m2) ->
				// m2.getCreationDate()
				// .compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("customer")) {

				customerList = permissionService.getUserListForCustomer();
				// Collections.sort(customerList, (PermissionMapper m1, PermissionMapper m2) ->
				// m2.getCreationDate()
				// .compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("opportunity")) {

				opportunityList = permissionService.getUserListForOpportunity();
				// Collections.sort(opportunityList, (PermissionMapper m1, PermissionMapper m2)
				// -> m2.getCreationDate()
				// .compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(opportunityList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("partner")) {

				partnerlist = permissionService.getUserListForPartner();
				// Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				// m2.getCreationDate()
				// .compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(partnerlist, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("partnerContact")) {

				partnerContactlist = permissionService.getUserListForPartnerContact();
				// Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				// m2.getCreationDate()
				// .compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(partnerContactlist, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("planner")) {

				partnerlist = permissionService.getUserListforPlanner();
				/*
				 * Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				 * m2.getCreationDate() .compareTo(m1.getCreationDate()));
				 */
				return new ResponseEntity<>(partnerlist, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("task")) {

				tasklist = permissionService.getUserListforTask();
				/*
				 * Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				 * m2.getCreationDate() .compareTo(m1.getCreationDate()));
				 */
				return new ResponseEntity<>(tasklist, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("call")) {

				calllist = permissionService.getUserListforCall();
				/*
				 * Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				 * m2.getCreationDate() .compareTo(m1.getCreationDate()));
				 */

				return new ResponseEntity<>(calllist, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("event")) {

				eventlist = permissionService.getUserListforCall();
				/*
				 * Collections.sort(partnerlist, (PermissionMapper m1, PermissionMapper m2) ->
				 * m2.getCreationDate() .compareTo(m1.getCreationDate()));
				 */

				return new ResponseEntity<>(calllist, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/details")

	public ResponseEntity<List<?>> getPermissionDetailsListsByUserId(
			@RequestBody PermissionUsersMapper permissionUsersList,
			@RequestHeader("Authorization") String authorization) {

		List<CandidateViewMapper> candidateList = null;
		List<ContactViewMapper> contactList = null;
		List<CustomerResponseMapper> customerList = null;
		List<OpportunityViewMapper> OpportunityList = null;
		List<PartnerMapper> partnerList = null;
		List<ContactViewMapper> partnerContactsList = null;
		List<CallViewMapper> callList = null;
		List<EventViewMapper> eventList = null;
		List<TaskViewMapper> taskList = null;

		String type = permissionUsersList.getType();
		List<String> permissionMapperList = permissionUsersList.getUserId();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("candidate")) {

				candidateList = permissionService.getCandidateListForAllUsers(permissionMapperList);
				Collections.sort(candidateList, (CandidateViewMapper m1, CandidateViewMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("contact")) {

				contactList = permissionService.getContactListForAllUsers(permissionMapperList);
				Collections.sort(contactList, (ContactViewMapper m1, ContactViewMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("customer")) {

				customerList = permissionService.getCustomerListListForAllUsers(permissionMapperList);
				Collections.sort(customerList, (CustomerResponseMapper m1, CustomerResponseMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(customerList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("opportunity")) {

				OpportunityList = permissionService.getOpportunityListForAllUsers(permissionMapperList);
				Collections.sort(OpportunityList, (OpportunityViewMapper m1, OpportunityViewMapper m2) -> m2
						.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(OpportunityList, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("partner")) {

				partnerList = permissionService.getPartnerListForAllUsers(permissionMapperList);
				Collections.sort(partnerList,
						(PartnerMapper m1, PartnerMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(partnerList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("call")) {

				callList = permissionService.getCallListForAllUsers(permissionMapperList);
				Collections.sort(callList,
						(CallViewMapper m1, CallViewMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(callList, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("event")) {

				eventList = permissionService.getEventListForAllUsers(permissionMapperList);
				Collections.sort(eventList, (EventViewMapper m1, EventViewMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(eventList, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("task")) {

				taskList = permissionService.getTaskListForAllUsers(permissionMapperList);
				Collections.sort(taskList,
						(TaskViewMapper m1, TaskViewMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(taskList, HttpStatus.OK);
			}

			else if (type.equalsIgnoreCase("partnerContact")) {

				partnerContactsList = permissionService.getPartnerContactsListForAllUsers(permissionMapperList);
				Collections.sort(partnerContactsList, (ContactViewMapper m1, ContactViewMapper m2) -> m2
						.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(partnerContactsList, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/permission")
	public ResponseEntity<?> saveAdminPermission(@RequestBody PermissionMapper permissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			permissionMapper.setUserId(loggedInUserId);

			String permissionId = permissionService.savePermission(permissionMapper);

			return new ResponseEntity<>(permissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/candidate")
	public ResponseEntity<PermissionMapper> saveCandidateSharePermission(@RequestBody PermissionMapper permissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		PermissionMapper resultMapper = new PermissionMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			// String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			// String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			// permissionMapper.setUserId(loggedInUserId);

			String permissionId = permissionService.saveCandidateSharePermission(permissionMapper);
			resultMapper = permissionService.getAdminCandidateShareByOrgId(permissionMapper.getOrganizationId());
			return new ResponseEntity<PermissionMapper>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/candidate/{orgId}")
	public ResponseEntity<?> getAdminCandidateShareByOrgId(@PathVariable("orgId") String orgId,
			HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PermissionMapper candidate = permissionService.getAdminCandidateShareByOrgId(orgId);

			return new ResponseEntity<>(candidate, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/engagement/access")
	public ResponseEntity<?> createEngagementAccess(@RequestBody ThirdPartyMapper thirdPartyMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			thirdPartyMapper.setOrgId(loggedInOrgId);
			thirdPartyMapper.setUserId(loggedInUserId);
			String thirdPartyId = permissionService.saveEngagementAccess(thirdPartyMapper);

			return new ResponseEntity<>(thirdPartyId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/engagement/access/{orgId}")
	public ResponseEntity<?> getEngagementAccessByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ThirdPartyMapper thirdPartyList = permissionService.getEngagementAccessByOrgId(orgId);

			return new ResponseEntity<>(thirdPartyList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/access/department")
	public ResponseEntity<?> saveDepartmentPermission(
			@RequestBody DepartmentPermissionMapper departmentPermissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			departmentPermissionMapper.setUserId(loggedInUserId);
			departmentPermissionMapper.setOrganizationId(loggedInOrgId);

			String departmentPermissionId = permissionService.saveDepartmentPermission(departmentPermissionMapper);

			return new ResponseEntity<>(departmentPermissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/access/department/{roleTypeId}")
	public ResponseEntity<?> getDepartmentPermissiondetailByDepartmentId(
			@PathVariable("roleTypeId") String roleTypeId, HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			DepartmentPermissionMapper candidateList = permissionService.getPermisionByDepartmentId(roleTypeId,
					loggedInOrgId);

			return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/candidate/access/compliance")
	public ResponseEntity<?> savecompliance(@RequestBody ComplianceMapper complianceMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			complianceMapper.setUserId(loggedInUserId);
			complianceMapper.setOrganizationId(loggedInOrgId);

			String complianceId = permissionService.savecompliance(complianceMapper);

			return new ResponseEntity<>(complianceId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/compliance/access/{orgId}")
	public ResponseEntity<?> getComplianceAccessByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ComplianceMapper complianceList = permissionService.getComplianceAccessByOrgId(orgId);

			return new ResponseEntity<>(complianceList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/communication/access")
	public ResponseEntity<?> createCommunicationAccess(@RequestBody CommunicationMapper communicationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			communicationMapper.setOrgId(loggedInOrgId);
			communicationMapper.setUserId(loggedInUserId);
			String communicationId = permissionService.saveCommunicationAccess(communicationMapper);

			return new ResponseEntity<>(communicationId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/communication/access/{orgId}")
	public ResponseEntity<?> getCommunicationAccessByOrgId(@PathVariable("orgId") String orgId,
			HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CommunicationMapper communicationList = permissionService.getCommunicationAccessByOrgId(orgId);

			return new ResponseEntity<>(communicationList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/sourcing/access")
	public ResponseEntity<?> createSourcingAccess(@RequestBody SourcingMapper sourcingMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);

			sourcingMapper.setOrgId(loggedInOrgId);
			sourcingMapper.setUserId(loggedInUserId);
			String sourcingId = permissionService.saveSourcingAccess(sourcingMapper);

			return new ResponseEntity<>(sourcingId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/sourcing/access/{orgId}")
	public ResponseEntity<?> getSourcingAccessByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			SourcingMapper sourcingMapper = permissionService.getSourcingAccessByOrgId(orgId);

			return new ResponseEntity<>(sourcingMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/access/notification")
	public ResponseEntity<?> saveNotificationPermission(
			@RequestBody NotificationPermissionMapper notificationPermissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			notificationPermissionMapper.setUserId(loggedInUserId);
			notificationPermissionMapper.setOrganizationId(loggedInOrgId);

			String notificationPermissionId = permissionService
					.saveNotificationPermission(notificationPermissionMapper);

			return new ResponseEntity<>(notificationPermissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/access/notification/{userId}")
	public ResponseEntity<?> getNotificationPermissiondetailByUserId(@PathVariable("userId") String userId,
			HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			NotificationPermissionMapper candidateList = permissionService.getPermisionByUserId(userId, loggedInOrgId);

			return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/permission/assessment")
	public ResponseEntity<?> createAssessment(@RequestBody AssessmentMapper assessmentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			assessmentMapper.setOrgId(loggedInOrgId);
			assessmentMapper.setUserId(loggedInUserId);
			String assessmentDetailsId = permissionService.saveAssessment(assessmentMapper);

			return new ResponseEntity<>(assessmentDetailsId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/permission/assessment/{orgId}")
	public ResponseEntity<?> getAssessmentByOrgId(@PathVariable("orgId") String orgId,
			HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			AssessmentMapper assessmentList = permissionService.getAssessmentByOrgId(orgId);

			return new ResponseEntity<>(assessmentList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/partner/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getPartnerDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(userId.equalsIgnoreCase("All")) {
				List<PartnerMapper> partnerMapperList  = partnerService.getAllPartnerList( pageNo, pageSize);
				Collections.sort(partnerMapperList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
			    //return ResponseEntity.ok(partnerList);
			}else {
			List<PartnerMapper> partnerList = permissionService.getPartnerList(userId, pageNo, pageSize); 	
			Collections.sort(partnerList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(partnerList, HttpStatus.OK);
		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/partner/contact/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getPartnerContactDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 10;

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
	
	@GetMapping("/api/v1/permission/customer/contact/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getCustomerContactDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;
		String authToken = authorization.replace(TOKEN_PREFIX, "");
        String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
//			if(userId.equalsIgnoreCase("All")) {
//				List<ContactViewMapper> contactList  = contactService.getAllContatList(pageNo, pageSize);
//		    	Collections.sort(contactList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//		        return ResponseEntity.ok(contactList);
//			}else {
				List<ContactViewMapper> contactList = contactService.getContactListByUserId(userId, pageNo, pageSize);
				Collections.sort(contactList, ( m1,m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			
				return  ResponseEntity.ok(contactList);
		}
//		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/customer/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getCustomerDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
	           String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			if(userId.equalsIgnoreCase("All")) {
				List<CustomerResponseMapper> customerList = customerService.getAllCustomerList(pageNo, pageSize, orgId);
				Collections.sort(customerList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return ResponseEntity.ok(customerList);
			}else {
			List<CustomerResponseMapper> customerDetailsList = customerService.getCustomerDetailsPageWiseByuserId(userId,pageNo, pageSize);
			Collections.sort(customerDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/candidate/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getCandidateDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(userId.equalsIgnoreCase("All")) {
				List<CandidateViewMapper> candidateList = candidateService.getAllCandidateList(pageNo, pageSize);
				Collections.sort(candidateList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return ResponseEntity.ok(candidateList);
			}else {
			List<CandidateViewMapper> candidateList = candidateService.getCandidateListPageWiseByUserId(userId,pageNo, pageSize);
			Collections.sort(candidateList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateList, HttpStatus.OK);
		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/candidate/details/{category}/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getCandidateDetailsListsPermissionByUserIdAndCategory(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@PathVariable("category") String category,@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			 if(userId.equalsIgnoreCase("All")) {
		    	   List<CandidateViewMapper> candidateList  = candidateService.getAllCandidateListByCategory( pageNo, pageSize,category);
					Collections.sort(candidateList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
					return new ResponseEntity<>(candidateList, HttpStatus.OK);
				}else {
					List<CandidateViewMapper> typeList = candidateService.getcandidateLisstByCategory(category,userId, pageNo, pageSize);
			   		 Collections.sort(typeList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));      
				   return new ResponseEntity<>(typeList, HttpStatus.OK);
				}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/permission/opportunity/details/{userId}/{pageNo}")
	public ResponseEntity<List<?>> getOpportunityDetailsListsPermissionByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization){
		
		int pageSize = 20;
		String authToken = authorization.replace(TOKEN_PREFIX, "");
        String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(userId.equalsIgnoreCase("All")) {
				List<OpportunityViewMapper> oppList = opportunityService.getOpportunityDetailsListPageWiseByOrgId(orgId,pageNo, pageSize);
				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return ResponseEntity.ok(oppList);
			}else {
			List<OpportunityViewMapper> oppList = opportunityService.getOpportunityDetailsListPageWiseByUserId(userId,pageNo, pageSize);

			Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(oppList, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
