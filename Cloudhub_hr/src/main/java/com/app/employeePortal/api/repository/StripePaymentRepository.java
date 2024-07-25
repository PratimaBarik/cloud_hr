
package com.app.employeePortal.api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.api.entity.CurrencyExchange;
import com.app.employeePortal.api.entity.Payment;

@Repository
public interface StripePaymentRepository extends JpaRepository<Payment, String>{

	Payment findByPaymentId(String paymentId);
}

 