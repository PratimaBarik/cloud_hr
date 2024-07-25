package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class EmployeeAdminUpdateRequestMapper {

	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("adminInd")
	private boolean adminInd;
	
}
