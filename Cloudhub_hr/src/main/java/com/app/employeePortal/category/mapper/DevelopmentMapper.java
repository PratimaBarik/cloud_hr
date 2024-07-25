package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DevelopmentMapper {

	@JsonProperty("developmentId")
	private String developmentId;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("liveInd")
	private boolean liveInd;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("updationDate")
	private String updationDate;

	@JsonProperty("updatedBy")
	private String updatedBy;

	@JsonProperty("value")
	private double value;
	
	@JsonProperty("roletypeId")
	private String roletypeId;
	
	@JsonProperty("roletype")
	private String roletype;

	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("department")
	private String department;

	@JsonProperty("taskTypeId")
	private String taskTypeId;
	
	@JsonProperty("taskType")
	private String taskType;

	@JsonProperty("developmentType")
	private String developmentType;

}
