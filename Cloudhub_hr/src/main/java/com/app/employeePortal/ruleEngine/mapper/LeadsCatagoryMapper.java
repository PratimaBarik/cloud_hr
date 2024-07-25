package com.app.employeePortal.ruleEngine.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LeadsCatagoryMapper {
	
	@JsonProperty("days")
	private int days;
	
	@JsonProperty("category")
	private String category;

	
	

}
