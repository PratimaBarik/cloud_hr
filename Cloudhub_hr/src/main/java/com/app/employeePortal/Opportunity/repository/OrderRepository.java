package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.Opportunity.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findByOpportunityId(String opportunityId);
	
}
