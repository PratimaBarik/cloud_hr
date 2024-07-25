package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.task.entity.TaskType;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "task_checklist")
public class TaskChecklist {

	@Id
	@GenericGenerator(name = "task_checklist_id", strategy = "com.app.employeePortal.category.generator.TaskChecklistGenerator")
    @GeneratedValue(generator = "task_checklist_id")
	
	@Column(name="task_checklist_id")
	private String taskChecklistId;

	@Column(name="task_checklist_name")
	private String taskChecklistName;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;

}
