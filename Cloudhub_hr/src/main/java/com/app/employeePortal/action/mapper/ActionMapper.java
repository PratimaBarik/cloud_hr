package com.app.employeePortal.action.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActionMapper {

	
	@JsonProperty("actionId")
	private String actionId;

	@JsonProperty("actionName")
	private String actionName;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	
	
}
