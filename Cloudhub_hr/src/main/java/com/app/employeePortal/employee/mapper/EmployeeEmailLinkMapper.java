package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class EmployeeEmailLinkMapper {

	@JsonProperty("employeeEmailLinkId")
	private String employeeEmailLinkId;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("secondaryEmailId")
	private String secondaryEmailId;
	
	@JsonProperty("primaryEmailId")
	private String primaryEmailId;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
}
