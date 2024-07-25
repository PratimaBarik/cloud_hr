package com.app.employeePortal.call.mapper;

import java.util.List;

import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CallViewMapper {
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
	
	@JsonProperty("callDescription")
	private String callDescription;
	
//	@JsonProperty("ownerIds")
//	private List<String> ownerIds;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("included")
	private List<EmployeeShortMapper> included;
	
	@JsonProperty("woner")
	private String woner;
	
	@JsonProperty("contactName")
	private String contactName;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("candidateName")
	private String candidateName;
	
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
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("oppertunity")
	private String oppertunity;
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
	
	@JsonProperty("assignedBy")
	private String assignedBy;
}
