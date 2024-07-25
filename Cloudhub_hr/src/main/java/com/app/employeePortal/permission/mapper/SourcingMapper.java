package com.app.employeePortal.permission.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SourcingMapper {

	@JsonProperty("sourcingId")
	private String sourcingId;
	
	@JsonProperty("talentOutRichInd")
	private boolean talentOutRichInd;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("lastUpdatedOn")
	private String lastUpdatedOn;
	
	@JsonProperty("name")
	private String name;

	
}
