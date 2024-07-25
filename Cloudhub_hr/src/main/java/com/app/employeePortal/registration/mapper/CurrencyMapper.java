package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyMapper {
	
	@JsonProperty("currencyId")
	private String currency_id;

	@JsonProperty("currencyName")
	private String currency_name;

	@JsonProperty("currencyCode")
	private String currency_code;
	
	@JsonProperty("mandatoryInd")
	private boolean mandatoryInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("salesInd")
	private boolean salesInd;
	
	@JsonProperty("investorInd")
	private boolean investorInd;
}
