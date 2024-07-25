package com.app.employeePortal.contact.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"contactDesignationId","creatorId","contactDesignationName","creationDate","userId","organizationId"})
public class ContactDesignationMapper {
	
	@JsonProperty("contactDesignationId")
	private String contactDesignationId;
	
	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("contactDesignationName")
	private String contactDesignationName;

	@JsonProperty("creationDate")
	private String creationDate;
	
	

}
