package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPasswordRq {

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("organizationId")
	private String organizationId;


	//@NotEmpty
	//@Email
	//@Email(message="please provide registered email address")
	@JsonProperty("emailId")
	private String emailId;

	//@NotEmpty(message="password must be provided")
	@JsonProperty("password")
	private String password;

	

	
	
}
