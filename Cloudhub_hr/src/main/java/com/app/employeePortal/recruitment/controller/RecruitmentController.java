package com.app.employeePortal.recruitment.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.SkillSetMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.customer.mapper.CustomerRecruitmentMapper;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.mapper.CandidateProjectMapper;
import com.app.employeePortal.recruitment.mapper.CommissionMapper;
import com.app.employeePortal.recruitment.mapper.FunnelMapper;
import com.app.employeePortal.recruitment.mapper.JobVacancyMapper;
import com.app.employeePortal.recruitment.mapper.PingMapper;
import com.app.employeePortal.recruitment.mapper.ProcessDocumentLinkMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentActionMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentCloseRuleMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentProcessMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentRecruitOwnerMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageApproveMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageMapper;
import com.app.employeePortal.recruitment.mapper.UpworkMapper;
import com.app.employeePortal.recruitment.mapper.WebsiteMapper;
import com.app.employeePortal.recruitment.mapper.WebsitePartnerLinkMapper;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.ruleEngine.service.RuleEngineService;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)
public class RecruitmentController {

	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	RuleEngineService ruleEngineService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	DocumentService documentService;
	@Autowired
	RecruitmentProfileDetailsRepository recruitProfileDetailsRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;

	@PostMapping("/api/v1/link/recruitment/opportunity")
	public ResponseEntity<?> requirementOpportunity(
			 @RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			recruitmentOpportunityMapper.setTagUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			recruitmentOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			if (!StringUtils.isEmpty(recruitmentOpportunityMapper.getJobOrder())) {
				boolean b = recruitmentService.requirementExistsByJobOrder(recruitmentOpportunityMapper.getJobOrder());
				if (b == true) {
					map.put("jobOrderInd", b);
					map.put("message", "Requirement can not be created as same job Order already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					RecruitmentOpportunityMapper recruitmentId = recruitmentService
							.linkRecruitmentToOpportunity(recruitmentOpportunityMapper);
					return new ResponseEntity<RecruitmentOpportunityMapper>(recruitmentId, HttpStatus.OK);
				}
			} else {
				RecruitmentOpportunityMapper recruitmentId = recruitmentService.linkRecruitmentToOpportunity(recruitmentOpportunityMapper);
				return new ResponseEntity<RecruitmentOpportunityMapper>(recruitmentId, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/link/recruitment/opportunity/{opportunityId}")
	public List<RecruitmentOpportunityMapper> getRecruitmentOfOpportunity(
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentOpportunityMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			list = recruitmentService.getRecriutmentListByOppId(opportunityId, orgId, userId);
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		}

		return list;

	}

	@PostMapping("/api/v1/recruitment/process")
	public String createProcessOfAdmin(@RequestBody RecruitmentProcessMapper recruitmentProcessMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		String id = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			/// String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			recruitmentProcessMapper.setOrganizationId(orgId);
			id = recruitmentService.saveRecruitmentProcess(recruitmentProcessMapper);

		}

		return id;

	}

	@PutMapping("/api/v1/employee/recruitment-details")
	public RecruitmentProcessMapper updateProcess(@RequestBody RecruitmentProcessMapper recruitmentProcessMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		RecruitmentProcessMapper recruitmentProcessMapperNew = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			recruitmentProcessMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			recruitmentProcessMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String id = recruitmentService.updateProcessOfRecruiter(recruitmentProcessMapper);
			recruitmentProcessMapperNew = recruitmentService.getProcessMapperByProcessId(id);

		}

		return recruitmentProcessMapperNew;

	}

	@PostMapping("/api/v1/recruitment/process/stage")
	public ResponseEntity<?> createStagesOfProcess(@RequestBody RecruitmentStageMapper recruitmentStageMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {
		Map map = new HashMap();
		String id = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			// String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			recruitmentStageMapper.setOrgId(orgId);

			if (0 <= (recruitmentStageMapper.getProbability())) {
				boolean b = recruitmentService.stageExistsByWeightage(recruitmentStageMapper.getProbability(),
						recruitmentStageMapper.getRecruitmentProcessId());
				if (b == true) {
					map.put("probabilityInd", b);
					map.put("message", "Stage can not be created as same weightage already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			id = recruitmentService.createStagesForProcess(recruitmentStageMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruitment/process/{processId}")
	public List<RecruitmentStageMapper> createStagesOfProcess(@PathVariable("processId") String processId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentStageMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			mapperList = recruitmentService.getStagesOfProcess(processId);

		}

		return mapperList;

	}

	@PutMapping("/api/v1/employee/recriutment-stage")
	public RecruitmentStageMapper updateStage(@RequestBody RecruitmentStageMapper recruitmentStageMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		RecruitmentStageMapper recruitmentStageMapperNew = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			recruitmentStageMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = recruitmentService.updateStageOfProcess(recruitmentStageMapper);
			recruitmentStageMapperNew = recruitmentService.getStageDetailsByStageId(id);

		}

		return recruitmentStageMapperNew;

	}

	@GetMapping("/api/v1/all/process")
	public List<RecruitmentStageMapper> getProcessWithStages(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentStageMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			mapperList = recruitmentService.getAllProcessStagesOfAdmin(orgId);

		}

		return mapperList;

	}

	@GetMapping("/api/v1/admin/process/{orgId}")
	public List<RecruitmentProcessMapper> getProcessOfAdmin(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentProcessMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = recruitmentService.getProcessesOfAdmin(orgId);

		}

		return mapperList;

	}

	@GetMapping("/api/v1/admin/setting/process/{orgId}")
	public List<RecruitmentProcessMapper> getProcessOfAdminInSetting(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentProcessMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = recruitmentService.getProcessOfAdminInSetting(orgId);

		}

		return mapperList;

	}

	@PostMapping("/api/v1/link/profile/recruit")
	public String getProcessOfAdmin(@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		String id = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			id = recruitmentService.linkProfileToRecruitment(recruitmentOpportunityMapper);

		}

