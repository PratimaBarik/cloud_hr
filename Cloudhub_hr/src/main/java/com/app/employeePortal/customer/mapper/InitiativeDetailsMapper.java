package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InitiativeDetailsMapper {
	
	@JsonProperty("initiativeDetailsId")
	private String initiativeDetailsId;
	
	@JsonProperty("initiativeName")
	private String initiativeName;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("skilId")
	private String skilId;

	@JsonProperty("skillName")
	private String skillName;
	
	@JsonProperty("skillList")
	private List<String> skillList;

	@JsonProperty("userId")
    private String userId;
	
	@JsonProperty("orgId")
    private String orgId;
	
	@JsonProperty("liveInd")
    private boolean liveind;
	
	@JsonProperty("initiativeSkillMapper")
	private List<InitiativeSkillMapper> initiativeSkillMapper;

	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("description")
	private String description;
	
}
