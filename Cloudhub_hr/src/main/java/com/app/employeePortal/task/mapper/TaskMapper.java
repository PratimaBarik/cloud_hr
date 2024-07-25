package com.app.employeePortal.task.mapper;

import java.util.List;
import java.util.Set;

import com.app.employeePortal.category.mapper.TaskChecklistStageLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskMapper {

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
	
	@JsonProperty("owner")
	private List<EmployeeMapper> owner;
	
	@JsonProperty("included")
	private Set<String> included;
	
	@JsonProperty("submittedBy")
	private String submittedBy;
	
	@JsonProperty("assignedOn")
	private String assignedOn;
	
	@JsonProperty("approvedInd")
	private String approvedInd;
	
	@JsonProperty("approvedDate")
	private String approvedDate;
	
	@JsonProperty("notificationmapper")
	private List<NotificationMapper> notificationmapper;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("candidateId")
 	private Set<String> candidateId;

	@JsonProperty("taskTypeId")
	private String taskTypeId;

	@JsonProperty("assignedTo")
	public String assignedTo; 
	
	@JsonProperty("assignedBy")
	public String assignedBy;
		
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;
	
	@JsonProperty("updateDate")
	private String updateDate;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("assignedDate")
	private String assignedDate;
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("unit")
	private String unit;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("documentId")
	private String documentId;
	
//	@JsonProperty("documentIds")
//	private List<String> documentIds;
	
	@JsonProperty("complexity")
	private String complexity;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("complitionStatus")
	private String complitionStatus;
	
	@JsonProperty("taskChecklist")
	private String taskChecklist;
	
	@JsonProperty("taskChecklistId")
	private String taskChecklistId;
	
	@JsonProperty("taskChecklistStageLinkMapper")
	private List<TaskChecklistStageLinkMapper> taskChecklistStageLinkMapper;
	
	@JsonProperty("taskCheckListInd")
	private boolean taskCheckListInd;

	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("link")
	private String link;

	@JsonProperty("investorLeadsId")
	private String investorLeadsId;
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("oppertunity")
	private String oppertunity;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("investorId")
	private String investorId;
	
	@JsonProperty("roomId")
	private String roomId;
}
