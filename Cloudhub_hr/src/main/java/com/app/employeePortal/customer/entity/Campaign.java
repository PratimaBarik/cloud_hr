package com.app.employeePortal.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "campaign")

public class Campaign {
    @Id
    @GenericGenerator(name = "campaign_id", strategy = "com.app.employeePortal.customer.generator.CampaignGenerator")
    @GeneratedValue(generator = "campaign_id")

    @Column(name="campaign_id")
    private String campaignId;

    @Column(name="customer_id")
    private String customerId;

    @Column(name="event_id")
    private String eventId;

    @Column(name="budget_value",nullable = false)
    private double budgetValue;

    @Column(name="org_id")
    private String orgId;

    @Column(name="user_id")
    private String userId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="updation_date")
    private Date updationDate;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="live_ind", nullable= false)
    private boolean liveInd=false;

}


