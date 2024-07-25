package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskTypeDropMapper {
	
	@JsonProperty("taskType")
	private String taskType;

	@JsonProperty("taskTypeId")
	private String taskTypeId;

	
}
