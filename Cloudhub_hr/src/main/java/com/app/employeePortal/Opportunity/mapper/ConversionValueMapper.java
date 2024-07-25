package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConversionValueMapper {
	
	@JsonProperty("userConversionPValue")
	private double userConversionPValue;
	
	@JsonProperty("userConversionWValue")
	private double userConversionWValue;
	
	@JsonProperty("userConversionCurrency")
	private String userConversionCurrency;
	
	@JsonProperty("orgConversionCurrency")
	private String orgConversionCurrency;
	
	@JsonProperty("orgConversionPValue")
	private double orgConversionPValue;
	
	@JsonProperty("orgConversionWValue")
	private double orgConversionWValue;
	
	@JsonProperty("message")
	private String message;
	
}
