package com.app.employeePortal.investorleads.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InvestorLeadsSkillLinkMapper {
	
	
	@JsonProperty("investorLeadsSkillLinkId")
	private String investorLeadsSkillLinkId;
	
	@JsonProperty("skillName")
	private String skillName;

	@JsonProperty("investorLeadsId")
	private String investorLeadsId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;	

}
