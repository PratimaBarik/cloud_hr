package com.app.employeePortal.candidate.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateTreeMapper {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("children")
	private List<EducationDetailsTreeMapper> children;
	
}
