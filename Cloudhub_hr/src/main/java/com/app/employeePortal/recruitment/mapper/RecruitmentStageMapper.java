package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "recruitmentProcessId", "stageId", "stageName", "days", "probability", "creationDate", "userId",
		"organizationId" })
public class RecruitmentStageMapper {

	@JsonProperty("recruitmentProcessId")
	private String recruitmentProcessId;

	@JsonProperty("stageId")
	private String stageId;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("stageName")
	private String stageName;

	@JsonProperty("days")
	private int days;

	@JsonProperty("probability")
	private double probability;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("publishInd")
	private boolean publishInd;

	@JsonProperty("candidateNo")
	private int candidateNo;

	@JsonProperty("responsible")
	private String responsible;

	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
