package com.app.employeePortal.category.controller;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.*;
import com.app.employeePortal.category.service.AssessmentQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

@RestController
@CrossOrigin(maxAge = 3600)
public class AssessmentQuestionController {

    @Autowired
    AssessmentQuestionService assessmentQuestionService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @PostMapping(value = "/api/v1/assessmentQuestion")
    public ResponseEntity<?> createQuestion(@RequestBody AssessmentQuestionMapper roleMapper,
                                            @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            roleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            roleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            AssessmentQuestionMapper RoleTypeId = assessmentQuestionService.createQuestion(roleMapper);
            return new ResponseEntity<>(RoleTypeId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/assessmentQuestion")
    public ResponseEntity<?> updateQuestion(@RequestBody AssessmentQuestionMapper roleMapper,
                                            @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            roleMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
            roleMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
            AssessmentQuestionMapper roleMapperr = assessmentQuestionService.updateQuestion(roleMapper.getAssessmentQstnId(), roleMapper);
            return new ResponseEntity<AssessmentQuestionMapper>(roleMapperr, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/api/v1/assessmentQuestion/{orgId}")
    public ResponseEntity<?> getAssessmentQuestionAllByOrgId(@PathVariable("orgId") String orgId,
                                            @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            List<AssessmentQuestionMapper> typeList = new ArrayList<>();
            typeList = assessmentQuestionService.getAssessmentQuestionAllByOrgId(orgId);
            if (null != typeList && !typeList.isEmpty()) {
                Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(typeList, HttpStatus.OK);
            } else {
                Map map = new HashMap();
                map.put("message", "Data Not Available!!!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

   
//    @GetMapping("/api/v1/assessmentQuestion/search/{name}")
//    public ResponseEntity<?> getroleDetailsByName(@PathVariable("name") String name,
//                                                  @RequestHeader("Authorization") String authorization, HttpServletRequest request)
//            throws JsonGenerationException, IOException {
//        Map map = new HashMap();
//        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//            String authToken = authorization.replace(TOKEN_PREFIX, "");
//            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
//            List<AssessmentQuestionMapper> roleMapper = assessmentQuestionService.getRoleDetailsByNameByOrgLevel(name, orgId);
//            if (null != roleMapper && !roleMapper.isEmpty()) {
//                return new ResponseEntity<>(roleMapper, HttpStatus.OK);
//            } else {
//                map.put("message", " No Records Found !!!");
//                return new ResponseEntity<>(map, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }


    @DeleteMapping("/api/v1/assessmentQuestion/{assessmentQuestionId}/{liveInd}")

    public ResponseEntity<?> deleteAssessmentQuestionById(@PathVariable("assessmentQuestionId") String assessmentQuestionId,@PathVariable("liveInd") boolean liveInd,
                                                   @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

            assessmentQuestionService.deleteAssessmentQuestionById(assessmentQuestionId,liveInd);

            return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/api/v1/assessmentQuestion/{departmentId}/{roleTypeId}")
    public ResponseEntity<?> getAssessmentQuestionByDepartmentAndRoleType(@PathVariable("departmentId") String departmentId,@PathVariable("roleTypeId") String roleTypeId,
                                                             @RequestHeader("Authorization") String authorization, HttpServletRequest request) {

        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
            List<AssessmentQuestionMapper> typeList = new ArrayList<>();
            String authToken = authorization.replace(TOKEN_PREFIX, "");
            String orgId=jwtTokenUtil.getOrgIdFromToken(authToken);
            typeList = assessmentQuestionService.getAssessmentQuestionByDepartmentAndRoleType(orgId,departmentId,roleTypeId);
            if (null != typeList && !typeList.isEmpty()) {
                Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                return new ResponseEntity<>(typeList, HttpStatus.OK);
            } else {
                Map map = new HashMap();
                map.put("message", "Data Not Available!!!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
