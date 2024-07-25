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
@Table(name="employee_address_link")
public class EmployeeAddressLink {
	

	@Id
	@GenericGenerator(name = "employee_address_link_id", strategy = "com.app.employeePortal.employee.generator.EmployeeAddressLinkGenerator")
	@GeneratedValue(generator = "employee_address_link_id")
	
	@Column(name="employee_address_link_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	@Column(name="live_ind")
	private boolean liveInd;

	

	
	

}
