package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonPropertyOrder({"countryId","countryName", "countryAlpha2Code","countryAlpha3Code","countryDialCode","countryCurrencyName","countryCurrencyCode","countryFlag"})
public class DailCodeMapper {
	
	@JsonProperty("countryDialCode")
	private String country_dial_code;
	
}
