package com.app.employeePortal.recruitment.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobVacancyMapper {
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("opprtunityName")
	private String opprtunityName;
	
	@JsonProperty("recruitmentId")
	private String recruitmentId;
	
	@JsonProperty("requirementName")
	private String requirementName;
	
	@JsonProperty("jobOrder")
	private String jobOrder;
	
	@JsonProperty("recruitmentProcessId")
	private String recruitmentProcessId;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("skillName")
	private String skillName;
	
	@JsonProperty("workPreference")
	private String workPreference;
	
	@JsonProperty("avilableDate")
	private String avilableDate;
	
	@JsonProperty("address")
	private List<JobPublishAddressMapper> address;
	
	
}
