package com.app.employeePortal.unboardingWorkflow.entity;

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
@Table(name="unboarding_workflow_details")
public class UnboardingWorkflowDetails {

	@Id
	@GenericGenerator(name = "unboarding_workflow_details_id", strategy = "com.app.employeePortal.unboardingWorkflow.generator.UnboardingWorkflowDetailsGenerator")
    @GeneratedValue(generator = "unboarding_workflow_details_id")
	
	@Column(name="unboarding_workflow_details_id")
	private String unboardingWorkflowDetailsId;
	
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
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
}
