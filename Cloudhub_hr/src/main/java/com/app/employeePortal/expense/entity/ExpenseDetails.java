package com.app.employeePortal.expense.entity;

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
@Table(name = "expense_details")
public class ExpenseDetails {
	@Id
	@GenericGenerator(name = "expense_details_id", strategy = "com.app.employeePortal.expense.generator.ExpenseDetailsGenerator")
	@GeneratedValue(generator = "expense_details_id")

	@Column(name = "expense_details_id")
	private String expense_details_id;

	@Column(name = "expense_id")
	private String expense_id;
	
	//@Column(name = "bill_type")
	//private String bill_type;
	
	@Column(name = "expense_type")
	private String expense_type;
	
	@Column(name = "expense_date")
	private Date expense_date;
	
	@Column(name = "project_name")
	private String project_name;

	@Column(name = "client_name")
	private String client_name;
	
	@Column(name = "particular")
	private String particular;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "amount")
	private double amount;
	
	
	@Column(name = "adjusted_amount")
	private double adjusted_amount;
	
	
	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "document_id")
	private String documentId;
	
	@Column(name = "organization_id")
	private String organization_id;
	
	 @Column(name="creation_date")
	 private Date creation_date;
	 
	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name = "more_info")
	private String moreInfo;
	
	
}
