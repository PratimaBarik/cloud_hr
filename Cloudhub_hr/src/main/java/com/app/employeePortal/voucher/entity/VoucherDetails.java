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
@Table(name="voucher_details")
public class VoucherDetails {
	@Id
	@GenericGenerator(name = "voucher_details_id", strategy = "com.app.employeePortal.voucher.generator.VoucherDetailsGenerator")
	@GeneratedValue(generator = "voucher_details_id")
	
	@Column(name="voucher_details_id")
	private String voucher_details_id;
	
	@Column(name="voucher_id")
	private String voucher_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="voucher_date")
	private Date voucher_date;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="voucher_type")
	private String voucher_type;
	
	@Column(name="status")
	private String status;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="status_modification_date")
	private Date status_modification_date;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="task_id")
	private String task_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="submitted_by")
	private String submitted_by;
	
	@Column(name="live_ind")
	private boolean live_ind;

	@Column(name="reject_reason")
	private String rejectReason;
	
	@Column(name="total_amount")
	private String totalAmount;
	
	@Column(name="voucher_name")
	private String voucherName;
}
