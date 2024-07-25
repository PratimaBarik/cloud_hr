package com.app.employeePortal.attendance.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OnTravelMapper {
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("noOfDays")
	private int noOfDays;
	
}
