package com.app.employeePortal.leave.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LeavesMapper {
	
	@JsonProperty("leaveId")
 	private String leaveId;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	
	@JsonProperty("endDate")
	private String endDate;
	
	
	@JsonProperty("coverDetails")
	private String coverDetails;
	
	@JsonProperty("reason")
	private String reason;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("rejectReason")
	private String rejectReason;
	
	@JsonProperty("halfDayInd")
	private boolean halfDayInd;
	
	@JsonProperty("halfDayType")
	private boolean halfDayType;
	
	@JsonProperty("totalApproved")
	private int totalApproved;
	
	@JsonProperty("totalPending")
	private int totalPending;
	
	@JsonProperty("totalRejected")
	private int totalRejected;

	@JsonProperty("otherUser")
	private String otherUser;
	
	@JsonProperty("selfOtherInd")
	private boolean selfOtherInd;
	
	@JsonProperty("approvalScore")
	private String approvalScore;
}
