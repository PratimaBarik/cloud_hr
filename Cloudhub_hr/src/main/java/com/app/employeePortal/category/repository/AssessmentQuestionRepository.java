package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, String> {

    List<AssessmentQuestion> findByOrgIdAndLiveInd(String orgId, boolean b);

    List<AssessmentQuestion> findByOrgIdAndDepartmentIdAndRoleTypeIdAndLiveInd(String orgId, String departmentId, String roleTypeId, boolean b);
}
