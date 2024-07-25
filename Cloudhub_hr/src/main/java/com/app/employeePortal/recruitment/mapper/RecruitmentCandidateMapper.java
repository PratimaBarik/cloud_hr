package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentCandidateMapper {
	
	@JsonProperty("profileId")
	private String profileId;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("recruitmentId")
	private String recruitmentId;

	

}
