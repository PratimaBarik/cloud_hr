package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDropDownMapper {
	
	@JsonProperty("currencyId")
	private String currency_id;

	@JsonProperty("currencyName")
	private String currency_name;

	@JsonProperty("currencyCode")
	private String currency_code;
	
	@JsonProperty("mandatoryInd")
	private boolean mandatoryInd;
	
	@JsonProperty("salesInd")
	private boolean salesInd;
	
	@JsonProperty("investorInd")
	private boolean investorInd;
}
