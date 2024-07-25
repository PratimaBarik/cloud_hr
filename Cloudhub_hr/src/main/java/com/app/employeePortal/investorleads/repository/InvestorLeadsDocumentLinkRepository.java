package com.app.employeePortal.investorleads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeadsDocumentLink;
@Repository
public interface InvestorLeadsDocumentLinkRepository  extends JpaRepository<InvestorLeadsDocumentLink, String>{
	
	@Query(value = "select a  from InvestorLeadsDocumentLink a  where a.investorleadsId=:investorLeadsId" )
	List<InvestorLeadsDocumentLink> getDocumentByInvestorLeadsId(@Param(value="investorLeadsId")String investorLeadsId);
	
}
