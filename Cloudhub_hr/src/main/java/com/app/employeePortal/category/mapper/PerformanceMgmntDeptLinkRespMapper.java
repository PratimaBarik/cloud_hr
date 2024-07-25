package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PerformanceMgmntDeptLinkRespMapper {
	
	@JsonProperty("performanceManagementId")
	private String performanceManagementId;

	@JsonProperty("kpi")
	private String kpi;
	
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
	
	@JsonProperty("roleType")
	private boolean liveInd;
	
}
