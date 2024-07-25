package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserKpiRequestForCompletedValueMapper {
	
	@JsonProperty("userKpiLinkId")
	private String userKpiLinkId;
	
	@JsonProperty("month1CompletedValue")
 	private double month1CompletedValue;
	
	@JsonProperty("month2CompletedValue")
 	private double month2CompletedValue;
	
	@JsonProperty("month3CompletedValue")
 	private double month3CompletedValue;
	
	@JsonProperty("month1ActualCompletedValue")
 	private double month1ActualCompletedValue;
	
	@JsonProperty("month2ActualCompletedValue")
 	private double month2ActualCompletedValue;
	
	@JsonProperty("month3ActualCompletedValue")
 	private double month3ActualCompletedValue;
	
	@JsonProperty("employeeId")
	private String employeeId;		

	@JsonProperty("actualCmpltValueAddedBy")
	private String actualCmpltValueAddedBy;		
	

}
