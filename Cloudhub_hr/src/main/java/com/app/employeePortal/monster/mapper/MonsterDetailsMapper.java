package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MonsterDetailsMapper {
	
	@JsonProperty("monsterDetailsId")
	private String monsterDetailsId;

	@JsonProperty("monsterId")
	private String monsterId;
	
	@JsonProperty("jobDuration")
	private String jobDuration;

	@JsonProperty("jobCategory")
	private String jobCategory;
	
	@JsonProperty("jobOccupation")
	private String jobOccupation;
	
	@JsonProperty("jobBoardName")
	private boolean jobBoardName;
	
	@JsonProperty("displayTemplate")
	private boolean displayTemplate;
	
	@JsonProperty("vedio")
	private boolean vedio;

}
