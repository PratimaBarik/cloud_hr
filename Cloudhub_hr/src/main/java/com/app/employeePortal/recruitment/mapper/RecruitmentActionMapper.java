package com.app.employeePortal.recruitment.mapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentActionMapper {
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("profileId")
	private String profileId;
	
	@JsonProperty("stageId")
	private String stageId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("jobOrder")
	private String jobOrder;
	
	@JsonProperty("recruitmentId")
	private String recruitmentId;
	
	@JsonProperty("actionType")
	private String actionType;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("recruitOwner")
	private String recruitOwner;
}
