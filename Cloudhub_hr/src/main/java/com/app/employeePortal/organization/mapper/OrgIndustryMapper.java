package com.app.employeePortal.organization.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrgIndustryMapper {
	
	@JsonProperty("orgType")
	private String orgType;

}
