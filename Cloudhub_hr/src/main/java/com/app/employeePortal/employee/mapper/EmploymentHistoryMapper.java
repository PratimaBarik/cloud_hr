package com.app.employeePortal.employee.mapper;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EmploymentHistoryMapper {

	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("designation")
	private String designation;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("salary")
	private double salary;
	
	@JsonProperty("salaryType")
	private String salaryType;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("documentId")
	private String documentId;

	public String getEmployeeId() {
		return employeeId;
	}
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;

	@JsonProperty("designationType")
	private String designationType;
	
	@JsonProperty("documentType")
	private String documentType;
}
