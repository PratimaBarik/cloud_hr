package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvanceRecruitRuleMapper {
	
	@JsonProperty("avilableDate")
	private double avilableDate;
	
	@JsonProperty("billing")
	private double billing;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;

	
	
	

}
