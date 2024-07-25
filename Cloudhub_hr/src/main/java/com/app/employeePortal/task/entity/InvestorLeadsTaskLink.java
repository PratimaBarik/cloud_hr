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
@Table(name="investor_lead_task_link")
public class InvestorLeadsTaskLink {
    @Id
    @GenericGenerator(name = "investor_lead_task_link_id", strategy = "com.app.employeePortal.task.generator.InvestorLeadTaskLinkGenerator")
    @GeneratedValue(generator = "investor_lead_task_link_id")

    @Column(name="investor_lead_task_link_id")
    private String investorLeadTaskLinkId;


    @Column(name="task_id")
    private String taskId;

    @Column(name="investor_leads_id")
    private String investorLeadsId;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="live_ind")
    private boolean liveInd;

    @Column(name="complition_status")
    private String complitionStatus;

}
