package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.PaymentMapper;

public interface PaymentService {

	public PaymentMapper savePayment(PaymentMapper paymentMapper);

	public PaymentMapper getPaymentCatagoryById(String paymentId);

	public List<PaymentMapper> getPaymentCategoryByOrgId(String orgId);

	public PaymentMapper updatePaymentCategory(String paymentCatagoryId, PaymentMapper paymentMapper);

	public void deletePaymentCatagory(String paymentCatagoryId, String userId);

//	public List<PaymentMapper> getPaymentByName(String name);

	public HashMap getPaymentCategoryCountByOrgId(String orgId);

	public ByteArrayInputStream exportPaymentListToExcel(List<PaymentMapper> list);

	public boolean checkNameInPaymentByOrgLevel(String name, String orgId);

	public List<PaymentMapper> getPaymentByNameByOrgLevel(String name, String orgId);

}
