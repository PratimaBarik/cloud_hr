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
@Table(name="task_info")
public class TaskInfo {
	@Id
	@GenericGenerator(name = "task_id", strategy = "com.app.employeePortal.task.generator.TaskInfoGenerator")
	@GeneratedValue(generator = "task_id")
	
	@Column(name="task_id")
	private String task_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
	
}
