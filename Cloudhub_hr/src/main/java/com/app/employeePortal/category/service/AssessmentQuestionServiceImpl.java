package com.app.employeePortal.category.service;

import com.app.employeePortal.category.entity.AssessmentQuestion;
import com.app.employeePortal.category.mapper.*;
import com.app.employeePortal.category.repository.AssessmentQuestionRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssessmentQuestionServiceImpl implements AssessmentQuestionService {
    @Autowired
    AssessmentQuestionRepository assessmentQuestionRepository;
    @Autowired
    EmployeeService employeeService;

    @Override
    public AssessmentQuestionMapper createQuestion(AssessmentQuestionMapper mapper) {
        AssessmentQuestion assessmentQuestion = new AssessmentQuestion();
        assessmentQuestion.setQuestion(mapper.getQuestion());
        assessmentQuestion.setDepartmentId(mapper.getDepartmentId());
        assessmentQuestion.setRoleTypeId(mapper.getRoleTypeId());
        assessmentQuestion.setOrgId(mapper.getOrgId());
        assessmentQuestion.setUserId(mapper.getUserId());
        assessmentQuestion.setCreationDate(new Date());
        assessmentQuestion.setLiveInd(true);
        return getAssessmentQuestionById(assessmentQuestionRepository.save(assessmentQuestion).getAssmntQstnId());
    }

    @Override
    public AssessmentQuestionMapper getAssessmentQuestionById(String assmntQstnId) {
        AssessmentQuestionMapper mapper = new AssessmentQuestionMapper();
        AssessmentQuestion assessmentQuestion = assessmentQuestionRepository.findById(assmntQstnId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with id " + assmntQstnId));
        if (null != assessmentQuestion) {
            mapper.setAssessmentQstnId(assessmentQuestion.getAssmntQstnId());
            mapper.setQuestion(assessmentQuestion.getQuestion());
            mapper.setDepartmentId(assessmentQuestion.getDepartmentId());
            mapper.setRoleTypeId(assessmentQuestion.getRoleTypeId());
            mapper.setOrgId(assessmentQuestion.getOrgId());
            mapper.setUserId(assessmentQuestion.getUserId());
            if (null != assessmentQuestion.getCreationDate()) {
                mapper.setCreationDate(Utility.getISOFromDate(assessmentQuestion.getCreationDate()));
            }
            mapper.setLiveInd(assessmentQuestion.isLiveInd());

            if (null != assessmentQuestion.getUpdationDate()) {
                mapper.setUpdationDate(Utility.getISOFromDate(assessmentQuestion.getUpdationDate()));
            }

            if (null != assessmentQuestion.getUpdatedBy()) {
                mapper.setUpdatedBy(employeeService.getEmployeeFullName(assessmentQuestion.getUpdatedBy()));
            }
        }

        return mapper;
    }

    @Override
    public AssessmentQuestionMapper updateQuestion(String assessmentQstnId, AssessmentQuestionMapper mapper) {
        AssessmentQuestion assessmentQuestion = assessmentQuestionRepository.findById(assessmentQstnId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with id " + assessmentQstnId));
        assessmentQuestion.setQuestion(mapper.getQuestion());
        assessmentQuestion.setUpdationDate(new Date());
        assessmentQuestion.setUpdatedBy(mapper.getUserId());
        return getAssessmentQuestionById(assessmentQuestionRepository.save(assessmentQuestion).getAssmntQstnId());
    }

    @Override
    public List<AssessmentQuestionMapper> getAssessmentQuestionAllByOrgId(String orgId) {
        return assessmentQuestionRepository.findByOrgIdAndLiveInd(orgId, true).stream().map(a -> getAssessmentQuestionById(a.getAssmntQstnId())).collect(Collectors.toList());
    }

    @Override
    public String deleteAssessmentQuestionById(String assmntQstnId, boolean liveInd) {
        AssessmentQuestion assessmentQuestion = assessmentQuestionRepository.findById(assmntQstnId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with id " + assmntQstnId));
        if (!liveInd) {
            assessmentQuestion.setLiveInd(false);
            assessmentQuestionRepository.save(assessmentQuestion);
            return "Deleted successfully";
        } else {
            assessmentQuestion.setLiveInd(true);
            assessmentQuestionRepository.save(assessmentQuestion);
            return "Reinstated successfully";
        }

    }

    @Override
    public List<AssessmentQuestionMapper> getAssessmentQuestionByDepartmentAndRoleType(String orgId, String departmentId, String roleTypeId) {
        return assessmentQuestionRepository.findByOrgIdAndDepartmentIdAndRoleTypeIdAndLiveInd(orgId,departmentId,roleTypeId, true).stream().map(a -> getAssessmentQuestionById(a.getAssmntQstnId())).collect(Collectors.toList());
    }
}