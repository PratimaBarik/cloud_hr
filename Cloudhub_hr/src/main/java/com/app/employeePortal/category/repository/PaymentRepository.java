package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.PaymentCategory;
@Repository
public interface PaymentRepository extends JpaRepository<PaymentCategory, String>{

	public List<PaymentCategory> findByOrganizationIdAndLiveInd(String orgId, boolean b);

	public PaymentCategory findByPaymentCatagoryId(String paymentCatagoryId);

//	public List<PaymentCategory> findByNameContainingAndLiveInd(String name, boolean b);

	public List<PaymentCategory> findByNameAndOrganizationIdAndLiveInd(String name, String orgId, boolean b);

	public List<PaymentCategory> findByNameContainingAndLiveIndAndOrganizationId(String name, boolean b, String orgId);

	
	
}
