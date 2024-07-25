package com.app.employeePortal.task.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDragDropMapper {
	
	@JsonProperty("presentWeakStartDate")
	private String presentWeakStartDate;
	
	@JsonProperty("targetWeakStartDate")
	private String targetWeakStartDate;
	 
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("taskId")
	private String taskId;
}
