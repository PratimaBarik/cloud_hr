package com.app.employeePortal.candidate.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class FilterMapper {

	@JsonProperty("workLocation")
	private String workLocation;
	
	@JsonProperty("workPreferance")
	private String workPreference;
	
	@JsonProperty("roleType")
	private String roleType;
	
	@JsonProperty("billing")
	private String billing;
	
	@JsonProperty("orAnd")
	private String orAnd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("filterDetailsId")
	private String filterDetailsId;
	
}
