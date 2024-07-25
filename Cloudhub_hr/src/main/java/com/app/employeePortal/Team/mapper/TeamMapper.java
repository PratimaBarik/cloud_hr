package com.app.employeePortal.Team.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TeamMapper {
	
	@JsonProperty("teamId")
	private String teamId;

	@JsonProperty("teamName")
	private String teamName;
	
	@JsonProperty("teamLogo")
	private String teamLogo;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("teamLead")
	private String teamLead;
	
	@JsonProperty("teamMemberIds")
	private List<String> teamMemberIds;
	
}
