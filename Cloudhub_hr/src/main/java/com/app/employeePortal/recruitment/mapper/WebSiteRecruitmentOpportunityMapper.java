package com.app.employeePortal.recruitment.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class WebSiteRecruitmentOpportunityMapper {
	
	@JsonProperty("recruitmentProcessId")
	private String recruitmentProcessId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
		
	@JsonProperty("recruitmentId")
	private String recruitmentId;  

	@JsonProperty("candidateIds")
	private List<String> candidateIds;
	
	@JsonProperty("tagUserId")
	private String tagUserId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("intrestInd")
	private boolean intrestInd;
}
