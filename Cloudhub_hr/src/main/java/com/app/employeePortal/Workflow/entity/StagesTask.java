package com.app.employeePortal.Workflow.entity;

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
@Table(name="stages_task")
public class StagesTask {

	@Id
	@GenericGenerator(name = "stages_task_id", strategy = "com.app.employeePortal.Workflow.generator.StagesTaskGenerator")
    @GeneratedValue(generator = "stages_task_id")
	
	@Column(name="stages_task_id")
	private String stagesTaskId;
	
	@Column(name="stage_task_name")
	private String stageTaskName;
	
	@Column(name="stage_id")
	private String stageId;
	
	@Column(name="department_id")
	private String departmentId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "mandatory_ind")
	private boolean mandatoryInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;

	@Column(name = "global_ind")
	private boolean globalInd;
}
