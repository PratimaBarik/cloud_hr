package com.app.employeePortal.ruleEngine.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RecruitProMailMapper {
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("template")
	private String template;
	
	@JsonProperty("recepient")
	private List<String> recepient;
	
	
	@JsonProperty("attachmentInd")
	private boolean attachmentInd;
	
	@JsonProperty("approverContactInd")
	private boolean approverContactInd;
	
	@JsonProperty("sponserTempId")
	private String sponserTempId;
	
	@JsonProperty("sponserApproveInd")
	private boolean sponserApproveInd;
	
	@JsonProperty("sponserAttachmentInd")
	private boolean sponserAttachmentInd;

	@JsonProperty("sponserReceiver")
	public List<String> sponserReceiver;
	
	@JsonProperty("emailLinkId")
	public String emailLinkId;
	
	@JsonProperty("candidateToggleInd")
	private boolean candidateToggleInd;
	
	@JsonProperty("sponsorToggleInd")
	private boolean sponsorToggleInd;
	
	@JsonProperty("stageInd")
	private boolean stageInd;
	
	@JsonProperty("stageTemplateId")
	private String stageTemplateId;
	
	@JsonProperty("candidateReminderInd")
	private boolean candidateReminderInd;
	
	
	@JsonProperty("candidateFrequency")
	private String candidateFrequency;
	
	@JsonProperty("candidateNoOfTimes")
	private long candidateNoOfTimes;
	
	@JsonProperty("sponsorReminderInd")
	private boolean sponsorReminderInd;
	
	
	@JsonProperty("sponsorFrequency")
	private String sponsorFrequency;
	
	@JsonProperty("SponsorNoOfTimes")
	private long SponsorNoOfTimes;

	
}
