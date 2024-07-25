package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class EmailValidationMapper {

	@JsonProperty("empId")
	private String empId;
	
	//@NotEmpty
	//@Email(message="please provide valid email address")
	@JsonProperty("emailId")
	private String emailId;
	
	//@NotEmpty(message="token has been expired")
	@JsonProperty("token")
	private String token;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("ipAddress")
	private String ipAddress;

	

	
}
