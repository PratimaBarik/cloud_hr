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
@Table(name="leads_call_link")
public class LeadsCallLink {
    @Id
    @GenericGenerator(name = "leads_call_link_id", strategy = "com.app.employeePortal.call.generator.LeadsCallGenerator")
    @GeneratedValue(generator = "leads_call_link_id")

    @Column(name="leads_call_link_id")
    private String leadsCallLinkId;

    @Column(name="call_id")
    private String callId;

    @Column(name="leads_id")
    private String leadsId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
