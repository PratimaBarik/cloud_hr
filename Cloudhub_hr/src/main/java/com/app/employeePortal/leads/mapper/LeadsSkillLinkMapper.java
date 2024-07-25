package com.app.employeePortal.leads.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LeadsSkillLinkMapper {
	
	
	@JsonProperty("leadsSkillLinkId")
	private String leadsSkillLinkId;
	
	@JsonProperty("skillName")
	private String skillName;

	@JsonProperty("leadsId")
	private String leadsId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;	

}
