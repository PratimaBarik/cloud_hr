package com.app.employeePortal.call.mapper;

import java.util.List;

import com.app.employeePortal.notification.mapper.NotificationMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CallMapper {

	
	@JsonProperty("callId")
	private String callId;

	@JsonProperty("callType")
	private String callType;

	@JsonProperty("callCategory")
	private String callCategory;
	
	@JsonProperty("callPurpose")
	private String callPurpose;
	
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
	
	@JsonProperty("callDescription")
	private String callDescription;
	
	@JsonProperty("included")
	private List<String> included;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("woner")
	private String woner;
	
//	@JsonProperty("included")
//	private List<EmployeeMapper> included;

	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("notificationmapper")
	private List<NotificationMapper> notificationmapper;
	
	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("candidateId")
 	private String candidateId;
	
	@JsonProperty("mode")
 	private String mode;
	
	@JsonProperty("modeType")
 	private String modeType;
	
	@JsonProperty("modeLink")
 	private String modeLink;
	
	@JsonProperty("remindTime")
 	private String remindTime;
	
	@JsonProperty("remindInd")
 	private boolean remindInd;
	
	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;
	
	@JsonProperty("updateDate")
	private String updateDate;

	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("investorLeadsId")
	private String investorLeadsId;
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("oppertunity")
	private String oppertunity;
	
	@JsonProperty("investorId")
	private String investorId;
	
	@JsonProperty("assignedBy")
	private String assignedBy;
}
