package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppDocsLink;

public interface InvestorOppDocsLinkRepo extends JpaRepository<InvestorOppDocsLink,String> {
    @Query(value = "select a  from InvestorOppDocsLink a  where a.invOpportunityId=:invOpportunityId" )
    List<InvestorOppDocsLink> getByInvOpportunityId(@Param("invOpportunityId") String invOpportunityId);
}
