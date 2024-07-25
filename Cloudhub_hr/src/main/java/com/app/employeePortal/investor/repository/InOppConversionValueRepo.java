package com.app.employeePortal.investor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.investor.entity.InOppConversionValue;

public interface InOppConversionValueRepo extends JpaRepository<InOppConversionValue,String> {

	InOppConversionValue findByInvestorId(String investorId);
}
