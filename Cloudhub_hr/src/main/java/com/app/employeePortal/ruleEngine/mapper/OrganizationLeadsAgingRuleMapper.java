package com.app.employeePortal.ruleEngine.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrganizationLeadsAgingRuleMapper {
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("coldLeads")
	private LeadsCatagoryMapper coldLeads;

	@JsonProperty("warmLeads")
	private LeadsCatagoryMapper warmLeads;
	
	@JsonProperty("hotLeads")
	private LeadsCatagoryMapper hotLeads;
	
	@JsonProperty("otherLeads")
	private LeadsCatagoryMapper otherLeads;

	
	
	}


