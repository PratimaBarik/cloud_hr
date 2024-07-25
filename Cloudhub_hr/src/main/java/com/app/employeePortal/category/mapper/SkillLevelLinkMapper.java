package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillLevelLinkMapper {
	
	@JsonProperty("skillLevelLinkId")
	private String skillLevelLinkId;
	
	@JsonProperty("skillDefinationId")
	private String skillDefinationId;
	
	@JsonProperty("skill")
	private String skill;
	
	@JsonProperty("level1")
	private float level1;
	
	@JsonProperty("level2")
	private float level2;
	
	@JsonProperty("level3")
	private float level3;
	
	@JsonProperty("level4")
	private float level4;
	
	@JsonProperty("level5")
	private float level5;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("countryId")
	private String countryId;
	
//	private List<SkillLevelLinkMapper> list;
}
