package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MonsterCategoryMapper {
	
	@JsonProperty("monsterCategoryId")
	private String monsterCategoryId;

	@JsonProperty("jobCategoryId")
	private String jobCategoryId;
	
	@JsonProperty("jobCategoryAlias")
	private String jobCategoryAlias;

}
