package com.app.employeePortal.leads.mapper;

import java.util.List;

import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivityMapper {
	@JsonProperty("callId")
	private String callId;
	
	@JsonProperty("eventId")
	private String eventId;
	
	@JsonProperty("taskId")
	private String taskId;

	@JsonProperty("activityType")
	private String activityType;

	@JsonProperty("category")
	private String category;

	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("included")
	private List<EmployeeShortMapper> included;
	
	@JsonProperty("documents")
	private List<DocumentMapper> documents;
	
	@JsonProperty("woner")
	private String woner;
	
	@JsonProperty("taskStatus")
	private String taskStatus;
	
	@JsonProperty("assignedBy")
	private String assignedBy;
	
//	@JsonProperty("candidateName")
//	private String candidateName;
//	
//	@JsonProperty("customer")
//	private String customer;
//	
//	@JsonProperty("oppertunity")
//	private String oppertunity;
//	
//	@JsonProperty("note")
//	private String note;
//	
//	@JsonProperty("address")
//	private List<AddressMapper> address ;
//	
//	@JsonProperty("investor")
//	private String investor;

}
