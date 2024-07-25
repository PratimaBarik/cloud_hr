package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityDropdownMapper {
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
}
