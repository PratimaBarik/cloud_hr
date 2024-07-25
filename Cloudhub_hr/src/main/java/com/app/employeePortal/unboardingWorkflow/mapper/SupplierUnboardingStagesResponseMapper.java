package com.app.employeePortal.unboardingWorkflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SupplierUnboardingStagesResponseMapper {
	
	@JsonProperty("supplierUnboardingStagesId")
	private String supplierUnboardingStagesId;
	
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
	
//	@JsonProperty("responsible")
//	private String responsible;
	
	@JsonProperty("supplierUnboardingWorkflowDetailsId")
	private String supplierUnboardingWorkflowDetailsId;
	
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
