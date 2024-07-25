package com.app.employeePortal.Workflow.controller;

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

import com.app.employeePortal.Workflow.mapper.StagesRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesResponseMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskResponseMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowRequestMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowResponseMapper;
import com.app.employeePortal.Workflow.service.WorkflowService;
import com.app.employeePortal.authentication.config.TokenProvider;

@RestController
@CrossOrigin(maxAge = 3600)
public class WorkflowController {

    @Autowired
    WorkflowService workflowService;
    @Autowired
    private TokenProvider jwtTokenUtil;

    @PostMapping("/api/v1/workflow")
    public ResponseEntity<?> createworkflow(@RequestBody WorkflowRequestMapper requestMapper,
                                            @RequestHeader("Authorization") String authorization) {
    	 Map map = new HashMap();
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            if (null!=requestMapper.getWorkflowName()) {
                boolean b = workflowService.workflowExistsByName(requestMapper.getWorkflowName(),
                        requestMapper.getOrgId(), requestMapper.getType());
                if (b == true) {
                    map.put("workflowInd", b);
                    map.put("message", "Workflow can not be created as same name already exists!!!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }else {
                	WorkflowResponseMapper id = workflowService.saveWorkflow(requestMapper);
                    return new ResponseEntity<>(id, HttpStatus.OK);
                }
            }else {
                 map.put("message", "Please provide Wrokflow name!!!");
                 return new ResponseEntity<>(map, HttpStatus.OK);
            }
            
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/{orgId}/{type}")
    public ResponseEntity<?> getworkflowListByOrgId(@PathVariable("orgId") String orgId,
                                                    @PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
                                                    HttpServletRequest request) {

        List<WorkflowResponseMapper> mapperList = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            mapperList = workflowService.getWorkflowListByOrgId(orgId, type);
            if (null != mapperList) {
                Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(mapperList, HttpStatus.OK);
            }
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/workflow/{workflowDetailsId}")
    public ResponseEntity<?> updateworkflowDetails(@PathVariable("workflowDetailsId") String workflowDetailsId,
                                                   @RequestBody WorkflowRequestMapper requestMapper, @RequestHeader("Authorization") String authorization,
                                                   HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            WorkflowResponseMapper responseMapper = workflowService.updateWorkflowDetails(workflowDetailsId,
                    requestMapper);

            return new ResponseEntity<WorkflowResponseMapper>(responseMapper, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/api/v1/workflow/{workflowDetailsId}")
    public ResponseEntity<?> deleteworkflowDetails(@PathVariable("workflowDetailsId") String workflowDetailsId,
                                                   @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String userId = jwtTokenUtil.getUserIdFromToken(authToken);
            workflowService.deleteWorkflowDetails(workflowDetailsId,userId);
            return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/api/v1/workflow/stages")
    public ResponseEntity<?> createOpportunityStages(@RequestBody StagesRequestMapper requestMapper,
                                                     @RequestHeader("Authorization") String authorization) {
        Map map = new HashMap();
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

            if (0 <= (requestMapper.getProbability())) {
                boolean b = workflowService.stageExistsByWeightage(requestMapper.getProbability(),
                        requestMapper.getWorkflowDetailsId());
                if (b == true) {
                    map.put("probabilityInd", b);
                    map.put("message", "Stage can not be created as same weightage already exists!!!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }else {
                	StagesResponseMapper id = workflowService.saveStages(requestMapper);
                    return new ResponseEntity<>(id, HttpStatus.OK);
                }
            }else {
                 map.put("message", "Probability can not be Negative!!!");
                 return new ResponseEntity<>(map, HttpStatus.OK);
            }
            
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/Stages/{workflowDetailsId}")
    public List<StagesResponseMapper> getStagesByworkflowDetailsId(
            @PathVariable("workflowDetailsId") String workflowDetailsId,
            @RequestHeader("Authorization") String authorization, HttpServletRequest request)
            throws JsonGenerationException, JsonMappingException, Exception {
        List<StagesResponseMapper> mapperList = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            mapperList = workflowService.getStagesByWorkflowDetailsId(workflowDetailsId);
        }
        return mapperList;
    }

    @PutMapping("/api/v1/workflow/stages/{StagesId}")
    public ResponseEntity<?> updateStagesId(@PathVariable("StagesId") String StagesId,
                                            @RequestBody StagesRequestMapper requestMapper, @RequestHeader("Authorization") String authorization,
                                            HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            StagesResponseMapper responseMapper = workflowService.updateStagesId(StagesId, requestMapper);
            return new ResponseEntity<StagesResponseMapper>(responseMapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/workflow/Stages/{StagesId}")
    public ResponseEntity<?> deleteStages(@PathVariable("StagesId") String StagesId,
                                          @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String userId = jwtTokenUtil.getUserIdFromToken(authToken);
            workflowService.deleteStagesById(StagesId,userId);
            return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/Stages/update/publishInd")
    public ResponseEntity<?> updateStagesPubliahInd(@RequestBody StagesRequestMapper requestMapper,
                                                    @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            StagesResponseMapper responseMapper = workflowService.updateStagesPubliahInd(requestMapper);
            return new ResponseEntity<StagesResponseMapper>(responseMapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/workflow/update/publishInd")
    public ResponseEntity<?> updateworkflowDetailsPublishInd(@RequestBody StagesRequestMapper requestMapper,
                                                             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            WorkflowResponseMapper responseMapper = workflowService.updateWorkflowDetailsPublishInd(requestMapper);
            return new ResponseEntity<WorkflowResponseMapper>(responseMapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/for_dropdown/{orgId}/{type}")
    public ResponseEntity<?> getworkflowListByOrgIdForDropdown(@PathVariable("orgId") String orgId,
                                                               @PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
                                                               HttpServletRequest request) {
        List<WorkflowResponseMapper> mapperList = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            mapperList = workflowService.getWorkflowListByOrgIdForDropdown(orgId, type);
            if (null != mapperList) {
                Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(mapperList, HttpStatus.OK);
            }
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/stages/for_dropdown/{orgId}")
    public ResponseEntity<?> getStagesByOrgIdForDropdown(@PathVariable("orgId") String orgId,
                                                         @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        List<StagesResponseMapper> mapperList = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            mapperList = workflowService.getStagesByOrgIdForDropdown(orgId);
            if (null != mapperList) {
                Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(mapperList, HttpStatus.OK);
            }
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/workflow/stages/{globalInd}/{workflowId}")
    public ResponseEntity<?> updateGlobalIndForWorkflow(@PathVariable("globalInd") boolean globalInd, @PathVariable("workflowId") String workflowId,
                                                        @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
            WorkflowResponseMapper mapper = workflowService.updateGlobalIndForWorkflow(orgId, globalInd, workflowId);
            return new ResponseEntity<>(mapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/workflow/globalIndTrueList/{orgId}/{type}")
    public ResponseEntity<?> listOfWorkflowWithGlobalInd(@PathVariable("orgId") String orgId, @PathVariable("type") String type,
                                                         @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        List<WorkflowResponseMapper> mapperList = null;
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            mapperList = workflowService.listOfWorkflowWithGlobalInd(orgId, type);
            if (null != mapperList) {
                Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(mapperList, HttpStatus.OK);
            }
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/v1/workflow/createNew/{orgId}/{workflowId}/{type}")
    public ResponseEntity<?> createNewWorkflowForOrg(@PathVariable("orgId") String orgId, @PathVariable("workflowId") String workflowId, @PathVariable("type") String type,
                                                     @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String userId = jwtTokenUtil.getUserIdFromToken(authToken);

            WorkflowResponseMapper mapper = workflowService.createNewWorkflowForOrg(orgId, workflowId, userId,type);
            return new ResponseEntity<>(mapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/api/v1/workflow/stage-task/save")
    public ResponseEntity<?> createStageTask(@RequestBody StagesTaskRequestMapper requestMapper,
                                            @RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            StagesTaskResponseMapper id = workflowService.createStageTask(requestMapper);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/api/v1/workflow/stage-task/{orgId}/{stageId}")
    public ResponseEntity<?> getStageTaskByStageId(@PathVariable("orgId") String orgId, 
    		@PathVariable("stageId") String stageId, @RequestHeader("Authorization") String authorization,
    		HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	 List<StagesTaskResponseMapper> mapperList = workflowService.getStageTaskByStageId(orgId, stageId);
            if (null != mapperList) {
                Collections.sort(mapperList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
            }
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/api/v1/moveWorkflow/{type}")
    public ResponseEntity<?> moveWF(@PathVariable("type")String type,
    		@RequestHeader("Authorization") String authorization,HttpServletRequest request) {
    	 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            return new ResponseEntity<>(workflowService.moveWF(type), HttpStatus.OK);
        }
    	 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/workflow/stage-task/{stagesTaskId}/{mandatoryInd}")
    public ResponseEntity<?> updateMandatoryIndForStagesTask(@PathVariable("stagesTaskId") String stagesTaskId,@PathVariable("mandatoryInd") boolean mandatoryInd, @RequestHeader("Authorization") String authorization,
                                                   HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            StagesTaskResponseMapper mapperList = workflowService.updateMandatoryIndForStagesTask(stagesTaskId,mandatoryInd);
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PutMapping("/api/v1/workflow/stage-task/{stagesTaskId}")
    public ResponseEntity<?> updateStagesTask(@PathVariable("stagesTaskId") String stagesTaskId,@RequestBody StagesTaskRequestMapper requestMapper, @RequestHeader("Authorization") String authorization,
                                                             HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            StagesTaskResponseMapper mapperList = workflowService.updateStagesTask(stagesTaskId,requestMapper);
            return new ResponseEntity<>(mapperList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/workflow/stage-task/delete/{stagesTaskId}/{liveInd}")
    public ResponseEntity<?> deleteReinstateStagesTask(@PathVariable("stagesTaskId") String stagesTaskId,@PathVariable("liveInd") boolean liveInd, @RequestHeader("Authorization") String authorization,
                                              HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
        	String authToken = authorization.replace(TOKEN_PREFIX, "");
        	String userId = jwtTokenUtil.getUserIdFromToken(authToken);
            String msg = workflowService.deleteReinstateStagesTask(stagesTaskId,liveInd,userId);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
