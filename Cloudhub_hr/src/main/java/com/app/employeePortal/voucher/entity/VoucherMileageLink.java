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
@Table(name="voucher_mileage_link")
public class VoucherMileageLink {
	
	@Id
	@GenericGenerator(name = "voucher_mileage_id", strategy = "com.app.employeePortal.voucher.generator.VoucherMileageLinkGenerator")
	@GeneratedValue(generator = "voucher_mileage_id")
	
	@Column(name="voucher_mileage_id")
	private String voucher_mileage_id;
	
	@Column(name="voucher_id")
	private String voucher_id;
	
	@Column(name="mileage_id")
	private String mileage_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
	
}
