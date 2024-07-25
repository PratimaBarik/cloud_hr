package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.WorkflowCategoryMapper;
import com.app.employeePortal.category.service.WorkflowCategoryService;

@RestController
@CrossOrigin(maxAge = 3600)

public class WorkflowCategoryController {
	@Autowired
	WorkflowCategoryService workflowCategoryService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/workflowCategory")
	public ResponseEntity<?> createWorkflowCategory(@RequestBody WorkflowCategoryMapper mapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(mapper.getName())) {
				boolean b = workflowCategoryService.checkNameInWorkflowCategory(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken));
				if (b == true) {
					System.out.println("true");
					map.put("WorkflowCategoryInd", b);
					map.put("message", "WorkflowCategory can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					WorkflowCategoryMapper Id = workflowCategoryService.saveWorkflowCategory(mapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", "please provide a WorkflowCategory");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/workflowCategory/All")
	public ResponseEntity<?> getWorkflowCategoryByOrgId(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<WorkflowCategoryMapper> mapper = workflowCategoryService
					.getWorkflowCategoryByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/workflowCategory/{workflowCategoryId}")
	public ResponseEntity<?> updateWorkflowCategory(@PathVariable("workflowCategoryId") String workflowCategoryId,
			@RequestBody WorkflowCategoryMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(mapper.getName())) {

				boolean b = workflowCategoryService.checkNameInWorkflowCategoryInUpdate(mapper.getName(),
						jwtTokenUtil.getOrgIdFromToken(authToken), workflowCategoryId);
				if (b == true) {
					map.put("WorkflowCategoryInd", b);
					map.put("message", "WorkflowCategory can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			return new ResponseEntity<WorkflowCategoryMapper>(
					workflowCategoryService.updateWorkflowCategory(workflowCategoryId, mapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/workflowCategory/{workflowCategoryId}")
	public ResponseEntity<?> deleteWorkflowCategory(@PathVariable("workflowCategoryId") String workflowCategoryId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			workflowCategoryService.deleteWorkflowCategory(workflowCategoryId,
					jwtTokenUtil.getUserIdFromToken(authToken));

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/workflowCategory/search/{name}")
	public ResponseEntity<?> getWorkflowCategoryByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<WorkflowCategoryMapper> mapper = workflowCategoryService.getWorkflowCategoryByName(name,
					jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != mapper && !mapper.isEmpty()) {
				return new ResponseEntity<>(mapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/workflowCategory/count/All")
	public ResponseEntity<?> getWorkflowCategoryCountByOrgId(@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(
					workflowCategoryService.getWorkflowCategoryCountByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken)));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
