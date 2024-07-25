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

@Entity
@Getter
@Setter
@Table(name="task_comment")
public class TaskComment {
	
	@Id
	@GenericGenerator(name = "task_comment_id", strategy = "com.app.employeePortal.task.generator.TaskCommentGenerator")
    @GeneratedValue(generator = "task_comment_id")
	
	@Column(name="task_comment_id")
	private String taskCommentId;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="provider_id")
	private String providerId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "live_ind")
	private boolean liveInd;


}
