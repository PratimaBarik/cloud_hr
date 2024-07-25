package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class EmployeeContractMapper {
	
	@JsonProperty("id")
 	private String id;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("previous_start_date")
	private String previousStartDate;
	
	/*
	 * @JsonProperty("present_start_date") private String presentStartDate;
	 */
	
	@JsonProperty("previous_end_date")
	private String previousEndDate;
	
	/*
	 * @JsonProperty("present_end_date") private String presentEndDate;
	 */
	
	@JsonProperty("renwal_date")
	private String renwalDate;
	
	@JsonProperty("contract_Type")
	private String contractType;
	
	@JsonProperty("notes")
	private String notes;

}
