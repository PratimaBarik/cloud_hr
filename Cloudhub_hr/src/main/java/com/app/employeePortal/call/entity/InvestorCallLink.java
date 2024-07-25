package com.app.employeePortal.call.entity;

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
@Table(name="investor_call_link")
public class InvestorCallLink {
    @Id
    @GenericGenerator(name = "investor_call_link_id", strategy = "com.app.employeePortal.call.generator.InvestorCallGenerator")
    @GeneratedValue(generator = "investor_call_link_id")

    @Column(name="investor_call_link_id")
    private String investorCallLinkId;

    @Column(name="call_id")
    private String callId;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
