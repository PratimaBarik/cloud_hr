package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserSalaryBreakoutReqMapper {
	
	@JsonProperty("userSalaryBreakoutId")
	private String userSalaryBreakoutId;

	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("transportation")
	private double transportation;
	
	@JsonProperty("basic")
	private double basic;
	
	@JsonProperty("housing")
	private double housing;
	
	@JsonProperty("others")
	private double others;
	
}
