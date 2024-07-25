package com.app.employeePortal.processApproval.entity;

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
@Table(name="approve_task_link")
public class ApproveTaskLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.processApproval.generator.ApproveTaskLinkGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name="approve_task_link_id")
	private String id;
	
	@Column(name="approve_status")
	private String approveStatus;
	
	@Column(name="approved_by")
	private String approvedBy;
	
	@Column(name="level")
	private int level;
	
	@Column(name="approved_date")
	private Date approvedDate;
	
	@Column(name="sub_process_approval_id")
	private String subProcessApprovalId;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="user_id")
	private String userId;
	
}
