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
@Table(name = "customer_contact_link")
public class CustomerContactLink {
	
	@Id
	@GenericGenerator(name = "customer_contact_link_id", strategy = "com.app.employeePortal.customer.generator.CustomerContactLinkGenerator")
    @GeneratedValue(generator = "customer_contact_link_id")
	
	@Column(name="customer_contact_link_id")
	private String id;

	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="creation_date")
	private Date creationDate;

	
}
