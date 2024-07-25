package com.app.employeePortal.category.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleRequestMapper {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("value")
	private boolean value;

}
