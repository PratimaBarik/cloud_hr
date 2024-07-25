package com.app.employeePortal.investor.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.app.employeePortal.investor.mapper.InvestorOppStagesMapper;
import com.app.employeePortal.investor.mapper.InvestorOppWorkflowMapper;
import com.app.employeePortal.investor.service.InvestorOppWorkflowService;

@RestController
@CrossOrigin(maxAge = 3600)
public class InvestorOppWorkflowController {
    @Autowired
    InvestorOppWorkflowService investorOppWorkflowService;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @PostMapping("/api/v1/workflow/investorOpportunityWorkflow")
    public ResponseEntity<?> createInvOpportunityWorkflow(@RequestBody InvestorOppWorkflowMapper opportunityWorkflowMapper,
                                                       @RequestHeader("Authorization") String authorization) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            // String authToken = authorization.replace(TOKEN_PREFIX, "");
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
            opportunityWorkflowMapper.setUserId(loggedInUserId);
            opportunityWorkflowMapper.setOrgId(loggedInOrgId);

            InvestorOppWorkflowMapper invOpportunityWorkflowDetailsId = investorOppWorkflowService
                    .saveInvOpportunityWorkflow(opportunityWorkflowMapper);

            return new ResponseEntity<>(invOpportunityWorkflowDetailsId, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/investorOpportunityWorkflow/{orgId}")
    public ResponseEntity<?> getWorkflowListByOrgId(@PathVariable("orgId") String orgId,
                                                                  @RequestHeader("Authorization") String authorization, HttpServletRequest request){

        List<InvestorOppWorkflowMapper> mapperList = null;

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            mapperList = investorOppWorkflowService.getInvOppWorkflowListByOrgId(orgId);
            if(null!=mapperList) {
   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
   			}
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping("/api/v1/investorOpportunityWorkflow/{investorOppWorkflowId}")
    public ResponseEntity<?> updateInvOpportunityWorkflow(
            @PathVariable("investorOppWorkflowId") String investorOppWorkflowId,
            @RequestBody InvestorOppWorkflowMapper investorOppWorkflowMapper,
            @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            investorOppWorkflowMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            investorOppWorkflowMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            InvestorOppWorkflowMapper opportunityWorkflowMapper = investorOppWorkflowService
                    .updateInvOpportunityWorkflow(investorOppWorkflowId, investorOppWorkflowMapper);

            return new ResponseEntity<InvestorOppWorkflowMapper>(opportunityWorkflowMapper, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/api/v1/investorOpportunityWorkflow/{investorOppWorkflowId}")

    public ResponseEntity<?> deleteOpportunityWorkflow(
            @PathVariable("investorOppWorkflowId") String investorOppWorkflowId,
            @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            investorOppWorkflowService.deleteInvOpportunityWorkflowById(investorOppWorkflowId,loggedInUserId);

            return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/api/v1/investorOpportunityWorkflow/opportunityStages")
    public ResponseEntity<?> createOpportunityStages(@RequestBody InvestorOppStagesMapper opportunityStagesMapper,
                                                     @RequestHeader("Authorization") String authorization) {

        Map  map = new HashMap();

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            // String authToken = authorization.replace(TOKEN_PREFIX, "");
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            String loggedInOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
            opportunityStagesMapper.setUserId(loggedInUserId);
            opportunityStagesMapper.setOrgId(loggedInOrgId);

            if (0 <= (opportunityStagesMapper.getProbability())) {
                boolean b = investorOppWorkflowService.stageExistsByWeightage(opportunityStagesMapper.getProbability(),
                        opportunityStagesMapper.getInvestorOppStagesId());
                if (b) {
                    map.put("probabilityInd", b);
                    map.put("message", "Stage can not be created as same weightage already exists!!!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }

            InvestorOppStagesMapper opportunityStagesId = investorOppWorkflowService.saveInvOpportunityStages(opportunityStagesMapper);

            return new ResponseEntity<InvestorOppStagesMapper>(opportunityStagesId, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/investorOpportunityWorkflow/opportunityStages/{investorOppWorkflowId}")
    public List<?> getStagesByInvOppWorkFlowId(@PathVariable("investorOppWorkflowId") String investorOppWorkflowId,
                                                                  @RequestHeader("Authorization") String authorization, HttpServletRequest request)
            throws JsonGenerationException, JsonMappingException, Exception {

        List<InvestorOppStagesMapper> mapperList = null;

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            // String authToken = authorization.replace(TOKEN_PREFIX, "");

            mapperList = investorOppWorkflowService.getStagesByInvOppworkFlowId(investorOppWorkflowId);

        }

        return mapperList;

    }

    @PutMapping("/api/v1/investorOpportunityWorkflow/opportunityStages/{investorOppStagesId}")
    public ResponseEntity<?> updateOpportunityStages(@PathVariable("investorOppStagesId") String investorOppStagesId,
                                                     @RequestBody InvestorOppStagesMapper opportunityStagesMapper,
                                                     @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            // opportunityStagesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            opportunityStagesMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            InvestorOppStagesMapper opportunityStagesMapperr = investorOppWorkflowService
                    .updateInvOpportunityStages(investorOppStagesId, opportunityStagesMapper);

            return new ResponseEntity<>(opportunityStagesMapperr, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/api/v1/investorOpportunityWorkflow/opportunityStages/{investorOppStagesId}")

    public ResponseEntity<?> deleteOpportunityStages(@PathVariable("investorOppStagesId") String investorOppStagesId,
                                                     @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            investorOppWorkflowService.deleteInvOpportunityStagesById(investorOppStagesId,loggedInUserId);

            return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/api/v1/investorOpportunityWorkflow/opportunityStage/{orgId}")
    public ResponseEntity<?> getStagesByOrgId(@PathVariable("orgId") String orgId,
                                                          @RequestHeader("Authorization") String authorization, HttpServletRequest request){

        List<InvestorOppStagesMapper> mapperList = null;

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            // String authToken = authorization.replace(TOKEN_PREFIX, "");

            mapperList = investorOppWorkflowService.getStagesByOrgId(orgId);
            if(null!=mapperList) {
      			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
      			return new ResponseEntity<>(mapperList, HttpStatus.OK);
      			}
               return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping("/api/v1/investorOpportunityWorkflow/opportunityStages/update/publishInd")
    public ResponseEntity<?> updateOpportunityStagesPubliahInd(@RequestBody InvestorOppStagesMapper opportunityStagesMapper,
                                                               @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            //String authToken = authorization.replace(TOKEN_PREFIX, "");
            //opportunityStagesMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            //opportunityStagesMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            InvestorOppStagesMapper opportunityStagesMapperr = investorOppWorkflowService.updateInvOpportunityStagesPublishInd( opportunityStagesMapper);

            return new ResponseEntity<>(opportunityStagesMapperr, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping("/api/v1/investorOpportunityWorkflow/update/publishInd")
    public ResponseEntity<?> updateOpportunityWorkflowDetailsPublishInd(@RequestBody InvestorOppWorkflowMapper opportunityWorkflowMapper,
                                                                        @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            //String authToken = authorization.replace(TOKEN_PREFIX, "");
            //opportunityWorkflowMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            //opportunityWorkflowMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            InvestorOppWorkflowMapper opportunityWorkflowMapperr = investorOppWorkflowService.updateInvOpportunityWorkflowDetailsPublishInd(opportunityWorkflowMapper);

            return new ResponseEntity<>(opportunityWorkflowMapperr, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    
    @GetMapping("/api/v1/workflow/investorOpportunityWorkflow/for-dropdown/{orgId}")
    public ResponseEntity<?> getWorkflowListByOrgIdForDropDown(@PathVariable("orgId") String orgId,
    		 @RequestHeader("Authorization") String authorization, HttpServletRequest request){

        List<InvestorOppWorkflowMapper> mapperList = null;

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            mapperList = investorOppWorkflowService.getWorkflowListByOrgIdForDropDown(orgId);
            if(null!=mapperList) {
   			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
   			return new ResponseEntity<>(mapperList, HttpStatus.OK);
   			}
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/api/v1/investorOpportunityWorkflow/opportunityStage/for-dropdown/{orgId}")
    public ResponseEntity<?> getOpportunityWorkflowStagesByOrgIdForDropDown(@PathVariable("orgId") String orgId,
                                                          @RequestHeader("Authorization") String authorization, HttpServletRequest request){

        List<InvestorOppStagesMapper> mapperList = null;

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            // String authToken = authorization.replace(TOKEN_PREFIX, "");

            mapperList = investorOppWorkflowService.getOpportunityWorkflowStagesByOrgIdForDropDown(orgId);
            if(null!=mapperList) {
      			 Collections.sort(mapperList, ( m1, m2) ->m2.getCreationDate().compareTo(m1.getCreationDate()));
      			return new ResponseEntity<>(mapperList, HttpStatus.OK);
      			}
               return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
