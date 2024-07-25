package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LeadsCategoryMapper {
	
	@JsonProperty("leadsCatagoryId")
	private String leadsCatagoryId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
		
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("updationDate")
	private String updationDate;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("hot")
	private int hot;
	
	@JsonProperty("cold")
	private int cold;
	
	@JsonProperty("worm")
	private int worm;
	
	@JsonProperty("notDefined")
	private int notDefined;

}
