package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) 
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"recruitmentProcessId","recruitmentProcessName","creationDate", "userId", "organizationId"})

public class RecruitmentProcessMapper {

	
	@JsonProperty("recruitmentProcessId")
	private String recruitmentProcessId;
	
	@JsonProperty("recruitmentProcessName")
	private String recruitmentProcessName;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;   

	
	@JsonProperty("recruiterName")
	private String recruiterName;
	
	@JsonProperty("stageId")
	private String stageId;

	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("publishInd")
	private boolean publishInd;	
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
}
