package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VisaMapper {
	
	@JsonProperty("visaId")
 	private String visaId;
	
	@JsonProperty("creationDate")
 	private String creationDate;
	
	@JsonProperty("country")
 	private String country;
	
	@JsonProperty("documentId")
 	private String documentId;
	
	@JsonProperty("endDate")
 	private String endDate;
	
	@JsonProperty("liveInd")
 	private boolean liveInd;
	
	@JsonProperty("multipleEntryInd")
 	private boolean multipleEntryInd;
	
	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("startDate")
 	private String startDate;
	
	@JsonProperty("type")
 	private String type;
	
	@JsonProperty("userId")
 	private String userId;

	@JsonProperty("documentType")
	private String documentType;	
	

}
