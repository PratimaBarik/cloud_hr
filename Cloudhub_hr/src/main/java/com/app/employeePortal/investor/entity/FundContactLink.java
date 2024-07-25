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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name = "fund_contact_link")
public class FundContactLink {
    @Id
    @GenericGenerator(name = "fund_contact_link_id", strategy = "com.app.employeePortal.investor.generator.InvestorFundGenerator")
    @GeneratedValue(generator = "fund_contact_link_id")

    @Column(name="fund_contact_link_id")
    private String id;

    @Column(name="investor_opportunity_id")
    private String invOpportunityId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;
    
    @Column(name="borrow_date")
    private Date borrowDate;
    
    @Column(name="repay_month")
    private String repayMonth;
    
    @Column(name="borrow_ind", nullable =false)
    private boolean borrowInd = false;
    
    @Column(name="amount")
    private float amount;
    
    @Column(name="interest")
    private float interest;
    
    @Column(name="currency")
    private String currency;
}
