package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.AssessmentQuestionMapper;

import java.util.List;

public interface AssessmentQuestionService {
    public AssessmentQuestionMapper createQuestion(AssessmentQuestionMapper roleMapper);

    AssessmentQuestionMapper getAssessmentQuestionById(String assmntQstnId);

    public AssessmentQuestionMapper updateQuestion(String assessmentQstnId, AssessmentQuestionMapper roleMapper);

    List<AssessmentQuestionMapper> getAssessmentQuestionAllByOrgId(String orgId);

    String deleteAssessmentQuestionById(String assessmentQuestionId,boolean liveInd);

    List<AssessmentQuestionMapper> getAssessmentQuestionByDepartmentAndRoleType(String orgId, String departmentId, String roleTypeId);
}
