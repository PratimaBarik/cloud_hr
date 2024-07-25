package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PerformanceMgmtDropDownMapper {
	
	@JsonProperty("performanceManagementId")
	private String performanceManagementId;

	@JsonProperty("kpi")
	private String kpi;
	
}
