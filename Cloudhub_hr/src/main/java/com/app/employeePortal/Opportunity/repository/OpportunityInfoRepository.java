package com.app.employeePortal.Opportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityInfo;

@Repository
public interface OpportunityInfoRepository extends JpaRepository<OpportunityInfo, String> {

}
