package com.app.employeePortal.organization.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmailCredentialsMapper {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	
	@JsonProperty("email")
	private String email;
	
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("host")
	private String host;
	
	@JsonProperty("port")
	private int port;

	@JsonProperty("defaultInd")
	private boolean defaultInd;
	

}
