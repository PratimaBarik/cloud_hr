package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.AdvanceRecruitmentRule;

@Repository
public interface AdvanceRecruitmentRuleRepository extends JpaRepository<AdvanceRecruitmentRule, String> {

}
