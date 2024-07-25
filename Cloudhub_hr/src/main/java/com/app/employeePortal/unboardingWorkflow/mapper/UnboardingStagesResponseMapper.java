package com.app.employeePortal.unboardingWorkflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UnboardingStagesResponseMapper {
	
	@JsonProperty("unboardingStagesId")
	private String unboardingStagesId;
	
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
	
	@JsonProperty("unboardingWorkflowDetailsId")
	private String unboardingWorkflowDetailsId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("onboardingEmpId")
	private String onboardingEmpId;
	
	@JsonProperty("onboardingEmpName")
	private String onboardingEmpName;
}
