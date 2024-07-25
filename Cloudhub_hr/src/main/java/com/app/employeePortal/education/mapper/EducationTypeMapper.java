package com.app.employeePortal.education.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationTypeMapper {

	@JsonProperty("educationTypeId")
	private String educationTypeId;
	
	@JsonProperty("educationType")
	private String educationType;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
