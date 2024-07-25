package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RecruiterMapper {

	@JsonProperty("employeeId")
 	private String employeeId;
	
	 @JsonProperty("fullName")
	private String fullName;
	 
	 @JsonProperty("imageId")
	  private String imageId;
	 
}
