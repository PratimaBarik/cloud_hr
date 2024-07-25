package com.app.employeePortal.leave.mapper;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmployeeLeaveMapper {

	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("employeeName")
	private String employeeName;
	
	@JsonProperty("leaveList")
	private List<LeavesMapper> leaveList;
	
	
	

	
	
	
}
