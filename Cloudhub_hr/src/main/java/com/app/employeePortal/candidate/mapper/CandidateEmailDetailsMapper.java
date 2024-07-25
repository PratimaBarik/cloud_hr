package com.app.employeePortal.candidate.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateEmailDetailsMapper {

	@JsonProperty("nameInd")
	private boolean nameInd;
	
	@JsonProperty("roleInd")
	private boolean roleInd;
	
	@JsonProperty("mobileNoInd")
	private boolean mobileNoInd;
	
	@JsonProperty("skillInd")
	private boolean skillInd;
	
	@JsonProperty("categoryInd")
	private boolean categoryInd;
	
	@JsonProperty("billingInd")
	private boolean billingInd;
	
	@JsonProperty("availableDateInd")
	private boolean availableDateInd;
	
	@JsonProperty("emailInd")
	private boolean emailInd;
	
	@JsonProperty("resumeInd")
	private boolean resumeInd;
	
	@JsonProperty("experienceInd")
	private boolean experienceInd;
	
	@JsonProperty("identityCardInd")
	private boolean identityCardInd;
	
	@JsonProperty("skillWiseExperienceInd")
	private boolean skillWiseExperienceInd;
	
	@JsonProperty("candidateEmailsDetailsId")
	private String candidateEmailsDetailsId;
	
	@JsonProperty("candidateIds")
	private List<String> candidateIds;
	
	@JsonProperty("to")
	private String to;
	
	@JsonProperty("cc")
	private String cc;
	
	@JsonProperty("bcc")
	private String bcc;
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("candidateEmailLinkId")
	private String candidateEmailLinkId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("customer1")
	private String customer1;
	
	
	@JsonProperty("customer2")
	private String customer2;
	
	@JsonProperty("customer3")
	private String customer3;
	
	@JsonProperty("contact1")
	private String contact1;
	
	@JsonProperty("contact2")
	private String contact2;
	
	@JsonProperty("contact3")
	private String contact3;
	
}
