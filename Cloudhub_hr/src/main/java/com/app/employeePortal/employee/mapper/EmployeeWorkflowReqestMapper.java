package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeWorkflowReqestMapper {

	@JsonProperty("unboardingWorkflowDetailsId")
	private String unboardingWorkflowDetailsId;
	
	@JsonProperty("unboardingStagesId")
	private String unboardingStagesId;

	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;

}
