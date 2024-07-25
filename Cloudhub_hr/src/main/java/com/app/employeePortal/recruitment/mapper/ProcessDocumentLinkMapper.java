package com.app.employeePortal.recruitment.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessDocumentLinkMapper {
	
	@JsonProperty("processDocumentLinkId")
	private String processDocumentLinkId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("processId")
	private String processId;
	
	@JsonProperty("documentTypeId")
	private String documentTypeId;
	
	@JsonProperty("documentTypeName")
	private String documentTypeName;
	
	@JsonProperty("mandatoryInd")
    private boolean mandatoryInd;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("name")
	private String name;
}
