package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavRequestMapper {
	
	@JsonProperty("navId")
	private String navId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;

	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("navName")
	private String navName;
		
	@JsonProperty("creationDate")
	private boolean liveInd;
}
