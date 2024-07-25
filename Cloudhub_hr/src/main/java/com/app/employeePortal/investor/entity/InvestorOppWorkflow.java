package com.app.employeePortal.investor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name="investor_opp_workflow")
@Data
public class InvestorOppWorkflow {
    @Id
    @GenericGenerator(name = "investor_opp_workflow_id", strategy = "com.app.employeePortal.investor.generator.InvestorOppWorkflowGenerator")
    @GeneratedValue(generator = "investor_opp_workflow_id")

    @Column(name="investor_opp_workflow_id")
    private String investorOppWorkflowId;

    @Column(name="workflow_name")
    private String workflowName;

    @Column(name = "user_id")
    private String userId;

    @Column(name="org_id")
    private String orgId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "live_ind")
    private boolean liveInd;

    @Column(name = "publish_ind")
    private boolean publishInd;

    @Column(name = "update_date")
    private Date updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

}
