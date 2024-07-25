package com.app.employeePortal.customer.mapper;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransferMapper {


	@JsonProperty("customerIds")
	private List<String> customerIds;
	
	@JsonProperty("contactIds")
	private List<String> contactIds;
	
	@JsonProperty("opportunityIds")
	private List<String> opportunityIds;
	
	@JsonProperty("leadsIds")
	private List<String> leadsIds;
	
	@JsonProperty("investorIds")
	private List<String> investorIds;
	
	@JsonProperty("invOpportunityId")
	private List<String> invOpportunityId;
	
	@JsonProperty("invLeadsIds")
	private List<String> invLeadsIds;
	
}