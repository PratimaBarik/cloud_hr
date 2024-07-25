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
@Table(name="contact_event_link")
public class ContactEventLink {
    @Id
    @GenericGenerator(name = "contactEventLinkId", strategy = "com.app.employeePortal.event.generator.ContactEventGenerator")
    @GeneratedValue(generator = "contactEventLinkId")

    @Column(name="contact_event_link_id")
    private String contactEventLinkId;

    @Column(name="event_id")
    private String eventId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
