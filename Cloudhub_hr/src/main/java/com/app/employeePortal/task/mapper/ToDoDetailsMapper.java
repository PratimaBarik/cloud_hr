package com.app.employeePortal.task.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoDetailsMapper {

	@JsonProperty("completionInd")
	private boolean completionInd;

	@JsonProperty("rating")
	private float rating;

	@JsonProperty("callId")
	private String callId;

	@JsonProperty("eventId")
	private String eventId;

	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("toDoDetailsId")
	private String toDoDetailsId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updateDate")
	private String updateDate;
	
	@JsonProperty("userId")
	private String userId;

}