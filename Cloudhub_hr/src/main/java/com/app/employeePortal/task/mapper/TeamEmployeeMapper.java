package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TeamEmployeeMapper {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
}
