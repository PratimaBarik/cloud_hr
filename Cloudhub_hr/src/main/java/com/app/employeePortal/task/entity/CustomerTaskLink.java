package com.app.employeePortal.task.entity;

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
@Table(name="customer_task_link")
public class CustomerTaskLink {
    @Id
    @GenericGenerator(name = "customerTaskLinkId", strategy = "com.app.employeePortal.task.generator.CustomerTaskLinkGenerator")
    @GeneratedValue(generator = "customerTaskLinkId")

    @Column(name="customer_task_link_id")
    private String customerTaskLinkId;

    @Column(name="task_id")
    private String taskId;

    @Column(name="customer_id")
    private String customerId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

    @Column(name="complition_status")
    private String complitionStatus;

}
