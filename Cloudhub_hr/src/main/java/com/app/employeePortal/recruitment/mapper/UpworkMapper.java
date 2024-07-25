package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpworkMapper {

	@JsonProperty("upworkId")
	private String upworkId;
	
	@JsonProperty("upworkInd")
	private boolean upworkInd;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
}
