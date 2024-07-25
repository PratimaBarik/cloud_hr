package com.app.employeePortal.Opportunity.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityOrderMapper {
	
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
	
	@JsonProperty("orderIds")
	private List<String> orderIds;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
}
