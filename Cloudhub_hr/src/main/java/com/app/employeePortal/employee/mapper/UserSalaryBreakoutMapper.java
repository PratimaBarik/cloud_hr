package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserSalaryBreakoutMapper {

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("transportation")
	private double transportation;
	
	@JsonProperty("basic")
	private double basic;
	
	@JsonProperty("housing")
	private double housing;
	
	@JsonProperty("others")
	private double others;
	
	@JsonProperty("totalSalary")
	private double totalSalary;
	
	@JsonProperty("currency")
	private String currency;
	
}
