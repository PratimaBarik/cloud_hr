package com.app.employeePortal.customer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRecruitmentMapper {
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("requirementName")
	private String requirementName;
	
	@JsonProperty("recruitmentId")
	private String recruitmentId;
	
	@JsonProperty("recruiterId")
	private String recruiterId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("Opportunity")
	private int opportunity;
	
	@JsonProperty("OpenRequirements")
	private int openRequirmentNo;
	
	@JsonProperty("Selected")
	private int selectedNo;
	
	@JsonProperty("OnBoarded")
	private int onboardedNo;
	
	@JsonProperty("Positions")
	private int position;

	@JsonProperty("Closer")
	private float closer;
	
	@JsonProperty("creationDate")
	private String creationDate;

}
