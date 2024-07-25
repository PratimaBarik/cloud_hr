package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionMapper {
	
	@JsonProperty("functionTypeId")
	private String functionTypeId;

	@JsonProperty("functionType")
	private String functionType;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

}
