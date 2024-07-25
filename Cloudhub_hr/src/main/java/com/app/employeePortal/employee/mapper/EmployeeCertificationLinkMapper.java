package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeCertificationLinkMapper {

	@JsonProperty("employeeCertificationLinkId")
	private String employeeCertificationLinkId;

	@JsonProperty("employeeCertificationName")
	private String employeeCertificationName;

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;

}
