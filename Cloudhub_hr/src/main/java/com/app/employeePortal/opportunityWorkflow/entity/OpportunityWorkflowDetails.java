package com.app.employeePortal.opportunityWorkflow.entity;

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
@Table(name="opportunity_workflow_details")
public class OpportunityWorkflowDetails {

	@Id
	@GenericGenerator(name = "opportunity_workflow_details_id", strategy = "com.app.employeePortal.opportunityWorkflow.generator.OpportunityWorkflowDetailsGenerator")
    @GeneratedValue(generator = "opportunity_workflow_details_id")
	
	@Column(name="opportunity_workflow_details_id")
	private String opportunityWorkflowDetailsId;
	
	@Column(name="workflow_name")
	private String workflowName;
	
	@Column(name = "user_id")
	private String user_id;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "publish_ind")
	private boolean publishInd;
	
	
}
