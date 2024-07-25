package com.app.employeePortal.event.entity;

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
@Table(name="investor_leads_event_link")
public class InvestorLeadsEventLink {
    @Id
    @GenericGenerator(name = "investor_leads_event_link_id", strategy = "com.app.employeePortal.event.generator.InvestorLeadsEventGenerator")
    @GeneratedValue(generator = "investor_leads_event_link_id")

    @Column(name="investor_leads_event_link_id")
    private String investorLeadsEventLinkId;


    @Column(name="event_id")
    private String eventId;

    @Column(name="investor_leads_id")
    private String investorLeadsId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
