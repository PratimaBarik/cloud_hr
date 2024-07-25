package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FunnelMapper {
	
	@JsonProperty("stage")
	private String stage;
	
	@JsonProperty("number")
	private int number;

}
