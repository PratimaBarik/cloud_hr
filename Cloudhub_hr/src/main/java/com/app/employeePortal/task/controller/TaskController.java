package com.app.employeePortal.task.controller;

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
import com.app.employeePortal.candidate.mapper.CandidateEmailDetailsMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.task.mapper.ApprovedStatusMapper;
import com.app.employeePortal.task.mapper.SubTaskMapper;
import com.app.employeePortal.task.mapper.SubViewTaskMapper;
import com.app.employeePortal.task.mapper.TaskCommentMapper;
import com.app.employeePortal.task.mapper.TaskDragDropMapper;
import com.app.employeePortal.task.mapper.TaskMapper;
import com.app.employeePortal.task.mapper.TaskStepsMapper;
import com.app.employeePortal.task.mapper.TaskTypeDropMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.mapper.TeamEmployeeMapper;
import com.app.employeePortal.task.service.TaskService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@RestController
@CrossOrigin(maxAge = 3600)
public class TaskController {

	@Autowired
	TaskService taskService;
	@Autowired
	WebsiteRepository websiteRepository;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/task")
	public ResponseEntity<?> createTask(@RequestBody TaskMapper taskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			taskMapper.setUserId(loggedInUserId);
			taskMapper.setOrganizationId(loggedInOrgId);
			TaskViewMapper taskId = taskService.saveTaskProcess(taskMapper, "normal");

			return new ResponseEntity<>(taskId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/employee/{employeeId}/{pageNo}")

	public ResponseEntity<?> gettaskDetailsByEmpId(@PathVariable("employeeId") String employeeId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByEmployeeIdPagination(employeeId, pageNo, false);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/employee/{employeeId}/{priority}/{pageNo}")
	public ResponseEntity<?> gettaskDetailsByEmpIdAndType(@PathVariable String employeeId,
			@PathVariable String priority, @PathVariable int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTasksByUserIdAndPriority(employeeId, priority, pageNo,
					false);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/organization/{organizationId}")

	public ResponseEntity<?> gettaskDetailsByOrgId(@PathVariable("organizationId") String organizationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByOrganizationId(organizationId);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/task/{taskId}")

	public ResponseEntity<?> updatetaskDetails(@RequestBody TaskMapper taskMapper,
			@PathVariable("taskId") String taskId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			taskMapper.setUserId(loggedInUserId);
			taskMapper.setOrganizationId(loggedInOrgId);
			TaskViewMapper taskViewMapper = taskService.updateTaskDetails(taskId, taskMapper);

			return new ResponseEntity<>(taskViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/task/{taskId}/employee/{employeeId}")

	public ResponseEntity<?> delinktask(@PathVariable("taskId") String taskId,
			@PathVariable("employeeId") String employeeId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = taskService.delinkTask(employeeId, taskId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/approve/task/{taskId}")
	public ResponseEntity<?> approveTaskByTaskId(@PathVariable("taskId") String taskId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			TaskViewMapper taskMapper = taskService.approveTask(taskId, userId);
			return new ResponseEntity<>(taskMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/reject/task/{taskId}")
	public ResponseEntity<?> rejectTaskByTaskId(@PathVariable("taskId") String taskId, @RequestBody String rejectReason,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			TaskViewMapper taskMapper = taskService.rejectTask(taskId, userId, rejectReason);
//			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(taskMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/taskType")
	public ResponseEntity<?> createTaskType(@RequestBody TaskMapper taskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		String taskTypeId = null;
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			if (!StringUtils.isEmpty(taskMapper.getTaskType())) {
				boolean b = taskService.checkTaskNameInTaskTypeByOrgLevel(taskMapper.getTaskType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("taskInd", b);
					map.put("message", "TaskType can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					taskMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
					taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

					taskTypeId = taskService.saveTaskType(taskMapper);
					return new ResponseEntity<>(taskTypeId, HttpStatus.OK);
				}

			} else {
				map.put("message", "Please Provide TaskType !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/taskType")
	public ResponseEntity<?> getAllTaskType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = taskService.getTaskTypeByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(typeList, HttpStatus.OK);
	}

	@GetMapping("/api/v1/taskType/drop-down/{orgId}")
	public ResponseEntity<?> getTaskTypeForDropDownByOrgId(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskTypeDropMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			typeList = taskService.getTaskTypeForDropDownByOrgId(orgId);
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(typeList, HttpStatus.OK);
	}

	@PutMapping("/api/v1/taskType")
	public ResponseEntity<?> updateTaskType(@RequestBody TaskMapper taskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (!StringUtils.isEmpty(taskMapper.getTaskType())) {
				Map map = new HashMap();
				boolean b = taskService.checkTaskNameInTaskTypeByOrgLevel(taskMapper.getTaskType(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					map.put("taskInd", b);
					map.put("message", "TaskType can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}

			}
			TaskMapper taskMapperr = taskService.updateTaskType(taskMapper.getTaskTypeId(), taskMapper);

			return new ResponseEntity<TaskMapper>(taskMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/task/{candidateId}")

	public ResponseEntity<?> getTaskListOfCandidate(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskListOfCandidateByCandidateId(candidateId);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/task/convert/contact/{contactId}")
	public ResponseEntity<?> createTaskForAccessContactToUser(@PathVariable("contactId") String contactId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			ContactViewMapper mapper = taskService.saveToTaskConvertPartnerContact(contactId, userId, organizationId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/task/save/email-details")
	public String saveCandidateEmailDetails(@RequestBody CandidateEmailDetailsMapper candidateEmailDetailsMapper,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException,
			IOException {
		String candidateEmailsDetailsId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));

			candidateEmailDetailsMapper.setUserId(userId);
			candidateEmailDetailsMapper.setOrganizationId(organizationId);

			candidateEmailsDetailsId = taskService.saveCandidateEmailDetails(candidateEmailDetailsMapper);

		}

		return candidateEmailsDetailsId;

	}

	@GetMapping("/api/v1/taskType/{name}")
	public ResponseEntity<?> getTasktypeByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<TaskMapper> taskMapper = taskService.getTasktypeByNameByOrgLevel(name, orgId);
			if (null != taskMapper && !taskMapper.isEmpty()) {
				return new ResponseEntity<>(taskMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/taskType/{taskTypeId}")

	public ResponseEntity<?> deleteTaskType(@PathVariable("taskTypeId") String taskTypeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskService.deleteTaskTypeById(taskTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/count/Lists/{userId}")
	public ResponseEntity<?> getTaskListsByUserIdStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = taskService.getTaskListsByUserIdStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/type/Lists/{userId}")
	public ResponseEntity<?> getTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			Map<String, List<TaskViewMapper>> mapperList = taskService
					.getTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/candidate/web/{candidateId}")
	public ResponseEntity<?> getTaskByCandidateIdForWebsite(@PathVariable("candidateId") String candidateId,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			List<TaskViewMapper> taskList = taskService.getTaskByCandidateIdForWebsite(candidateId);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@PutMapping("/api/v1/task/candidate/web/status/{candidateId}/{taskId}")
	public ResponseEntity<?> updateCandidateTaskStatus(@RequestBody TaskMapper taskMapper,
			@PathVariable("candidateId") String candidateId, @PathVariable("taskId") String taskId,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			TaskViewMapper task = taskService.updateCandidateComplitionStatus(taskMapper, candidateId, taskId);
			return new ResponseEntity<>(task, HttpStatus.OK);

		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@GetMapping("/api/v1/task/task-list/{ProjectId}")
	public ResponseEntity<?> getListOfTaskByProjectId(@PathVariable("ProjectId") String ProjectId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskViewMapper> mapperList = taskService.getListOfTaskByProjectId(ProjectId);
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/task/completionstatus/{taskId}")
	public ResponseEntity<?> updatetaskCompletionInd(@RequestBody TaskMapper taskMapper,
			@PathVariable("taskId") String taskId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			taskMapper.setUserId(userId);
			TaskViewMapper taskViewMapper = taskService.updatetaskCompletionInd(taskId, taskMapper);

			return new ResponseEntity<>(taskViewMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/un-completed/task-list/{candidateId}")
	public ResponseEntity<?> getListOfTaskByCandidateId(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskViewMapper> mapperList = taskService.getListOfTaskByCandidateId(candidateId);
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/employee/delete/task-list/{employeeId}")

	public ResponseEntity<?> getDeletedTaskListByEmpId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getDeletedTaskListByEmpId(employeeId);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/task/comment/save")
	public ResponseEntity<?> createTaskComment(@RequestBody TaskCommentMapper taskCommentMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			TaskCommentMapper taskId = taskService.createTaskComment(taskCommentMapper);

			return new ResponseEntity<>(taskId, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/task-comment/{taskCommentId}")
	public ResponseEntity<?> getTaskCommentByTaskCommentId(@PathVariable("taskCommentId") String taskCommentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			TaskCommentMapper mapperList = taskService.getTaskCommentByTaskCommentId(taskCommentId);
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/task-comment/all/list/{taskId}")
	public ResponseEntity<?> getTaskCommentListByTaskId(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskCommentMapper> mapperList = taskService.getTaskCommentListByTaskId(taskId);
			if (null != mapperList && !mapperList.isEmpty()) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/task/comment/save/website")
	public ResponseEntity<?> createTaskCommentForWebSite(@RequestBody TaskCommentMapper taskCommentMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			TaskCommentMapper taskId = taskService.createTaskComment(taskCommentMapper);

			return new ResponseEntity<>(taskId, HttpStatus.OK);

		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/task/task-comment/all/list/website/{taskId}")
	public ResponseEntity<?> getTaskCommentListByTaskIdForWebsite(@PathVariable("taskId") String taskId,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			List<TaskCommentMapper> mapperList = taskService.getTaskCommentListByTaskId(taskId);
			if (null != mapperList && !mapperList.isEmpty()) {
				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/taskApprove/employee/{employeeId}/{pageNo}")
	public ResponseEntity<?> getApprovedAndRejecttaskByEmpId(@PathVariable("employeeId") String employeeId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByEmployeeIdPagination(employeeId, pageNo, true);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/taskType/activeTaskCheckList")
	public ResponseEntity<?> activeTaskCheckList(@RequestBody TaskMapper taskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			taskMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			taskMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			TaskMapper taskMapperr = taskService.activeTaskCheckList(taskMapper);

			return new ResponseEntity<TaskMapper>(taskMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/count/opentask/{userId}")
	public ResponseEntity<?> getOpenTaskListsByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = taskService.getOpenTaskListsByUserId(userId);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping("/api/v1/task/employee/{employeeId}")
	public ResponseEntity<?> gettaskDetailsByEmpId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByEmployeeId(employeeId);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/teamList/{taskId}")
	public ResponseEntity<?> gettaskTeamListByTaskId(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TeamEmployeeMapper> resultList = taskService.getTaskTeamListByTaskId(taskId);
			if (null != resultList && !resultList.isEmpty()) {
				Collections.sort(resultList, (m1, m2) -> m1.getName().compareTo(m2.getName()));
			}
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/taskType/taskcheckList")
	public ResponseEntity<?> getAllTaskTypeTaskcheckList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		List<TaskMapper> typeList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			typeList = taskService.getAllTaskTypeTaskcheckList(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != typeList && !typeList.isEmpty()) {
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(typeList, HttpStatus.OK);
	}

	@PostMapping("/api/v1/task/subTask")
	public ResponseEntity<?> createSubTaskByTaskId(@RequestBody SubTaskMapper subTaskMapper, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = (jwtTokenUtil.getUserIdFromToken(authToken));
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			SubViewTaskMapper mapper = taskService.createSubTask(subTaskMapper);

			return new ResponseEntity<>(mapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/subTask/{taskId}")
	public ResponseEntity<?> getSubTaskByTaskId(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<SubViewTaskMapper> resultList = taskService.getSubTaskByTaskId(taskId);
			if (null != resultList && !resultList.isEmpty()) {
				// Collections.sort(resultList, (m1, m2) ->
				// m1.getName().compareTo(m2.getName()));
			}
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/type/count/{userId}")
	public ResponseEntity<?> getOpenTaskCountByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(taskService.getOpenTaskCountByUserId(userId, organizationId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/leave/status/{leaveId}")
	public ResponseEntity<?> getLeaveStatusByLeaveId(@PathVariable("leaveId") String leaveId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ApprovedStatusMapper> mapper = taskService.getLeaveStatusByLeaveId(leaveId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/mileage/status/{voucherId}")
	public ResponseEntity<?> getMileageStatusByVoucherId(@PathVariable("voucherId") String voucherId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ApprovedStatusMapper> mapper = taskService.getMileageStatusByVoucherId(voucherId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/expense/status/{voucherId}")
	public ResponseEntity<?> getExpenseStatusByVoucherId(@PathVariable("voucherId") String voucherId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ApprovedStatusMapper> mapper = taskService.getExpenseStatusByVoucherId(voucherId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/type/list/{userId}")
	public ResponseEntity<?> getTaskListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return new ResponseEntity<>(taskService.getTaskListByUserId(userId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/{leadsId}/{pageNo}")

	public ResponseEntity<?> getTaskDetailsByLeads(@PathVariable("leadsId") String leadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByLeads(leadsId, pageNo);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/count/highPriority/{userId}")
	public ResponseEntity<?> getHighPriorityByUserIdStartdateAndEndDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(
					taskService.getHighPriorityByUserIdStartdateAndEndDate(userId, startDate, endDate), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/document/{taskId}")
	public ResponseEntity<?> getDocumentListByTaskId(@PathVariable("taskId") String taskId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> documentList = taskService.getDocumentListByTaskId(taskId);
			return new ResponseEntity<>(documentList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/task/document/{documentId}")
	public ResponseEntity<?> deleteLeadsDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskService.deleteDocumentsById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/count/openTask/{userId}")
	public ResponseEntity<?> getOpenTaskCountByUserIdBetweenStartDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(
					taskService.getOpenTaskCountByUserIdBetwennStartDate(userId, startDate, endDate), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/count/deadlineTask/{userId}")
	public ResponseEntity<?> getDeadlineTaskByUserIdBetweenDate(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(taskService.getDeadlineTaskByUserIdBetweenDate(userId, startDate, endDate),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/dateRange/openTask/{userId}")
	public ResponseEntity<?> getOpenTasktByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(taskService.getOpenTasktByUserIdAndDateRange(userId, startDate, endDate),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/dateRange/myTask/{userId}")
	public ResponseEntity<?> getMyTasktByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(taskService.getMyTasktByUserIdAndDateRange(userId, startDate, endDate),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/openTask/list/{userId}")
	public ResponseEntity<?> getOpenTaskListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(taskService.getOpenTaskListByUserId(userId), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/investor/{investorLeadsId}/{pageNo}")

	public ResponseEntity<?> getTaskDetailsByInvestorLeadsId(@PathVariable("investorLeadsId") String investorLeadsId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByInvestorLeadsId(investorLeadsId, pageNo);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/type/completed/count/{userId}")
	public ResponseEntity<?> getCountTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<Map<String, Integer>> mapperList = taskService
					.getCountTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/task/notes")
	public ResponseEntity<?> createTaskNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = taskService.saveTaskNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/note/{taskId}")
	public ResponseEntity<?> getNoteListByTaskId(@PathVariable("taskId") String taskId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = taskService.getNoteListByTaskId(taskId);
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

//	@PutMapping("/api/v1/task/note/update")
//	public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//		NotesMapper notesMapperr = null;
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			notesMapperr = taskService.updateNoteDetails(notesMapper);
//			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@GetMapping("/api/v1/task/open/type/list/{userId}/{Typeame}")
	public ResponseEntity<?> getTaskListByTaskTypeIdAndUserId(@PathVariable("userId") String userId,
			@PathVariable("Typeame") String Typeame, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			return new ResponseEntity<>(taskService.getTaskListByTaskTypeIdAndUserId(userId, Typeame), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/task/note/{notesId}")
	public ResponseEntity<?> deleteTaskNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			taskService.deleteTaskNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/activity/task/create")
	public ResponseEntity<?> createactivityTask(@RequestBody TaskMapper taskMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			taskMapper.setUserId(loggedInUserId);
			taskMapper.setOrganizationId(loggedInOrgId);
			ActivityMapper taskId = taskService.saveActivityTask(taskMapper);

			return new ResponseEntity<>(taskId, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/closeTask/list/{userId}/{type}")
	public ResponseEntity<?> getCloseTaskListByUserId(@PathVariable("userId") String userId, @PathVariable String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return new ResponseEntity<>(taskService.getCloseTaskListByUserId(userId, type), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/included/user/{userId}/{pageNo}")
	public ResponseEntity<?> getTaskListPageWiseByIncludedUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> oppList = taskService.getTaskListPageWiseByIncludedUserId(userId, pageNo, pageSize);
			if (null != oppList && !oppList.isEmpty()) {
				Collections.sort(oppList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
			}
			return new ResponseEntity<>(oppList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/included/record/count/{userId}")
	public ResponseEntity<?> getCountListByIncludedUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(taskService.getCountListByIncludedUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/taskType/count/{orgId}")
	public ResponseEntity<?> getTaskTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(taskService.getTaskTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/teams/{userId}/{pageNo}")
	public ResponseEntity<?> getTeamTaskByUnderUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization) {
		int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskViewMapper> oppViewMapper = taskService.getTeamTaskByUnderUserId(userId, pageNo, pageSize);
			return new ResponseEntity<>(oppViewMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/teams/count/{userId}")
	public ResponseEntity<?> getTeamTaskCountByUnderUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(taskService.getTeamTaskCountByUnderUserId(userId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/type/completed/Lists/{userId}{typeName}")
	public ResponseEntity<?> getCompletedTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "typeName") String typeName, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskViewMapper> mapperList = taskService
					.getCompletedTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(userId, startDate, endDate,
							typeName);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/all-task/list/{userId}/{typeName}/{quarter}/{year}")
	public ResponseEntity<?> getTaskListByTaskTypeIdAndUserIdAndQuarterAndYear(@PathVariable("userId") String userId,
			@PathVariable("typeName") String typeName, @PathVariable("quarter") String quarter,
			@PathVariable("year") int year, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> mapperList = taskService.getTaskListByTaskTypeIdAndUserIdAndQuarterAndYear(userId,
					typeName, quarter, year);
			if (null != mapperList && !mapperList.isEmpty()) {
				Collections.sort(mapperList, (v1, v2) -> v1.getEndDate().compareTo(v2.getEndDate()));
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/task/step")
	public ResponseEntity<?> createTaskStep(@RequestBody TaskStepsMapper mapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);

			TaskStepsMapper result = taskService.createTaskStep(mapper, loggedInUserId);

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/steps/{taskId}")
	public ResponseEntity<?> getTaskStepsByTaskId(@RequestHeader("Authorization") String authorization,
			@PathVariable String taskId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(taskService.getTaskStepsByTaskId(taskId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/task/steps")
	public ResponseEntity<?> updateTaskStepsByStepId(@RequestHeader("Authorization") String authorization,
			@RequestBody TaskStepsMapper mapper) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			return ResponseEntity.ok(taskService.updateTaskStepsByStepId(mapper, loggedInUserId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/task/steps/{stepId}")
	public ResponseEntity<?> deleteTaskStepsByStepId(@RequestHeader("Authorization") String authorization,
			@PathVariable String stepId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(taskService.deleteTaskStepsByStepId(stepId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/task/drag-and-drop/weak-to-weak")
	public ResponseEntity<?> transferTaskOneWeakToAnother(@RequestBody TaskDragDropMapper mapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			TaskViewMapper mapperList = taskService.transferTaskOneWeakToAnother(mapper);
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/all/pending/un-completed/task-list/{userId}/{typeName}")
	public ResponseEntity<?> getPendingTaskListByTaskTypeIdAndUserId(@PathVariable("userId") String userId,
			@PathVariable("typeName") String typeName, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> mapperList = taskService.getPendingTaskListByTaskTypeIdAndUserId(userId, typeName);
			if (null != mapperList && !mapperList.isEmpty()) {
				Collections.sort(mapperList, (v1, v2) -> v1.getEndDate().compareTo(v2.getEndDate()));
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/dev/{userId}")
	public ResponseEntity<?> getTaskDevelopmentHourByDateRange(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<Map<String, Object>> result = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			result = taskService.calculateHoursAndDefinedHours(startDate, endDate, userId);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/all-task/list/sales-plan/{userId}")
	public ResponseEntity<?> getTaskListByUserIdWithYearAndQuarter(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> mapperList = taskService.getTaskListByUserIdWithYearAndQuarter(userId);
			if (null != mapperList && !mapperList.isEmpty()) {
				Collections.sort(mapperList, (v1, v2) -> v1.getEndDate().compareTo(v2.getEndDate()));
			}
			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/task/activity/update/{taskId}")
	public ResponseEntity<?> updateActivityTaskDetails(@RequestBody TaskMapper taskMapper,
			@PathVariable("taskId") String taskId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			taskMapper.setUserId(loggedInUserId);
			taskMapper.setOrganizationId(loggedInOrgId);
			ActivityMapper taskViewMapper = taskService.updateActivityTaskDetails(taskId, taskMapper);

			return new ResponseEntity<>(taskViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/task/report/self/{userId}/{type}")
	public ResponseEntity<?> getTaskListsByTaskTypeIdAndUserIdAndStartdateAndEndDate(@PathVariable String userId,
			@PathVariable String type, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TaskViewMapper> mapperList = taskService
					.getTaskListsByTaskTypeIdAndUserIdAndStartdateAndEndDate(userId, startDate, endDate, type);

			return new ResponseEntity<>(mapperList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/room/{roomId}/{pageNo}")
	public ResponseEntity<?> getTaskDetailsByRoomId(@PathVariable String roomId, @PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<TaskViewMapper> taskList = taskService.getTaskDetailsByRoomId(roomId, pageNo);
			Collections.sort(taskList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(taskList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/task/search/alltype/{name}/{type}")
	public ResponseEntity<?> getCustomerByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<TaskViewMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = taskService.getTaskDetailsByNameByOrgId(name, orgId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else if (type.equalsIgnoreCase("team")) {
				list = taskService.getTaskDetailsByNameForTeam(name, userId, orgId);
				return ResponseEntity.ok(list);
			} else if (type.equalsIgnoreCase("user")) {
				list = taskService.getTaskDetailsByNameByUserId(name, userId, orgId);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("contact list is empty", HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
