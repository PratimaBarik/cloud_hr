package com.app.employeePortal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.SubsriptionPrice;

@Repository
public interface SubscriptionPriceRepository extends JpaRepository<SubsriptionPrice, String> {

	SubsriptionPrice findBySubscriptionType(int subscriptionType);
}
