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
@Table(name="customer_info")
public class CustomerInfo {
	
	@Id
	@GenericGenerator(name = "customer_info_id", strategy = "com.app.employeePortal.customer.generator.CustomerInfoGenerator")
    @GeneratedValue(generator = "customer_info_id")
	
	@Column(name="customer_info_id")
	private String id;
	
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	
	

	
}
