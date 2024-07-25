package com.app.employeePortal.investor.entity;

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
@Table(name = "investors_share")
public class InvestorsShare {
    @Id
    @GenericGenerator(name = "investorsShareId", strategy = "com.app.employeePortal.investor.generator.InvestorShareGenerator")
    @GeneratedValue(generator = "investorsShareId")

    @Column(name="investors_share_id")
    private String investorsShareId;

    @Column(name="quantity_of_share")
    private double quantityOfShare;

    @Column(name="amount_per_share")
    private double amountPerShare;
    
    @Column(name="total_amount_of_share")
    private double totalAmountOfShare;

    @Column(name="currency")
    private String currency;
    
    @Column(name="investor_id")
    private String investorId;
    
    @Column(name = "buying_date")
    private Date buyingDate;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name="document_id")
    private String documentId;
    
    @Column(name="user_id")
    private String userId;
    
    @Column(name="org_id")
    private String orgId;
    
    @Column(name="live_ind")
    private boolean liveInd;
    
    @Column(name = "updation_date")
    private Date updationDate;
    
    @Column(name="updated_by")
    private String updatedBy;

    
}
