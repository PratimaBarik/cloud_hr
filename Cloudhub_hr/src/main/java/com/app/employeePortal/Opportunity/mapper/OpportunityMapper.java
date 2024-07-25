package com.app.employeePortal.Opportunity.mapper;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityMapper {
	
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
	@JsonProperty("proposalAmount")
	private String proposalAmount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("recruiterId")
	private List<String> recruiterId;
	 	
	@JsonProperty("recruiterName")
	private String recruiterName; 
	
	@JsonProperty("salesUserIds")
	//private List<String> salesUserIds;
	private String salesUserIds;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("opportunityIds")
	private List<String> opportunityIds;
	
	@JsonProperty("opportunitySkill")
    private List<OpportunitySkillLinkMapper> opportunitySkill;
	
	@JsonProperty("oppInnitiative")
    private String oppInnitiative;
	
	@JsonProperty("wonInd")
    private boolean wonInd;
	
	@JsonProperty("lostInd")
    private boolean lostInd;
	
	@JsonProperty("closeInd")
    private boolean closeInd;

	@JsonProperty("opportunityForecast")
    private List<OpportunityForecastLinkMapper> opportunityForecast;
	
	@JsonProperty("oppWorkflow")
	private String oppWorkflow;
	
	@JsonProperty("oppStage")
	private String oppStage;
	
	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("excelId")
	private String excelId;
	
	@JsonProperty("xlUpdateInd")
	private boolean xlUpdateInd;;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("included")
	private Set<String> included;
	
	}
