package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CandidateEmploymentHistoryMapper {
	
	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("candidateId")
 	private String candidateId;
	
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

	@JsonProperty("documentTypeId")
	private String documentTypeId;
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;

	@JsonProperty("designationType")
	private String designationType;
	
	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("departmentName")
	private String departmentName;
}
