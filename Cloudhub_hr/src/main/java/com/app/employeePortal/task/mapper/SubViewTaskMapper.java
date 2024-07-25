package com.app.employeePortal.task.mapper;

import java.util.List;

import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubViewTaskMapper {
	
	@JsonProperty("taskChecklistStagelinkId")
	private String taskChecklistStagelinkId;
	
	@JsonProperty("taskChecklistStageName")
	private String taskChecklistStageName;
	
	@JsonProperty("taskChecklistId")
	private String taskChecklistId;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("days")
	private int days;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("included")
	private List<EmployeeViewMapper> included;
}
