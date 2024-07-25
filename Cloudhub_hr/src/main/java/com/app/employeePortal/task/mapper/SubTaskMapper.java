package com.app.employeePortal.task.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTaskMapper {
	
	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("taskChecklistStagelinkId")
	private String taskChecklistStagelinkId;
	 
	@JsonProperty("included")
	List<String> included;
}
