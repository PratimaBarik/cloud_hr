package com.app.employeePortal.productionWorkflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductionStagesResponseMapper {
	
	@JsonProperty("productionStagesId")
	private String productionStagesId;
	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("days")
	private int days;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("publishInd")
	private boolean publishInd;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("productionWorkflowDetailsId")
	private String productionWorkflowDetailsId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
