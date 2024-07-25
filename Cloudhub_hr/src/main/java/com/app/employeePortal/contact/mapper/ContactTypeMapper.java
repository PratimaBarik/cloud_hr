package com.app.employeePortal.contact.mapper;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContactTypeMapper {
	
	@JsonProperty("contactTypeId")
	private String contactTypeId;

	

	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("contactTypeName")
	private String contactTypeName;

	@JsonProperty("creationDate")
	private String creationDate;

	

}
