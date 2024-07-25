package com.app.employeePortal.ruleEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.ruleEngine.entity.OrganizationHotAgingRule;
@Repository

public interface OrganizationHotAgingRepository extends JpaRepository<OrganizationHotAgingRule, String>{

}
