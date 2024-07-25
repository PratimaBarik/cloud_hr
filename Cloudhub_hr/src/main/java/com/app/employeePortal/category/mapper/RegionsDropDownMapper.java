package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegionsDropDownMapper {
	
	@JsonProperty("regionsId")
	private String regionsId;

	@JsonProperty("regions")
	private String regions;

}
