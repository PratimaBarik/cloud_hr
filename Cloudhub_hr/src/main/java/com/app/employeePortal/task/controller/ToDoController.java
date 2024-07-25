package com.app.employeePortal.task.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.task.mapper.ToDoDetailsMapper;
import com.app.employeePortal.task.mapper.ToDoMapper;
import com.app.employeePortal.task.mapper.UpcomingEventMapper;
import com.app.employeePortal.task.mapper.UserPlannerMapper;
import com.app.employeePortal.task.repository.LastActivityLogRepository;
import com.app.employeePortal.task.service.ToDoService;

@RestController
@CrossOrigin(maxAge = 3600)
public class ToDoController {

	@Autowired
	private ToDoService toDoService;
	@Autowired
	LastActivityLogRepository lastActivityLogRepository;
	
	@Autowired
    private TokenProvider jwtTokenUtil;


	@GetMapping("/api/v1/todo/{userId}")
	public ResponseEntity<?> getToDoByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ToDoMapper> toDoList = toDoService.getToDoByUserId(userId, startDate, endDate);
			Collections.sort(toDoList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(toDoList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/todo/update/{eventCallTaskId}")
	public ResponseEntity<?> updateToDo(@PathVariable("eventCallTaskId") String eventCallTaskId,
			@RequestBody ToDoDetailsMapper toDoDetailsMapper,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (type.equalsIgnoreCase("Call")) {
				ToDoMapper toDoMapperr = toDoService.updateToDoByCallId(eventCallTaskId,
						toDoDetailsMapper);
				return new ResponseEntity<>(toDoMapperr, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("Task")) {
				ToDoMapper toDoMapperr = toDoService.updateToDoByTaskId(eventCallTaskId,
						toDoDetailsMapper);
				return new ResponseEntity<>(toDoMapperr, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("Event")) {
				ToDoMapper toDoMapperr = toDoService.updateToDoByEventId(eventCallTaskId,
						toDoDetailsMapper);
				return new ResponseEntity<>(toDoMapperr, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/todo/upcomingBirthdayAndAniversary")
	public ResponseEntity<?> upcomingBirthdayAndAniversary(@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<UpcomingEventMapper> toDoList = toDoService.upcomingBirthdayAndAniversary();
			Collections.sort(toDoList, (m1, m2) -> m1.getDate().compareTo(m2.getDate()));
			return new ResponseEntity<>(toDoList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/todoCount/{userId}")
	public ResponseEntity<?> getToDoCountByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(toDoService.getToDoCountByUserId(userId, startDate, endDate), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/todo/lastActivity/{id}")
	public ResponseEntity<?> getLastActivityByUserId(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(toDoService.getLastActivityByUserId(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	 @GetMapping("/api/v1/todo/activity/notes/{type}/{id}")
	    public ResponseEntity<?> getActivityNotesList(@PathVariable String type,@PathVariable String id,
	                                                         @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            List<NotesMapper> mapper = toDoService.getActivityNotesList(type,id);
	            if (null != mapper && !mapper.isEmpty()) {
	                Collections.sort(mapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

	                return new ResponseEntity<>(mapper, HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(mapper, HttpStatus.OK);
	            }
	        }
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	 
	 @PostMapping("/api/v1/todo/activity/notes/{type}/{id}")
	    public ResponseEntity<?> saveActivityNotes(@RequestBody NotesMapper notesMapper,@PathVariable String type,@PathVariable String id,
	                                                         @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	        	String authToken = authorization.replace(TOKEN_PREFIX, "");
				notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
	        	String responseId = toDoService.saveActivityNotes(notesMapper,type,id);
	        	return new ResponseEntity<>(responseId, HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	 
	 @DeleteMapping("/api/v1/todo/activity/delete/{activityType}/{activityId}/{creatorType}/{creatorId}")
		
		public ResponseEntity<?> deleteActivity(@RequestHeader("Authorization") String authorization,
				@PathVariable("activityType") String activityType,@PathVariable("activityId") String activityId, 
				@PathVariable("creatorType") String creatorType,@PathVariable("creatorId") String creatorId, 
				HttpServletRequest request) {
		
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
		
				String b = toDoService.deleteActivity(activityType, activityId, creatorType, creatorId);
				return new ResponseEntity<>(b, HttpStatus.OK);
		
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	 
	 @GetMapping("/api/v1/todo/planner/{userId}")
		public ResponseEntity<?> getPlannerDataByUserId(@PathVariable("userId") String userId,
				@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request)
				throws JsonGenerationException, JsonMappingException, IOException {
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				List<UserPlannerMapper> plannerList = toDoService.getPlannerDataByUserId(userId, startDate, endDate);
				if(null!=plannerList && !plannerList.isEmpty()) {
					Collections.sort(plannerList, (m1, m2) -> m1.getStartDate().compareTo(m2.getStartDate()));
				}
				return new ResponseEntity<>(plannerList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	 
}
