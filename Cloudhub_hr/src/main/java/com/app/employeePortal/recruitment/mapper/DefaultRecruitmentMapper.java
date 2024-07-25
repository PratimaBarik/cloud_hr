package com.app.employeePortal.recruitment.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultRecruitmentMapper {
	
	@JsonProperty("processId")
	private String processId;
	
	@JsonProperty("processName")
	private String processName;
	
	@JsonProperty("stageName")
	private String stageName;
	
	@JsonProperty("stageId")
	private String stageId;
	
	@JsonProperty("probability")
	private double probability;
	
	
	/*@JsonProperty("processDetails")
	private ProcessDetails processDetails;
	
	@JsonProperty("stageMapper")
	private List<StageDetails> stageMapper;*/

	
	
	

}
