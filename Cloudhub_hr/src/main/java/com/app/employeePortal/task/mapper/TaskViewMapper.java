package com.app.employeePortal.task.mapper;

import java.util.List;

import com.app.employeePortal.category.mapper.TaskChecklistStageLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class TaskViewMapper {
	
	@JsonProperty("taskId")
	private String taskId;
		
	@JsonProperty("taskType")
	private String taskType;
	
	@JsonProperty("taskName")
	private String taskName;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("taskStatus")
	private String taskStatus;
	
	@JsonProperty("timeZone")
	private String timeZone;
		
	@JsonProperty("startDate")
	private String startDate;


	@JsonProperty("endDate")
	private String endDate;
		
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	
	@JsonProperty("taskDescription")
	private String taskDescription;
	
//	@JsonProperty("owner")
//	private List<EmployeeShortMapper> owner;
	
	@JsonProperty("ownerIds")
	private List<String> ownerIds;
	
	@JsonProperty("submittedBy")
	private String submittedBy;
	
	@JsonProperty("assignedOn")
	private String assignedOn;
	
	@JsonProperty("approvedInd")
	private String approvedInd;
	
	@JsonProperty("approvedDate")
	private String approvedDate;
	
	@JsonProperty("creationDate")
	private String creationDate;

//	@JsonProperty("candidateId")
//	private String candidateId;
	
	@JsonProperty("candidates")
	private  List<CandidateViewMapper> candidates;
	
	@JsonProperty("taskTypeId")
	private String taskTypeId;
	
	@JsonProperty("edit_ind")
	private boolean editInd;
	
	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;
	
	@JsonProperty("updateDate")
	private String updateDate;
	
	@JsonProperty("assignedDate")
	private String assignedDate;
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("unit")
	private String unit;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("projectId")
	private String projectId;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("complexity")
	private String complexity;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("complitionStatus")
	private String complitionStatus;
	
	@JsonProperty("assignedTo")
	public String assignedTo; 
	
	@JsonProperty("assignedBy")
	public String assignedBy;
	
	@JsonProperty("assignedToName")
	public String assignedToName;
	
	@JsonProperty("taskChecklist")
	private String taskChecklist;
	
	@JsonProperty("taskChecklistId")
	private String taskChecklistId;
	
	@JsonProperty("taskChecklistStageLinkMapper")
	private List<TaskChecklistStageLinkMapper> taskChecklistStageLinkMapper;
	
	@JsonProperty("documentIds")
	private List<String> documentIds;
	
	private int noOfPages;
	
	@JsonProperty("filterTaskInd")
	private boolean filterTaskInd;
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("oppertunity")
	private String oppertunity;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
	
	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("included")
	private List<EmployeeShortMapper> included;
	
	@JsonProperty("link")
	private String link;
}
