package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentStageApproveMapper {
	
	@JsonProperty("level1")
	private String level1;
	
	@JsonProperty("level2")
	private String level2;
	
	@JsonProperty("threshold")
	private String threshold;
	
	@JsonProperty("approvalType")
	private String approvalType;
	
	@JsonProperty("stageId")
	private String stageId;
	
	@JsonProperty("functionTypeId")
	private String functionTypeId;
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;
	
	@JsonProperty("CreationDate")
	private String CreationDate;
	
	@JsonProperty("approvalIndicator")
	private boolean approvalIndicator;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("jobLevel")
	private int jobLevel;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("processId")
	private String processId;
	
	@JsonProperty("designationName")
	private String designationName;
	
	@JsonProperty("functionName")
	private String functionName;

	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("taskTypeId")
	private String taskTypeId;

	@JsonProperty("taskName")
	private String taskName;
	
	@JsonProperty("taskStatus")
	private String taskStatus;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("unit")
	private String unit;
	
	@JsonProperty("executorLevelType")
	private String executorLevelType;
	
	@JsonProperty("unitValue")
	private String unitValue;
	
	@JsonProperty("executorFunctionId")
	private String executorFunctionId;
	
	@JsonProperty("executorFunctionName")
	private String executorFunctionName;
	
	@JsonProperty("ApprovalInd")
	private boolean ApprovalInd;
	
	@JsonProperty("approvalLevel")
	private String approvalLevel;
	
	@JsonProperty("approvalUnit")
	private String approvalUnit;
	
	@JsonProperty("approvalUnitValue")
	private String approvalUnitValue;
	
	@JsonProperty("approverLevelType")
	private String approverLevelType;
	
	@JsonProperty("approverFunctionId")
	private String approverFunctionId;
	
	@JsonProperty("ApproverFunctionName")
	private String ApproverFunctionName;
	
	@JsonProperty("approvalDepartment")
	private String approvalDepartment;
	
	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("taskType")
	private String taskType;
	
	@JsonProperty("ownerDepartmentName")
	private String ownerDepartmentName;
}
