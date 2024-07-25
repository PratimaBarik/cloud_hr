package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpportunityForecastLinkMapper {

    @JsonProperty("opportunityForecastLinkId")
    private String opportunityForecastLinkId;
    
    @JsonProperty("skill")
    private String skill;
    
    @JsonProperty("opportunityId")
    private String opportunityId;
    
    @JsonProperty("noOfPosition")
    private String noOfPosition;
    
    @JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
    private String creationDate;

	@JsonProperty("skillName")
	private String skillName;

	@JsonProperty("month")
	private String month;
	
	@JsonProperty("year")
	private String year;
}
