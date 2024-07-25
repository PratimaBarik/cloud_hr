package com.app.employeePortal.call.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class DonotCallMapper {

	@JsonProperty("callId")
	private String callId;
	
	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("startTime")
	private long startTime;

	@JsonProperty("endDate")
	private String endDate;

	@JsonProperty("endTime")
	private long endTime;
	
	@JsonProperty("creationDate")
	private String creationDate;
}
