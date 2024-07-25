package com.app.employeePortal.employee.entity;

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
@Table(name="employee_info")
public class EmployeeInfo {

	
	@Id
	@GenericGenerator(name = "employee_id", strategy = "com.app.employeePortal.employee.generator.EmployeeInfoGenerator")
	@GeneratedValue(generator = "employee_id")
	
	@Column(name="employee_id")
	private String id;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="creator_id")
	private String creatorId;

	
	
}
