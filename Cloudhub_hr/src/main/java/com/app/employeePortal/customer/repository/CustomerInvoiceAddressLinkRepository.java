package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerInvoiceAddressLink;


@Repository
public interface CustomerInvoiceAddressLinkRepository extends JpaRepository<CustomerInvoiceAddressLink, String>{

	List<CustomerInvoiceAddressLink> findByCustomerId(String customerId);

	
	
	
}
