package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserKpiResponseMapper {
	
	@JsonProperty("userKpiLinkId")
	private String userKpiLinkId;
	
	@JsonProperty("kpiId")
	private String kpiId;
	
	@JsonProperty("kpiName")
	private String kpiName;
	
	@JsonProperty("assignedValue")
 	private double assignedValue;
	
	@JsonProperty("month1AssignedValue")
 	private double month1AssignedValue;
	
	@JsonProperty("month2AssignedValue")
 	private double month2AssignedValue;
	
	@JsonProperty("month3AssignedValue")
 	private double month3AssignedValue;
	
	@JsonProperty("weitageValue")
 	private double weitageValue;
	
	@JsonProperty("completedValue")
 	private double completedValue;
	
	@JsonProperty("month1CompletedValue")
 	private double month1CompletedValue;
	
	@JsonProperty("month2CompletedValue")
 	private double month2CompletedValue;
	
	@JsonProperty("month3CompletedValue")
 	private double month3CompletedValue;
	
	@JsonProperty("creationDate")
 	private String creationDate;
	
	@JsonProperty("cmpltValueAddDate")
 	private String cmpltValueAddDate;
	
	@JsonProperty("liveInd")
 	private boolean liveInd;
	
	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("userId")
 	private String userId;

	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("year")
	private double year;
	
	@JsonProperty("quarter")
	private String quarter;
	
	@JsonProperty("actualCompletedValue")
 	private double actualCompletedValue;
	
	@JsonProperty("month1ActualCompletedValue")
 	private double month1ActualCompletedValue;
	
	@JsonProperty("month2ActualCompletedValue")
 	private double month2ActualCompletedValue;
	
	@JsonProperty("month3ActualCompletedValue")
 	private double month3ActualCompletedValue;
	
	@JsonProperty("actualCmpltValueAddDate")
 	private String actualCmpltValueAddDate;
	
	@JsonProperty("actualCmpltValueAddedBy")
	private String actualCmpltValueAddedBy;	

	@JsonProperty("userCurrency")
	private String userCurrency;
	
	@JsonProperty("currencyInd")
	private boolean currencyInd;
	
	@JsonProperty("lobDetailsId")
	private String lobDetailsId;
	
	@JsonProperty("lobName")
	private String lobName;
}
