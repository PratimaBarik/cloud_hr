package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MachinaryMapper {
	
	@JsonProperty("machinaryId")
	private String machinaryId;

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

	@JsonProperty("description")
	private String description;

}
