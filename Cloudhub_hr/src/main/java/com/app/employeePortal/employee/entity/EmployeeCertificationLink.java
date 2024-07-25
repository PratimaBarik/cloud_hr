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
@Table(name ="employee_certification_link")

public class EmployeeCertificationLink {

	@Id
	@GenericGenerator(name = "employee_certification_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeCertificationLinkGenerator")
	@GeneratedValue(generator = "employee_certification_link_id")

	@Column(name = "employee_certification_link_id")
	private String employeeCertificationLinkId;

	@Column(name = "employee_certification_name")
	private String employeeCertificationName;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "edit_ind")
	private boolean editInd;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name="user_id")
	private String userId;

}
