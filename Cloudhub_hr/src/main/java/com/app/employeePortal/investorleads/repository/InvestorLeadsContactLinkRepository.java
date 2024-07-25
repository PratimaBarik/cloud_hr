package com.app.employeePortal.investorleads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeadsContactLink;
@Repository
public interface InvestorLeadsContactLinkRepository extends JpaRepository<InvestorLeadsContactLink, String> {

	@Query(value = "select a  from InvestorLeadsContactLink a  where a.investorLeadsId=:investorleadsId" )
	public InvestorLeadsContactLink getContactByInvestorLeadsId(@Param(value="investorleadsId")String investorleadsId);

	@Query(value = "select a  from InvestorLeadsContactLink a  where a.investorLeadsId=:investorLeadsId" )
	public List<InvestorLeadsContactLink> getContactIdByInvestorLeadsId(@Param(value="investorLeadsId")String investorLeadsId);
	
}
