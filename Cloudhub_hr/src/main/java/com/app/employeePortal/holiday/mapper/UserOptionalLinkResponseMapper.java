package com.app.employeePortal.holiday.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserOptionalLinkResponseMapper {
	
	@JsonProperty("userOptionalLinkId")
 	private String userOptionalLinkId;
	
	@JsonProperty("holidayId")
 	private String holidayId;
	
	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("userId")
	private String userId;
	
}
