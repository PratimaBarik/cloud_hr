package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityMapper {
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("eventTypeId")
	private String eventTypeId;
	
	@JsonProperty("taskTypeId")
	private String taskTypeId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("topic")
	private String topic;
	
	@JsonProperty("activity")
	private String activity;

	@JsonProperty("timeZone")
	private String timeZone;

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
//	@JsonProperty("callId")
//	private String callId;
	
//	@JsonProperty("eventId")
//	 private String eventId;

//	@JsonProperty("taskId")
//	 private String taskId;
	
	@JsonProperty("startTime")
	 private long startTime;
	
	@JsonProperty("endTime")
	 private long endTime;
	
	@JsonProperty("completionInd")
    private boolean completionInd;
	
   @JsonProperty("status")
   private String status;

   @JsonProperty("name")
   private String name;

	
}
