package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.investor.entity.InvestorInvoiceAddressLink;

public interface InvestorInvoiceAddressLinkRepo extends JpaRepository<InvestorInvoiceAddressLink,String> {
    List<InvestorInvoiceAddressLink> findByInvestorId(String investorId);
}
