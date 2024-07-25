package com.app.employeePortal.api.service;

import java.util.Date;

import org.json.JSONException;

import com.app.employeePortal.api.dto.PaymentDTO;
import com.app.employeePortal.api.dto.StripePaymentViewDTO;

public interface StripeService {

	StripePaymentViewDTO connectToStripe(PaymentDTO paymentDTO) throws JSONException;

	StripePaymentViewDTO confirmPaymentFromStripe(PaymentDTO paymentDTO);

	StripePaymentViewDTO confirmPayment(PaymentDTO paymentDTO);
}
