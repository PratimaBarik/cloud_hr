package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserKpiDashBoardResponseMapper {
	
	@JsonProperty("month1AssignedValue")
 	private double month1AssignedValue;
	
	@JsonProperty("month2AssignedValue")
 	private double month2AssignedValue;
	
	@JsonProperty("month3AssignedValue")
 	private double month3AssignedValue;
	
	@JsonProperty("assignedValue")
 	private double assignedValue;
	
	@JsonProperty("weitageValue")
 	private double weitageValue;
	
	@JsonProperty("month1CompletedValue")
 	private double month1CompletedValue;
	
	@JsonProperty("month2CompletedValue")
 	private double month2CompletedValue;
	
	@JsonProperty("month3CompletedValue")
 	private double month3CompletedValue;
	
	@JsonProperty("completedValue")
 	private double completedValue;
	
	@JsonProperty("cmpltValueAddDate")
 	private String cmpltValueAddDate;
	
	@JsonProperty("month1ActualCompletedValue")
 	private double month1ActualCompletedValue;
	
	@JsonProperty("month2ActualCompletedValue")
 	private double month2ActualCompletedValue;
	
	@JsonProperty("month3ActualCompletedValue")
 	private double month3ActualCompletedValue;
	
	@JsonProperty("actualCompletedValue")
 	private double actualCompletedValue;
	
	@JsonProperty("actualCmpltValueAddDate")
 	private String actualCmpltValueAddDate;
	
	@JsonProperty("actualCmpltValueAddedBy")
	private String actualCmpltValueAddedBy;	
	
	@JsonProperty("kpiId")
	private String kpiId;
	
	@JsonProperty("kpiName")
	private String kpiName;
	
	@JsonProperty("lobDetailsId")
	private String lobDetailsId;
	
	@JsonProperty("lobName")
	private String lobName;
	
	@JsonProperty("currencyInd")
	private boolean currencyInd;
	
	@JsonProperty("userCurrency")
	private String userCurrency;
	
}
