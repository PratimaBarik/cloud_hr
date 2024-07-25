package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SalaryDetailsMapper {
	
	@JsonProperty("salaryDetailsId")
 	private String salaryDetailsId;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("grossMonthlySalary")
 	private String grossMonthlySalary;
	
	@JsonProperty("netSalary")
 	private String netSalary;
	
	@JsonProperty("startingDate")
 	private String startingDate;
	
	@JsonProperty("endDate")
 	private String endDate;
	
	@JsonProperty("currency")
 	private String currency;
	
	@JsonProperty("type")
 	private String type;
	
	@JsonProperty("basic")
 	private String basic;
	
	@JsonProperty("hra")
 	private String hra;
	
	@JsonProperty("lta")
 	private String lta;
	
	@JsonProperty("holidayPay")
 	private String holidayPay;

	
	
	
	
	

}
