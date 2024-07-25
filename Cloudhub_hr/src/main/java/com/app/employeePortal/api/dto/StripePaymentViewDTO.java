package com.app.employeePortal.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripePaymentViewDTO {

	private Long cartId;
	private String stripePaymentId;
	private String status;
	private String clientSecret;

//	private StoreCartResponse storecartResponse;

//	private StoreCart storeCart;

	private String currency;
	private BigDecimal amount;

	private String paymentId;

	private String message;

	private String name;

}
