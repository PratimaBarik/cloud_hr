package com.app.employeePortal.RoleTypeExternal.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleTypeExternalMapper {
	@JsonProperty("roleType")
	private String roleType;

	@JsonProperty("roleTypeId")
	private String roleTypeExternalId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;
		
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;

}
