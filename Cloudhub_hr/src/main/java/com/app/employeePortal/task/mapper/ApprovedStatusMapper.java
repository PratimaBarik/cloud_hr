package com.app.employeePortal.task.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovedStatusMapper {
	
	@JsonProperty("employeeName")
	private String employeeName;
	
	@JsonProperty("approvedDate")
	private String approvedDate;
	
	@JsonProperty("approvedStatus")
	private String approvedStatus;
	
	@JsonProperty("createdOn")
	private String createdOn;
	
}
