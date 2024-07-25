package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorDocumentLink;

public interface InvestorDocumentLinkRepo extends JpaRepository<InvestorDocumentLink,String> {
    @Query(value = "select a  from InvestorDocumentLink a  where a.investorId=:investorId" )
    List<InvestorDocumentLink> getDocumentByInvestorId(@Param("investorId") String investorId);
}
