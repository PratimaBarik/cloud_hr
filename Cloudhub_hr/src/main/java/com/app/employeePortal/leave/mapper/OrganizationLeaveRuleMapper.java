package com.app.employeePortal.leave.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrganizationLeaveRuleMapper {

	@JsonProperty("organizationLeaveRuleId")
	private String organizationLeaveRuleId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("maximumLeaves")
	private int maximumLeaves;
	
	@JsonProperty("carryForward")
	private double carryForward;
	
	@JsonProperty("leavesCappedTimesAnnualy")
	private double leavesCappedTimesAnnualy;

	@JsonProperty("country")
	private String country;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;

	@JsonProperty("mileageRate")
	private double mileageRate;
	
	@JsonProperty("maximumLeavesEffectiveDate")
	private String maximumLeavesEffectiveDate;
	
	@JsonProperty("carryForwardEffectiveDate")
	private String carryForwardEffectiveDate;
	
	@JsonProperty("mileageRateEffectiveDate")
	private String mileageRateEffectiveDate;
	
	@JsonProperty("leavesCappedTimesAnnualyEffectiveDate")
	private String leavesCappedTimesAnnualyEffectiveDate;
	
	@JsonProperty("maxOpsnlHoliday")
	private int maxOpsnlHoliday;
}
