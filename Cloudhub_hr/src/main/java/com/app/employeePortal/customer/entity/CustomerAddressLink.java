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
@Table(name = "customer_address_link")
public class CustomerAddressLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.customer.generator.CustomerAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="customer_address_link_id")
	private String id;

	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;

	
	
}