		return id;

	}

	@PutMapping("/api/v1/link/recriutment/skill")
	public RecruitmentOpportunityMapper linkCategoryToRecrutment(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			String stageId = recruitmentService.linkSkillToRecruitment(recruitmentOpportunityMapper);

			/*
			 * recruitmentOpportunityMappernew = recruitmentService.getRecuitmentByProfile(
			 * recruitmentOpportunityMapper.getOpportunityId(),
			 * recruitmentOpportunityMapper.getProfileId(), orgId, userId);
			 */
			recruitmentOpportunityMappernew = recruitmentService.getRecriutmentByOppIdAndRecruitId(
					recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getRecruitmentId(),
					orgId);
		}
		return recruitmentOpportunityMappernew;

	}

	@GetMapping("/api/v1/recriutment/{opportunityId}")
	public List<RecruitmentOpportunityMapper> getListOfRequirement(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		List<RecruitmentOpportunityMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
//			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			mapperList = recruitmentService.getRecriutmentListByOppId(opportunityId);

			Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return mapperList;

	}

	@GetMapping("/api/v1/candidate/{skill}/{recriutmentId}/{oppId}")
	public List<CandidateViewMapper> getCandidateList(@PathVariable("skill") String skill,
			@PathVariable("recriutmentId") String recriutmentId, @PathVariable("oppId") String oppId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		List<CandidateViewMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			Permission permission = permissionRepository.findByOrgId(orgId);
			System.out.println("ind............." + permission.isCandiContSrchInd());
			if (skill.equalsIgnoreCase("all")) {
				// mapperList =candidateService.getCandidatesBasedOnCategory(recriutmentId,
				// orgId);
				mapperList = candidateService.getCandidatesBasedOnOrgLevelAndUserLevel(recriutmentId, orgId, userId);

			} else {
				if (permission.isCandiContSrchInd() == true) {
					System.out.println("Now we are searching glogally...............");
					mapperList = recruitmentService.getCandidatesBasedOnSkill(skill, recriutmentId, oppId, orgId);
				} else {
					mapperList = recruitmentService.getCandidatesBasedOnSkillByUserId(skill, recriutmentId, oppId,
							userId);
					System.out.println("Now we are searching user base...............");
				}
			}
		}
		return mapperList;
	}

	@PutMapping("/api/v1/link/recriutment/contcat")
	public RecruitmentOpportunityMapper linkCandidateToRecruitment(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMapperNew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			// String userId = jwtTokenUtil.getUserIdFromToken(authToken);

			recruitmentOpportunityMapper.setTagUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			recruitmentOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			recruitmentService.linkCandidateToRecruitment(recruitmentOpportunityMapper);

			/*
			 * recruitmentOpportunityMapperNew =
			 * recruitmentService.getRecriutmentListByOppId(recruitmentOpportunityMapper.
			 * getOpportunityId(), jwtTokenUtil.getUserIdFromToken(authToken),
			 * jwtTokenUtil.getOrgIdFromToken(authToken));
			 */
			recruitmentOpportunityMapperNew = recruitmentService.getRecriutmentUpdateResponse(
					recruitmentOpportunityMapper.getRecruitmentId(), recruitmentOpportunityMapper.getOrgId());
		}
		return recruitmentOpportunityMapperNew;

	}

	@GetMapping("/api/v1/skill/word/cloud")
	public List<SkillSetMapper> SkillWordCloud(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {
		List<SkillSetMapper> skills = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			skills = candidateService.getWordCloudHistory(orgId);

		}
		return skills;
	}

	@PostMapping("/api/v1/recruit/stage/approve")
	public ResponseEntity<?> createRecruitmentStageApprove(
			 @RequestBody RecruitmentStageApproveMapper recruitmentStageApproveMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			recruitmentStageApproveMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			recruitmentStageApproveMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			// recruitmentStageMapper.setOrgId(orgId);

			String recruitmentStageId = recruitmentService.createRecruitmentStageApprove(recruitmentStageApproveMapper);

			return new ResponseEntity<String>(recruitmentStageId, HttpStatus.OK);

		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitmentStageApprove/{stageId}")

	public ResponseEntity<?> getRecruitmentStageApprove(

			@PathVariable("stageId") String stageId,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			RecruitmentStageApproveMapper recruitmentStageApproveMapper = recruitmentService
					.getRecruitmentStageApprove(stageId);

			return new ResponseEntity<>(recruitmentStageApproveMapper, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/api/v1/note/stage")
	public String linkNoteToStage(@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		String stageId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			recruitmentOpportunityMapper
					.setTagUserId(jwtTokenUtil.getUserIdFromToken(authorization.replace(TOKEN_PREFIX, "")));
			recruitmentOpportunityMapper
					.setOrgId(jwtTokenUtil.getOrgIdFromToken(authorization.replace(TOKEN_PREFIX, "")));

			stageId = recruitmentService.createNoteForRecrutmentStage(recruitmentOpportunityMapper);

		}
		return stageId;

	}

	@GetMapping("/api/v1/note/{profileId}")
	public List<RecruitmentOpportunityMapper> getStageNoteData(@PathVariable("profileId") String profileId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<RecruitmentOpportunityMapper> list = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = recruitmentService.getRecruitStageNote(profileId);
			if(null!=list && !list.isEmpty()){
			Collections.sort(list, (RecruitmentOpportunityMapper m1, RecruitmentOpportunityMapper m2) -> m2
					.getCreationDate().compareTo(m1.getCreationDate()));
			}
		}
		return list;

	}

	@PutMapping("/api/v1/recriutment/update/stage")
	public RecruitmentOpportunityMapper updateStageOfAProfile(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			// String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			// String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			recruitmentOpportunityMappernew = recruitmentService.updateStageOfAProfile(recruitmentOpportunityMapper);

			/*
			 * (recruitmentOpportunityMappernew = recruitmentService.getRecuitmentByProfile(
			 * recruitmentOpportunityMapper.getOpportunityId(),
			 * recruitmentOpportunityMapper.getProfileId(), orgId, userId);
			 */
			// recruitmentOpportunityMappernew =
			// recruitmentService.getTagCandidateListByRecriutmentId(recruitmentOpportunityMapper.getRecruitmentId());

//			String fromEmail = "support@innoverenit.com";
//			String to = recruitmentOpportunityMappernew.getEmailId();
//			String subject = "Hiring update";
//			String message = "";
//
//			message = recruitmentService.getUpdateStageCandidateEmailContent(recruitmentOpportunityMappernew);
//			System.out.println("candidateName:::::::::::::::::::::::::::::::::::::::::::::::"
//					+ recruitmentOpportunityMappernew.getCandidateName());
//
//			System.out.println("MSG>>" + message);
//			String serverUrl = "https://develop.tekorero.com/kite/email/send";
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//			body.add("fromEmail", fromEmail);
//			body.add("message", message);
//			body.add("subject", subject);
//			body.add("toEmail", to);
//			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//			RestTemplate restTemplate = new RestTemplate();
//			ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		}
		return recruitmentOpportunityMappernew;

	}

	@GetMapping("/api/v1/candidate/recruitment/{candidateId}")
	public List<RecruitmentOpportunityMapper> getRecruitmentListOfCandidate(
			@PathVariable("candidateId") String candidateId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		List<RecruitmentOpportunityMapper> list = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = recruitmentService.getrecruitmentsOfACandidate(candidateId);

		}
		return list;

	}

	/* recruitment dashboard */
	@GetMapping("/api/v1/recruitments/{opportunityId}")
	public int getNoOfRecruitmentOfOpportunity(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			count = recruitmentService.getNoOfRecruitmentOfOpportunity(opportunityId);

		}

		return count;

	}

	@GetMapping("/api/v1/recruitment/position/{opportunityId}")
	public int getNoOfPositionsOfRecruitmentInOpportunity(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			count = recruitmentService.getTotalNoOfPositionOfRecruitments(opportunityId);

		}

		return count;

	}

	@GetMapping("/api/v1/filled/position/{opportunityId}")
	public int getTotalFilledPositions(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			count = recruitmentService.getTotalFilledPosition(opportunityId);

		}

		return count;

	}

	@GetMapping("/api/v1/count/defination/{recruitmentId}/{orgId}")
	public Map getNoOfSkillsCountInRecruitment(@PathVariable("recruitmentId") String recruitmentId,
			@PathVariable("orgId") String orgId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			map = recruitmentService.getSkillsCountInDescription(recruitmentId, orgId);

		}

		return map;

	}

	@PutMapping("/api/v1/recruitment/process/unpublish")
	public ResponseEntity<?> updateProcessPublish(@RequestBody RecruitmentProcessMapper recruitmentProcessMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		RecruitmentProcessMapper recruitmentProcessMapperNew = new RecruitmentProcessMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = recruitmentService.updateProcessPublish(recruitmentProcessMapper);
			if(b==true) {
			recruitmentProcessMapperNew = recruitmentService
					.getProcessMapperByProcessId(recruitmentProcessMapper.getRecruitmentProcessId());

			return new ResponseEntity<>(recruitmentProcessMapperNew, HttpStatus.OK);
		}else {
			
			map.put("message", "This Workflow have no stages except Drop and select.!!! so the workflow can not be published ...!! please ADD stage..!!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/recruitment/stages/unpublish")
	public ResponseEntity<?> unpublishTheStages(@RequestBody RecruitmentProcessMapper recruitmentProcessMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		RecruitmentStageMapper recruitmentProcessMapperNew = new RecruitmentStageMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			recruitmentService.unpublishTheStages(recruitmentProcessMapper);
			recruitmentProcessMapperNew = recruitmentService
					.getStageDetailsByStageId(recruitmentProcessMapper.getStageId());

			return new ResponseEntity<>(recruitmentProcessMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/recruitment/publish")
	public ResponseEntity<?> publishTheRecruitment(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		// RecruitmentOpportunityMapper recruitmentOpportunityMappernew = new
		// RecruitmentOpportunityMapper();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = recruitmentService.publishTheRecruitment(recruitmentOpportunityMapper);

			return new ResponseEntity<>(b, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/recruitment/ubpublish")
	public ResponseEntity<?> unpublishTheRecruitment(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = new RecruitmentOpportunityMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = recruitmentService.UnpublishTheRecruitment(recruitmentOpportunityMapper);
			recruitmentOpportunityMappernew = recruitmentService.getRecuitmentByProfile(
					recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getProfileId(),
					recruitmentOpportunityMapper.getOrgId(), recruitmentOpportunityMapper.getTagUserId());

			return new ResponseEntity<>(recruitmentOpportunityMappernew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruitment/website/publish/{website}")
	public ResponseEntity<?> publishTheRecruitmentsToWebsite(@PathVariable("website") String website,
			HttpServletRequest request) throws Exception {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		mapperList = recruitmentService.getPublishedRecruitemntsToWebsite(website);

		return new ResponseEntity<>(mapperList, HttpStatus.OK);

	}

	@GetMapping("/api/v1/recruitment/summary/{oppId}")
	public ResponseEntity<?> publishTheRecruitmentsToWebsite(@PathVariable("oppId") String oppId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		mapperList = recruitmentService.getRecruitmentSummary(oppId);
		Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		return new ResponseEntity<>(mapperList, HttpStatus.OK);

	}

	@PostMapping("/api/v1/process/stage/task/")
	public String createTaskForProfile(@RequestBody RecruitmentStageApproveMapper recruitmentStageApproveMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		String id = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
//			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			id = recruitmentService.createTaskForProfile(recruitmentStageApproveMapper);

		}

		return id;

	}

	@GetMapping("/api/v1/recruitment/stage/task/{stageId}")
	public List<RecruitmentStageApproveMapper> getProcessStageTaskLinkByStageId(@PathVariable("stageId") String stageId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<RecruitmentStageApproveMapper> list = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = recruitmentService.getTasksOfStages(stageId);

		}
		return list;

	}

	@GetMapping("/api/v1/recruitment/stage/profile/{profileId}")
	public List<RecruitmentStageApproveMapper> getProfileStageTaskLinkByProfileId(
			@PathVariable("profileId") String profileId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		List<RecruitmentStageApproveMapper> list = new ArrayList<>();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			list = recruitmentService.getOfProfoileDetails(profileId);

		}
		return list;

	}

	@DeleteMapping("api/v1/delete/profile/{recruitId}")

	public boolean deleteProfileOfArecruitment(@PathVariable("recruitId") String recruitId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		boolean b = false;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			b = recruitmentService.deleteProfilesOfARecruitment(recruitId);

		}
		return b;
	}

	@PutMapping("/api/v1/recriutment/update/recruitment")
	public RecruitmentOpportunityMapper updateRecriutment(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			String recruitId = recruitmentService.updateRecriutment(recruitmentOpportunityMapper);

			recruitmentOpportunityMappernew = recruitmentService.getRecriutmentListByOppIdandRecruitId(
					recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getRecruitmentId(),
					orgId);
		}
		return recruitmentOpportunityMappernew;

	}

	@PutMapping("/api/v1/update/recriutment/status")
	public RecruitmentOpportunityMapper updateRecruitmentStatus(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String authUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			// recruitmentOpportunityMapper.setUserId(authUserId);
			recruitmentOpportunityMapper.setOrgId(orgId);

			recruitmentService.updateStatusOfRecrutment(recruitmentOpportunityMapper);

			recruitmentOpportunityMappernew = recruitmentService.getRecuitmentByProfile(
					recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getProfileId(), orgId,
					authUserId);

			String fromEmail = "support@innoverenit.com";
			String to = recruitmentOpportunityMappernew.getEmailId();
			String subject = "";
			String message = "";
			if (recruitmentOpportunityMapper.isApproveInd() == true) {
				subject = "Congratulations!!";
				message = recruitmentService.getSelectedCandidateEmailContent(recruitmentOpportunityMappernew);
				System.out.println("candidateName:::::::::::::::::::::::::::::::::::::::::::::::"
						+ recruitmentOpportunityMappernew.getCandidateName());
				/*
				 * message = "<div><p>Dear" +
				 * "</p></div>  <br> <div>    <p>Congratulations!!</p>  </div>" + "<div> " +
				 * "<p> We are happy to inform you that you have been selected by " + " as a " +
				 * " More details about the offer will be shared with you soon.</p></div>" +
				 * " <div><p> Warm Regards " + "<br>" + "</p>  </div>";
				 */
			}
			if (recruitmentOpportunityMapper.isRejectInd() == true) {
				subject = "Sorry";
				message = recruitmentService.getDropCandidateEmailContent(recruitmentOpportunityMappernew);
				/*
				 * message = "<div><p>Dear" + candidateDetails.getFullName()
				 * +"</p></div>  <br>  <div> " +
				 * "<p> We regret to inform you that you have not been selected by " +
				 * customerName + " as a " +role + " For more details contact " + customerName
				 * +"</p></div>" + " <div><p> Warm Regards " +recruiterOwner+ "<br>" +OrgName+
				 * "</p>  </div>";
				 */
			}
//			System.out.println("MSG>>" + message);
//			String serverUrl = "https://develop.tekorero.com/kite/email/send";
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//			body.add("fromEmail", fromEmail);
//			body.add("message", message);
//			body.add("subject", subject);
//			body.add("toEmail", to);
//			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//			RestTemplate restTemplate = new RestTemplate();
//			ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		}
		return recruitmentOpportunityMappernew;

	}

	@PostMapping("/api/v1/sendMail/stage")
	public void sendEmailInEveryStageUpdate(
			 @RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String orgId = jwtTokenUtil.getOrgIdFromToken(authorization.replace(TOKEN_PREFIX, ""));

			// String orgId, String oppId, String recruitId, String loginUser,String
			// profileId

			ruleEngineService.sendEmailAtEachStageUpdateForCandidate(orgId,
					recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getContactId(),
					recruitmentOpportunityMapper.getStageId(), recruitmentOpportunityMapper.getTagUserId());

		}

	}

	@GetMapping("/api/v1/recruit/dashbord/record/{userId}")
	public ResponseEntity<?> getRecruitDashBoardRecordByuserIdAndDateRange(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			if(type.equalsIgnoreCase("recruiter")) {
			return ResponseEntity.ok(
					recruitmentService.getRecruitDashBoardRecordByuserIdAndDateRange(userId, startDate, endDate));
			}else {
				return ResponseEntity
						.ok(recruitmentService.getAllUserRecordByuserIdAndDateRange(userId, startDate, endDate));
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	
	@GetMapping("/api/v1/recruit/open/report/{recruiterId}/{startDate}/{endDate}")
	public ResponseEntity<?> getOpenRecruitmentByuserIdAndDateRange(
			@RequestHeader("Authorization") String authorization, @PathVariable("recruiterId") String recruiterId,
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(recruitmentService.getOpenRecruitmentByuserIdAndDateRange(recruiterId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruiter/recruitment/{recruiterId}")
	public List<RecruitmentOpportunityMapper> getRecruitmentOfRecruiterId(
			@PathVariable("recruiterId") String recruiterId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentOpportunityMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			list = recruitmentService.getRecruitmentOfRecruiterId(recruiterId,
					jwtTokenUtil.getOrgIdFromToken(authToken));
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return list;
	}

	@GetMapping("/api/v1/allRecruiters/{departmentId}")
	public ResponseEntity<?> getAllRecruiterListByDepartmentName(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeViewMapper> department = recruitmentService.getAllRecruiterByDepartmentId();

			// Collections.sort(candidateMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(department, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruit/selected/report/{recruiterId}/{startDate}/{endDate}")
	public ResponseEntity<?> getSelectedRecruitmentByuserIdAndDateRange(
			@RequestHeader("Authorization") String authorization, @PathVariable("recruiterId") String recruiterId,
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(recruitmentService.getSelectedRecruitmentByuserIdAndDateRange(recruiterId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruitment/dashboard/{category}")
	public int getNoOfRecruitmentByJobOrderAndCategory(@PathVariable("category") String category,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		int count = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			count = recruitmentService.getNoOfRecruitmentByJobOrderAndCategory(category);

		}

		return count;

	}

	@GetMapping("/api/v1/recruit/open/report/category/{category}")
	public ResponseEntity<?> getOpenRecruitmentByJobOrderAndCategory(
			@RequestHeader("Authorization") String authorization, @PathVariable("category") String category,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(recruitmentService.getOpenRecruitmentByJobOrderAndCategory(category));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recriutment/jobOrder/{opportunityId}")
	public List<RecruitmentOpportunityMapper> getListOfJobOrder(@PathVariable("opportunityId") String opportunityId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		List<RecruitmentOpportunityMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

//				String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//				String userId = jwtTokenUtil.getUserIdFromToken(authToken);
//				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			mapperList = recruitmentService.getJobOrderListByOppId(opportunityId);

			Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return mapperList;

	}

	@GetMapping("/api/v1/recruitment/publish")
	public List<RecruitmentOpportunityMapper> getPublishRequirement(
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentOpportunityMapper> list = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			list = recruitmentService.getPublishRequirement();
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return list;

	}

	/*@GetMapping("/api/v1/recruitment/count")
	public ResponseEntity<?> getNoOfRecruitmentByCreationDate(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			return ResponseEntity.ok(recruitmentService.getNoOfRecruitmentByCreationDate());

		}

		return null;

	}
	*/

	@PostMapping("/api/v1/commission")
	public ResponseEntity<?> createCommission(@RequestBody CommissionMapper commisionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			commisionMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String commissionId = recruitmentService.saveCommission(commisionMapper);

			return new ResponseEntity<>(commissionId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/commission/{orgId}/{type}")
	public List<CommissionMapper> getCommissionByOrgId(@PathVariable("orgId") String orgId,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<CommissionMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = recruitmentService.getCommissionByOrgId(orgId, type);

		}

		return mapperList;

	}

	@PutMapping("/api/v1/commission")
	public ResponseEntity<?> updateCommission(@RequestBody CommissionMapper commissionMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		CommissionMapper commissionMapperNew = new CommissionMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			commissionMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			recruitmentService.updateCommission(commissionMapper);
			commissionMapperNew = recruitmentService.getCommissionMapperByUserId(commissionMapper.getUserId());

			return new ResponseEntity<>(commissionMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/recruitment/candidate/onboarding")
	public ResponseEntity<?> updateCandidateOnboarding(
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws Exception {
		HashMap map = new HashMap();
		
		RecruitmentOpportunityMapper recruitmentOpportunityMapperNew = new RecruitmentOpportunityMapper();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			recruitmentOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			recruitmentOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			
			OpportunityRecruitDetails recruitmentNo = recruitmentOpportunityDetailsRepository.getRecruitmentDetailsByRecruitId(recruitmentOpportunityMapper.getRecruitmentId());
			if(null!=recruitmentNo) {
				if(null!=recruitmentNo.getWorkType()) {
			if(recruitmentNo.getWorkType().equalsIgnoreCase("fullTime")) {
			
			List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
					.getProfileDetailsByCandidateId(recruitmentOpportunityMapper.getCandidateId());
			if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
				for(RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
					
					Date onboardDate = Utility.removeTime(Utility.getDateFromISOString(recruitmentOpportunityMapper.getOnboardDate()));
					
				if(recruitProfileLinkDetails.isOnboard_ind()==true && onboardDate.getTime()< Utility.removeTime(recruitProfileLinkDetails.getActualEndDate()).getTime()) {
					map.put("message","Candidate already onboarded to a project,Do you want to onboard another project....!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}else {
			recruitmentOpportunityMapperNew = recruitmentService.updateCandidateOnboarding(recruitmentOpportunityMapper);
			if(null!=recruitmentOpportunityMapperNew){

			return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("All Positions in This Recruitment has been Filled up !!!", HttpStatus.OK);
			}
		}
			}
		}
			
			}else {
				recruitmentOpportunityMapperNew = recruitmentService.updateCandidateOnboarding(recruitmentOpportunityMapper);
				if(null!=recruitmentOpportunityMapperNew){

				return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("All Positions in This Recruitment has been Filled up !!!", HttpStatus.OK);
				}
			}
			}else {
				recruitmentOpportunityMapperNew = recruitmentService.updateCandidateOnboarding(recruitmentOpportunityMapper);
				if(null!=recruitmentOpportunityMapperNew){

				return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("All Positions in This Recruitment has been Filled up !!!", HttpStatus.OK);
				}
			}
		}else {
			map.put("message","Requirment Not Found....!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruit/dashboard/open/{userId}")
	public ResponseEntity<?> getDashBoardOpenRecruitmentByRecruiterId(
			@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type) {

		List<RecruitmentOpportunityMapper> requirmentList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("recruiter")) {

				requirmentList = recruitmentService.getDashBoardOpenRecruitmentByRecruiterId(userId);
				Collections.sort(requirmentList, (RecruitmentOpportunityMapper m1,
						RecruitmentOpportunityMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(requirmentList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("sales")) {

				requirmentList = recruitmentService.getDashBoardOpenRecruitmentByEmployeeId(userId);
				Collections.sort(requirmentList, (RecruitmentOpportunityMapper m1,
						RecruitmentOpportunityMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(requirmentList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/recruitment/website")
	public ResponseEntity<?> createWebsite(@RequestBody WebsiteMapper websiteMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			websiteMapper.setUserId(loggedInUserId);
			websiteMapper.setOrgId(loggedInOrgId);

			String websiteId = recruitmentService.saveWebsite(websiteMapper);

			return new ResponseEntity<String>(websiteId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/website/{orgId}")
	public List<WebsiteMapper> getWebsiteListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<WebsiteMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = recruitmentService.getWebsiteListByOrgId(orgId);

		}

		return mapperList;

	}

	@GetMapping("/api/v1/recriutment/candidate/{recriutmentId}")
	public List<RecruitmentOpportunityMapper> getTagCandidateListByRecriutmentId(
			@PathVariable("recriutmentId") String recriutmentId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {
		List<RecruitmentOpportunityMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapperList = recruitmentService.getTagCandidateListByRecriutmentId(recriutmentId);

			// Collections.sort(mapperList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return mapperList;

	}

	@GetMapping("/api/v1/recruit/dashbord/{recruitmentId}/{startMonth}/{endMonth}")
	public ResponseEntity<?> getRecruitDashBoardRecordByuserIdAndDateRange1(
			@RequestHeader("Authorization") String authorization, @PathVariable("recruitmentId") String recruitmentId,
			@PathVariable("startMonth") String startMonth, @PathVariable("endMonth") String endMonth) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(recruitmentService.getRecruitDashBoardRecordByuserIdAndDateRange1(recruitmentId,
					startMonth, endMonth));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruiter/recruitment/candidate/{recruitmentId}")
	public List<CandidateViewMapper> getCandidateListByCategory(@PathVariable("recruitmentId") String recruitmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
		List<CandidateViewMapper> mapperList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			mapperList = candidateService.getCandidatesBasedOnCategory(recruitmentId, orgId);
		}
		return mapperList;
	}

	@PutMapping("/api/v1/note/feedback/{recruitmentStageNoteId}")
	public ResponseEntity<?> updatefeedback(@PathVariable("recruitmentStageNoteId") String recruitmentStageNoteId,
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			recruitmentService.updatefeedback(recruitmentOpportunityMapper, recruitmentStageNoteId);
			recruitmentOpportunityMappernew = recruitmentService
					.getFeedbackByRecruitmentStageNoteId(recruitmentStageNoteId);
			return new ResponseEntity<>(recruitmentOpportunityMappernew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/recruitmentCandidate/{profileId}")
	public ResponseEntity<?> updateRecruitmentCandidate(@PathVariable("profileId") String profileId,
			@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		RecruitmentOpportunityMapper recruitmentOpportunityMappernew = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			recruitmentService.updateRecruitmentCandidate(recruitmentOpportunityMapper, profileId);
			recruitmentOpportunityMappernew = recruitmentService.getRecruitmentCandidateProfileId(profileId);
			return new ResponseEntity<>(recruitmentOpportunityMappernew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruitment/publish/website")
	public ResponseEntity<?> publishRequirementToWebsite(@RequestParam(value = "url", required = true) String url,
			HttpServletRequest request) {
		// recruitmentOpportunityMappernew = null;
		Map map = new HashMap();
		boolean b = recruitmentService.ipAddressExists(url);
		if (b == true) {
			List<JobVacancyMapper> jobVacancyMapper = recruitmentService
					.getJobVacancyListForWebsite();
			return new ResponseEntity<>(jobVacancyMapper, HttpStatus.OK);

		} else {
			map.put("website", b);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}

	@GetMapping("/api/v1/recruitment/progress/details/{userId}")
	public ResponseEntity<List<?>> getRecruitmentProgressDetailsListsByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<RecruitmentOpportunityMapper> requirmentList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("recruiter")) {

				requirmentList = recruitmentService.getRecruitmentProgressDetailsListsByRecruiterId(userId);
				Collections.sort(requirmentList, (RecruitmentOpportunityMapper m1,
						RecruitmentOpportunityMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(requirmentList, HttpStatus.OK);

			} else if (type.equalsIgnoreCase("sales")) {

				requirmentList = recruitmentService.getRecruitmentProgressDetailsListsByUserId(userId);
				Collections.sort(requirmentList, (RecruitmentOpportunityMapper m1,
						RecruitmentOpportunityMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(requirmentList, HttpStatus.OK);

			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/onboarding/count/{oppId}")
	public ResponseEntity<?> getCountListByOppId(@RequestHeader("Authorization") String authorization,
			@PathVariable("oppId") String oppId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			// String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			return ResponseEntity.ok(recruitmentService.getOnboardCountByOpportunityId(oppId));

		}
		return null;

	}

	@PutMapping("/api/v1/recruitment/close/{recruitmentId}")
	public ResponseEntity<?> closeRecruitment(@PathVariable("recruitmentId") String recruitmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = recruitmentService.closeRecruitment(recruitmentId);

			return new ResponseEntity<>(msg, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/link/recruitment/close/opportunity/{opportunityId}")
	public List<RecruitmentOpportunityMapper> getCloseRecruitmentOfOpportunity(
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentOpportunityMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			list = recruitmentService.getCloseRecruitmentOfOpportunity(opportunityId, orgId, userId);
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		}

		return list;

	}

	@PutMapping("/api/v1/recruitment/open/{recruitmentId}")
	public ResponseEntity<?> OpenRecruitment(@PathVariable("recruitmentId") String recruitmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = recruitmentService.OpenRecruitment(recruitmentId);

			return new ResponseEntity<>(msg, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/recruitment/publish/ping/{recruitmentId}")
	public ResponseEntity<?> publishWebsiteRequirementPing(@PathVariable("recruitmentId") String recruitmentId,@RequestBody PingMapper pingMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String msg = recruitmentService.publishWebsiteRequirementPing(recruitmentId,pingMapper);

			return new ResponseEntity<>(msg, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/recruitment/upwork")
	public ResponseEntity<?> createUpwork(@RequestBody UpworkMapper upworkMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			upworkMapper.setOrgId(loggedInOrgId);
			upworkMapper.setUserId(loggedInUserId);

			String upworkId = recruitmentService.saveUpwork(upworkMapper);

			return new ResponseEntity<>(upworkId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/upwork/{orgId}")
	public ResponseEntity<?> getUpworkByOrgId(@PathVariable("orgId") String orgId, HttpServletRequest request,

			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			UpworkMapper upworkList = recruitmentService.getUpworkByOrgId(orgId);

			return new ResponseEntity<>(upworkList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/customer/{userId}")

	public ResponseEntity<List<CustomerRecruitmentMapper>> getCustomerListByUserId(
			@PathVariable("userId") String userId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<CustomerRecruitmentMapper> custoList = recruitmentService.getCustomerListByUserId(userId);

			return new ResponseEntity<>(custoList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	
	@GetMapping("/api/v1/recruitment/customer/org/{organizationId}")
	public ResponseEntity<List<CustomerRecruitmentMapper>> getCustomerRecruitSummaryListByOrgId(
			@PathVariable("organizationId") String organizationId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		List<CustomerRecruitmentMapper> custoList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				custoList = recruitmentService
						.getCustomerRecruitSummaryListByOrgId(organizationId);

				return new ResponseEntity<>(custoList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruit/dashbord/record/organisation/{orgId}")
	public ResponseEntity<?> getAllRecordByorgIdAndDateRange(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId, @RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(recruitmentService.getAllRecordByorgIdAndDateRange(orgId, startDate, endDate));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/recruitment/partner/website")
	public ResponseEntity<?> createWebsiteForPartner(@RequestBody WebsitePartnerLinkMapper websitePartnerLinkMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);

			websitePartnerLinkMapper.setOrgId(loggedInOrgId);
			websitePartnerLinkMapper.setUserId(loggedInUserId);

			String websiteId = recruitmentService.saveWebsiteForPartner(websitePartnerLinkMapper);

			return new ResponseEntity<String>(websiteId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/partner/website/{orgId}")
	public List<WebsitePartnerLinkMapper> getWebsitePartnerListByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<WebsitePartnerLinkMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			mapperList = recruitmentService.getWebsitePartnerListByOrgId(orgId);

		}

		return mapperList;

	}

	@GetMapping("/api/v1/recruitment/user/closer/{userId}")
	public ResponseEntity<?> getRecruiterAndSalesCloserByUserId(@PathVariable("userId") String userId,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		List<CustomerRecruitmentMapper> resultMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("recruiter")) {

				resultMapper = recruitmentService.getRecruiterCloserByUserId(userId, type, startDate, endDate);
				return new ResponseEntity<>(resultMapper, HttpStatus.OK);

			} else {

				resultMapper = recruitmentService.getSalesCloserByUserId(userId, startDate, endDate);
				return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/org/closer/{organisationId}")
	public ResponseEntity<?> getRecruitmentCloserByOrganisationId(@RequestHeader("Authorization") String authorization,
			@PathVariable("organisationId") String organisationId, HttpServletRequest request,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {
		// List<CustomerRecruitmentMapper> resultMapper = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<CustomerRecruitmentMapper> resultMapper = recruitmentService.getUsersCloserByOrgId(organisationId,
					startDate, endDate);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/recruitment/customer/sort/{organizationId}")
	public ResponseEntity<List<CustomerRecruitmentMapper>> getCustomerSortListByUserId(
			@PathVariable("organizationId") String organizationId, @RequestHeader("Authorization") String authorization,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate, HttpServletRequest request) {
		List<CustomerRecruitmentMapper> custoList = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				custoList = recruitmentService
						.getCustomerRequirementByOrgId(organizationId, startDate, endDate);

				return new ResponseEntity<>(custoList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruitment/candidate/internal-external/record/{recruitmentId}")
	public ResponseEntity<?> getCandidateExternalAndInternalCountListByRecruitmentId(
			@RequestHeader("Authorization") String authorization, @PathVariable("recruitmentId") String recruitmentId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity
					.ok(recruitmentService.getCandidateExternalAndInternalCountListByRecruitmentId(recruitmentId));

		}
		return null;

	}

	@PostMapping("/api/v1/abc")
	public String testUpdates(@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		String id = null;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			/*/// String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			// recruitmentProcessMapper.setOrganizationId(orgId);
			recruitmentService.updateCustomerLatestRequirement();*/
			Date date = Utility.getPlusMonth(new Date(), 2);
			System.out.println("result=="+date);

		}

		return id;

	}

	@GetMapping("/api/v1/recriutment/candidate/RecruitOwner/{recriutmentId}")
	public List<RecruitmentRecruitOwnerMapper> getTagCandidateRecruitOwnerListByRecriutmentId(
			@PathVariable("recriutmentId") String recriutmentId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {
		List<RecruitmentRecruitOwnerMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapperList = recruitmentService.getTagCandidateRecruitOwnerListByRecriutmentId(recriutmentId);

			// Collections.sort(mapperList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
		}
		return mapperList;

	}

	@PostMapping("/api/v1/note/feedback/average/{candidateId}")
	public void averageFeedBack(@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		int avg = 0;
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			recruitmentService.averageFeedBack();

		}
	}

	@GetMapping("/api/v1/recruitment/candidate/count/per-month")
	public ResponseEntity<?> getCandidateCountCameFromWebsitePerMonth(
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(recruitmentService.getCandidateCountCameFromWebsitePerMonth(startDate, endDate));

		}
		return null;
	}

	@GetMapping("/api/v1/deleted/recruitment/opportunity/{opportunityId}")
	public List<RecruitmentOpportunityMapper> getDeletedRecruitmentOfOpportunity(
			@PathVariable("opportunityId") String opportunityId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

		List<RecruitmentOpportunityMapper> list = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

			list = recruitmentService.getDeletedRecriutmentListByOppId(opportunityId, orgId, userId);
			Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		}

		return list;

	}

	@GetMapping("/api/v1/recruit/dashbord/funel/record/{orgId}")
	public ResponseEntity<?> getFunelRecordByOrganizationId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(recruitmentService.getFunelRecordByOrganizationId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/recruit/dashbord/speedo/record/{orgId}")
	public ResponseEntity<?> getSeedoMeterRecordByOrganizationId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(recruitmentService.getSeedoMeterRecordByOrganizationId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	 @PutMapping("/api/v1/recruit/close/rule")
     public ResponseEntity<?> updateRecruitmentClose(@RequestBody RecruitmentCloseRuleMapper recruitmentCloseRuleMapper,
             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        RecruitmentCloseRuleMapper recruitmentCloseRuleMapperr = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            String authToken = authorization.replace(TOKEN_PREFIX, "");

            recruitmentCloseRuleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            recruitmentCloseRuleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));


            recruitmentCloseRuleMapperr = recruitmentService.updateRecruitmentCloseRule(recruitmentCloseRuleMapper);
             return new ResponseEntity<>(recruitmentCloseRuleMapperr, HttpStatus.OK);

         }

         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
         
	    }
	    
	    
	    @GetMapping("/api/v1/recruit/close/rule/{orgId}")
      public ResponseEntity<?> getRecruitmentCloseRuleByOrgId(@RequestHeader("Authorization") String authorization,
              @PathVariable("orgId") String orgId, HttpServletRequest request) {
          if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
              //String authToken = authorization.replace(TOKEN_PREFIX, "");

              return ResponseEntity.ok(
                      recruitmentService.getRecruitmentCloseRuleByOrgId(orgId));
          }
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	    @PutMapping("/api/v1/recruitment/stage")
	     public ResponseEntity<?> updateReruitmentStage(@RequestBody RecruitmentStageMapper recruitmentStageMapper,
	             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

	    	RecruitmentStageMapper recruitmentStageMapperr = null;
	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");

	            recruitmentStageMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
	            recruitmentStageMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));


	            recruitmentStageMapperr = recruitmentService.updateReruitmentStage(recruitmentStageMapper);
	             return new ResponseEntity<>(recruitmentStageMapperr, HttpStatus.OK);

	         }

	         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	         
		    }
	    
	    @GetMapping("/api/v1/recruit/dashboard/open/org/{orgId}")
		public ResponseEntity<?> getOrgDashBoardOpenRecruitmentByOrgId(
				@RequestHeader("Authorization") String authorization, @PathVariable("orgId") String orgId){

			List<RecruitmentOpportunityMapper> requirmentList = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

					requirmentList = recruitmentService.getOrgDashBoardOpenRecruitmentByOrgId(orgId);
					Collections.sort(requirmentList, (RecruitmentOpportunityMapper m1,
							RecruitmentOpportunityMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);

			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	    
	    @GetMapping("/api/v1/recruit/dashbord/funnel/user/record/{userId}")
		public ResponseEntity<?> getFunnelRecordByUserId(@RequestHeader("Authorization") String authorization,
				@PathVariable("userId") String userId,@RequestParam(value = "type", required = true) String type,
				HttpServletRequest request) {
			
			List<FunnelMapper> requirmentList = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				if (type.equalsIgnoreCase("recruiter")) {

					requirmentList = recruitmentService.getRecruiterFunnelRecordByUserId(userId);
					
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);

				} else  {

					requirmentList = recruitmentService.getSalesFunnelRecordByUserId(userId);
					
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);

				}
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    @DeleteMapping("/api/v1/recruit/delete/workFlow/{processId}")
		public ResponseEntity<?> deleteWorkflowByProcessId(@PathVariable("processId") String processId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				recruitmentService.deleteWorkflowByProcessId(processId);

				return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	    
	    @GetMapping("/api/v1/recuitment/deleteHistory/workFlow/record/count/{orgId}")
		public ResponseEntity<?> getWorkFlowDeleteHistoryCountList(@PathVariable("orgId") String orgId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				// String authToken = authorization.replace(TOKEN_PREFIX, "");

				return ResponseEntity.ok(recruitmentService.getWorkFlowDeleteHistoryCountList(orgId));
			}
			return null;
		}
	    
	    @GetMapping("/api/v1/recuitment/contact/open/recuitment/{contactId}")
		public ResponseEntity<?> getOpenRecuitmentByContactId(@RequestHeader("Authorization") String authorization,
				@PathVariable("contactId") String contactId) {

			List<RecruitmentOpportunityMapper> contactList = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
				contactList = recruitmentService.getOpenRecuitmentByContactId(contactId,orgId);
				Collections.sort(contactList, ( m1,  m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(contactList, HttpStatus.OK);

			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	    @GetMapping("/api/v1/recuitment/workFlow/deleteHistory/list/{orgId}")
		public ResponseEntity<?> getAllWorkFlowDeleteHistoryListByOrgId(@PathVariable("orgId") String orgId,@RequestHeader("Authorization") String authorization,
				HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<RecruitmentProcessMapper> recruitmentProcessMapper = recruitmentService.getAllWorkFlowDeleteHistoryList(orgId);

				System.out.println("get recruitmentProcessMapper" + recruitmentProcessMapper.toString());
				Collections.sort(recruitmentProcessMapper, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));
				return new ResponseEntity<>(recruitmentProcessMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	    

	    @GetMapping("/api/v1/recruit/action/{userId}")
		public ResponseEntity<?> getRecruitmentActionByUserId(
				@RequestHeader("Authorization") String authorization, @PathVariable("userId") String userId,
				@RequestParam(value = "type", required = true) String type) {

			List<RecruitmentActionMapper> requirmentList = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				if (type.equalsIgnoreCase("recruiter")) {

					requirmentList = recruitmentService.getRecruiterRecruitmentActionByUserId(userId);
//					Collections.sort(requirmentList, (RecruitmentActionMapper m1,
//							RecruitmentActionMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);

				} else  {

					requirmentList = recruitmentService.getSalesRecruitmentActionByUserId(userId);
//					Collections.sort(requirmentList, (RecruitmentActionMapper m1,
//							RecruitmentActionMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);

				}
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

			
		@GetMapping("/api/v1/recuitment/employee/create/all-employees")
		public ResponseEntity<?> getEmployeeListByOrgIdForRecruitmentCreate(HttpServletRequest request,
				@RequestHeader("Authorization") String authorization) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);

				List<EmployeeViewMapper> empList = employeeService.getEmployeeListByOrgIdForRecruitmentCreate(organizationId);
				 //Collections.sort(empList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(empList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		@PutMapping("/api/v1/recruit/approve/action/{profileId}")
		public ResponseEntity<?> approveTaskByTaskId(@PathVariable("profileId") String profileId,
				@RequestBody RecruitmentActionMapper recruitmentActionMapper,HttpServletRequest request,
				@RequestHeader("Authorization") String authorization) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				recruitmentActionMapper.setUserId(jwtTokenUtil.getOrgIdFromToken(authToken));
				TaskViewMapper taskMapper = recruitmentService.approveAction(profileId,recruitmentActionMapper);
				return new ResponseEntity<>(taskMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@DeleteMapping("/api/v1/recuitment/delete/{stageId}")

		public ResponseEntity<?> deleteRecuitmentByStageId(@PathVariable("stageId") String stageId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				recruitmentService.deleteRecuitmentByStageId(stageId);

				return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@DeleteMapping("/api/v1/recruitment/delete/candidate/onboarding")
		public ResponseEntity<?> deleteCandidateOnboarding(@PathVariable("profileId") String profileId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			RecruitmentOpportunityMapper recruitmentOpportunityMapperNew = new RecruitmentOpportunityMapper();
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				recruitmentOpportunityMapperNew = recruitmentService.deleteCandidateOnboarding(profileId);
				return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@PutMapping("/api/v1/recriutment/canban/{profileId}/{stageId}")
		public RecruitmentOpportunityMapper changeCandidateAnotherStage(@PathVariable("profileId") String profileId,
				@PathVariable("stageId") String stageId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request)
				throws JsonGenerationException, JsonMappingException, Exception {

			RecruitmentOpportunityMapper mapper = null;
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				//String authToken = authorization.replace(TOKEN_PREFIX, "");
				//(jwtTokenUtil.getOrgIdFromToken(authToken));
				mapper = recruitmentService.changeCandidateAnotherStage(profileId,stageId);

			}

			return mapper;
			}
		
//		@PostMapping("/api/v1/recriutment/process/document/link")
//		public ResponseEntity<?> createProcessDocumentLink(@RequestBody List<ProcessDocumentLinkMapper> processDocumentLinkMapper,
//				@RequestHeader("Authorization") String authorization) {
//			
//			List<ProcessDocumentLinkMapper> processDocumentLinkMapper1 = null;
//
//			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//				//String authToken = authorization.replace(TOKEN_PREFIX, "");
//			
//				processDocumentLinkMapper1 = recruitmentService.saveProcessDocumentLink(processDocumentLinkMapper);
//
//				return new ResponseEntity<>(processDocumentLinkMapper1, HttpStatus.OK);
//			}
//
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//		@GetMapping("/api/v1/recriutment/process/document/link/{processId}")
//		public ResponseEntity<?> getProcessDocumentLinkByProcessId(
//				@PathVariable("processId") String processId, @RequestHeader("Authorization") String authorization,
//				HttpServletRequest request) {
//
//			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//				List<ProcessDocumentLinkMapper> processDocumentLinkMapper = recruitmentService
//						.getProcessDocumentLinkByProcessId(processId);
//				// Collections.sort(oppList,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
//				return new ResponseEntity<>(processDocumentLinkMapper, HttpStatus.OK);
//			}
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
		
		@PutMapping("/api/v1/recriutment/process/document/link/mandatory")

		public ResponseEntity<?> convertDocumentToMandatory(@RequestBody ProcessDocumentLinkMapper processDocumentLinkMapper,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				ProcessDocumentLinkMapper documentTypeMapperr = recruitmentService.convertDocumentToMandatory(processDocumentLinkMapper);

				return new ResponseEntity<ProcessDocumentLinkMapper>(documentTypeMapperr, HttpStatus.OK);

			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@GetMapping("/api/v1/openRecriutment/{orgId}")
		public List<RecruitmentOpportunityMapper> getListOfOpenRequirement(@PathVariable("orgId") String orgId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {
			List<RecruitmentOpportunityMapper> mapperList = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				mapperList = recruitmentService.getOpenRequirementListByOrgId(orgId);

				Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return mapperList;

		}
		
		@PutMapping("/api/v1/recruitment/candidate/reonboarding")
		public ResponseEntity<?> updateCandidateReOnboarding(
				@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request)
				throws IOException, TemplateException {

			RecruitmentOpportunityMapper recruitmentOpportunityMapperNew = new RecruitmentOpportunityMapper();
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				recruitmentOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				recruitmentOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				
				recruitmentOpportunityMapperNew = recruitmentService.updateCandidateReOnboarding(recruitmentOpportunityMapper);
				if(null!=recruitmentOpportunityMapperNew){

				return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("All Positions in This Recruitment has been Filled up !!!", HttpStatus.OK);
				}
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@PutMapping("/api/v1/recruitment/candidate/onboarding/after-disonboard")
		public ResponseEntity<?> onboardingCandidateAfterDisOnboard(
				@RequestBody RecruitmentOpportunityMapper recruitmentOpportunityMapper,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request)
				throws IOException, TemplateException {
			
			RecruitmentOpportunityMapper recruitmentOpportunityMapperNew = new RecruitmentOpportunityMapper();
			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				
				String authToken = authorization.replace(TOKEN_PREFIX, "");
				recruitmentOpportunityMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
				recruitmentOpportunityMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
				
				List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
						.getProfileDetailsByCandidateId(recruitmentOpportunityMapper.getCandidateId());
				if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
					for(RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
						recruitProfileLinkDetails.setOnboard_ind(false);
						recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
					}
				}
				
					recruitmentOpportunityMapperNew = recruitmentService.updateCandidateOnboarding(recruitmentOpportunityMapper);
					if(null!=recruitmentOpportunityMapperNew){

					return new ResponseEntity<>(recruitmentOpportunityMapperNew, HttpStatus.OK);
					}else {
						return new ResponseEntity<>("All Positions in This Recruitment has been Filled up !!!", HttpStatus.OK);
					}
				}
			
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@GetMapping("/api/v1/recriutment/project-name/{candidateId}")
		public ResponseEntity<?> getListOfOpenProjectByCandidateId(@PathVariable("candidateId") String candidateId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfOpenProjectByCandidateId(candidateId);
				Collections.sort(mapperList, (m1, m2) -> m2.getActualEndDate().compareTo(m1.getActualEndDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		
		@GetMapping("/api/v1/candidate/suggested/recruitment/{candidateId}")
		public ResponseEntity<?> getSuggestedRecquirementToCandidate(@PathVariable("candidateId") String candidateId,
				@RequestParam(value = "url", required = true) String url,
				HttpServletRequest request) {

			Map map = new HashMap();
			boolean b = false;
			Website web = websiteRepository.getByUrl(url);
			if (null != web) {							
				List<RecruitmentOpportunityMapper> mapper = recruitmentService.getSuggestedRecquirementToCandidate(candidateId,web.getOrgId());
				return new ResponseEntity<>(mapper, HttpStatus.OK);

			} else {
				map.put("website", b);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
		
		@GetMapping("/api/v1/recriutment/web/project-name/{candidateId}")
		public ResponseEntity<?> getListOfOpenProjectByCandidateIdForWeb(@PathVariable("candidateId") String candidateId,
				@RequestParam(value = "url", required = true) String url,
				HttpServletRequest request) {

			Map map = new HashMap();
			boolean b = candidateService.ipAddressExists(url);
			if (b == true) {
				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfOpenProjectByCandidateId(candidateId);
				Collections.sort(mapperList, (m1, m2) -> m2.getActualEndDate().compareTo(m1.getActualEndDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			} else {
				map.put("website", b);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
		
		@GetMapping("/api/v1/recruit/dashboard/candidate/{candidateId}")
		public ResponseEntity<?> getDashBoardRecruitmentByCandidateId(@PathVariable("candidateId") String candidateId,
				@RequestParam(value = "url", required = true) String url,
				HttpServletRequest request) {

			Map map = new HashMap();
			boolean b = candidateService.ipAddressExists(url);
			if (b == true) {
				List<RecruitmentOpportunityMapper> requirmentList = recruitmentService.getDashBoardRecruitmentByCandidateId(candidateId);
				if(null!=requirmentList && !requirmentList.isEmpty()) {
				Collections.sort(requirmentList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(requirmentList, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(requirmentList, HttpStatus.OK);
				}
			} else {
				map.put("website", b);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
		
		@GetMapping("/api/v1/recriutment/details/dashboard/{recruitmentId}")
		public ResponseEntity<?> getRecruitmentDetailsByRecruitmentId(@PathVariable("recruitmentId") String recruitmentId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				RecruitmentOpportunityMapper mapperList = recruitmentService.getRecruitmentDetailsByRecruitmentId(recruitmentId);
				
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		
		@GetMapping("/api/v1/recriutment/web/project-name/customer/{candidateId}")
		public ResponseEntity<?> getListOfCustomerNameFromOpenProjectByCandidateIdForWeb(@PathVariable("candidateId") String candidateId,
				@RequestParam(value = "url", required = true) String url,
				HttpServletRequest request) {

			Map map = new HashMap();
			boolean b = candidateService.ipAddressExists(url);
			if (b == true) {
				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfCustomerNameFromOpenProjectByCandidateIdForWeb(candidateId);
				Collections.sort(mapperList, (m1, m2) -> m2.getActualEndDate().compareTo(m1.getActualEndDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			} else {
				map.put("website", b);
				map.put("message", " website not Present !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		
		@GetMapping("/api/v1/recriutment/projectList/{customerId}")
		public ResponseEntity<?> getListOfProjectNameByCustomerId(@PathVariable("customerId") String customerId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfProjectNameByCustomerId(customerId);
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@GetMapping("/api/v1/recriutment/candidateList/{projectName}")
		public ResponseEntity<?> getListOfCandidateByProjectName(@PathVariable("projectName") String projectName,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfCandidateByProjectName(projectName);
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@GetMapping("/api/v1/recriutment/project-name/all/{orgId}")
		public ResponseEntity<?> getListOfOpenProjectByOrgId(@PathVariable("orgId") String orgId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfOpenProjectByOrgId(orgId);
				Collections.sort(mapperList, (m1, m2) -> m2.getActualEndDate().compareTo(m1.getActualEndDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		

		@GetMapping("/api/v1/recriutment/project-name/all/candidate/{orgId}")
		public ResponseEntity<?> getListOfOpenProjectCandidateListByOrgId(@PathVariable("orgId") String orgId,
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				List<CandidateProjectMapper> mapperList = recruitmentService.getListOfOpenProjectCandidateListByOrgId(orgId);
				Collections.sort(mapperList, (m1, m2) -> m2.getActualEndDate().compareTo(m1.getActualEndDate()));
				return new ResponseEntity<>(mapperList, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
		
		@GetMapping("/api/v1/link/recruitment/all/recruitment/{orgId}")
		public ResponseEntity<?> getAllRecruitmentsByOrgId(
				@PathVariable("orgId") String orgId, @RequestHeader("Authorization") String authorization,
				HttpServletRequest request) throws JsonGenerationException, JsonMappingException, Exception {

			List<RecruitmentOpportunityMapper> list = null;

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

				String authToken = authorization.replace(TOKEN_PREFIX, "");
				String userId = jwtTokenUtil.getUserIdFromToken(authToken);
				//String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

				list = recruitmentService.getAllRecruitmentsByOrgId(orgId, userId);
				Collections.sort(list, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(list, HttpStatus.OK);

			}

			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

		}
		
		
/*		@PostMapping("/api/v1/xyz")
		public ResponseEntity<?> sendMail(
				@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

			if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
				List<MultipartFile> file= new ArrayList<>();
				List<String> docIds = new ArrayList<>();
				docIds.add("DOCI54566315181122022");
				docIds.add("DOCI54566315181122022");
				docIds.add("DOCI54566315181122022");
				for (String documentId : docIds) {
					DocumentDetails doc = documentService.getDocumentDetailsByDocumentId(documentId);	
					file.add( new ByteArrayResource(doc.getDocument_data()));
				}
						
        String serverUrl ="https://develop.tekorero.com/kite/email/send";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	    body.add("file",file); 
	    body.add("fromEmail", "support@innoverenit.com");
	    body.add("message", "This is to inform you that it is the weekly requirement report.");
	    body.add("subject","Weekly Requirement Report");
	    body.add("toEmail", "skb4mail@gmail.com");
	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	   RestTemplate restTemplate = new RestTemplate();
	   ResponseEntity<String> response = restTemplate
	     .postForEntity(serverUrl, requestEntity, String.class);
	   System.out.println("response="+response.toString());
			}
			return null;
		}	*/
	}
