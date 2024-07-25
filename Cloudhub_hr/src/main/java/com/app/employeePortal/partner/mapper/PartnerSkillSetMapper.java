package com.app.employeePortal.partner.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerSkillSetMapper {
	@JsonProperty("skillSetDetailsId")
	private String skillSetDetailsId;
	
	@JsonProperty("skillName")
	private String skillName;


	@JsonProperty("partnerId")
	private String partnerId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;	
	
}
