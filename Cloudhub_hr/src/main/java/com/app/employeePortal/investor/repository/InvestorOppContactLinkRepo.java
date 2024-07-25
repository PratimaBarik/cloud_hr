package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppContactLink;

public interface InvestorOppContactLinkRepo extends JpaRepository<InvestorOppContactLink,String> {
    InvestorOppContactLink findByContactIdAndInvOpportunityId(String contactId, String invOpportunityId);
    @Query(value = "select a  from InvestorOppContactLink a  where a.invOpportunityId=:invOpportunityId" )
    List<InvestorOppContactLink> getByInvOpportunityId(@Param("invOpportunityId") String invOpportunityId);
}
