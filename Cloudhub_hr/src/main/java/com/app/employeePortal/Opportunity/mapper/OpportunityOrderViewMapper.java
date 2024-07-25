package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityOrderViewMapper {
	
	@JsonProperty("opportunityOrderLinkId")
	private String opportunityOrderLinkId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("orderId")
	private String orderId;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
}
