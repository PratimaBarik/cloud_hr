package com.app.employeePortal.category.entity;

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
@Table(name = "invoice")
public class Invoice{

	@Id
    @GenericGenerator(name = "invoice_id", strategy = "com.app.employeePortal.category.generator.InvoiceGenerator")
    @GeneratedValue(generator = "invoice_id")

	@Column(name="invoice_id")
	private String invoiceId;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;	
	
	@Column(name = "project_Id")
	private String projectId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "candidate_id")
    private String candidateId;
	
	@Column(name = "customer_id")
    private String customerId;
	
	@Column(name = "hour")
    private float hour;	
	
	@Column(name = "month")
    private String month;
	
	@Column(name = "year")
    private String year;
	
	@Column(name = "billing_amount")
    private float billingAmount;
	
	@Column(name = "actual_billable_hour")
	private float actualBillableHour;

	@Column(name = "projected_billable_hour")
	private float projectedBillableHour;
	
	@Column(name = "billable_curency")
	private String billableCurency;
	
	@Column(name = "projected_billable_amount")
	private float projectedBillableAmount;
	
	@Column(name = "actual_billable_amount")
	private float actualBillableAmount;
	
}
