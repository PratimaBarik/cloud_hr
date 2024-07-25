package com.app.employeePortal.organization.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FiscalMapper {
	
	@JsonProperty("fiscalStartDate")
	private String fiscalStartDate;
	
	@JsonProperty("fiscalEndDate")
	private String fiscalEndDate;
	
	
	@JsonProperty("currentMonthStartDate")
	private String currentMonthStartDate;
	
	@JsonProperty("currentMonthEndDate")
	private String currentMonthEndDate;
	
	@JsonProperty("currentWeekStartDate")
	private String currentWeekStartDate;
	

	@JsonProperty("currentWeekEndDate")
	private String currentWeekEndDate;


	

}
