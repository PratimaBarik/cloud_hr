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
@Table(name = "opportunity_workflow_details_delete")
public class OpportunityWorkflowDetailsDelete {

	@Id
	@GenericGenerator(name = "opportunity_workflow_details_delete_id", strategy = "com.app.employeePortal.opportunityWorkflow.generator.OpportunityWorkflowDetailsDeleteGenerator")
    @GeneratedValue(generator = "opportunity_workflow_details_delete_id")
	
	@Column(name="opportunity_workflow_details_delete_id")
	private String opportunityWorkflowDetailsDeleteId;

	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="user_id")
	private String userId;

	@Column(name="opportunity_workflow_details_id")
	private String opportunityWorkflowDetailsId;
}
