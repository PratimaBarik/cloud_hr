package com.app.employeePortal.task.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.category.entity.TaskChecklist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name="task_types")
public class TaskType {
	
	@Id
	@GenericGenerator(name = "task_type_id", strategy = "com.app.employeePortal.task.generator.TaskTypeGenerator")
    @GeneratedValue(generator = "task_type_id")
	
	@Column(name="task_type_id")
	private String taskTypeId;
	
	@Column(name="taskType")
	private String taskType;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "task_check_list_ind", nullable =false)
	private boolean taskCheckListInd =false;

	@OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	 private List<TaskChecklist> taskChecklist;
}
