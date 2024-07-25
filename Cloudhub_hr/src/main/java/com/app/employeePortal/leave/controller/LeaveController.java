package com.app.employeePortal.leave.controller;

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
import com.app.employeePortal.leave.mapper.EmployeeLeaveMapper;
import com.app.employeePortal.leave.mapper.LeaveBalanceMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.leave.mapper.OrganizationLeaveRuleMapper;
import com.app.employeePortal.leave.service.LeaveService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.service.TaskService;

@RestController
@CrossOrigin(maxAge = 3600)
public class LeaveController {
	@Autowired  
	LeaveService leaveService;
	
	@Autowired  
	TaskService taskService;
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	@Autowired
	WebsiteRepository websiteRepository;
	
	@PutMapping("/api/v1/rule/leaves")
	public ResponseEntity<?> saveOrgLeaveRule( @RequestBody OrganizationLeaveRuleMapper organizationLeaveRuleMapper,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) {
		
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
			
			organizationLeaveRuleMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			organizationLeaveRuleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationLeaveRuleMapper resultMapper = leaveService.saveToOrganizationLeaveRuleProcess(organizationLeaveRuleMapper);

  			return new ResponseEntity<>(resultMapper,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
	
	
	@GetMapping("/api/v1/rule/leaves/{countryId}")

	public ResponseEntity<?> getOrgLeaveRuleById(@RequestHeader("Authorization") String authorization,
			@PathVariable("countryId") String countryId,HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			OrganizationLeaveRuleMapper organizationLeaveRuleMapper = leaveService.getOrganizationLeaveRuleDetails(loggedInUserOrgId,countryId);

			return new ResponseEntity<OrganizationLeaveRuleMapper>(organizationLeaveRuleMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	
	
	@PostMapping("/api/v1/employee/leave")
	public ResponseEntity<?> saveLeave( @RequestBody LeavesMapper leavesMapper,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) throws Exception {
		
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			leavesMapper.setOrgId(loggedInUserOrgId);
              String leaveId = leaveService.saveToLeaveProcess(leavesMapper);

  			return new ResponseEntity<>(leaveId,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }

	@PutMapping("/api/v1/employee/leave")

	public ResponseEntity<?> updateLeave( @RequestBody LeavesMapper leavesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			leavesMapper.setOrgId(loggedInUserOrgId);
			LeavesMapper newLeavesMapper  = leaveService.updateLeaveDetails(leavesMapper);
			return new ResponseEntity<LeavesMapper>(newLeavesMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	/* retrieve  leaves of an employee */
	@GetMapping("/api/v1/employee/leaves/{employeeId}")

	public ResponseEntity<?> getLeavesListByEmpId(@PathVariable("employeeId") String employeeId,
			                                      HttpServletRequest request,
			                                     @RequestHeader("Authorization") String authorization){
			                    
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeavesMapper> leaveList = leaveService.getEmployeeLeavesList(employeeId);
			if (null!=leaveList && !leaveList.isEmpty()) {
			Collections.sort(leaveList, (LeavesMapper m1, LeavesMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
        return new ResponseEntity<>(leaveList, HttpStatus.OK);
			}
			return new ResponseEntity<>(leaveList, HttpStatus.OK);
       }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	/*approve a leave by taskid*/
	
	@PostMapping("/api/v1/employee/leave/approve/{taskId}")
	public ResponseEntity<?> approveLeavesByTaskId(@PathVariable("taskId") String taskId,
                                                   HttpServletRequest request,
                                                   @RequestHeader("Authorization") String authorization){

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			TaskViewMapper taskMapper = taskService.approveLeaveTask(taskId);
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskMapper.setUserId(jwtTokenUtil.getOrgIdFromToken(authToken));
			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(taskMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			
			}
	
	/*reject a leave by taskid*/

	@PostMapping("/api/v1/employee/leave/reject/{taskId}")

	public ResponseEntity<?> rejectLeavesByTaskId(@PathVariable("taskId") String taskId,
                                                  HttpServletRequest request,
                                                 @RequestHeader("Authorization") String authorization){

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			TaskViewMapper taskMapper = taskService.rejectLeaveTask(taskId);
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskMapper.setUserId(jwtTokenUtil.getOrgIdFromToken(authToken));
			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
		return new ResponseEntity<>(taskMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	
	@GetMapping("/api/v1/employee/leave/leave-balance/{employeeId}")

	public ResponseEntity<?> calculateLeaveBalance(@PathVariable("employeeId") String employeeId,
			                                      HttpServletRequest request,
			                                     @RequestHeader("Authorization") String authorization){
			                    
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			LeaveBalanceMapper leaveBalanceMapper = leaveService.calculateLeaveBalanceByEmpId(employeeId);
        return new ResponseEntity<>(leaveBalanceMapper, HttpStatus.OK);
       }
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PostMapping("/api/v1/employee/leave/website")
	public ResponseEntity<?> saveLeaveForWebsite( @RequestBody LeavesMapper leavesMapper,
			@RequestParam(value = "url", required = true) String url,
			                     HttpServletRequest request) throws Exception {
		
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			String loggedInUserOrgId = web.getOrgId();
			leavesMapper.setOrgId(loggedInUserOrgId);
              String leaveId = leaveService.saveToLeaveProcess(leavesMapper);

  			return new ResponseEntity<>(leaveId,HttpStatus.OK);
		}else {
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
        
     }

	@PutMapping("/api/v1/employee/leave/update/website")

	public ResponseEntity<?> updateLeaveForWebsite( @RequestBody LeavesMapper leavesMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) throws Exception {

		

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			String loggedInUserOrgId = web.getOrgId();
			leavesMapper.setOrgId(loggedInUserOrgId);
			LeavesMapper newLeavesMapper  = leaveService.updateLeaveDetails(leavesMapper);
			return new ResponseEntity<LeavesMapper>(newLeavesMapper, HttpStatus.OK);
		}else {
			//map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}
	
	/* retrieve  leaves of an employee */
	@GetMapping("/api/v1/employee/leaves/{employeeId}/website")

	public ResponseEntity<?> getLeavesListByEmpIdForWebsite(@PathVariable("employeeId") String employeeId,
			@RequestParam(value = "url", required = true) String url,
			                                      HttpServletRequest request){
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {               
		
			List<LeavesMapper> leaveList = leaveService.getEmployeeLeavesList(employeeId);
			Collections.sort(leaveList, (LeavesMapper m1, LeavesMapper m2) -> m2.getCreationDate()
 					.compareTo(m1.getCreationDate()));
        return new ResponseEntity<>(leaveList, HttpStatus.OK);
		}else {
			//map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@PostMapping("/api/v1/employee/leave/reapply/{leaveId}")
	public ResponseEntity<?> reApplyLeavesByLeaveId(@PathVariable("leaveId") String leaveId,
                                                   HttpServletRequest request,
                                                   @RequestHeader("Authorization") String authorization){

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			TaskViewMapper taskMapper = taskService.reApplyLeavesByLeaveId(leaveId);
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskMapper.setUserId(jwtTokenUtil.getOrgIdFromToken(authToken));
			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(taskMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			
			}
	
	@DeleteMapping("/api/v1/employee/leave/delete/{leaveId}")
	public ResponseEntity<String> deleteLeave(@PathVariable("leaveId") String leaveId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = taskService.deleteLeave(leaveId);

			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/employee/leaves/status/{employeeId}/{status}")
	public ResponseEntity<?> getEmployeeleaveStatusListByEmployeeId(@PathVariable("employeeId") String employeeId,@PathVariable("status") String status,
			 @RequestHeader("Authorization") String authorization, HttpServletRequest request){
		 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeavesMapper> leaveList = leaveService.getEmployeeleaveStatusListByEmployeeId(employeeId,status);
			return new ResponseEntity<>(leaveList, HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/employee/leaves/date-wise/{employeeId}/{status}")
	public ResponseEntity<?> getEmployeeleaveStatusListByEmployeeIdWithDateWise(@PathVariable("employeeId") String employeeId,@PathVariable("status") String status,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<LeavesMapper> leaveList = leaveService.getEmployeeleaveStatusListByEmployeeIdWithDateWise(employeeId,status,startDate,endDate);
			return new ResponseEntity<>(leaveList, HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	@GetMapping("/api/v1/leaves/employee/leave-list/date-wise/{orgId}")
	public ResponseEntity<?> getEmployeeLeaveListByOrgIdWithDateWise(@PathVariable("orgId") String orgId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			List<EmployeeLeaveMapper> map =leaveService.getEmployeeLeaveListByOrgIdWithDateWise(orgId,startDate,endDate);
			if(null!=map && !map.isEmpty()) {
				Collections.sort(map, ( m1,  m2) -> m1.getEmployeeName()
	 					.compareTo(m2.getEmployeeName()));
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/leaves/org/leave-list/date-wise/{orgId}/{status}")
	public ResponseEntity<?> getEmployeeLeaveListByOrgIdWithDateWiseAndStatus(@PathVariable("orgId") String orgId,
			@PathVariable("status") String status,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			List<EmployeeLeaveMapper> map =leaveService.getEmployeeLeaveListByOrgIdWithDateWiseAndStatus(orgId,startDate,endDate,status);
			if(null!=map && !map.isEmpty()) {
				Collections.sort(map, ( m1,  m2) -> m1.getEmployeeName()
	 					.compareTo(m2.getEmployeeName()));
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/leave/notes")
	public ResponseEntity<?> createLeaveNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			NotesMapper id = leaveService.saveLeaveNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/leave/note/{leaveId}")
	public ResponseEntity<?> getNoteListByLeaveId(@PathVariable("leaveId") String leaveId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = leaveService.getNoteListByLeaveId(leaveId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
                notesMapper.sort((NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(notesMapper, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(notesMapper, HttpStatus.OK);
            }

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/leave/note/update/{notesId}")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@PathVariable("notesId") String notesId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = leaveService.updateNoteDetails(notesId, notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}
	
	@DeleteMapping("/api/v1/leave/note/{notesId}")
	public ResponseEntity<?> deleteLeaveNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			leaveService.deleteLeaveNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	
}
