package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePreferedLanguageMapper {

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("preferedLanguage")
	private String preferedLanguage;

}
