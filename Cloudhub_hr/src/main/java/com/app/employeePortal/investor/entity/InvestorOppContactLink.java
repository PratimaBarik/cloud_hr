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
@Table(name="investor_opp_contact_link")
public class InvestorOppContactLink {
    @Id
    @GenericGenerator(name = "investor_opp_contact_link_id", strategy = "com.app.employeePortal.investor.generator.InvestorOppContactLinkGenerator")
    @GeneratedValue(generator = "investor_opp_contact_link_id")

    @Column(name="investor_opp_contact_link_id")
    private String id;

    @Column(name="investor_opportunity_id")
    private String invOpportunityId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;

}
