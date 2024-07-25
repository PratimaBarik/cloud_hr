package com.app.employeePortal.category.mapper;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class QualityMapper {
	
	@JsonProperty("qualityId")
	private String qualityId;
	
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
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("description")
	private String description;
}
