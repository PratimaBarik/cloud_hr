package com.app.employeePortal.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazorPayDTO {
	
	private String orderId ;
	
	private String razorpayOrderId;
	
	private String razorpaySignature;
	
	private String currency;
	
	private String razorpayPaymentId;
	
	private String razorPayAmount;
	
	
}
