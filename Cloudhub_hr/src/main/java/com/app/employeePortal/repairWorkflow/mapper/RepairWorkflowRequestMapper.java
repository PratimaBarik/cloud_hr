package com.app.employeePortal.repairWorkflow.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairWorkflowRequestMapper {

	@JsonProperty("repairWorkflowDetailsId")
	private String repairWorkflowDetailsId;
	
	@JsonProperty("workflowName")
	private String workflowName;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("publishInd")
	private boolean publishInd;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
}
