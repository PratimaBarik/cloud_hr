package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MonsterOccupationMapper {
	
	@JsonProperty("monsterOccupationId")
	private String monsterOccupationId;

	@JsonProperty("occupationId")
	private String occupationId;
	
	@JsonProperty("occupationAlias")
	private String occupationAlias;

}



