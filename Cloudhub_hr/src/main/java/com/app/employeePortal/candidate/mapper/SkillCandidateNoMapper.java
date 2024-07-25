package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillCandidateNoMapper {

	@JsonProperty("skillCandidateNoId")
	private String skillCandidateNoId;
	
	@JsonProperty("skill")
	private String skill;
	
	@JsonProperty("number")
	private int number;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	

	
}
