package com.app.employeePortal.ruleEngine.controller;



import static  com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.ruleEngine.mapper.OrganizationLeadsAgingRuleMapper;
import com.app.employeePortal.ruleEngine.mapper.RecruitProMailMapper;
import com.app.employeePortal.ruleEngine.mapper.RecruitProNotificationMapper;
import com.app.employeePortal.ruleEngine.mapper.ReportSchedulingMapper;
import com.app.employeePortal.ruleEngine.service.AgeingService;
import com.app.employeePortal.ruleEngine.service.RuleEngineService;






@RestController
@CrossOrigin(maxAge = 3600)

public class RuleEngineController {
	
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@Autowired
	AgeingService ageingService;
	
	@Autowired
	RuleEngineService ruleEngineService;
	
	@PutMapping("/api/v2.0/rule/leads/aging")

	public ResponseEntity<?> saveLeadsAgingRule(@RequestBody OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper, 
			                    @RequestHeader("Authorization") String authorization,
			                    HttpServletRequest request) throws Exception {
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	
            String authToken = authorization.replace(TOKEN_PREFIX,"");

                 //organizationLeadsAgingRuleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
      
                 OrganizationLeadsAgingRuleMapper resultMapper= ageingService.saveLeadsAgingRule(organizationLeadsAgingRuleMapper);
                                   
    		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			
        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v2.0/rule/leads/aging")

	public ResponseEntity<?> getLeadsAgingRule( 
			                    @RequestHeader("Authorization") String authorization,
			                    HttpServletRequest request) throws Exception {
		
		
			String escalationRuleId = null;	   	 
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	
            String authToken = authorization.replace(TOKEN_PREFIX,"");

            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
                  
            OrganizationLeadsAgingRuleMapper resultMapper= ageingService.getLeadsAgingRule(orgId);
                                   
    		return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			
        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v2.0/recruit/mail")
	public RecruitProMailMapper createRecruitMailRule(@RequestBody RecruitProMailMapper recruitProMailLink,
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request)
			                                    throws JsonGenerationException, 
			                                    JsonMappingException, Exception {
		
		String authToken = null;
		String approvalRuleId = null;
		
		RecruitProMailMapper recruitProMailLinkNew = null; 
		
 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			authToken = authorization.replace(TOKEN_PREFIX, "");

			recruitProMailLink.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			
			approvalRuleId = ruleEngineService.saveRecruitProMailRuleLink(recruitProMailLink);
			
			recruitProMailLinkNew = ruleEngineService.recruitProMailMapper(recruitProMailLink.getOrgId());
			
			

			
		}

		return  recruitProMailLinkNew;

	}
	
	@GetMapping("/api/v1/recruitPro/mail/{orgId}")
	public RecruitProMailMapper getRecruitEmailRule(@PathVariable("orgId") String orgId,
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request)
			                                    throws JsonGenerationException, 
			                                    JsonMappingException, Exception {
		
		
		RecruitProMailMapper recruitProMailMapperNew = null; 
		
 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			
			
			
			recruitProMailMapperNew = ruleEngineService.recruitProMailMapper(orgId);
			
			

			
		}

		return  recruitProMailMapperNew;

	}
	
	@PutMapping("/api/v2.0/recruit/notification")
	public RecruitProNotificationMapper saveRecruitNotification( @RequestBody RecruitProNotificationMapper recruitProNotificationMapper,
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request)
			                                    throws JsonGenerationException, 
			                                    JsonMappingException, Exception {
		
		String authToken = null;
		String approvalRuleId = null;
		
		RecruitProNotificationMapper recruitProNotificationMapperNew = null; 
		
 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			authToken = authorization.replace(TOKEN_PREFIX, "");

			recruitProNotificationMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			
			
			approvalRuleId = ruleEngineService.saveRecruitProNotificationRuleLink(recruitProNotificationMapper);
			
			//recruitProNotificationMapperNew = ruleEngineService.recruitProNotification(recruitProNotificationMapper.getOrgId());
		
		}

		return  recruitProNotificationMapperNew;

	}
	
	@GetMapping("/api/v2.0/recruitPro/notification/{orgId}")
	public RecruitProNotificationMapper getRecruitProNotification(@PathVariable("orgId") String orgId,
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request)
			                                    throws JsonGenerationException, 
			                                    JsonMappingException, Exception {
		
		
		RecruitProNotificationMapper recruitProNotificationMapper = null; 
		
 
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			
			
			
			recruitProNotificationMapper = ruleEngineService.getRecruitProNotification(orgId);
			
			

			
		}

		return  recruitProNotificationMapper;

	}
	
	@PostMapping("/api/v1/ruleEngine/report/scheduling")
	public ResponseEntity<?> createReportScheduling( @RequestBody ReportSchedulingMapper reportSchedulingMapper ,
			                     @RequestHeader("Authorization") String authorization
			                     ,HttpServletRequest request) {
		
		
		
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");

        	reportSchedulingMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
        	reportSchedulingMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
              String msg = ruleEngineService.saveReportScheduling(reportSchedulingMapper);

  			return new ResponseEntity<>(msg,HttpStatus.OK);

        }
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }

	
	@GetMapping("/api/v1/ruleEngine/report/scheduling/{departmentId}")
	public List<ReportSchedulingMapper> getReportSchedulingListByOrgId(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		List<ReportSchedulingMapper> mapperList = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			
			mapperList = ruleEngineService.getReportSchedulingListByOrgId(departmentId,orgId);

		}

		return mapperList;

	}
	@GetMapping("/api/v1/report/scheduling/weekly")
	public List<ReportSchedulingMapper> getAllReport( HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, Exception {

		//List<ReportSchedulingMapper> mapperList = null;


			//String authToken = authorization.replace(TOKEN_PREFIX, "");
			
			ruleEngineService.getAllReport();


		return null;
	}
	
	@DeleteMapping("/api/v1/ruleEngine/report-scheduling/{reportSchedulingId}")
	public ResponseEntity<?> deleteReportSchedulingRule(@PathVariable("reportSchedulingId") String reportSchedulingId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			ruleEngineService.deleteReportSchedulingRule(reportSchedulingId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
}
