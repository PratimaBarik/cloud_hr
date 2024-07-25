package com.app.employeePortal.investorleads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investorleads.entity.InvestorLeadsAddressLink;

@Repository
public interface InvestorLeadsAddressLinkRepository extends JpaRepository<InvestorLeadsAddressLink, String> {

	@Query(value = "select a  from InvestorLeadsAddressLink a  where a.investorLeadsId=:investorleadsId" )
	List<InvestorLeadsAddressLink> getAddressListByInvestorLeadsId(@Param(value="investorleadsId")String investorLeadsId);
	
}
