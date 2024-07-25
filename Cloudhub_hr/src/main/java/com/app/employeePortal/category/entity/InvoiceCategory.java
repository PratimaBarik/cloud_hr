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
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "invoice_category")
public class InvoiceCategory {

	@Id
	@GenericGenerator(name = "invoice_category_id", strategy = "com.app.employeePortal.category.generator.InvoiceCategoryGenerator")
	@GeneratedValue(generator = "invoice_category_id")

	@Column(name = "invoice_category_id")
	private String invoiceCategoryId;

	@Column(name = "orgId")
	private String orgId;

	@Column(name = "piInd")
	private boolean piInd;

	@Column(name = "autoCiInd")
	private boolean autoCiInd;

	@Column(name = "inniInspectInd")
	private boolean inniInspectInd;
	
}
