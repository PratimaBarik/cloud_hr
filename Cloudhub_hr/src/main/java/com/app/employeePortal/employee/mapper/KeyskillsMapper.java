package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class KeyskillsMapper {
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("keySkillsId")
 	private String keySkillsId;
	
	@JsonProperty("keySkillsName")
 	private String keySkillsName;
	
	@JsonProperty("creationDate")
	private String creationDate;

	private String userId;

	private String organizationId;

	private float experience;
	
	 private String skillRole;
	 
	 private boolean pauseInd;
}
