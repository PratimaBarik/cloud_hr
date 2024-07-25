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
@Table(name = "payment_catagory")
public class PaymentCategory {

	@Id
	@GenericGenerator(name = "payment_catagory_id", strategy = "com.app.employeePortal.category.generator.PaymentCategoryGenerator")
	@GeneratedValue(generator = "payment_catagory_id")

	@Column(name = "payment_catagory_id")
	private String paymentCatagoryId;

	@Column(name = "name")
	private String name;

	@Column(name = "organization_id")
	private String organizationId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
}
