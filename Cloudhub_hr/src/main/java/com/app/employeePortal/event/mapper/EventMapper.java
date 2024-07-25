package com.app.employeePortal.event.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EventMapper {

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
	
//	@JsonProperty("owner")
//	private List<EmployeeMapper> owner;
	
	@JsonProperty("included")
	private List<String> included;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("assignedToName")
	private String assignedToName;
	
	@JsonProperty("woner")
	private String woner;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("notificationMapper")
	private List<NotificationMapper> notificationMapper;

	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("candidateId")
 	private String candidateId;
	
	@JsonProperty("eventTypeId")
 	private String eventTypeId;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;
	
	@JsonProperty("updateDate")
	private String updateDate;

	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("leadsId")
	private String leadsId;
	
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
	
	@JsonProperty("campaignInd")
	private boolean campaignInd ;
	
	@JsonProperty("assignedBy")
	private String assignedBy;
}
