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
@Table(name="salary_details")
public class SalaryDetails {
	
	@Id
	@GenericGenerator(name = "salary_details_id", strategy = "com.app.employeePortal.employee.generator.SalaryDetailsGenerator")
	@GeneratedValue(generator = "salary_details_id")
	
	@Column(name="salary_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="gross_monthly_salary")
	private String grossMonthlySalary;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="net_salary")
	private String netSalary;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="type")
	private String type;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="basic")
	private String basic;
	
	@Column(name="hra")
	private String hra;
	
	//@Convert(converter = AesEncryptor.class)
	@Column(name="lta")
	private String lta;

	//@Convert(converter = AesEncryptor.class)
	@Column(name="holidayPay")
	private String holidayPay;
	
	@Column(name="starting_date")
	private Date startingDate;
	
	@Column(name="end_date")
	private Date endDate;

	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="salary_id")
	private String salary_id;

}
