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
@Table(name = "investor_contact_link")
public class InvestorContactLink {
    @Id
    @GenericGenerator(name = "investor_contact_link_id", strategy = "com.app.employeePortal.investor.generator.InvestorContactLinkGenerator")
    @GeneratedValue(generator = "investor_contact_link_id")

    @Column(name="investor_contact_link_id")
    private String id;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;
}
