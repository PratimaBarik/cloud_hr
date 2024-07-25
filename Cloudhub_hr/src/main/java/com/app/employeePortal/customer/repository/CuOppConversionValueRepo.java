package com.app.employeePortal.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.customer.entity.CuOppConversionValue;

public interface CuOppConversionValueRepo extends JpaRepository<CuOppConversionValue,String> {

	CuOppConversionValue findByCustomerId(String customerId);
}
