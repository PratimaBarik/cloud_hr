package com.app.employeePortal.organization.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrganizationValueMapper {
	
	@JsonProperty("orgValue")
	private double orgValue;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("orgName")
	private String orgName;
	
}
