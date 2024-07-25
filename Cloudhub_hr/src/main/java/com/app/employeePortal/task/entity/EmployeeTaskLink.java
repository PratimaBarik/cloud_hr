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
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name="task_employee_link")
public class EmployeeTaskLink {

	@Id
	@GenericGenerator(name = "task_employee_link_id", strategy = "com.app.employeePortal.task.generator.EmployeeTaskGenerator")
	@GeneratedValue(generator = "task_employee_link_id")
	
	@Column(name="task_employee_link_id")
	private String task_employee_link_id;
	
	
	@Column(name="task_id")
	private String task_id;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

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
	
	@Column(name="filter_task_ind", nullable =false)
	private boolean filterTaskInd = false;
}
