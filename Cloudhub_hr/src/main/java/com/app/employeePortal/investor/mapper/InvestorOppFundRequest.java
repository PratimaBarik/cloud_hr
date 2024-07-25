package com.app.employeePortal.investor.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvestorOppFundRequest {

	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("invOpportunityId")
	private String invOpportunityId;
	
	@JsonProperty("borrowDate")
	private String borrowDate;
	
	@JsonProperty("repayMonth")
	private String repayMonth;
	
	@JsonProperty("borrowInd")
	private boolean borrowInd;
	
	@JsonProperty("amount")
	private float amount;
	
	@JsonProperty("interest")
	private float interest;
	
	@JsonProperty("currency")
	private String currency;
}
