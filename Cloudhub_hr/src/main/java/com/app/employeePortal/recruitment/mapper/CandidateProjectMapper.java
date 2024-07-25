package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CandidateProjectMapper {
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("candidateName")
	private String candidateName;
	
	@JsonProperty("onboardDate")
	private String onboardDate;

	@JsonProperty("actualEndDate")
	private String actualEndDate;
	
	@JsonProperty("billableHour")
	private float billableHour;

	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("projectId")
	private String projectId;
}
