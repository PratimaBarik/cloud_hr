package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerInvoice;


@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, String>{

	List<CustomerInvoice> findByCustomerId(String customerId);

	
	
}
