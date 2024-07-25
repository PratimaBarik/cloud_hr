package com.app.employeePortal.call.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.app.employeePortal.call.mapper.CallMapper;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.call.service.CallService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class CallController {

	@Autowired
	CallService callService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/call")
	public ResponseEntity<?> createCall(@RequestBody CallMapper callMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			callMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			callMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			CallViewMapper callId = callService.saveCallProcess(callMapper);

			return new ResponseEntity<>(callId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/employee/{employeeId}/{pageNo}")

	public ResponseEntity<?> getCallDetailsByEmpId(@PathVariable("employeeId") String employeeId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getCallDetailsByEmployeeIdPageWise(employeeId, pageNo,
					pageSize);
			Collections.sort(callList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(callList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/organization/{organizationId}")

	public ResponseEntity<?> getCallDetailsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getCallDetailsByOrganizationId(organizationId);
			callList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(callList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/call/{callId}")

	public ResponseEntity<?> updateCallDetails(@RequestBody CallMapper callMapper,
			@PathVariable("callId") String callId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			callMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			callMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			CallViewMapper id = callService.updateCallDetails(callId, callMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/call/{callId}/employee/{employeeId}")

	public ResponseEntity<?> delinkCall(@PathVariable("callId") String callId,
			@PathVariable("employeeId") String employeeId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = callService.delinkCall(employeeId, callId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/call/candidate/{candidateId}")

	public ResponseEntity<?> getCallListOfACandidate(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getcallListOfACandidate(candidateId);
			callList.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(callList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/{name}")
	public ResponseEntity<?> getCallDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callViewMapper = callService.getCallDetailsByName(name);
			return new ResponseEntity<>(callViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/Lists/count/{userId}")
	public ResponseEntity<?> getCallListsCountByUserIdAndStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = callService.getCallListsByUserIdStartdateAndEndDate(userId, startDate, endDate);
//  	           	 Collections.sort(map, ( m1,  m2) -> m2.getCreationDate()
//  	     					.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(map, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/employee/{employeeId}")
	public ResponseEntity<?> getCallDetailsListByEmpId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getCallDetailsByEmployeeId(employeeId);
			Collections.sort(callList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(callList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/leads/{leadsId}/{pageNo}")
	public ResponseEntity<?> getCallDetailsByLeadsId(@PathVariable("leadsId") String leadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getCallDetailsByLeadsId(leadsId, pageNo, pageSize);
			Collections.sort(callList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(callList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/investorLeads/{investorLeadsId}/{pageNo}")
	public ResponseEntity<?> getCallDetailsByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		int pageSize = 20;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CallViewMapper> callList = callService.getCallDetailsByInvestorLeadsId(investorLeadsId, pageNo,
					pageSize);
			Collections.sort(callList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(callList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/completed/count/{userId}")
	public ResponseEntity<?> getCompletedCallsCountByUserIdAndStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = callService.getCompletedCallsCountByUserIdAndStartdateAndEndDate(userId, startDate, endDate);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/call/completed/list/{userId}")
	public ResponseEntity<?> getCompletedCallsListByUserIdAndStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CallViewMapper> callList = callService.getCompletedCallsListByUserIdAndStartdateAndEndDate(userId,
					startDate, endDate);
			return new ResponseEntity<>(callList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/call/notes")
	public ResponseEntity<?> createCallNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = callService.saveCallNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/call/note/{callId}")
	public ResponseEntity<?> getNoteListByCallId(@PathVariable("callId") String callId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = callService.getNoteListByCallId(callId);
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

//	@PutMapping("/api/v1/call/note/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr =callService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/call/note/{notesId}")
	public ResponseEntity<?> deleteCallNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			callService.deleteCallNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/call/calltype/completed/list/{userId}/{callType}")
	public ResponseEntity<?> getCompletedCallsTypeListByUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @PathVariable("callType") String callType,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CallViewMapper> callList = callService.getCompletedCallsTypeListByUserIdAndStartdateAndEndDate(userId,
					startDate, endDate, callType);
			return new ResponseEntity<>(callList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/call/calltype/completed/count/{userId}/{callType}")
	public ResponseEntity<?> getCompletedCallsTypeCountByUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @PathVariable("callType") String callType,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = callService.getCompletedCallsTypeCountByUserIdAndStartdateAndEndDate(userId, startDate, endDate,
					callType);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/activity/call/save")
	public ResponseEntity<?> saveActivityCall(@RequestBody CallMapper callMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			callMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			callMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ActivityMapper callId = callService.saveActivityCall(callMapper);

			return new ResponseEntity<>(callId, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/call/activity/update/{callId}")
	public ResponseEntity<?> updateActivityCallDetails(@RequestBody CallMapper callMapper,
			@PathVariable("callId") String callId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			callMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			callMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			ActivityMapper id = callService.updateActivityCallDetails(callId, callMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/contact/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<CallViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = callService.getCallDetailsByNameByOrgLevel(name, orgId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = callService.getCallDetailsByNameForTeam(name, userId);
				return ResponseEntity.ok(list);
			} else if (type.equalsIgnoreCase("user")) {
				list = callService.getCallDetailsByNameByUserId(name, userId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
