package com.app.employeePortal.document.mapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"documentTypeId","creatorId","documentName","creationDate","userId","organizationId"})

public class DocumentTypeMapper {

	@JsonProperty("documentTypeId")
	private String documentTypeId;

	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("documentTypeName")
	private String documentTypeName;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("edit_ind")
	private boolean editInd;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("mandatory_ind")
	private boolean mandatoryInd;
	
	@JsonProperty("processId")
	private String processId;
	
	@JsonProperty("userType")
	private String userType;

	@JsonProperty("availableInd")
	private boolean availableInd;

}
