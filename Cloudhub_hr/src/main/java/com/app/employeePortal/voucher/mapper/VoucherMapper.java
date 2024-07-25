package com.app.employeePortal.voucher.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class VoucherMapper {

	@JsonProperty("voucherId")
 	private String voucherId;
	
	@JsonProperty("userId")
 	private String userId;
	
	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("voucherDate")
 	private String voucherDate;
	
	@JsonProperty("voucherType")
 	private String voucherType;
	
	@JsonProperty("currency")
 	private String currency;
	
	@JsonProperty("amount")
 	private double amount;

	@JsonProperty("status")
 	private String status;
	
	@JsonProperty("submittedBy")
 	private String submittedBy;
	
	@JsonProperty("rejectReason")
	private String rejectReason;

	@JsonProperty("voucherName")
	private String voucherName;
	
	@JsonProperty("totalAmount")
	private String totalAmount;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
}
