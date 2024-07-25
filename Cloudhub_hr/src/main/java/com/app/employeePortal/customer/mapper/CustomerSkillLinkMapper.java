package com.app.employeePortal.customer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomerSkillLinkMapper {
	
	@JsonProperty("customerSkillLinkId")
	private String customerSkillLinkId;
	
	@JsonProperty("skillName")
	private String skillName;

	@JsonProperty("customerId")
	private String customerId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;	

}
