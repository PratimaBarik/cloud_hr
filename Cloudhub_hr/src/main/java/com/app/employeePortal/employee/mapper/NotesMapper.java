package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotesMapper {
	
	@JsonProperty("notesId")
	private String notesId;
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("leadsId")
	private String leadsId;

	@JsonProperty("investorId")
	private String investorId;

	@JsonProperty("invOpportunityId")
	private String invOpportunityId;
	
	@JsonProperty("investorLeadsId")
	private String investorLeadsId;
	
	@JsonProperty("callId")
	private String callId;
	
	@JsonProperty("eventId")
	private String eventId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("leaveId")
	private String leaveId;
	
	@JsonProperty("expenseId")
	private String expenseId;
	
	@JsonProperty("taskId")
	private String taskId;
	
	@JsonProperty("mileageId")
	private String mileageId;
	
	@JsonProperty("live_ind")
	private boolean liveInd;
	
	@JsonProperty("roomId")
	private String roomId;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("id")
	private String id;
}
