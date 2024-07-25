package com.app.employeePortal.features.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeaturesMapper {

	@JsonProperty("advanceFeatureId")
	private String advanceFeatureId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("creationDate")
	private String creationDate;		
	
	@JsonProperty("inactiveDate")
	private String inactiveDate;
	
	
	@JsonProperty("website")
	private String website;
}
