package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MonsterBoardMapper {
	@JsonProperty("monsterBoardId")
	private String monsterBoardId;

	@JsonProperty("jobBoardName")
	private String jobBoardName;
	
	@JsonProperty("testBoardId")
	private String testBoardId;

	

}
