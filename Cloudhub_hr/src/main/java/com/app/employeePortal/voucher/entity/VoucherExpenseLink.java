package com.app.employeePortal.voucher.entity;

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
@Table(name="voucher_expense_link")
public class VoucherExpenseLink {
	
	@Id
	@GenericGenerator(name = "voucher_expense_id", strategy = "com.app.employeePortal.voucher.generator.VoucherExpenseLinkGenerator")
	@GeneratedValue(generator = "voucher_expense_id")
	
	@Column(name="voucher_expense_id")
	private String voucher_expense_id;
	
	@Column(name="voucher_id")
	private String voucher_id;
	
	@Column(name="expense_id")
	private String expense_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	

}
