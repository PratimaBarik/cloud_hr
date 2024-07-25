package com.app.employeePortal.investorleads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeadsOpportunityLink;
@Repository
public interface InvestorLeadsOpportunityLinkRepository  extends JpaRepository<InvestorLeadsOpportunityLink, String>{

	@Query(value = "select a  from InvestorLeadsOpportunityLink a  where a.investorLeadOppId=:investorLeadOppId and a.liveInd=true" )
	public InvestorLeadsOpportunityLink getInvestorLeadsOpportunityDetailsByInvestorLeadsOppId(@Param(value="investorLeadOppId")String investorLeadOppId);

	@Query(value = "select a  from InvestorLeadsOpportunityLink a  where a.investorLeadsId=:investorLeadsId and a.liveInd=true" )
	public List<InvestorLeadsOpportunityLink> getOpportunityListByInvestorLeadsId(@Param(value="investorLeadsId")String investorLeadsId);

	@Query(value = "select a  from InvestorLeadsOpportunityLink a  where a.investorLeadsId=:investorLeadsId and a.liveInd=true" )
	public InvestorLeadsOpportunityLink getByInvestorLeadsId(String investorLeadsId);

	
}
