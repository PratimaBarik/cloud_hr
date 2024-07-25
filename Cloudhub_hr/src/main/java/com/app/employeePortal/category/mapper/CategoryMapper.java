package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CategoryMapper {
	
	@JsonProperty("categoryId")
	private String categoryId;
	
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

	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("name")
	private String name;

}
