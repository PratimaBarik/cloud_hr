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
@Table(name="investor_task_link")
public class InvestorTaskLink {
    @Id
    @GenericGenerator(name = "investor_task_link_id", strategy = "com.app.employeePortal.task.generator.InvestorTaskLinkGenerator")
    @GeneratedValue(generator = "investor_task_link_id")

    @Column(name="investor_task_link_id")
    private String investorTaskLinkId;


    @Column(name="task_id")
    private String taskId;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

    @Column(name="complition_status")
    private String complitionStatus;

}
