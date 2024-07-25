package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentCloseRule;


@Repository
public interface RecruitmentCloseRuleRepository extends JpaRepository<RecruitmentCloseRule,String>{

    RecruitmentCloseRule findByOrgId(String orgId);

    
    

}
