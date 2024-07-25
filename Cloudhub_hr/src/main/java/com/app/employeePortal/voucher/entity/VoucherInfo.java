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
@Table(name="voucher_info")
public class VoucherInfo {
	@Id
	@GenericGenerator(name = "voucher_id", strategy = "com.app.employeePortal.voucher.generator.VoucherInfoGenerator")
	@GeneratedValue(generator = "voucher_id")
	
	@Column(name="voucher_id")
	private String voucher_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
}
