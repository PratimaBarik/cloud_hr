package com.app.employeePortal.candidate.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CandidateEmailRequestMapper {

	@JsonProperty("candidateIds")
	private List<String> candidateIds;
	
	@JsonProperty("nameInd")
	private boolean nameInd;
	
	@JsonProperty("roleInd")
	private boolean roleInd;
	
	@JsonProperty("mobileNoInd")
	private boolean mobileNoInd;
	
	@JsonProperty("skillInd")
	private boolean skillInd;
	
	@JsonProperty("categoryInd")
	private boolean categoryInd;
	
	@JsonProperty("billingInd")
	private boolean billingInd;
	
	@JsonProperty("availableDateInd")
	private boolean availableDateInd;
	
	@JsonProperty("emailInd")
	private boolean emailInd;
	
	@JsonProperty("experienceInd")
	private boolean experienceInd;
	
	@JsonProperty("identityCardInd")
	private boolean identityCardInd;
	
	@JsonProperty("skillWiseExperienceInd")
	private boolean skillWiseExperienceInd;
	
	@JsonProperty("resumeInd")
	private boolean resumeInd;
}
