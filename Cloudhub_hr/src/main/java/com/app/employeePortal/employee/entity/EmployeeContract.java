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
@Table(name="employee_contract")

public class EmployeeContract {
	
	@Id
	@GenericGenerator(name = "employee_contract_id", strategy = "com.app.employeePortal.employee.generator.EmployeeContractGenerator")
	@GeneratedValue(generator = "employee_contract_id")
	
	@Column(name="employee_contract_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="previous_start_date")
	private Date previousStartDate;
	
	/*
	 * @Column(name="present_start_date") private Date presentStartDate;
	 */
	
	@Column(name="previous_end_date")
	private Date previousEndDate;
	
	/*
	 * @Column(name="present_end_date") private Date presentEndDate;
	 */
	
	@Column(name="renwal_date")
	private Date renwalDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	
	@Column(name="contract_Type")
	private String contractType;

	@Lob
	@Column(name="notes")
	private String notes;
	

}
