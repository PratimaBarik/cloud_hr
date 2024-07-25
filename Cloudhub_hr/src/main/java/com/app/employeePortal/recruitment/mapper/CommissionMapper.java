package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommissionMapper {
	
	
	@JsonProperty("commissionId")
	private String commissionId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("commissionPrice")
	private float commissionPrice;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("calculatedOn")
	private String calculatedOn;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("comPersion")
	private String comPersion;
	
}
