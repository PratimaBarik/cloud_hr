package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AssessmentMapper {
	
	@JsonProperty("assessmentDetailsId")
	private String assessmentDetailsId;
	
	@JsonProperty("assessmentInd")
	private boolean assessmentInd;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;

}
