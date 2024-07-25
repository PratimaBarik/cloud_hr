package com.app.employeePortal.Opportunity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="opportunity_detail")
public class OpportunityDetails {
	@Id
	@GenericGenerator(name = "opportunity_details_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityDetailsGenerator")
	@GeneratedValue(generator = "opportunity_details_id")
	
	@Column(name="opportunity_details_id")
	private String id;
	
	@Column(name="opportunity_id")
	private String opportunityId;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="opportunity_name")
	private String opportunityName;
	
	@Column(name="proposal_amount")
	private String proposalAmount;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="contact_id")
	private String contactId;

	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Lob
	@Column(name="description")
	private String description;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "reinstate_ind")
	private boolean reinstateInd;
	
	@Column(name="opp_innitiative")
    private String oppInnitiative;
	
	@Column(name = "won_ind")
	private boolean wonInd;
	
	@Column(name = "won_date")
	private Date wonDate;
	
	@Column(name = "lost_ind")
	private boolean lostInd;
	
	@Column(name = "close_ind")
	private boolean closeInd;
	
	@Column(name = "close_date")
	private Date closeDate;
	
	@Column(name="opp_workflow")
    private String oppWorkflow;
	
	@Column(name="opp_stage")
    private String oppStage;
	
	@Column(name = "Modified_date")
	private Date ModifiedDate;
	
	@Column(name="assignedTo")
    private String assignedTo;
	
	@Column(name="excelId")
    private String excelId;
	
	@Column(name="assignedBy")
    private String assignedBy;
	
	@Column(name = "lost_date")
	private Date lostDate;
	
	@Column(name="new_opp_id")
	private String newOppId;
}