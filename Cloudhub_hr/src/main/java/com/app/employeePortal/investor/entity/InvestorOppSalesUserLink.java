package com.app.employeePortal.investor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity@Table(name="Investor_opp_sales_user_link")@Data
public class InvestorOppSalesUserLink {
    @Id
    @GenericGenerator(name = "Investor_opp_sales_user_link_id",strategy="com.app.employeePortal.investor.generator.InvestorOpportunitySalesUserLinkGenerator")
    @GeneratedValue(generator = "Investor_opp_sales_user_link_id")

    @Column(name="Investor_opp_sales_user_link_id")
    private String investorOppSalesId;

    @Column(name="investor_opportunity_id")
    private String invOpportunityId ;

    @Column(name="employee_id")
    private String employeeId;

    @Column(name="org_id")
    private String orgId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;
}
