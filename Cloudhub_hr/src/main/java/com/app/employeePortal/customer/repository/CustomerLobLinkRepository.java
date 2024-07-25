package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerLobLink;

@Repository

public interface CustomerLobLinkRepository extends JpaRepository<CustomerLobLink, String> {

	CustomerLobLink findByLobDetailsIdAndCustomerId(String lobDetailsId, String customeId);

	List<CustomerLobLink> findByCustomerId(String customerId);
}