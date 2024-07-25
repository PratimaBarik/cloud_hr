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
@Table(name="task_included_link")
public class TaskIncludedLink {
	
	@Id
	@GenericGenerator(name = "task_included_link_id",strategy="com.app.employeePortal.task.generator.TaskIncludedGenerator")
	@GeneratedValue(generator = "task_included_link_id")
	
	@Column(name="task_included_link_id")
	private String taskIncludedLinkId;
	
	@Column(name="task_id")
	private String taskId ;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	
	
	

}
