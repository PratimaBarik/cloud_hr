package com.app.employeePortal.ruleEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.ruleEngine.entity.OrganizationWarmLeadsAgingRule;

public interface OrganizationWarmAgingRepository extends JpaRepository<OrganizationWarmLeadsAgingRule, String> {

}
