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

@Entity
@Getter
@Setter
@ToString
@Table(name="task_steps")
public class TaskSteps {
	
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.task.generator.TaskStepsGenerator")
    @GeneratedValue(generator = "id")
    
	@Column(name="id")
	private String id;
	
	@Column(name="end_date")
	private Date endDate;
	
    @Column(name="creation_date")
	private Date creationDate;

    @Column(name = "live_ind")
	private boolean liveInd;
	
    @Column(name = "step")
    private String step;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "task_id")
    private String taskId;
    
    @Column(name = "user_id")
    private String userId;
  
}
