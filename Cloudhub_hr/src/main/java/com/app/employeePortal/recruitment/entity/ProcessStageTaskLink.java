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
@Table(name = "process_stage_task_link")
public class ProcessStageTaskLink {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.ProcessStageTaskLinkGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name="process_stage_task_link_id")
	private String id;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="taskId")
	private String taskId;
	
	@Column(name="stageId")
	private String stageId;

}
