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
@Table(name="task_types_delete")
public class TaskTypeDelete {
	
	@Id
	@GenericGenerator(name = "task_typ_del_id", strategy = "com.app.employeePortal.task.generator.TaskTypeDeleteGenerator")
    @GeneratedValue(generator = "task_typ_del_id")
	
	@Column(name="task_typ_del_id")
	private String task_typ_del_id;
	
	@Column(name="task_type_id")
	private String taskTypeId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name = "updated_by")
	private String updatedBy;
	
}
