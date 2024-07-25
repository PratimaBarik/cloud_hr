package com.app.employeePortal.Workflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StagesTaskRequestMapper {
	
	@JsonProperty("stageTaskName")
	private String stageTaskName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("mandatoryInd")
	private boolean mandatoryInd;
	
	@JsonProperty("stageId")
	private String stageId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
}
