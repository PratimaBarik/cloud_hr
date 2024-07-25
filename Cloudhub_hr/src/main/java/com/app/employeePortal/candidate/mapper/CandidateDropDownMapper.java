package com.app.employeePortal.candidate.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CandidateDropDownMapper {
	@JsonProperty("candidateId")
	private String candidateId;
	
	//@NotEmpty(message="first name should not empty")
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("liveInd")
    private boolean liveInd;
}
