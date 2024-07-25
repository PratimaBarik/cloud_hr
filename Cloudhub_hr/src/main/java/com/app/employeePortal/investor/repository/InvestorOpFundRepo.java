package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.investor.entity.FundContactLink;
@Repository
public interface InvestorOpFundRepo extends JpaRepository<FundContactLink, String>{

	List<FundContactLink> findByContactIdInAndInvOpportunityId(List<String> contactIds, String invOpportunityId);

	FundContactLink findByContactIdAndInvOpportunityId(String contactId, String invOpportunityId);

	List<FundContactLink> findByInvOpportunityId(String invOpportunityId); 
}
