package com.app.employeePortal.investor.entity;

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

import lombok.Data;

@Data
@Entity
@Table(name="investor_opportunity")
public class InvestorOpportunity {
    @Id
    @GenericGenerator(name = "investor_opportunity_id", strategy = "com.app.employeePortal.investor.generator.InvestorOpportunityGenerator")
    @GeneratedValue(generator = "investor_opportunity_id")

    @Column(name="investor_opportunity_id")
    private String invOpportunityId;

	@Convert(converter = AesEncryptor.class)
    @Column(name="opportunity_name")
    private String opportunityName;

    @Column(name="proposal_amount")
    private String proposalAmount;

    @Column(name="currency")
    private String currency;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="investor_id")
    private String investorId;

//    @Column(name="customer_name")
//    private String customerName;

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

//    @Column(name="opp_innitiative")
//    private String oppInnitiative;

    @Column(name = "won_ind")
    private boolean wonInd;

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
    
    @Column(name = "modified_date")
	private Date modifiedDate;
    
    @Column(name="assignedTo")
    private String assignedTo;
    
    @Column(name="assignedBy")
    private String assignedBy;
    
    @Column(name="source")
    private String source;
    
    @Column(name="new_deal_no")
    private String newDealNo;

    @Column(name="payment_received",nullable = false)
    private double paymentReceived;

    @Column(name="payment_received_date")
    private Date paymentReceivedDate;

    @Column(name="won_date")
    private Date wonDate;

    @Column(name="lost_date")
    private Date lostDate;
}
