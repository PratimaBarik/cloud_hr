package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterCredentialsMapper {
	
	
	@JsonProperty("monsterCredentialsId")
	private String monsterCredentialsId;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("monsterInd")
	private boolean monsterInd;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;
	
}
