package com.app.employeePortal.monster.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.monster.entity.MonsterPublish;
import com.app.employeePortal.monster.mapper.MonsterBoardMapper;
import com.app.employeePortal.monster.mapper.MonsterCategoryMapper;
import com.app.employeePortal.monster.mapper.MonsterCredentialsMapper;
import com.app.employeePortal.monster.mapper.MonsterIndustryMapper;
import com.app.employeePortal.monster.mapper.MonsterOccupationMapper;
import com.app.employeePortal.monster.mapper.MonsterPublishMapper;
import com.app.employeePortal.monster.service.MonsterService;


@RestController
@CrossOrigin(maxAge = 3600)



public class MonsterController {
	
	

	@Autowired
	MonsterService monsterService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	
	
	@PostMapping("/api/v1/monster/credentials")
	public ResponseEntity<?> createMonsterCredentials(@RequestBody MonsterCredentialsMapper monsterCredentialsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(authorization)) {
			//String authToken = authorization.replace(authorization, "");
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
		
			monsterCredentialsMapper.setOrgId(loggedInOrgId);
			monsterCredentialsMapper.setUserId(loggedInUserId);
			
			String monsterCredentialsId = monsterService.saveMonsterCredentials(monsterCredentialsMapper);

			return new ResponseEntity<String>(monsterCredentialsId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/monster/credentials/{orgId}")
	public ResponseEntity<?> getMonsterCredentialsByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,
			
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(authorization)) {
			

			MonsterCredentialsMapper monsterCredentialsList = monsterService.getMonsterCredentialsByOrgId(orgId);
				
				return new ResponseEntity<>(monsterCredentialsList, HttpStatus.OK);

		
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/job/board")
	public ResponseEntity<?> getAllJobBoardList( HttpServletRequest request,@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(authorization)) {
			
			List<MonsterBoardMapper> monsterBoardList = monsterService.jobBoardList();
	
			return new ResponseEntity<>(monsterBoardList, HttpStatus.OK);

			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/job/category")
	public ResponseEntity<?> getAllJobCategoryList( HttpServletRequest request,@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(authorization)) {
			
			List<MonsterCategoryMapper> monsterCategoryList = monsterService.jobCategoryList();
	
			return new ResponseEntity<>(monsterCategoryList, HttpStatus.OK);

			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/job/occupation")
	public ResponseEntity<?> getAllJobOccupationList( HttpServletRequest request,@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(authorization)) {
			
			List<MonsterOccupationMapper> monsterOccupationList = monsterService.jobOccupationList();
	
			return new ResponseEntity<>(monsterOccupationList, HttpStatus.OK);

			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/job/industry")
	public ResponseEntity<?> getAllJobIndustryList( HttpServletRequest request,@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(authorization)) {
			
			List<MonsterIndustryMapper> monsterIndustryList = monsterService.jobIndustryList();
	
			return new ResponseEntity<>(monsterIndustryList, HttpStatus.OK);

			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/api/v1/monster/publish")
	public String createMonsterPublish(@RequestBody MonsterPublishMapper monsterPublishMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		String monsterPublishId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			monsterPublishMapper.setOrganizationId(orgId);
			monsterPublishMapper.setUserId(userId);
			
			monsterPublishId = monsterService.saveToMonsterPublish(monsterPublishMapper);
        }

		return monsterPublishId;
	}
	
	@GetMapping("/api/v1/monster/{requirementId}")
	public ResponseEntity<?> getMonsterDetailsById(@PathVariable("requirementId") String requirementId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<MonsterPublishMapper> monsterPublishMapper = monsterService.getMonsterDetailsById(requirementId);
			//Collections.sort(candidateEducationDetailsMapper, (CandidateEducationDetailsMapper m1, CandidateEducationDetailsMapper m2) -> m2.getCreationDate()
 					//.compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(monsterPublishMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/monster/all/publish")

	public ResponseEntity<?> getallMonsterPublish(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<MonsterPublish> monsterPublishList = monsterService.getMonsterPublishList();

			if (null != monsterPublishList && !monsterPublishList.isEmpty()) {

				return new ResponseEntity<>(monsterPublishList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	

	    	
	
}
	