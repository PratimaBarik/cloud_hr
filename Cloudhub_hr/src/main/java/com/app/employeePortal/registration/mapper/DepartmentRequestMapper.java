package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequestMapper {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("value")
	private boolean value;
}
