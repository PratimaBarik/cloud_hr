package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorContactLink;

public interface InvestorContactLinkRepo extends JpaRepository<InvestorContactLink,String> {
    @Query(value = "select a  from InvestorContactLink a  where a.investorId=:investorId" )
    List<InvestorContactLink> getContactIdByInvestorId(@Param("investorId") String investorId);
}
