package com.app.employeePortal.mileage.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MileageRateMapper {

	
	@JsonProperty("mileageRate")
	private double mileageRate;
	
	@JsonProperty("country")
	private String country;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("creationDate")
	 private String creationDate;
		
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
	 
	
}
