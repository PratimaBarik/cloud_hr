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
@Table(name="contact_call_link")
public class ContactCallLink {
    @Id
    @GenericGenerator(name = "contactCallLinkId", strategy = "com.app.employeePortal.call.generator.ContactCallGenerator")
    @GeneratedValue(generator = "contactCallLinkId")

    @Column(name="contact_call_link_id")
    private String contactCallLinkId;

    @Column(name="call_id")
    private String callId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
