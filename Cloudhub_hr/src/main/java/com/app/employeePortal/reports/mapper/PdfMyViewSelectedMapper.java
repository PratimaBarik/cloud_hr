package com.app.employeePortal.reports.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfMyViewSelectedMapper {

	@JsonProperty("pdfMyViewSelectedId")
	private String pdfMyViewSelectedId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("selectedInd")
	private boolean selectedInd;
	
	@JsonProperty("requirementInd")
	private boolean requirementInd;
	
	@JsonProperty("jobIDInd")
	private boolean jobIDInd;
	
	@JsonProperty("createdInd")
	private boolean createdInd;
	
	@JsonProperty("startDateInd")
	private boolean startDateInd;
	
	@JsonProperty("sponsorInd")
	private boolean sponsorInd;
	
	@JsonProperty("skillSetInd")
	private boolean skillSetInd;
	
	@JsonProperty("submittedInd")
	private boolean submittedInd;
	
	@JsonProperty("onboardedInd")
	private boolean onboardedInd;
}
