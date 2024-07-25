package com.app.employeePortal.ruleEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.ruleEngine.entity.OrganizationColdLeadsAgingRule;

public interface OrganizationColdAgingRepository extends JpaRepository<OrganizationColdLeadsAgingRule, String> {

}
