package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class EmailMapper {

	@JsonProperty("emailId")
	private String emailId;
	
}
