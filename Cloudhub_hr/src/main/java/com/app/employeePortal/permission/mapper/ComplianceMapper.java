package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ComplianceMapper {

	@JsonProperty("complianceId")
	private String complianceId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("gdprApplicableInd")
	private boolean gdprApplicableInd;
	
	/*@JsonProperty("candidateId")
	private String candidateId;*/
	
	@JsonProperty("lastUpdaten")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
	
}
