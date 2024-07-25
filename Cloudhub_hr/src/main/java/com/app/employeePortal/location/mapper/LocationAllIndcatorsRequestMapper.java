package com.app.employeePortal.location.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationAllIndcatorsRequestMapper {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("locationId")
	private String locationId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("value")
	private boolean value;

}
