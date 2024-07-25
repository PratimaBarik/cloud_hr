package com.app.employeePortal.notification.mapper;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationConfigResponse {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private List<String> type;
	
	@JsonProperty("reportingManager")
	private boolean reportingManager;
	
	@JsonProperty("reportingManager1")
	private boolean reportingManager1;
	
	@JsonProperty("admin")
	private boolean admin;
	
	@JsonProperty("userName")
	private String userName;

	@JsonProperty("updatedDate")
	private String updatedDate;
}
