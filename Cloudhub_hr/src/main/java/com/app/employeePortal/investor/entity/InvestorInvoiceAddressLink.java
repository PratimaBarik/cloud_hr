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
@Table(name = "investor_invoice_address_link")
public class InvestorInvoiceAddressLink {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.investor.generator.InvestorInvoiceAddressLinkGenerator")
    @GeneratedValue(generator = "id")

    @Column(name="investor_invoice_address_link_id")
    private String id;

    @Column(name="investor_invoice_id")
    private String investorInvoiceId;

    @Column(name="address_id")
    private String addressId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="investor_id")
    private Date investorId;
}
