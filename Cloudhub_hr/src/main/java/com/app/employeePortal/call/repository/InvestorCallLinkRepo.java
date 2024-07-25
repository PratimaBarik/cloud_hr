package com.app.employeePortal.call.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.call.entity.InvestorCallLink;

public interface InvestorCallLinkRepo extends JpaRepository<InvestorCallLink,String> {
	 
	 @Query("select a from InvestorCallLink a where a.investorId=:investorId and a.liveInd=true")
		List<InvestorCallLink> getCallListByInvestorIdAndLiveInd(@Param(value="investorId")String investorId);
	 
	 @Query("select a from InvestorCallLink a where a.investorId=:investorId and a.callId=:callId and a.liveInd=true")
	 InvestorCallLink getByInvestorIdAndCallIdAndLiveInd(@Param("investorId") String investorId, @Param("callId") String callId);
   
}
