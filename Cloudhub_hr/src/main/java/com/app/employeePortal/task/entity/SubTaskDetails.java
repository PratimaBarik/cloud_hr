package com.app.employeePortal.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="sub_task")
public class SubTaskDetails {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
    @Column(name="creation_date")
	private Date creationDate;

    @Column(name = "live_ind")
	private boolean liveInd;
	
    @Column(name = "task_checklist_stage_linkId")
    private String taskChecklistStagelinkId;
    
    @Column(name = "task_id")
    private String taskId;
  
}
