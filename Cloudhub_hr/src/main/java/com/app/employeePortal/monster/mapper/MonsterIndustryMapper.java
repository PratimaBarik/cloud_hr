package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter


public class MonsterIndustryMapper {
	
	@JsonProperty("monsterIndustryId")
	private String monsterIndustryId;

	@JsonProperty("industryId")
	private String industryId;
	
	@JsonProperty("industryAlias")
	private String industryAlias;

	
}
