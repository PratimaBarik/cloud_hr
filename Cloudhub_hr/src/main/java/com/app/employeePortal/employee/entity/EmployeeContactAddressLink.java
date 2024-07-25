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
@Table(name="employee_contact_address")
public class EmployeeContactAddressLink {

	@Id
	@GenericGenerator(name = "emp_contact_address_id", strategy = "com.app.employeePortal.employee.generator.EmployeeContactAddressLinkGenerator")
	@GeneratedValue(generator = "emp_contact_address_id")
	
	
	@Column(name="emp_contact_address_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="contact_address_id")
	private String contactAddressId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="contact_person_id")
	private String contactPersonId;
	
	@Column(name="live_ind")
	private boolean liveInd;


	
	
	
}
