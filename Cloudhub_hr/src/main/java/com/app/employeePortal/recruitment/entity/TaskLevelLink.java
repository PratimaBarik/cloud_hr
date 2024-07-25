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
@Table(name = "task_level_link")
public class TaskLevelLink {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.TaskLevelLinkGenerator")
    @GeneratedValue(generator = "id")
	
	 @Column(name = "task_level_link_id")
	 private String id;
	
	@Column(name="level_executor")
	private String levelExecutor;
	
	@Column(name="level")
	private int level;
	
	@Column(name="owner_department")
	private String ownerDepartment;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="unit_values")
	private String unitValues;
		
	
	@Column(name="level_type")
	private String levelType;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="executor_function")
	private String executorFunction;
	
	@Column(name="live_ind")
	private boolean liveInd;
	

}
