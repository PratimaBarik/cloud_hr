package com.app.employeePortal.investorleads.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvestorLeadsOpportunityMapper {
	
	
	@JsonProperty("investorLeadOppId")
	private String investorLeadOppId;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
	@JsonProperty("proposalAmount")
	private String proposalAmount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("contactName")
	private String contactName;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("investorLeadsName")
	private String investorLeadsName;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("assignedToName")
	private String assignedToName;
	
	@JsonProperty("owner")
	private String owner;
	
	@JsonProperty("description")
	private String description;

	@JsonProperty("oppWorkflow")
	private String oppWorkflow;
	
	@JsonProperty("oppStage")
	private String oppStage;
	
	@JsonProperty("investorLeadsId")
	private String investorLeadsId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	
	}
