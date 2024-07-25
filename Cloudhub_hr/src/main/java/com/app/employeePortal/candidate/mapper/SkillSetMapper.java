package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SkillSetMapper {
	
	@JsonProperty("skillSetDetailsId")
	private String skillSetDetailsId;
	
	@JsonProperty("skillName")
	private String skillName;
	
	@JsonProperty("experience")
	private float experience;


	@JsonProperty("candidateId")
	private String candidateId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("count")
	private int count;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("skillRole")
    private String skillRole;
	
	@JsonProperty("pauseInd")
	private boolean pauseInd;
	
	@JsonProperty("pauseDate")
	private String pauseDate;
	
	@JsonProperty("unpauseDate")
	private String unpauseDate;
	
	@JsonProperty("pauseExperience")
	private float pauseExperience;

}
