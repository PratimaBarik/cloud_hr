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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="employee_email_link")
public class EmployeeEmailLink {

	@Id
	@GenericGenerator(name = "employee_email_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeEmailLinkGenerator")
	@GeneratedValue(generator = "employee_email_link_id")
	
	@Column(name="employee_email_link_id")
	private String employeeEmailLinkId;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="primary_email_id")
	private String primaryEmailId;
	
	@Column(name="secondary_email_id")
	private String secondaryEmailId;
	
	@Column(name="live_ind", nullable = false)
	private boolean liveInd =false;
	
}
