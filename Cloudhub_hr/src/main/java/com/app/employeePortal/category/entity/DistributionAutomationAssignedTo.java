package com.app.employeePortal.category.entity;

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
@Table(name = "distribution_automation_assignedTo")

public class DistributionAutomationAssignedTo {
	@Id
	@GenericGenerator(name = "distribution_automation_assigned_to_id", strategy = "com.app.employeePortal.category.generator.DistributionAutomationAssignedToGenerator")
    @GeneratedValue(generator = "distribution_automation_assigned_to_id")
	
	@Column(name="distribution_automation_assigned_to_id")
	private String distributionAutomationAssignedToId;
	
	@Column(name="asignedTO")
	private String asignedTO;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind", nullable= false)
	private boolean liveInd=false;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="distribution_automation_id")
	private String distributionAutomationId;
}

