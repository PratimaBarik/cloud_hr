package com.app.employeePortal.recruitment.entity;

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
@Table(name = "approval_task_link")
public class ApprovalTaskLink {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.ApprovalTaskLinkGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="approval_task_link_id")
	private String id;
	
	@Column(name="approval_level")
	private String approvalLevel;
	
	@Column(name="approval_department")
	private String approvalDepartment;
	
	
	@Column(name="approval_unit")
	private String approvalUnit;
	
	@Column(name="approval_unit_value")
	private String approvalUnitValue;
	
	@Column(name="approval_level_type")
	private String approvalLevelType;
	
	@Column(name="approval_ind")
	private boolean approvalInd;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="approval_function")
	private String approvalFunction;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
