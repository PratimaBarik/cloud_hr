package com.app.employeePortal.task.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStepsMapper {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("step")
	private String step;
	 
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("taskId")
	private String taskId;
}
