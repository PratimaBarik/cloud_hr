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
@Table(name="employee_admin_update")
public class EmployeeAdminUpdate {

	@Id
	@GenericGenerator(name = "employee_admin_update_id", strategy = "com.app.employeePortal.employee.generator.EmployeeAdminUpdateGenerator")
	@GeneratedValue(generator = "employee_admin_update_id")
	
	@Column(name="employee_admin_update_id")
	private String employeeAdminUpdateId;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="admin_ind")
	private boolean adminInd;
	


	
	
}
