package com.app.employeePortal.expense.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExpenseMapper {

	@JsonProperty("expenseId")
 	private String expenseId;
	
//	@JsonProperty("billType")
// 	private String billType;
	
	@JsonProperty("expenseDate")
 	private String expenseDate;
	
	@JsonProperty("projectName")
 	private String projectName;
	
	@JsonProperty("clientName")
 	private String clientName;
	
	@JsonProperty("particular")
 	private String particular;
	
	@JsonProperty("userId")
 	private String userId;
	
	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("currency")
 	private String currency;
	
	@JsonProperty("amount")
 	private double amount;
	
	@JsonProperty("adjustedAmount")
 	private double adjustedAmount;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("expenseTypeId")
	private String expenseTypeId;
	
	@JsonProperty("expenseType")
	private String expenseType;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("voucherId")
	private String voucherId;
	
	@JsonProperty("voucherName")
	private String voucherName;
	
	@JsonProperty("moreInfo")
	private String moreInfo;
}
