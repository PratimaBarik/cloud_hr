package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpportunitySkillLinkMapper {

    @JsonProperty("opportunitySkillLinkId")
    private String opportunitySkillLinkId;
    
    @JsonProperty("skill")
    private String skill;

    @JsonProperty("noOfPosition")
    private String noOfPosition;
    
    @JsonProperty("opportunityId")
    private String opportunityId;
    
    @JsonProperty("oppInnitiative")
    private String oppInnitiative;
    
    @JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("skillName")
	private String skillName;
 
}
