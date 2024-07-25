package com.app.employeePortal.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="employee_sub_taskLink")
public class EmployeeSubTaskLink {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 	@Column(name = "task_checklist_stage_linkId")
	    private String taskChecklistStagelinkId;
	 	
	 	@Column(name = "employee_id")
	    private String employeeId;
	 	
	 	@Column(name = "task_id")
	    private String taskId;

}
