package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CandidateEducationDetailsMapper {
	
	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("candidateId")
 	private String candidateId;
	
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

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("documentTypeId")
	private String documentTypeId;
	
	@JsonProperty("educationTypeId")
	private String educationTypeId;
	
	@JsonProperty("documentTypeName")
	private String documentTypeName;

}
