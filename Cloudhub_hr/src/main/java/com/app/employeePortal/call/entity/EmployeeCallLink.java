package com.app.employeePortal.call.entity;

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
@Table(name="call_employee_link")
public class EmployeeCallLink {
	
	@Id
	@GenericGenerator(name = "call_employee_link_id", strategy = "com.app.employeePortal.call.generator.EmployeeCallGenerator")
	@GeneratedValue(generator = "call_employee_link_id")
	
	@Column(name="call_employee_link_id")
	private String call_employee_link_id;
	
	
	@Column(name="call_id")
	private String call_id;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
	

}
