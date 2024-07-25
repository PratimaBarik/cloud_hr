package com.app.employeePortal.commercial.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCommissionMapper {
	
	
	@JsonProperty("customerCommissionId")
	private String customerCommissionId;

	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("requirementType")
	private String requirementType;
	
	@JsonProperty("commissionDeal")
	private String commissionDeal;
	
	@JsonProperty("paymentDate")
	private String paymentDate;
	
	@JsonProperty("commissionAmount")
	private String commissionAmount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;

	@JsonProperty("userId")
	private String userId;
		
	@JsonProperty("ownerName")
	private String ownerName;
}
