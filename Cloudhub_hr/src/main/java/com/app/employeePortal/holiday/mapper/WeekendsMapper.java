package com.app.employeePortal.holiday.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeekendsMapper {
	
	
	@JsonProperty("weekendsId")
 	private String weekendsId;

	@JsonProperty("organizationId")
 	private String organizationId;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("userId")
 	private String userId;
	
	@JsonProperty("mondayInd")
	private boolean mondayInd;
	
	@JsonProperty("tuesdayInd")
	private boolean tuesdayInd;
	
	@JsonProperty("wednesdayInd")
	private boolean wednesdayInd;
	
	@JsonProperty("thursdayInd")
	private boolean thursdayInd;
	
	@JsonProperty("fridayInd")
	private boolean fridayInd;
	
	@JsonProperty("saturdayInd")
	private boolean saturdayInd;
	
	@JsonProperty("sundayInd")
	private boolean sundayInd;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
}
