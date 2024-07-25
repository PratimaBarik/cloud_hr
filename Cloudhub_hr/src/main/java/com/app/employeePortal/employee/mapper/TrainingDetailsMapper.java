package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TrainingDetailsMapper {

	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("courseName")
	private String courseName;
	
	@JsonProperty("grade")
	private String grade;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("organization")
	private String organization;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("documentId")
	private String documentId;

	@JsonProperty("documentTypeName")
	private String documentTypeName;
	
	@JsonProperty("documentTypeId")
	private String documentTypeId;
	
}
