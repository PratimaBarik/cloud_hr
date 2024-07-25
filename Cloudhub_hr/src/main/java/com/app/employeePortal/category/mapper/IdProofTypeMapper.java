package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class IdProofTypeMapper {
	
	@JsonProperty("idProofTypeId")
	private String idProofTypeId;
	
	@JsonProperty("idProofType")
	private String idProofType;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("updationDate")
	private String updationDate;

	@JsonProperty("name")
	private String name;

}
