package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeWorkflowAndStageResponseMapper {
	
//	@JsonProperty("stageList")
//	private List<UnboardingStagesResponseMapper> StageList;
	
	@JsonProperty("unboardingWorkflowDetailsName")
	private String unboardingWorkflowDetailsName;
	
	@JsonProperty("unboardingWorkflowDetailsId")
	private String unboardingWorkflowDetailsId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("unboardingStagesId")
	private String unboardingStagesId;
	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("days")
	private int days;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("publishInd")
	private boolean publishInd;
	
	@JsonProperty("onboardingEmpId")
	private String onboardingEmpId;
	
	@JsonProperty("onboardingEmpName")
	private String onboardingEmpName;


}
