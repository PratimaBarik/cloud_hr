package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRoleCountMapper {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("number")
	private int number;
	
}
