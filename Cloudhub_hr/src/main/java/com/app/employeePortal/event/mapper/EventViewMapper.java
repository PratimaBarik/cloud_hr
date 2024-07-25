package com.app.employeePortal.event.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventViewMapper {
	
	@JsonProperty("eventId")
	private String eventId;
		
	@JsonProperty("eventType")
	private String eventType;
	
	@JsonProperty("eventSubject")
	private String eventSubject;
	
	@JsonProperty("eventDescription")
	private String eventDescription;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("timeZone")
	private String timeZone;
		
	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("startTime")
	private long startTime;

	@JsonProperty("endDate")
	private String endDate;

	@JsonProperty("endTime")
	private long endTime;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("complitionInd")
	private String complitionInd;
	
	@JsonProperty("address")
	private List<AddressMapper> address ;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("assignedToName")
	private String assignedToName;
	
	@JsonProperty("included")
	private List<EmployeeViewMapper> included;
	
	@JsonProperty("woner")
	private String woner;
	
//	@JsonProperty("ownerIds")
//	private List<String> ownerIds;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("candidateName")
	private String candidateName;
	
	@JsonProperty("eventTypeId")
	private String eventTypeId;
	
	@JsonProperty("EditInd")
	private boolean EditInd;

	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;
	
	@JsonProperty("updateDate")
	private String updateDate;
	
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
	
	@JsonProperty("assignedBy")
	private String assignedBy;
}
