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
@Table(name="customer_event_link")
public class CustomerEventLink {
    @Id
    @GenericGenerator(name = "customerEventLinkId", strategy = "com.app.employeePortal.event.generator.CustomerEventGenerator")
    @GeneratedValue(generator = "customerEventLinkId")

    @Column(name="customer_event_link_id")
    private String customerEventLinkId;

    @Column(name="event_id")
    private String eventId;

    @Column(name="customer_id")
    private String customerId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;
    
    @Column(name = "campaign_ind", nullable = false)
	private boolean campaignInd = false;

}
