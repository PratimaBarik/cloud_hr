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
@Table(name="customer_call_link")
public class CustomerCallLink {
    @Id
    @GenericGenerator(name = "customerCallLinkId", strategy = "com.app.employeePortal.call.generator.CustomerCallGenerator")
    @GeneratedValue(generator = "customerCallLinkId")

    @Column(name="customer_call_link_id")
    private String customerCallLinkId;

    @Column(name="call_id")
    private String callId;

    @Column(name="customer_id")
    private String customerId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

}
