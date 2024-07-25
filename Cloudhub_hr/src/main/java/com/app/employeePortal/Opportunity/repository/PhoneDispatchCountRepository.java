package com.app.employeePortal.Opportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.Opportunity.entity.PhoneDispatchCount;

public interface PhoneDispatchCountRepository extends JpaRepository<PhoneDispatchCount, String>{

	PhoneDispatchCount findByOrderPhoneId(String orderPhoneId);

	PhoneDispatchCount findByOpportunityId(String opportunityId);



}
