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
@Table(name = "investor_address_link")
public class InvestorAddressLink {
    @Id
    @GenericGenerator(name = "investor_address_link_id", strategy = "com.app.employeePortal.investor.generator.InvestorAddressLinkGenerator")
    @GeneratedValue(generator = "investor_address_link_id")

    @Column(name="investor_address_link_id")
    private String investorAddressLinkId;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="address_id")
    private String addressId;

    @Column(name="creation_date")
    private Date creationDate;

}
