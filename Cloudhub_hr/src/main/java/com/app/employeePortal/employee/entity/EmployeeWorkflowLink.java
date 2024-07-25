package com.app.employeePortal.employee.entity;

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
@Table(name ="employee_workflow_link")

public class EmployeeWorkflowLink {

	@Id
	@GenericGenerator(name = "employee_workflow_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeWorkflowGenerator")
	@GeneratedValue(generator = "employee_workflow_link_id")

	@Column(name = "employee_workflow_link_id")
	private String employeeWorkflowLinkId;

	@Column(name = "unboarding_workflow_details_id")
	private String unboardingWorkflowDetailsId;
	
	@Column(name = "unboarding_workflow_stage_id")
	private String unboardingWorkflowStageId;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
