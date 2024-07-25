package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class ThirdPartyMapper {
	
	@JsonProperty("thirdPartyId")
	private String thirdPartyId;

	@JsonProperty("customerContactInd")
	private boolean customerContactInd;

	@JsonProperty("partnerContactInd")
	private boolean partnerContactInd;
	
	@JsonProperty("monitizeInd")
	private boolean monitizeInd;
	
	@JsonProperty("customerAiInd")
	private boolean customerAiInd;
	
	@JsonProperty("partnerAiInd")
	private boolean partnerAiInd;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("allowPrfWithVendorInd")
	private boolean allowPrfWithVendorInd;

	@JsonProperty("enableHiringTeamInd")
	private boolean enableHiringTeamInd;
	
}

