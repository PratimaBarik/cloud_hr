package com.app.employeePortal.customer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InitiativeSkillMapper {

	@JsonProperty("skilId")
	private String skilId;

	@JsonProperty("skillName")
	private String skillName;
	
	@JsonProperty("definationId")
	private String definationId;

	@JsonProperty("name")
	private String name;
	
}
