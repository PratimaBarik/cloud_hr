package com.app.employeePortal.task.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ToDoMapper {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("eventTypeId")
	private String eventTypeId;
	
	@JsonProperty("taskTypeId")
	private String taskTypeId;
	
	@JsonProperty("callTypeId")
	private String callTypeId;
	
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
	
	@JsonProperty("startTime")
	 private long startTime;
	
	@JsonProperty("endTime")
	 private long endTime;
	
	@JsonProperty("completionInd")
    private boolean completionInd;
	
	@JsonProperty("rating")
	private float rating;
	
   @JsonProperty("status")
   private String status;

   @JsonProperty("name")
   private String name;
   
   @JsonProperty("eventId")
	private String eventId;
	
	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("callId")
	private String callId;

	@JsonProperty("team")
	private List<TeamEmployeeMapper> team;
	
	@JsonProperty("assignedTo")
	public String assignedTo; 
	
	@JsonProperty("assignedToName")
	public String assignedToName;
	
	
}
