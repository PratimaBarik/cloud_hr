package com.app.employeePortal.event.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.event.service.EventService;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class EventController {

	@Autowired
	EventService eventService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/event")
	public ResponseEntity<?> createEvent(@RequestBody EventMapper eventMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			eventMapper.setUserId(loggedInUserId);
			eventMapper.setOrganizationId(loggedInOrgId);
			EventViewMapper eventId = eventService.saveEventProcess(eventMapper);

			return new ResponseEntity<>(eventId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/employee/{employeeId}/{pageNo}")

	public ResponseEntity<?> getEventDetailsByEmpId(@PathVariable("employeeId") String employeeId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EventViewMapper> eventList = eventService.getEventDetailsByEmployeeIdPageWise(employeeId, pageNo,
					pageSize);
			Collections.sort(eventList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));

			return new ResponseEntity<>(eventList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/organization/{organizationId}")

	public ResponseEntity<?> getEventDetailsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EventViewMapper> eventList = eventService.getEventDetailsByOrganizationId(organizationId);
			return new ResponseEntity<>(eventList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/event/{eventId}")

	public ResponseEntity<?> updateEventDetails(@RequestBody EventMapper eventMapper,
			@PathVariable("eventId") String eventId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			eventMapper.setUserId(loggedInUserId);
			eventMapper.setOrganizationId(loggedInOrgId);
			EventViewMapper id = eventService.updateEventDetails(eventId, eventMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/event/{eventId}/employee/{employeeId}")

	public ResponseEntity<?> delinkContact(@PathVariable("eventId") String eventId,
			@PathVariable("employeeId") String employeeId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = eventService.delinkEvent(employeeId, eventId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/eventType")
	public ResponseEntity<?> createEventType(@RequestBody EventMapper eventMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(eventMapper.getEventType())) {
				boolean b = eventService.checkEventNameInEventTypeByOrgLevel(eventMapper.getEventType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("eventInd", b);
					map.put("message", "EventType can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					eventMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					eventMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					EventMapper eventTypeId = eventService.saveEventType(eventMapper);
					return new ResponseEntity<>(eventTypeId, HttpStatus.OK);
				}
			} else {
				map.put("message", "EventType can not be updated as same name already exists!!!");

			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/eventType")
	public ResponseEntity<?> getAllEventType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			List<EventMapper> typeList = eventService.getEventTypeByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/eventType")
	public ResponseEntity<?> updateEventType(@RequestBody EventMapper eventMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			eventMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			eventMapper.setOrganizationId(orgId);
			if (!StringUtils.isEmpty(eventMapper.getEventType())) {
				Map map = new HashMap<>();
				boolean b = eventService.checkEventNameInEventTypeByOrgLevel(eventMapper.getEventType(), orgId);
				if (b == true) {
					map.put("eventInd", b);
					map.put("message", "EventType can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			EventMapper eventMapperr = eventService.updateEventType(eventMapper.getEventTypeId(), eventMapper);

			return new ResponseEntity<EventMapper>(eventMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/event/{candidateId}")
	public ResponseEntity<?> getAllEventsOfCandidate(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<EventViewMapper> mapperList = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			mapperList = eventService.getEventListOfCandidate(candidateId);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/eventType/{name}")
	public ResponseEntity<?> getEventDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EventMapper> eventMapper = eventService.getEventDetailsByNameByOrgLevel(name, orgId);
			if (null != eventMapper && !eventMapper.isEmpty()) {
				return new ResponseEntity<>(eventMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/eventType/{eventTypeId}")

	public ResponseEntity<?> deleteEventType(@PathVariable("eventTypeId") String eventTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			eventService.deleteEventTypeById(eventTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/event/count/Lists/{userId}")
	public ResponseEntity<?> getEventListsByUserIdStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = eventService.getEventListsByUserIdStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/type/Lists/{userId}")
	public ResponseEntity<?> getEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Map<String, List<EventViewMapper>> mapperList = eventService
					.getEventListsByEventTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/employee/{employeeId}")
	public ResponseEntity<?> getPlannerEventDetailsByEmpId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EventViewMapper> eventList = eventService.getEventDetailsByEmployeeId(employeeId);
			Collections.sort(eventList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));

			return new ResponseEntity<>(eventList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/type/count/{userId}")
	public ResponseEntity<?> getOpenEventCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(eventService.getOpenEventCountByUserId(userId, organizationId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/type/list/{userId}")
	public ResponseEntity<?> getOpenEventListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(eventService.getOpenEventListByUserId(userId, organizationId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/events/{leadsId}/{pageNo}")
	public ResponseEntity<?> getEventsByLeads(@PathVariable("leadsId") String leadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(eventService.getEventsByLeads(leadsId, pageNo), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/events/investorLeads/{investorLeadsId}/{pageNo}")
	public ResponseEntity<?> getEventsByInvestorLeads(@PathVariable("investorLeadsId") String investorLeadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(eventService.getEventsByInvestorLeads(investorLeadsId, pageNo), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/type/completed/count/{userId}")
	public ResponseEntity<?> getCountEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Integer>> mapperList = eventService
					.getCountEventListsByEventTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/event/type/completed/Lists/{userId}")
	public ResponseEntity<?> getCompletedEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Map<String, List<EventViewMapper>> mapperList = eventService
					.getCompletedEventListsByEventTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/event/notes")
	public ResponseEntity<?> createEventNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = eventService.createEventNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/event/note/{eventId}")
	public ResponseEntity<?> getNoteListByEventId(@PathVariable("eventId") String eventId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = eventService.getNoteListByEventId(eventId);
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

//	@PutMapping("/api/v1/event/note/update")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = eventService.updateNoteDetails(notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@DeleteMapping("/api/v1/event/note/{notesId}")
	public ResponseEntity<?> deleteEventNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			eventService.deleteEventNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/activity/event/save")
	public ResponseEntity<?> createActivityEvent(@RequestBody EventMapper eventMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			eventMapper.setUserId(loggedInUserId);
			eventMapper.setOrganizationId(loggedInOrgId);
			ActivityMapper eventId = eventService.saveActivityEvent(eventMapper);

			return new ResponseEntity<>(eventId, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/eventType/count/{orgId}")
	public ResponseEntity<?> getEventTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(eventService.getEventTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/event/activity/update/{eventId}")

	public ResponseEntity<?> updateActivityEventDetails(@RequestBody EventMapper eventMapper,
			@PathVariable("eventId") String eventId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			eventMapper.setUserId(loggedInUserId);
			eventMapper.setOrganizationId(loggedInOrgId);
			ActivityMapper id = eventService.updateActivityEventDetails(eventId, eventMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/event/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EventViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = eventService.getEventDetailsByNameByOrgId(name, orgId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = eventService.getEventDetailsByNameForTeam(name, userId, orgId);
				return ResponseEntity.ok(list);
			} else if (type.equalsIgnoreCase("user")) {
				list = eventService.getEventDetailsByNameByUserId(name, userId, orgId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
