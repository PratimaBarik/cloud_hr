package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainingTreeMapper {
	
	@JsonProperty("name")
 	private String name;

}
