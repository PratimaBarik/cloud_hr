package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmployeeIDMapper {
	

	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("employeeId")
 	private String employeeId;
 	
	@JsonProperty("idType")
 	private String idType;
	
	@JsonProperty("idNo")
 	private String idNo;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("documentName")
	private String documentName;
	
	@JsonProperty("description")
	private String description;

	@JsonProperty("documentType")
	private String documentType;
	
}
