package com.app.employeePortal.notification.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationRuleMapper {
	
	@JsonProperty("notificationRuleId")
	private String notificationRuleId; 
	
	@JsonProperty("whatsappInd")
	private boolean whatsappInd;
	
	@JsonProperty("smsInd")
	private boolean smsInd;
	
	@JsonProperty("emailInd")
	private boolean emailInd;
	
	@JsonProperty("inappInd")
	private boolean inappInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("updatedDate")
	private String updatedDate;
	
	@JsonProperty("ownerName")
	private String ownerName;

}
