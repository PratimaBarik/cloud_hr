package com.app.employeePortal.notification.mapper;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"notificationId","notificationMessage","notificationHeading","notificationOwner","notificationDate","notificationTime","notificationReadInd","userId","notificationType"})

public class NotificationMapper {
	
	@JsonProperty("notificationId")
	private String notificationId; 
	
	@JsonProperty("notificationMessage")
	private String notificationMessage;
	
	@JsonProperty("notificationHeading")
	private String notificationHeading;
	
	@JsonProperty("notificationOwner")
	private String notificationOwner;
	
	@JsonProperty("notificationDate")
	private String notificationDate;
	
	@JsonProperty("notificationTime")
	private String notificationTime;
	
	@JsonProperty("notificationReadInd")
	private boolean notificationReadInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("AssignedBy")
	private String AssignedBy;
	
	@JsonProperty("AssignedTo")
	private String AssignedTo;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("notificationType")
	private String notificationType;
	
	@JsonProperty("creationDate")
	private String creationDate;


	

}
