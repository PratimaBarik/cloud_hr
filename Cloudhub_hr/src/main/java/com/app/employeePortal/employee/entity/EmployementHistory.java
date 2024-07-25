package com.app.employeePortal.employee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="employment_history")
public class EmployementHistory {

	@Id
	@GenericGenerator(name = "emp_history_id", strategy = "com.app.employeePortal.employee.generator.EmployementHistoryGenerator")
	@GeneratedValue(generator = "emp_history_id")
	
	@Column(name="emp_history_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="tenure")
	private String tenure;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="salary")
	private double salary;
	

	@Column(name="salary_type")
	private String salaryType;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name = "document_id")
	private String documentId;
	
	@Column(name="document_type")
	private String documentType;
	
	
}
