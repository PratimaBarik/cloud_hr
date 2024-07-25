package com.app.employeePortal.employee.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeTreeMapper {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("children")
	private List<EmployeeEducationDetailsTreeMapper> children;
	
}
