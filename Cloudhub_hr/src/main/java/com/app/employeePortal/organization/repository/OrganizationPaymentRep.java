package com.app.employeePortal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.organization.entity.OrganizationPayments;

public interface OrganizationPaymentRep extends JpaRepository<OrganizationPayments,String> {

	OrganizationPayments findByOrgIdAndPaymentGateway(String organizationId, String paymentGatway);
}
