package com.app.employeePortal.reports.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfOrgRequirementMapper {

	@JsonProperty("pdfORGRequirementId")
	private String pdfOrgRequirementId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("jobIDInd")
	private boolean jobIDInd;
	
	@JsonProperty("requirementInd")
	private boolean requirementInd;
	
	@JsonProperty("sponsorInd")
	private boolean sponsorInd;
	
	@JsonProperty("createdInd")
	private boolean createdInd;
	
	@JsonProperty("startDateInd")
	private boolean startDateInd;
	
	@JsonProperty("skillSetInd")
	private boolean skillSetInd;
	
	@JsonProperty("submittedInd")
	private boolean submittedInd;
	
	@JsonProperty("selectedInd")
	private boolean selectedInd;
	
	@JsonProperty("onboardedInd")
	private boolean onboardedInd;
}
