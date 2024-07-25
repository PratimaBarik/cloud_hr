package com.app.employeePortal.organization.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity
@Table(name = "organization_subscription_details")
public class OrganizationSubsriptionDetails {

    @Id
    @GenericGenerator(name = "organization_subscription_id", strategy = "com.app.employeePortal.organization.generator.OrganizationSubscriptionGenerator")
    @GeneratedValue(generator = "id")

    @Column(name = "organization_subscription_id",length = 80,nullable = false)
    private String organization_subscription_id;

    @Column(name = "organization_id",length = 80)
    private String organizationId;

    @Column(name = "subscriptionType")
    private int subscriptionType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "subscriptionStartDate")
    private Date subscription_start_date;
 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SubscriptionEndDate")
    private Date subscription_end_date;
    
    @Column(name="crm_ind", nullable =false)
	private boolean crmInd=false;
    
    @Column(name="ats_ind", nullable =false)
	private boolean atsInd=false;
    
    @Column(name="billing_ind", nullable =false)
	private boolean billingInd=false;
    
    @Column(name="lms_ind", nullable =false)
	private boolean lmsInd=false;
    
    @Column(name="vms_ind", nullable =false)
	private boolean vmsInd=false;
    
    

   }
