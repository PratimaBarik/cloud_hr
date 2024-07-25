package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.investor.entity.InvestorsShare;

public interface InvestorShareRepo extends JpaRepository<InvestorsShare,String> {

	List<InvestorsShare> findByInvestorIdAndLiveInd(String investorId , boolean b);
	
}
