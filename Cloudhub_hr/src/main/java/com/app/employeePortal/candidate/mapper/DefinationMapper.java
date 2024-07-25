package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefinationMapper {
	
	@JsonProperty("definationId")
	private String definationId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedName")
	private String updatedName;
}
