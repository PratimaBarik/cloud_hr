package com.app.employeePortal.employee.mapper;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EducationalDetailsMapper {

	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("educationType")
	private String educationType;
	
	@JsonProperty("courseName")
	private String courseName;
	
	@JsonProperty("courseType")
	private String courseType;
	
	@JsonProperty("specialization")
	private String specialization;
	
	@JsonProperty("university")
	private String university;
	
	@JsonProperty("yearOfPassing")
	private int yearOfPassing;
	
	@JsonProperty("marksSecured")
	private double marksSecured;
	
	@JsonProperty("marksType")
	private String marksType;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("documentId")
	private String documentId;

	@JsonProperty("educationTypeId")
	private String educationTypeId;
	
	@JsonProperty("documentType")
	private String documentType;
	
	@JsonProperty("departmentName")
	private String departmentName;
	
}
