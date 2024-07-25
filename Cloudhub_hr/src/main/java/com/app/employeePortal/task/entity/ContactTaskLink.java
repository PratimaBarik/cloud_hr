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
@Table(name="contact_task_link")
public class ContactTaskLink {
    @Id
    @GenericGenerator(name = "contactTaskLinkId", strategy = "com.app.employeePortal.task.generator.ContactTaskLinkGenerator")
    @GeneratedValue(generator = "contactTaskLinkId")

    @Column(name="contact_task_link_id")
    private String contactTaskLinkId;

    @Column(name="task_id")
    private String taskId;

    @Column(name="contact_id")
    private String contactId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

    @Column(name="complition_status")
    private String complitionStatus;

}
