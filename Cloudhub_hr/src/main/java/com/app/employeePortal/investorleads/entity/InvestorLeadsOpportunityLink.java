package com.app.employeePortal.investorleads.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="investor_leads_opportunity_link")
public class InvestorLeadsOpportunityLink {
	@Id
	@GenericGenerator(name = "investor_leads_opportunity_link_id", strategy ="com.app.employeePortal.investorleads.generator.InvestorLeadsOpportunityLinkGenerator")
	@GeneratedValue(generator = "investor_leads_opportunity_link_id")
	
	@Column(name="investor_leads_opportunity_link_id")
	private String investorLeadOppId;
	
	@Column(name="opportunityName")
	private String opportunityName;

	@Column(name="investorLeadsId")
	private String investorLeadsId;

	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name="proposalValue")
	private String proposalValue;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="opp_workflow")
    private String oppWorkflow;
	
	@Column(name="assigned_to")
	private String assignedTo;
	
	@Column(name="opp_stage")
    private String oppStage;
	
	
}
