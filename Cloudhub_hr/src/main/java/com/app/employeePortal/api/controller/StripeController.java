package com.app.employeePortal.api.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.api.dto.PaymentDTO;
import com.app.employeePortal.api.dto.StripePaymentViewDTO;
import com.app.employeePortal.api.service.StripeService;

@RestController
@CrossOrigin(maxAge = 3600)
public class StripeController {
	@Autowired
	StripeService stripeService;

	@PostMapping(value = "/api/v1/stripe/makePayment")
	public ResponseEntity<?> StripePayment(@RequestBody PaymentDTO paymentDTO) throws JSONException {
		return ResponseEntity.ok(stripeService.connectToStripe(paymentDTO));
	}

	@PostMapping(value = "/api/v1/stripe/makepaymentNewCard")
	public ResponseEntity<?> ConfirmStripePayment(@RequestBody PaymentDTO paymentDTO) {

		return ResponseEntity.ok(stripeService.confirmPaymentFromStripe(paymentDTO));
	}

	@PostMapping(value = "/api/v1/stripe/confirmPayment")
	public ResponseEntity<?> ConfirmPayment(@RequestBody PaymentDTO paymentDTO) {
		return ResponseEntity.ok(stripeService.confirmPayment(paymentDTO));
	}
}
