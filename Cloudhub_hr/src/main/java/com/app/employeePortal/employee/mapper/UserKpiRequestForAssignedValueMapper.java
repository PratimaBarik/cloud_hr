package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserKpiRequestForAssignedValueMapper {
	
	@JsonProperty("userKpiLinkId")
	private String userKpiLinkId;
	
	@JsonProperty("lobDetailsId")
	private String lobDetailsId;
	
	@JsonProperty("month1AssignedValue")
 	private double month1AssignedValue;
	
	@JsonProperty("month2AssignedValue")
 	private double month2AssignedValue;
	
	@JsonProperty("month3AssignedValue")
 	private double month3AssignedValue;
	
	@JsonProperty("weitageValue")
 	private double weitageValue;

	@JsonProperty("employeeId")
	private String employeeId;		
	

}
