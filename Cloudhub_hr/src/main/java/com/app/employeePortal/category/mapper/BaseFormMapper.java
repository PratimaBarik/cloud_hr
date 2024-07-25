package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BaseFormMapper {

	@JsonProperty("baseFormId")
	private String baseFormId;

	@JsonProperty("formType")
	private String formType;
	
	@JsonProperty("baseFormType")
	private String baseFormType;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("liveInd")
	private boolean liveInd;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("updationDate")
	private String updationDate;

	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("nameInd")
	private boolean nameInd;
	
	@JsonProperty("dailCodeInd")
	private boolean dailCodeInd;
	
	@JsonProperty("phoneNoInd")
	private boolean phoneNoInd;
	
	@JsonProperty("sectorInd")
	private boolean sectorInd;
	
	@JsonProperty("sourceInd")
	private boolean sourceInd;
	
	@JsonProperty("noteInd")
	private boolean noteInd;
	
	@JsonProperty("assignedToInd")
	private boolean assignedToInd;
	
	@JsonProperty("vatNoInd")
	private boolean vatNoInd;
	
	@JsonProperty("businessRegInd")
	private boolean businessRegInd;
	
	@JsonProperty("addressInd")
	private boolean addressInd;
	
	@JsonProperty("potentialInd")
	private boolean potentialInd;
	
	@JsonProperty("potentialCurrencyInd")
	private boolean potentialCurrencyInd;
	
	@JsonProperty("typeInd")
	private boolean typeInd;
	
	@JsonProperty("firstNameInd")
	private boolean firstNameInd;
	
	@JsonProperty("middleNameInd")
	private boolean middleNameInd;
	
	@JsonProperty("lastNameInd")
	private boolean lastNameInd;
	
	@JsonProperty("imageUploadInd")
	private boolean imageUploadInd;
	
	@JsonProperty("urlInd")
	private boolean urlInd;
	
	@JsonProperty("lobInd")
	private boolean lobInd;
	
	@JsonProperty("shipByInd")
	private boolean shipByInd;
	
	@JsonProperty("apiInd")
	private boolean apiInd;
	
	@JsonProperty("approveInd")
	private boolean approveInd;

}
