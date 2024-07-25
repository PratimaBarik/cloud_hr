package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyConversionResponseMapper {
	
	@JsonProperty("currencyConversionId")
	private String currencyConversionId;

	@JsonProperty("reportingCurrency")
	private String reportingCurrency;
	
	@JsonProperty("conversionCurrency")
	private String conversionCurrency;
	
	@JsonProperty("conversionFactor")
	private float conversionFactor;
	
	@JsonProperty("reportingFactor")
	private float reportingFactor;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;

	
}
