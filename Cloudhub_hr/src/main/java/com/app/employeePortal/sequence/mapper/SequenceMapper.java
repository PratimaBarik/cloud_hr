package com.app.employeePortal.sequence.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SequenceMapper {
	
	@JsonProperty("sequenceId")
	private String sequenceId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("priority")
	private String priority;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("noOfDays")
	private int noOfDays;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedName")
	private String updatedName;
}
