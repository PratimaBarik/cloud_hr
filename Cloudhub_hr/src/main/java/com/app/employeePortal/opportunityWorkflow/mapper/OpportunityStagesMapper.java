package com.app.employeePortal.opportunityWorkflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityStagesMapper {
	
	@JsonProperty("opportunityStagesId")
	private String opportunityStagesId;
	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("days")
	private int days;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("publishInd")
	private boolean publishInd;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
//	@JsonProperty("responsible")
//	private String responsible;
	
	@JsonProperty("opportunityWorkflowDetailsId")
	private String opportunityWorkflowDetailsId;
	
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
