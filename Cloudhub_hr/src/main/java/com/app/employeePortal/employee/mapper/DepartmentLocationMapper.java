package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DepartmentLocationMapper {
	

	@JsonProperty("locationid")
 	private String locationid;
	
	@JsonProperty("userId")
 	private String userId;
 	
	@JsonProperty("departmentId")
 	private String departmentId;
	
}
