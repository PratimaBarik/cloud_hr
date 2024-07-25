package com.app.employeePortal.customer.entity;

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
@Table(name = "customer_invoice")
public class CustomerInvoice {
	
	
	@Id
	@GenericGenerator(name = "customer_invoice_id", strategy = "com.app.employeePortal.customer.generator.CustomerInvoiceGenerator")
    @GeneratedValue(generator = "customer_invoice_id")
	
	@Column(name="customer_invoice_id")
	private String id;
	
	@Column(name="invoice_number")
	private String invoiceNumber;
	
	@Column(name="invoice_amount")
	private String invoiceAmount;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="status")
	private String status;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="customer_id")
	private String customerId;
}
