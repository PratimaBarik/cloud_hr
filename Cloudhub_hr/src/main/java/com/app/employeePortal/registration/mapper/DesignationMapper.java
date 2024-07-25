package com.app.employeePortal.registration.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignationMapper {
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;

	@JsonProperty("designationType")
	private String designationType;
	
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
