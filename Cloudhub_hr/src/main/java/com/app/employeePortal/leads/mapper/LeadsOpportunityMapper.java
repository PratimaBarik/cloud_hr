package com.app.employeePortal.leads.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LeadsOpportunityMapper {
	
	
	@JsonProperty("leadOppId")
	private String leadOppId;
	
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
	
	@JsonProperty("leadsName")
	private String leadsName;
	
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
	
	@JsonProperty("leadsId")
	private String leadsId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	
	}
