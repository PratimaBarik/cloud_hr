package com.app.employeePortal.customer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomerLobLinkMapper {
	
	@JsonProperty("lobDetailsId")
	private String lobDetailsId;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("applicable")
	private boolean applicable;;
	
	@JsonProperty("potential")
	private String potential;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("customeId")
	private String customeId;	
}
