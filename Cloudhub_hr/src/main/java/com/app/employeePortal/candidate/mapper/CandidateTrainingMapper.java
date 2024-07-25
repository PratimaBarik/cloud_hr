package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CandidateTrainingMapper {
	
	@JsonProperty("candidateTrainingId")
 	private String candidateTrainingId;
	
	@JsonProperty("candidateId")
 	private String candidateId;
	
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

	
}