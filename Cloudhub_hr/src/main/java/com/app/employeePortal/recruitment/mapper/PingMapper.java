package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PingMapper {

	@JsonProperty("pingInd")
	private boolean pingInd;
}
