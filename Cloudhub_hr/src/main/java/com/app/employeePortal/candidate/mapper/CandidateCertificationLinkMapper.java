package com.app.employeePortal.candidate.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CandidateCertificationLinkMapper {

	@JsonProperty("candiCertiLinkId")
	private String candiCertiLinkId;

	@JsonProperty("candidateCertificationName")
	private String candidateCertificationName;

	@JsonProperty("candidateId")
	private String candidateId;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;

}
