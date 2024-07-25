package com.app.employeePortal.registration.entity;

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
@Table(name="default_department")
public class DefaultDepartment {
	
	@Id
	@GenericGenerator(name = "defaultDepartmentId", strategy = "com.app.employeePortal.registration.generator.DefaultDepartmentGenerator")
	@GeneratedValue(generator = "defaultDepartmentId")
	
	@Column(name="default_department_id")
	private String defaultDepartmentId;
	
	@Column(name="department_id")
	private String departmentId;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
}
