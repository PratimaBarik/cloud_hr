package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.call.entity.InvestorLeadsCallLink;

public interface InvestorLeadsCallLinkRepo extends JpaRepository<InvestorLeadsCallLink,String> {

	 @Query("select a from InvestorLeadsCallLink a where a.investorLeadsId=:investorLeadsId and a.liveInd=true")
	Page<InvestorLeadsCallLink> getCallListByInvestorLeadsId(String investorLeadsId, Pageable paging1);
	 
	 @Query("select a from InvestorLeadsCallLink a where a.investorLeadsId=:investorLeadsId and a.liveInd=true")
		List<InvestorLeadsCallLink> getCallListByInvestorLeadsIdAndLiveInd(String investorLeadsId);
	 
	 @Query("select a from InvestorLeadsCallLink a where a.investorLeadsId=:investorLeadsId and a.callId=:callId and a.liveInd=true")
	 InvestorLeadsCallLink getByInvestorLeadsIdAndCallIdAndLiveInd(@Param("investorLeadsId") String investorLeadsId, @Param("callId") String callId);
   
}
