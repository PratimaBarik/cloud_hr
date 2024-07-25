package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskChecklistMapper {
	
	@JsonProperty("taskChecklistId")
	private String taskChecklistId;
	
	@JsonProperty("taskChecklistName")
	private String taskChecklistName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("lastUpdateOn")
	private String lastUpdateOn;
	
	@JsonProperty("lastUpdateByName")
	private String lastUpdateByName;
	
	@JsonProperty("taskTypeId")
	private String taskTypeId;
	
}
