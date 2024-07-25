package com.app.employeePortal.category.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PerformanceMgmntDeptLinkMapper {
	
	@JsonProperty("kpis")
	private List<PerformanceMgmtDropDownMapper> kpis;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("deparmentName")
	private String deparmentName;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
	@JsonProperty("roleType")
	private String roleType;
	
}
