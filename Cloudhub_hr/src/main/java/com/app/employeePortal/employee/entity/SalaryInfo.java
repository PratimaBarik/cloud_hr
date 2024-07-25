package com.app.employeePortal.employee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SalaryInfo {
	@Id
	@GenericGenerator(name = "salary_id", strategy = "com.app.employeePortal.employee.generator.SalaryInfoGenerator")
	@GeneratedValue(generator = "salary_id")
	
	@Column(name="salary_id")
	private String salary_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
	
	

}
