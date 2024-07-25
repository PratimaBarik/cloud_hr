package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class EmployeeRoleLinkMapper {

	@JsonProperty("employeeRoleLinkId")
	private String employeeRoleLinkId;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("provideDate")
	private String provideDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
}
