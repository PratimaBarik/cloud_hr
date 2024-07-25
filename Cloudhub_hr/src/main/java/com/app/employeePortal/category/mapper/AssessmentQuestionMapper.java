package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AssessmentQuestionMapper {
	
	@JsonProperty("assessmentQstnId")
	private String assessmentQstnId;
	
	@JsonProperty("question")
	private String question;
	
	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
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

}
