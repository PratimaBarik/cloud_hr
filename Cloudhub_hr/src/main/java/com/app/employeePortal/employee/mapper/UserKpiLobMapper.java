package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserKpiLobMapper {

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("lobName")
	private String lobName;
	
	@JsonProperty("lobId")
	private String lobId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("jan")
	private double jan;
	
	@JsonProperty("feb")
	private double feb;
	
	@JsonProperty("mar")
	private double mar;
	
	@JsonProperty("apr")
	private double apr;
	
	@JsonProperty("may")
	private double may;
	
	@JsonProperty("jun")
	private double jun;
	
	@JsonProperty("jul")
	private double jul;
	
	@JsonProperty("aug")
	private double aug;
	
	@JsonProperty("sep")
	private double sep;
	
	@JsonProperty("oct")
	private double oct;
	
	@JsonProperty("nov")
	private double nov;
	
	@JsonProperty("dec")
	private double dec;
	
}
