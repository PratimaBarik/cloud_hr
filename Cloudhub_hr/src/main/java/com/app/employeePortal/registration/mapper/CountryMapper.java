package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"countryId","countryName", "countryAlpha2Code","countryAlpha3Code","countryDialCode","countryCurrencyName","countryCurrencyCode","countryFlag"})
public class CountryMapper {
	@JsonProperty("countryId")
	private String country_id;

	@JsonProperty("countryName")
	private String country_name;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;
	
	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
	
	@JsonProperty("countryDialCode")
	private String country_dial_code;
	
	@JsonProperty("countryCurrencyName")
	private String country_currency_name;
	
	@JsonProperty("countryCurrencyCode")
	private String country_currency_code;
	
	@JsonProperty("countryFlag")
	private String country_flag;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("latitude")
	private String latitude;
	
	@JsonProperty("longitude")
	private String longitude;
	
	@JsonProperty("capital")
	private String capital;
	
	@JsonProperty("mandatoryInd")
	private boolean mandatoryInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("salesInd")
	private boolean salesInd;
	
	@JsonProperty("orgId")
	private String orgId;
	
}
