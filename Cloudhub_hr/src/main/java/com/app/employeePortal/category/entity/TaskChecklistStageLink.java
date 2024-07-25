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
@Table(name = "task_checklist_stage_link")
public class TaskChecklistStageLink {

	@Id
	@GenericGenerator(name = "task_checklist_stage_link_id", strategy = "com.app.employeePortal.category.generator.TaskChecklistStageLinkGenerator")
    @GeneratedValue(generator = "task_checklist_stage_link_id")
	
	@Column(name="task_checklist_stage_link_id")
	private String taskChecklistStagelinkId;

	@Column(name="task_checklist_stage_name")
	private String taskChecklistStageName;

	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="last_update_by_id")
	private String lastUpdateById;

	@Column(name="last_update_on")
	private Date lastUpdateOn;
	
	@Column(name="task_checklist_id")
	private String taskChecklistId;
	
	@Column(name="probability")
	private double probability;
	
	@Column(name="days")
	private int days;

}
