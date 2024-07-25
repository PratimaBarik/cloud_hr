package com.app.employeePortal.organization.mapper;



import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class OrganizationCurrencyLinkMapper {
	
	@JsonProperty("orgCurrencyLinkId")
	private String orgCurrencyLinkId;
	
	@JsonProperty("currencyId")
	private String currencyId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("pairCurrencyId")
	private String pairCurrencyId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("live_ind")
	private boolean liveInd;
	
}
