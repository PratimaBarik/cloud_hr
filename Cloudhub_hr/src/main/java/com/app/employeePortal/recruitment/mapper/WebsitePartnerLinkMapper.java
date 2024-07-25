package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WebsitePartnerLinkMapper {

	@JsonProperty("websitePartnerLinkId")
	private String websitePartnerLinkId;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("ip")
	private String ip;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("assignToUserId")
	private String assignToUserId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;
	
}
