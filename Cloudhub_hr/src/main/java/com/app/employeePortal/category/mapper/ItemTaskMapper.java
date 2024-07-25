package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ItemTaskMapper {
	
	@JsonProperty("itemTaskId")
	private String itemTaskId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
}
