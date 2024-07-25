package com.app.employeePortal.ruleEngine.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReportSchedulingMapper {
	
	@JsonProperty("reportSchedulingId")
	private String reportSchedulingId;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("department")
	private String department;

	@JsonProperty("frequency")
	private String frequency;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
