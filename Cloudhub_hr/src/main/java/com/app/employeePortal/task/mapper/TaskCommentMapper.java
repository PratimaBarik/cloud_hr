package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskCommentMapper {

	@JsonProperty("taskCommentId")
	private String taskCommentId;
	
	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("comment")
	private String comment;
	
	@JsonProperty("providerId")
	private String providerId;
	
	@JsonProperty("providerName")
	private String providerName;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;

}
