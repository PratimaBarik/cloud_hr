package com.app.employeePortal.leave.mapper;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LeaveBalanceMapper {

	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("employeeName")
	private String employeeName;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("totalLeaves")
	private int totalLeaves;
	
	@JsonProperty("totalAppliedLeaves")
	private int totalAppliedLeaves;
	
	@JsonProperty("totalPendingLeaves")
	private int totalPendingLeaves;
	
	@JsonProperty("leaveBalance")
	private int leaveBalance;
	
	
	

	
	
	
}
