package com.app.employeePortal.category.entity;

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
@Table(name = "development")

public class Development {
	@Id
	@GenericGenerator(name = "development_id", strategy = "com.app.employeePortal.category.generator.DevelopmentGenerator")
    @GeneratedValue(generator = "development_id")
	
	@Column(name="development_id")
	private String developmentId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="value")
	private double value;
	
	@Column(name="department_id")
	private String departmentId;
	
	@Column(name="taskType_id")
	private String taskTypeId;
	
	@Column(name="roletype_id")
	private String roletypeId;
	
	@Column(name="developmentType")
	private String developmentType;
}

