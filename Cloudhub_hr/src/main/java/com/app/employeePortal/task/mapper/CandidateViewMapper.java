package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateViewMapper {
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("candidateName")
	private String candidateName;
}
