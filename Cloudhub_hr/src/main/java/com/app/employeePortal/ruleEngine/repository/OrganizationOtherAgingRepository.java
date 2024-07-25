package com.app.employeePortal.ruleEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.ruleEngine.entity.OrganizationOtherLeadsAgingRule;

public interface OrganizationOtherAgingRepository extends JpaRepository<OrganizationOtherLeadsAgingRule, String> {

}
