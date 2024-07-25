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
@Table(name="department_delete")
public class DepartmentDelete {
	
	@Id
	@GenericGenerator(name = "department_delete_id", strategy = "com.app.employeePortal.registration.generator.DepartmentDeleteGenerator")
	@GeneratedValue(generator = "department_delete_id")
	
	@Column(name="department_delete_id")
	private String departmentDeleteId;
	
	@Column(name="department_id")
	private String departmentId;

	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	

}
