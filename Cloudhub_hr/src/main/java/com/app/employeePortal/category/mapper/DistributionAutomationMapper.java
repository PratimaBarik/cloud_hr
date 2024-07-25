package com.app.employeePortal.category.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DistributionAutomationMapper {
	
	@JsonProperty("distributionAutomationId")
	private String distributionAutomationId;

	@JsonProperty("asignedTO")
	private String asignedTO;
	
	@JsonProperty("multyAsignedTO")
	private List<String> multyAsignedTO;
	
	@JsonProperty("asignedTOId")
	private String asignedTOId;
	
	@JsonProperty("multyAsignedTOId")
	private List<String> multyAsignedTOId;
	
	@JsonProperty("type")
	private String type;
	
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
	
	@JsonProperty("singleMultiInd")
	private boolean singleMultiInd;
	
	@JsonProperty("departmentId")
	private String departmentId;
}
