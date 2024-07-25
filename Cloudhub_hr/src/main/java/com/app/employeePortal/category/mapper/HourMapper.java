package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourMapper {
	
	@JsonProperty("hourId")
	private String hourId;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("projectId")
	private String projectId;
	
	@JsonProperty("startTime")
	private long startTime;

	@JsonProperty("endTime")
	private long endTime;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
		
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("candidateId")
    private String candidateId;
	
	@JsonProperty("hour")
    private float hour;
		
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("billingAmount")
    private float billingAmount;
	
	@JsonProperty("finalBillableAmount")
	private float finalBillableAmount;
	
	@JsonProperty("customerId")
    private String customerId;
	
	@JsonProperty("customerName")
    private String customerName;
	
	@JsonProperty("taskId")
    private String taskId;
	
	@JsonProperty("taskName")
    private String taskName;
	
	@JsonProperty("projectManager")
    private String projectManager;
	
	@JsonProperty("aproveInd")
    private boolean aproveInd;
	
	@JsonProperty("completeUnit")
    private String completeUnit;
	
	@JsonProperty("note")
    private String note;
	
	@JsonProperty("remark")
    private String remark;
	
	@JsonProperty("aproveUnit")
    private String aproveUnit;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("plannerStartDate")
	private String plannerStartDate;
	
}
