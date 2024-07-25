package com.app.employeePortal.Opportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.Opportunity.entity.QrCode;

public interface QrCodeRepository extends JpaRepository<QrCode, Long>{

	QrCode findByPhoneId(String id);

}
