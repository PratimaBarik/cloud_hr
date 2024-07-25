package com.app.employeePortal.candidate.controller;

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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.app.employeePortal.call.service.CallService;
import com.app.employeePortal.candidate.mapper.ActivityMapper;
import com.app.employeePortal.candidate.mapper.CandidateBankDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateCertificationLinkMapper;
import com.app.employeePortal.candidate.mapper.CandidateDocumentMapper;
import com.app.employeePortal.candidate.mapper.CandidateDropDownMapper;
import com.app.employeePortal.candidate.mapper.CandidateEducationDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailRequestMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailResponseMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmploymentHistoryMapper;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.mapper.CandidateRoleCountMapper;
import com.app.employeePortal.candidate.mapper.CandidateTrainingMapper;
import com.app.employeePortal.candidate.mapper.CandidateTreeMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateWebsiteMapper;
import com.app.employeePortal.candidate.mapper.DefinationMapper;
import com.app.employeePortal.candidate.mapper.FilterMapper;
import com.app.employeePortal.candidate.mapper.SkillCandidateNoMapper;
import com.app.employeePortal.candidate.mapper.SkillSetMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.mapper.WebSiteRecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.videoClips.mapper.VideoClipsMapper;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "candidate" })

public class CandidateController {

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	CallService callService;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	
	@PostMapping("/api/v1/candidate")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveCandidate(@RequestBody CandidateMapper candidateMapper,
			@RequestHeader("Authorization") String authorization) {
		String ownerName = null;
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			candidateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			candidateMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			candidateMapper.setChannel("Self");

			if (!StringUtils.isEmpty(candidateMapper.getEmailId())) {
				boolean c = candidateService.emailExistsInCandidate(candidateMapper.getEmailId());
				if (c == true) {
					map.put("candidateInd", true);
					map.put("message", "Candidate with same mail already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					CandidateViewMapper id = candidateService.saveCandidate(candidateMapper);
					return new ResponseEntity<>(id, HttpStatus.OK);

				}
			}else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/{candidateId}")

	public ResponseEntity<?> getCandidateById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateViewMapper candidateMapper = candidateService.getCandidateDetailsById(candidateId);

			return new ResponseEntity<CandidateViewMapper>(candidateMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/{candidateId}")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> upateCandidate(@PathVariable("candidateId") String candidateId,
			@RequestBody CandidateMapper candidateMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		//String ownerName = null;
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			candidateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			candidateMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(candidateMapper.getEmailId())) {
				boolean c = candidateService.emailExistsInCandidate(candidateMapper.getEmailId());
				if (c == true) {
					map.put("candidateInd", true);
					map.put("message", "Candidate with same mail id already exists. ");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					CandidateViewMapper mapper = candidateService.upateCandidateDetailsById(candidateId,
							candidateMapper);

					return new ResponseEntity<CandidateViewMapper>(mapper, HttpStatus.OK);

				}
			} else {
				CandidateViewMapper mapper = candidateService.upateCandidateDetailsById(candidateId, candidateMapper);

				return new ResponseEntity<CandidateViewMapper>(mapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/user/{userId}/{pageNo}")
	//@Cacheable(key = "#userId")
	public ResponseEntity<List<CandidateViewMapper>> getCandidatetListByUserId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo, @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if(userId.equalsIgnoreCase("All")) {
				List<CandidateViewMapper> candidateList = candidateService.getAllCandidateList(pageNo, pageSize);
				Collections.sort(candidateList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return ResponseEntity.ok(candidateList);
			}else {
			List<CandidateViewMapper> candidateList = candidateService.getCandidateListPageWiseByUserId(userId,pageNo, pageSize);
			if (candidateList != null && !candidateList.isEmpty()) {
			Collections.sort(candidateList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(candidateList, HttpStatus.OK);
		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/skillSet")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveSkillSet(@RequestBody SkillSetMapper skillSetMapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(skillSetMapper.getSkillName())) {
				boolean b = candidateService.checkSkillInCustomerSkillSet(skillSetMapper.getSkillName(),skillSetMapper.getCandidateId());
				if (b == true) {
					map.put("skillInd", b);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			skillSetMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			skillSetMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = candidateService.saveSkillSet(skillSetMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/skill-set/{candidateId}")

	public ResponseEntity<?> getSkillSetById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<SkillSetMapper> skillSetMapper = candidateService.getSkillSetDetails(candidateId);
			skillSetMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(skillSetMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/candidate/candidate-training")
	public ResponseEntity<?> saveCandidateTraining(@RequestBody CandidateTrainingMapper candidateTrainingMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = candidateService.saveCandidateTraining(candidateTrainingMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/candidate-training/{candidateId}")

	public ResponseEntity<?> getTrainingDetailsById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateTrainingMapper> trainingDetailsMapper = candidateService.getCandidateTraining(candidateId);
			trainingDetailsMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(trainingDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/candidate/notes")
	public ResponseEntity<?> createCandidateNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			notesMapper.setEmployeeId(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = candidateService.saveCandidateNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/notes/{candidateId}")

	public ResponseEntity<?> getOpportunityListByContactId(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<NotesMapper> notesMapper = candidateService.getNoteListByCandidateId(candidateId);
			notesMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return  ResponseEntity.ok(notesMapper);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/document/{candidateId}")

	public ResponseEntity<?> getCandidateDocumentListByCandidateId(@PathVariable("candidateId") String candidateId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> contactList = candidateService.getCandidateDocumentListByCandidateId(candidateId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/candidate/document/{documentId}")

	public ResponseEntity<?> deleteCandidateDocument(@PathVariable("documentId") String documentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			candidateService.deleteDocumentById(documentId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* save to candidateEducationalDetails */
	@PostMapping("/api/v1/candidate/education-details")
	public ResponseEntity<?> saveCandidateEducationDetails(
			@RequestBody CandidateEducationDetailsMapper candidateEducationDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			candidateEducationDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = candidateService.saveCandidateEducationDetails(candidateEducationDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/education-details/{candidateId}")
	public ResponseEntity<?> getCandidateEducationDetailsById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateEducationDetailsMapper> candidateEducationDetailsMapper = candidateService
					.getCandidateEducationDetails(candidateId);
			if( null!=candidateEducationDetailsMapper && !candidateEducationDetailsMapper.isEmpty()) {
			Collections.sort(candidateEducationDetailsMapper, (CandidateEducationDetailsMapper m1,
					CandidateEducationDetailsMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateEducationDetailsMapper, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(candidateEducationDetailsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/education/details/{userId}")
	public ResponseEntity<?> getCandidateEducationDetailsByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateEducationDetailsMapper> candidateEducationDetailsMapper = candidateService
					.getCandidateEducationDetailsByUserId(userId);
			Collections.sort(candidateEducationDetailsMapper, (CandidateEducationDetailsMapper m1,
					CandidateEducationDetailsMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateEducationDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/employment-history")
	public ResponseEntity<?> saveCandidateEmploymentHistory(
			@RequestBody CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = candidateService.saveCandidateEmployment(candidateEmploymentHistoryMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/employment-history/{candidateId}")
	public ResponseEntity<?> getCandidateEmploymentHistoryById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateEmploymentHistoryMapper> candidateEmploymentHistoryMapper = candidateService
					.getCandidateEmploymentHisory(candidateId);
			if(null!=candidateEmploymentHistoryMapper && !candidateEmploymentHistoryMapper.isEmpty()) {
			Collections.sort(candidateEmploymentHistoryMapper, (CandidateEmploymentHistoryMapper m1,
					CandidateEmploymentHistoryMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateEmploymentHistoryMapper, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(candidateEmploymentHistoryMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/candidate/education-details")

	public ResponseEntity<?> updateEducationDetails(
			@RequestBody CandidateEducationDetailsMapper candidateEducationDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateEducationDetailsMapper candidateEducation = candidateService
					.updateCandidateEducationalDetails(candidateEducationDetailsMapper);

			return new ResponseEntity<CandidateEducationDetailsMapper>(candidateEducation, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/candidate/education-details/{id}")

	public ResponseEntity<?> deleteEducationDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			candidateService.deleteCandidateEducationDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/details/{name}")
	public ResponseEntity<List<CandidateViewMapper>> getCandidateDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateMapper = candidateService.getCandidateDetailsByName(name);

			return new ResponseEntity<>(candidateMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/details-skill/{skill}")
	public ResponseEntity<List<CandidateViewMapper>> getCandidateDetailsBySkill(@PathVariable("skill") String skill,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateMapper = candidateService.getCandidateDetailsBySkill(skill);

			// Collections.sort(candidateMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/bank-details")
	public ResponseEntity<?> saveCandidateBankDetails(
			@RequestBody CandidateBankDetailsMapper candidateBankDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = candidateService.saveCandidateBankDetails(candidateBankDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/bank-details/{candidateId}")

	public ResponseEntity<?> getBankDetailsById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateBankDetailsMapper> bankDetailsMapper = candidateService.getCandidateBankDetails(candidateId);
			if(null!=bankDetailsMapper && !bankDetailsMapper.isEmpty()) {
			Collections.sort(bankDetailsMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(bankDetailsMapper, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(bankDetailsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/bank-details")

	public ResponseEntity<?> updateBankDetails(@RequestBody CandidateBankDetailsMapper bankDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateBankDetailsMapper newBankDetailsMapper = candidateService.updateBankDetails(bankDetailsMapper);

			return new ResponseEntity<CandidateBankDetailsMapper>(newBankDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/candidate/bank-details/{id}")

	public ResponseEntity<?> deleteBankdetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			candidateService.deleteBankDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/candidate/all-candidate/{pageNo}")
	public ResponseEntity<List<CandidateViewMapper>> getCustomerList(@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	int pageSize = 20;
		List<CandidateViewMapper> candidateList = candidateService.getAllCandidateList(pageNo, pageSize);
		Collections.sort(candidateList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
		return ResponseEntity.ok(candidateList);
	}

	@DeleteMapping("/api/v1/candidate/skilsset/{id}")
	public ResponseEntity<?> deleteSkilsset(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			candidateService.deleteSkilsset(id);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/record/count/{userId}")
	public ResponseEntity<?> getCountListByuserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(candidateService.getCountListByuserId(userId));

		}
		return null;

	}

	@PutMapping("/api/v1/candidate/candidate-training")
	public ResponseEntity<?> updateCandidateTraining(@RequestBody CandidateTrainingMapper candidateTrainingMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateTrainingMapper candidateTrainingMapperr = candidateService
					.updateCandidateTraining(candidateTrainingMapper);
		}

		return new ResponseEntity<CandidateTrainingMapper>(candidateTrainingMapper, HttpStatus.OK);
	}
	/*
	 * @GetMapping("/api/v1/candidate/skillSet") public
	 * ResponseEntity<List<SkillSetMapper>>
	 * getCandidateSkillSetList(@RequestHeader("Authorization") String
	 * authorization, HttpServletRequest request) {
	 * 
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) { String
	 * authToken = authorization.replace(TOKEN_PREFIX, "");
	 * 
	 * List<SkillSetMapper> candidateMapper =
	 * candidateService.getSkillSetOfCandidatesOfUser(jwtTokenUtil.
	 * getUserIdFromToken(authToken));
	 * 
	 * 
	 * return new ResponseEntity<>(candidateMapper, HttpStatus.OK);
	 * 
	 * 
	 * }
	 * 
	 * return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	 * 
	 * 
	 * }
	 */

	@PutMapping("api/v1/candidate/employment-history")
	public ResponseEntity<?> updateEmploymentHistory(
			@RequestBody CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapperr = candidateService
					.updateEmploymentHistory(candidateEmploymentHistoryMapper);

			return new ResponseEntity<CandidateEmploymentHistoryMapper>(candidateEmploymentHistoryMapperr,
					HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate-details/{name}")
	public ResponseEntity<List<CandidateViewMapper>> getCandidateListByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateMapper = candidateService.getCandidateListByName(name);

			// Collections.sort(candidateMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/activity/candidate/{candidateId}")
	public ResponseEntity<?> getCandidateActivityByCandidateId(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<ActivityMapper> activityList = candidateService.getCandidateActivityByCandidateId(candidateId);
			Collections.sort(activityList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(activityList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/defination")
	public ResponseEntity<?> saveCandidateDefination(@RequestBody DefinationMapper definationMapper,
			@RequestHeader("Authorization") String authorization) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (!StringUtils.isEmpty(definationMapper.getName())) {
				boolean b = candidateService.checkSkillInSkillLibery(definationMapper.getName());
				if (b == true) {
					map.put("definitionInd", true);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			DefinationMapper id = candidateService.saveCandidateDefination(definationMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/defination/{orgId}")
	public ResponseEntity<?> getDefinationsOfAdmin(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {
		List<DefinationMapper> typeList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			typeList = candidateService.getDefinationsOfAdmin(orgId);
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/defination/count/{orgId}")
	public ResponseEntity<?> getDefinationsCountByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(candidateService.getDefinationsCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/candidate/definations")

	public ResponseEntity<?> updateDefinations(@RequestBody DefinationMapper definationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			definationMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			definationMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			if (!StringUtils.isEmpty(definationMapper.getName())) {
				boolean b = candidateService.checkSkillInSkillLiberyInUpdate(definationMapper.getName(),definationMapper.getDefinationId());
				if (b == true) {
					map.put("definitionInd", true);
					map.put("message", "Skill can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			DefinationMapper newDefinationMapper = candidateService.updateDefinations(definationMapper);

			return new ResponseEntity<DefinationMapper>(newDefinationMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/organization/{orgId}")
	public ResponseEntity<?> getCandidateByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateDropDownMapper> list = candidateService.getCandidateByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/blackList/{candidateId}")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> addCandidateToBlackList(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = candidateService.blackListedCandidate(candidateId);

			return new ResponseEntity<>(msg, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/unblock/{candidateId}")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> addCandidateToUnBlockList(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = candidateService.unBlockCandidate(candidateId);

			return new ResponseEntity<>(msg, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	 @GetMapping("/api/v1/candidate/categoryName/{category}/{pageNo}/{userId}")
		public ResponseEntity<?> getcandidateListByCategory(@PathVariable("category") String category,@PathVariable("pageNo") int pageNo,
				@PathVariable("userId") String userId,@RequestHeader("Authorization") String authorization,
				                               HttpServletRequest request) throws JsonGenerationException,
		                                       JsonMappingException, IOException{	
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
	 
	@GetMapping("/api/v1/candidateName/{fullName}")
	public ResponseEntity<List<CandidateViewMapper>> getCandidateList(@PathVariable("fullName") String fullName,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateMapper = candidateService.getCandidateList(fullName);

			// Collections.sort(candidateMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/search/{skill}")
	public ResponseEntity<?> getCandidateByNameAndSkill(@PathVariable("skill") String skill,
			@RequestHeader("Authorization") String authorization) {
		List<CandidateViewMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(userId);

			if (employeeDetails.getEmployeeType().equalsIgnoreCase("Employee")) {
				System.out.println("inside employee==================///////////=========");

				list = candidateService.getCandidateDetailsBySkillAndOrgId(skill, orgId);
				if (null == list || list.isEmpty()) {
					System.out.println("insert to the if");
					list = candidateService.getCandidateDetailsByNameAndOrgId(skill, orgId);
				}
				if (null == list || list.isEmpty()) {
					list = candidateService.getCandidateDetailsByIdNumber(skill, orgId);
				}
			}
			if (employeeDetails.getEmployeeType().equalsIgnoreCase("Contractor")) {

				Permission permission = permissionRepository.findByOrgId(orgId);
				if (permission.isCandiContSrchInd() == true) {
					System.out.println("inside Contractor ////////orgId==================///////////=========");
					list = candidateService.getCandidateDetailsBySkillAndOrgId(skill, orgId);
					if (null == list || list.isEmpty()) {
						System.out.println("insert to the if");
						list = candidateService.getCandidateDetailsByNameAndOrgId(skill, orgId);
					}
					if (null == list || list.isEmpty()) {
						list = candidateService.getCandidateDetailsByIdNumber(skill, orgId);
					}
				} else {
					System.out.println("inside Contractor ////////userid==================///////////=========");
					list = candidateService.getCandidateDetailsBySkillAndUserId(skill, userId);
					if (null == list || list.isEmpty()) {
						System.out.println("insert to the if");
						list = candidateService.getCandidateDetailsByNameAndUserId(skill, userId);
					}
					if (null == list || list.isEmpty()) {
						list = candidateService.getCandidateDetailsByIdNumberAndUserId(skill, userId);
					}
				}
			}
			if (null != list && !list.isEmpty()) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/record/count/categoryName/{category}")
	public ResponseEntity<?> getCountListBycategory(@RequestHeader("Authorization") String authorization,
			@PathVariable("category") String category) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			return ResponseEntity.ok(candidateService.getCountListBycategory(category, userId));

		}
		return null;

	}

	@GetMapping("/api/v1/candidate/filter/{role}/{cost}")
	public ResponseEntity<?> getCandidateListByRollAndCost(@RequestHeader("Authorization") String authorization,
			@PathVariable("role") String role, @PathVariable("cost") String cost) {

		List<CandidateViewMapper> list = new ArrayList();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			list = candidateService.getCandidateListByRollAndCost(role, cost);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/candidate/skill/{skillId}")

	public ResponseEntity<?> updateCandidateSkill(@PathVariable("skillId") String skillId,
			@RequestBody SkillSetMapper skillSetMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			SkillSetMapper skillSetMapperNew = candidateService.updateCandidateSkill(skillId, skillSetMapper);

			return new ResponseEntity<SkillSetMapper>(skillSetMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/{organizationId}/{skill}")
	public ResponseEntity<?> getCandidateSkillByOrgId(@PathVariable("organizationId") String organizationId,
			@PathVariable("skill") String skill, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CandidateViewMapper> candidateList = candidateService.getCandidateSkillByOrgId(organizationId, skill);
			Collections.sort(candidateList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/skill/record/{skill}")
	public ResponseEntity<?> getCandidateCountListBySkill(@RequestHeader("Authorization") String authorization,
			@PathVariable("skill") String skill) {
		int record = 0;
		HashMap map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(userId);

			if (employeeDetails.getEmployeeType().equalsIgnoreCase("Employee")) {

				map = candidateService.getCandidateCountListBySkill(skill, orgId);
				if (null == map || map.isEmpty()) {
					System.out.println("insert to the if");
					map = candidateService.getCandidateCountListByName(skill, orgId);
				}
				if (null == map || map.isEmpty()) {
					map = candidateService.getCandidateCountListByIdNumber(skill, orgId);
				}
			}
			if (employeeDetails.getEmployeeType().equalsIgnoreCase("Contractor")) {

				Permission permission = permissionRepository.findByOrgId(orgId);
				if (permission.isCandiContSrchInd() == true) {
					map = candidateService.getCandidateCountListBySkill(skill, orgId);
					if (null == map || map.isEmpty()) {
						System.out.println("insert to the if");
						map = candidateService.getCandidateCountListByName(skill, orgId);
					}
					if (null == map || map.isEmpty()) {
						map = candidateService.getCandidateCountListByIdNumber(skill, orgId);
					}
				} else {

					map = candidateService.getCandidateCountListBySkillAndUserId(skill, userId);
					if (null == map || map.isEmpty()) {
						System.out.println("insert to the if");
						map = candidateService.getCandidateCountListByNameAndUserId(skill, userId);
					}
					if (null == map || map.isEmpty()) {
						map = candidateService.getCandidateCountListByIdNumberAndUserId(skill, userId);
					}

				}

			}
			if (null != map && !map.isEmpty()) {
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				map.put("record", record);
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/candidate/transfer/{userId}")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> updateTransferOneUserToAnother(@RequestBody CandidateMapper candidateMapper,
			@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<String> candiList = candidateService.updateTransferOneUserToAnother(userId, candidateMapper);

			return new ResponseEntity<>(candiList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/candidate/website")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveCandidateThroughWebsite(@RequestBody CandidateWebsiteMapper candidateMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			if (!StringUtils.isEmpty(candidateMapper.getEmailId())) {
				boolean c = candidateService.emailExistsByWebsite(candidateMapper.getEmailId());
				if (c == true) {
					map.put("candidateInd", true);
					map.put("message", "We already have your details,Thank you for registering.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					candidateMapper.setUserId(web.getUser_id());
					candidateMapper.setChannel("Website");
					candidateMapper.setCategory("Both");
					String id = candidateService.saveCandidateThroughWebsite(candidateMapper);

					map.put("ID", id);
					map.put("message",
							"Thank you for Registering . We will shortly reachout to you with matched Opportunities");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}

	}

	@PostMapping("/api/v1/candidate/filter")
	public ResponseEntity<?> getCandidateFilterList(@RequestHeader("Authorization") String authorization,
			@RequestBody FilterMapper filterMapper) {

		List<CandidateViewMapper> list = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			list = candidateService.getCandidateFilterList(filterMapper, userId, orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/defination/website")
	public ResponseEntity<?> getDefinationsToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {

		Map map = new HashMap();
		boolean b = candidateService.ipAddressExists(url);
		if (b == true) {
			List<DefinationMapper> definationMappernew = candidateService.getDefinationsByUrl(url);
			return new ResponseEntity<>(definationMappernew, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@PostMapping("/api/v1/candidate/Email")
	public ResponseEntity<?> getCandidateDetailsByEmail(
			@RequestBody CandidateEmailRequestMapper candidateEmailRequestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		List<CandidateEmailResponseMapper> candidateEmailResponseMapper = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			candidateEmailResponseMapper = candidateService.getCandidateDetailsByEmail(candidateEmailRequestMapper);
			return new ResponseEntity<List<CandidateEmailResponseMapper>>(candidateEmailResponseMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/doNotCall/{candidateId}")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> addCandidateToDoNotCallList(@RequestBody CallMapper callMapper,
			@PathVariable("candidateId") String candidateId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			callMapper.setUserId(userId);
			boolean b = callService.doNotCallCandidate(callMapper, candidateId);

			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/*
	 * @GetMapping("api/v1/candidate/all-blackListCandidate") public
	 * ResponseEntity<?> getAllBlackListCandidate(@RequestHeader("Authorization")
	 * String authorization, HttpServletRequest request) { List<CandidateViewMapper>
	 * blackList = new ArrayList<>(); if(authorization!=null &&
	 * authorization.startsWith(TOKEN_PREFIX)) {
	 * 
	 * String authToken = authorization.replace(TOKEN_PREFIX, "");
	 * 
	 * blackList =
	 * candidateService.getAllBlackListCandidate(jwtTokenUtil.getOrgIdFromToken(
	 * authToken));
	 * Collections.sort(blackList,(v1,v2)->v2.getCreationDate().compareTo(v1.
	 * getCreationDate()));
	 * 
	 * } return new ResponseEntity<>(blackList, HttpStatus.OK);
	 * 
	 * 
	 * }
	 */

	@GetMapping("/api/v1/candidate/blackList/{userId}")
	@Cacheable(key = "#userId")
	public ResponseEntity<?> getAllBlackListCandidateByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateList = candidateService.getAllBlackListCandidateByUserId(userId);
			
			Collections.sort(candidateList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(candidateList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/candidate/verify/email/website")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> checkEmailIdThroughWebsite(@RequestBody WebSiteRecruitmentOpportunityMapper emailMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			if (!StringUtils.isEmpty(emailMapper.getEmailId())) {
				boolean c = candidateService.emailExistsByWebsite(emailMapper.getEmailId());
				if (c == true) {
					List<String> ids = new ArrayList<>();
					String candidateId = candidateService.getCandidateDetailsByEmailId(emailMapper.getEmailId());
					System.out.println("id%%%%%%%%%%%%%%%%%%%%" + candidateId);
					ids.add(candidateId);
					emailMapper.setCandidateIds(ids);
					emailMapper.setTagUserId(web.getUser_id());
					emailMapper.setOrgId(web.getOrgId());
					emailMapper.setIntrestInd(true);
					recruitmentService.linkCandidateToRecruitmentForWebsite(emailMapper);
					map.put("candidateInd", true);
					map.put("message", "Thank you for applying,you will here soon.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					map.put("candidateInd", false);
					map.put("email", emailMapper.getEmailId());
					map.put("OpportunityId", emailMapper.getOpportunityId());
					map.put("RecruitmentId", emailMapper.getRecruitmentId());
					map.put("RecruitmentProcessId", emailMapper.getRecruitmentProcessId());
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return null;

	}

	@GetMapping("/api/v1/candidate/candidate-document/{candidateId}")

	public ResponseEntity<?> getCandidateDocumentDetailsById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateDocumentMapper candidateDocumentMapper = candidateService
					.getCandidateDocumentDetailsById(candidateId);

			return new ResponseEntity<>(candidateDocumentMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/candidate/save-add/process/website")
	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveCandidateThroughWebsiteAndAddToProcess(
			@RequestBody CandidateWebsiteMapper candidateMapper,
			@RequestParam(value = "url", required = true) String url, HttpServletRequest request) {
		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			if (!StringUtils.isEmpty(candidateMapper.getEmailId())) {
				boolean c = candidateService.emailExistsByWebsite(candidateMapper.getEmailId());
				if (c == true) {
					map.put("candidateInd", true);
					map.put("message", "Candidate with same mail already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					List<String> ids = new ArrayList<>();
					candidateMapper.setUserId(web.getUser_id());
					candidateMapper.setChannel("Website");
					String id = candidateService.saveCandidateThroughWebsite(candidateMapper);
					ids.add(id);
					candidateMapper.setCandidateIds(ids);
					candidateMapper.setTagUserId(web.getUser_id());
					candidateMapper.setOrgId(web.getOrgId());
					candidateMapper.setIntrestInd(true);
					recruitmentService.linkCandidateToRecruitment(candidateMapper);
					map.put("ID", id);
					map.put("message", "Thank you for Registering . We will shortly reachout to you !!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/candidate/tree/{candidateId}")
	public ResponseEntity<?> getCandidateTreeByCandidateId(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			CandidateTreeMapper candidateTreeMapper = candidateService.getCandidateTreeByCandidateId(candidateId);

			return new ResponseEntity<CandidateTreeMapper>(candidateTreeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/filter/{userId}")
	@Cacheable(key = "#userId")
	public ResponseEntity<?> getAllFilterListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<FilterMapper> filterList = candidateService.getAllFilterListByUserId(userId);
			//Collections.sort(filterList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(filterList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/defination/search/{name}")
	public ResponseEntity<?> getDefinationDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DefinationMapper> definationMapper = candidateService.getDefinationDetailsByName(name);
			if (null != definationMapper && !definationMapper.isEmpty()) {
				return new ResponseEntity<>(definationMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/candidate/skill/role/{skillSetDetailsId}")
	public ResponseEntity<?> updateCandidateSkillRoleBySkillSetDetailsId(
			@PathVariable("skillSetDetailsId") String skillSetDetailsId, @RequestBody SkillSetMapper skillSetMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			SkillSetMapper skillSetMapperr = candidateService
					.updateCandidateSkillRoleBySkillSetDetailsId(skillSetDetailsId, skillSetMapper);

			return new ResponseEntity<SkillSetMapper>(skillSetMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/role/{orgId}")
	public ResponseEntity<?> getCandidatesRoleByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<CandidateRoleCountMapper> candidateRoleCountMapper = candidateService.getCandidaterRoleByOrgId(orgId);
			return new ResponseEntity<>(candidateRoleCountMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/roles/{userId}")
	public ResponseEntity<?> getCandidatesRolesByUserId(@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<CandidateRoleCountMapper> candidateRoleCountMapper = candidateService
					.getCandidaterRolesByUserId(userId);
			return new ResponseEntity<>(candidateRoleCountMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/vedio/{candidateId}")

	public ResponseEntity<?> getCandidateVedioListByCandidateId(@PathVariable("candidateId") String candidateId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<VideoClipsMapper> contactList = candidateService.getCandidateVedioListByCandidateId(candidateId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(contactList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/record/today/{userId}")

	public ResponseEntity<?> getRecordByToday(@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(candidateService.getRecordOfToday(userId));

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/sort/{userId}/{pageNo}")
	public ResponseEntity<?> getAllCandidateSortingByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,@PathVariable("pageNo") int pageNo,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	int pageSize = 20;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (type.equalsIgnoreCase("zToa")) {

				List<CandidateViewMapper> candidateList = candidateService.getCandidateListPageWiseByUserId(userId,pageNo, pageSize);
				Collections.sort(candidateList, (m1, m2) -> m2.getFullName().compareTo(m1.getFullName()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("aToz")) {

				List<CandidateViewMapper> candidateList = candidateService.getCandidateListPageWiseByUserId(userId,pageNo, pageSize);
				Collections.sort(candidateList, (m1, m2) -> m1.getFullName().compareTo(m2.getFullName()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/candidate/certification")
//	@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveCandidateCertification(
			@RequestBody CandidateCertificationLinkMapper candidateCertificationLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			candidateCertificationLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			candidateCertificationLinkMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = candidateService.saveCandidateCertification(candidateCertificationLinkMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/candidate/certification/{candidateId}")

	public ResponseEntity<?> getCandidateCertificationById(@PathVariable("candidateId") String candidateId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateCertificationLinkMapper> candidateCertificationLinkMapper = candidateService
					.getCandidateCertificationDetails(candidateId);
			Collections.sort(candidateCertificationLinkMapper,
					(m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateCertificationLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/candidate/certification/{candiCertiLinkId}")
	public ResponseEntity<?> deleteCandidateCertification(@PathVariable("candiCertiLinkId") String candiCertiLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(candidateService.deleteCandidateCertification(candiCertiLinkId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/candidate/onboarded/candidate/list/{userId}/{pageNo}")
	public ResponseEntity<?> getCandidateOnboardedListByRecruitmentId(@PathVariable("userId") String userId,
			@PathVariable("pageNo") int pageNo,@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
	int pageSize = 10;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CandidateViewMapper> candidateList = candidateService.getCandidateOnboardedListByRecruitmentId(userId,pageNo, pageSize);
			 Collections.sort(candidateList, (m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(candidateList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	


	@PostMapping("/api/v1/candidate/skillCandidateNo")
	//@CacheEvict(value = "candidate", allEntries = true)
	public ResponseEntity<?> saveSkillCandidateNo(@RequestBody SkillCandidateNoMapper skillCandidateNoMapper,
			@RequestHeader("Authorization") String authorization) {
		
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			skillCandidateNoMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			skillCandidateNoMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = candidateService.saveSkillCandidateNo(skillCandidateNoMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/candidate/skillCandidateNo/{orgId}")
	public ResponseEntity<?> getskillCandidateNo(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<SkillCandidateNoMapper> typeList = candidateService.getskillCandidateNo(orgId);
			
			return new ResponseEntity<>(typeList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	 @PutMapping("/api/v1/candidate/delete/{candidateId}")
		public ResponseEntity<?> deleteAndReinstateCandidateByCandidateId( @RequestBody CandidateMapper candidateMapper,
				                                       @PathVariable("candidateId") String candidateId ,
				                                       @RequestHeader("Authorization") String authorization,
				                                       HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");

			candidateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			candidateMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
				return ResponseEntity.ok(candidateService.deleteAndReinstateCandidateByCandidateId(candidateId,candidateMapper));
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    @GetMapping("/api/v1/candidate/deleted/candidate/list/{userId}/{pageNo}")

		public ResponseEntity<?> getDeletedCandidateDetailsByUserId(@PathVariable("userId") String userId,@PathVariable("pageNo") int pageNo,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			int pageSize = 20;
			
			//List<PartnerMapper> partnerMapperList = new ArrayList<>();

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//				if(userId.equalsIgnoreCase("All")) {
//					List<PartnerMapper> partnerMapperList  = partnerService.getAllPartnerList( pageNo, pageSize);
//					Collections.sort(partnerMapperList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//					return new ResponseEntity<>(partnerMapperList,HttpStatus.OK);
//				    //return ResponseEntity.ok(partnerList);
//				}else {
				
					List<CandidateViewMapper> candidateList = candidateService.getDeletedCandidateDetailsByUserId(userId, pageNo, pageSize);

				//if (null != partnerMapperList && !partnerMapperList.isEmpty()) {

					
					Collections.sort(candidateList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					  return new ResponseEntity<>(candidateList,HttpStatus.OK);
				//}
				//}
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
	    @GetMapping("api/v1/candidate/all-candidate/{category}/{pageNo}")
		public ResponseEntity<List<CandidateViewMapper>> getCandidateList(@RequestHeader("Authorization") String authorization, 
				@PathVariable("category") String category,@PathVariable("pageNo") int pageNo,HttpServletRequest request) {
		int pageSize = 20;
			List<CandidateViewMapper> candidateList  = candidateService.getAllCandidateListByCategory( pageNo, pageSize,category);
			Collections.sort(candidateList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			return new ResponseEntity<>(candidateList, HttpStatus.OK);
		}
	    
	    @GetMapping("/api/v1/candidate/search/{category}/{skill}")
		public ResponseEntity<?> getCandidateByNameAndSkillCategoryWise(@PathVariable("skill") String skill,
				@PathVariable("category") String category,   @RequestHeader("Authorization") String authorization) {

		  List<CandidateViewMapper> list  = new ArrayList<>();
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
					
				 list = candidateService.getCandidateDetailsBySkillAndCategory(skill,category);
				 
				if(null ==list || list.isEmpty()) {
					System.out.println("insert to the if");
					 list = candidateService.getCandidateDetailsByNameAndCategory(skill,category);
				}
				if(null ==list || list.isEmpty()) {
					 list = candidateService.getCandidateDetailsByIdNumberAndCategory(skill,category);
				}
				 return new ResponseEntity<>(list,HttpStatus.OK);


			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    @GetMapping("/api/v1/candidate/login/{candidateId}")
	    public ResponseEntity<?> getCandidateByIdForWebsite(@PathVariable("candidateId") String candidateId,
				 @RequestParam(value = "url", required = true) String url, HttpServletRequest request) {

	    	Map map = new HashMap();
			Website web = websiteRepository.getByUrl(url);
			if (null != web) {
				String orgId= web.getOrgId();
				CandidateViewMapper candidateMapper = candidateService.getCandidateDetailsById(candidateId);
				OrganizationDetails organizationDetails = organizationRepository
						.getOrganizationDetailsById(orgId);
				if(null!=organizationDetails) {
				candidateMapper.setOrgImgId(organizationDetails.getImage_id());
				}
				return new ResponseEntity<CandidateViewMapper>(candidateMapper, HttpStatus.OK);

			}else {
				map.put("website", true);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}

		}

	    @PutMapping("/api/v1/candidate/{candidateId}/website")
		@CacheEvict(value = "candidate", allEntries = true)
		public ResponseEntity<?> upateCandidateThroughWebsite(@PathVariable("candidateId") String candidateId,
				@RequestBody CandidateMapper candidateMapper, @RequestParam(value = "url", required = true) String url,
				HttpServletRequest request) {

			Map map = new HashMap();
			Website web = websiteRepository.getByUrl(url);
			if (null != web) {
				candidateMapper.setOrganizationId(web.getOrgId());
				candidateMapper.setUserId(web.getUser_id());
				candidateMapper.setChannel("website");
				

				if (!StringUtils.isEmpty(candidateMapper.getEmailId())) {
					boolean c = candidateService.emailExistsByWebsite(candidateMapper.getEmailId());
					if (c == true) {
						map.put("candidateInd", true);
						map.put("message", "Candidate with same mail id already exists...!!!! ");
						return new ResponseEntity<>(map, HttpStatus.OK);
					} else {
						CandidateViewMapper mapper = candidateService.upateCandidateDetailsById(candidateId,
								candidateMapper);

						return new ResponseEntity<CandidateViewMapper>(mapper, HttpStatus.OK);

					}
				} else {
					CandidateViewMapper mapper = candidateService.upateCandidateDetailsById(candidateId, candidateMapper);

					return new ResponseEntity<CandidateViewMapper>(mapper, HttpStatus.OK);
				}
			}else {
				map.put("website", true);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
	    
	    @PutMapping("/api/v1/candidate/pause/skill/experience/{skillId}")

		public ResponseEntity<?> pauseAndUnpauseCandidateSkillExperience(@PathVariable("skillId") String skillId,
				@RequestBody SkillSetMapper skillSetMapper, @RequestHeader("Authorization") String authorization,
				HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				SkillSetMapper skillSetMapperNew = candidateService.pauseAndUnpauseCandidateSkillExperience(skillId, skillSetMapper);

				return new ResponseEntity<SkillSetMapper>(skillSetMapperNew, HttpStatus.OK);

			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    
	    @GetMapping("/api/v1/candidate/billable/{userId}/{pageNo}/{month}/{year}")
		public ResponseEntity<?> getBillabeCandidateListByUserId(@PathVariable("userId") String userId,
				@PathVariable("pageNo") int pageNo, @PathVariable("month") String month,
				@PathVariable("year") String year, @RequestHeader("Authorization") String authorization,
				HttpServletRequest request) {
			int pageSize = 10;
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateViewMapper> candidateList = candidateService.getBillabeCandidateListByUserId(userId,
						pageNo, pageSize, month, year);
				Collections.sort(candidateList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(candidateList, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	    
	    @PutMapping("/api/v1/candidate/active/inActive/{candidateId}")
		public ResponseEntity<?> activeAndInActiveCandidateByCandidateId( @RequestBody CandidateMapper candidateMapper,
				                                       @PathVariable("candidateId") String candidateId ,
				                                       @RequestHeader("Authorization") String authorization,
				                                       HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				
				return ResponseEntity.ok(candidateService.activeAndInActiveCandidateByCandidateId(candidateId,candidateMapper));
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    @DeleteMapping("/api/v1/candidate/defination/{definationId}")
		public ResponseEntity<?> deleteCandidateDefination(@PathVariable("definationId") String definationId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				candidateService.deleteDefination(definationId);
				return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
//	    @PutMapping("/api/v1/candidate/note/update")
//		public ResponseEntity<?> updateNoteDetails(@RequestBody NotesMapper notesMapper,@RequestHeader("Authorization") String authorization,
//				HttpServletRequest request) {
//			NotesMapper notesMapperr = null;
//			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//				String authToken = authorization.replace(TOKEN_PREFIX, "");
//				notesMapperr =candidateService.updateNoteDetails(notesMapper);
//				return new ResponseEntity<>(notesMapperr, HttpStatus.OK);
//			}
//
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//		}
	    
	    @DeleteMapping("/api/v1/candidate/note/{notesId}")
		public ResponseEntity<?> deleteCandidateNote(@PathVariable("notesId") String notesId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				candidateService.deleteCandidateNotesById(notesId);

				return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}

		@PutMapping("/api/v1/candidate/hard-delete/{candidateId}")
	public ResponseEntity<?> hardDeleteCandidate(@PathVariable("candidateId") String candidateId,
													 @RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String msg = candidateService.hardDeleteCandidate(candidateId);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
}
