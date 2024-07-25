package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UserPlannerMapper {
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("startTime")
	private long startTime;
	
	@JsonProperty("endTime")
	private long endTime;
	
}
