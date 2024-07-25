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
@Table(name="bank_details")
public class BankDetails {

	@Id
	@GenericGenerator(name = "bank_details_id", strategy = "com.app.employeePortal.employee.generator.BankDetailsGenerator")
	@GeneratedValue(generator = "bank_details_id")
	
	@Column(name="bank_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="branch_name")
	private String branchName;
	
	@Column(name="account_no")
	private String accountNo;
	
	@Column(name="ifsc_code")
	private String ifscCode;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name="account_holder_name")
	private String accountHolderName;
	
	@Column(name="default_ind")
	private boolean defaultInd;
	


	
	
}
