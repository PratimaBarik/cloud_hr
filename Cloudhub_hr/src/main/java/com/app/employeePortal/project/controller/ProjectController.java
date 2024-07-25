package com.app.employeePortal.project.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.project.mapper.ProjectMapper;
import com.app.employeePortal.project.service.ProjectService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(maxAge = 3600)
public class ProjectController {
	@Autowired
	ProjectService projectService;

	@Autowired
	private TokenProvider jwtTokenUtil;

//	@PostMapping("/api/v1/project")
//	public ResponseEntity<?> createEvent(@RequestBody ProjectMapper projectMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
//			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
//
//			projectMapper.setUserId(loggedInUserId);
//			projectMapper.setOrganizationId(loggedInOrgId);
//
//			String projectId = projectService.saveProject(projectMapper);
//
//			System.out.println("projectId....." + projectId);
//			return new ResponseEntity<>(projectId, HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//
//	/*
//	 * @GetMapping("/api/v1/project/{projectId}") public ResponseEntity<?>
//	 * getProjectByProjectId(@PathVariable("projectId") String projectId,
//	 * 
//	 * @RequestHeader("Authorization") String authorization ,HttpServletRequest
//	 * request) throws JsonGenerationException, JsonMappingException, IOException{
//	 * 
//	 * 
//	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//	 * List<ProjectMapper> projectList =
//	 * projectService.getProjectByProjectId(projectId);
//	 * Collections.sort(projectList, (ProjectMapper m1, ProjectMapper m2) -> m2.
//	 * .compareTo(m1.getCreationDate())); return new
//	 * ResponseEntity<>(projectList,HttpStatus.OK);
//	 * 
//	 * 
//	 * }
//	 * 
//	 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
//	 * 
//	 * 
//	 * 
//	 * @DeleteMapping("/api/v1/project/projectId")
//	 * 
//	 * public ResponseEntity<?> deleteProject(@PathVariable("projectId") String
//	 * projectId,
//	 * 
//	 * @RequestHeader("Authorization") String authorization ,HttpServletRequest
//	 * request){
//	 * 
//	 * 
//	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//	 * 
//	 * boolean b = projectService.deleteProject(projectId); return new
//	 * ResponseEntity<>(b, HttpStatus.OK);
//	 * 
//	 * } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	 * 
//	 * }
//	 */
//
//	@PutMapping("/api/v1/project/{projectId}")
//
//	public ResponseEntity<?> updateProjectDetails(@RequestBody ProjectMapper projectMapper,
//			@PathVariable("projectId") String projectId, @RequestHeader("Authorization") String authorization,
//			HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
//			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
//			projectMapper.setUserId(loggedInUserId);
//			projectMapper.setOrganizationId(loggedInOrgId);
//			String id = projectService.updateProject(projectId, projectMapper);
//
//			return new ResponseEntity<>(id, HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	
	
	
	
	@PostMapping("/api/v1/project/save")
	public ResponseEntity<?> createProject(@RequestBody ProjectMapper projectMapper,
			@RequestHeader("Authorization") String authorization) {

		String unitId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			projectMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			projectMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			unitId = projectService.saveProject(projectMapper);
			return new ResponseEntity<>(unitId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/project/all/{orgId}")
	public ResponseEntity<?> getAllProjectTypeByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ProjectMapper>  projectList = projectService.getAllProjectTypeByOrgId(orgId);

			if (null != projectList && !projectList.isEmpty()) {
				Collections.sort(projectList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(projectList, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(projectList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@PutMapping("/api/v1/project/update")
	public ResponseEntity<?> updateProject(@RequestBody ProjectMapper projectMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ProjectMapper projectMapperr = projectService.updateProject(projectMapper);

			return new ResponseEntity<ProjectMapper>(projectMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/ProjectDetails/{ProjectId}")
	public ResponseEntity<?> getProjectDetailsById(@PathVariable("ProjectId") String ProjectId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ProjectMapper projectMapper = projectService.getProjectDetailsById(ProjectId);

			return new ResponseEntity<ProjectMapper>(projectMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@DeleteMapping("/api/v1/project/{projectId}")
	public ResponseEntity<?> deleteProjectDetails(@PathVariable("projectId") String projectId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			projectService.deleteProjectById(projectId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/ProjectDetails/project-list/{customerId}")
	public ResponseEntity<?> getProjectDetailsByCustomerId(@PathVariable("customerId") String customerId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<ProjectMapper> projectMapper = projectService.getProjectDetailsByCustomerId(customerId);

			return new ResponseEntity<>(projectMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	
}
