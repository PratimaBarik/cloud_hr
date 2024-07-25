package com.app.employeePortal.Workflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StagesTaskResponseMapper {
	
	@JsonProperty("stagesTaskId")
	private String stagesTaskId;
	
	@JsonProperty("stageTaskName")
	private String stageTaskName;
	
	@JsonProperty("mandatoryInd")
	private boolean mandatoryInd;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("department")
	private String department;
	
	@JsonProperty("stagesId")
	private String stagesId;
	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;

	@JsonProperty("global_ind")
	private boolean globalInd;
}
