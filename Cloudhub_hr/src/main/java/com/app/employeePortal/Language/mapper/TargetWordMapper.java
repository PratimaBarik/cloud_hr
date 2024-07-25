package com.app.employeePortal.Language.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetWordMapper {
	@JsonProperty("q")
	private List<String> q;

	@JsonProperty("target")
	private String target;
	
}
