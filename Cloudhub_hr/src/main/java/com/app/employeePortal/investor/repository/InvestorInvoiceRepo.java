package com.app.employeePortal.investor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.investor.entity.InvestorInvoice;

public interface InvestorInvoiceRepo extends JpaRepository<InvestorInvoice,String> {

    List<InvestorInvoice> findByInvestorId(String investorId);
}
