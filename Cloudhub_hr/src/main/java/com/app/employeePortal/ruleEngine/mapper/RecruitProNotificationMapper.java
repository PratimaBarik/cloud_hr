package com.app.employeePortal.ruleEngine.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RecruitProNotificationMapper {
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("template")
	private String template;
	
	@JsonProperty("recepient")
	private List<String> recepient;

	
}
