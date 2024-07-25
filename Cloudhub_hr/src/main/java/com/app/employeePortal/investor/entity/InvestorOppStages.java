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
@Table(name="investor_opp_stages")
@Data
public class InvestorOppStages {
    @Id
    @GenericGenerator(name = "investor_opp_stages_id", strategy = "com.app.employeePortal.investor.generator.InvestorOppStagesGenerator")
    @GeneratedValue(generator = "investor_opp_stages_id")

    @Column(name="investor_opp_stages_id")
    private String investorOppStagesId;

    @Column(name="stage_name")
    private String stageName;

    @Column(name="org_id")
    private String orgId;

    @Column(name = "probability")
    private double probability;

    @Column(name = "days")
    private int days;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "live_ind")
    private boolean liveInd;

    @Column(name = "publish_ind")
    private boolean publishInd;

    @Column(name="responsible")
    private String responsible;

    @Column(name="investor_opp_workflow__id")
    private String investorOppWorkflowId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "update_date")
    private Date updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;
}
