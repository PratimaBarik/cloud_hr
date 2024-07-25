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
@Table(name="customer_lob_link")
public class CustomerLobLink {
	@Id
	@GenericGenerator(name = "customer_lob_link_id", strategy = "com.app.employeePortal.customer.generator.CustomerLobLinkGenerator")
	@GeneratedValue(generator = "customer_lob_link_id")
	
	@Column(name = "customer_lob_link_id")
	private String customerLobLinkId;
	
	@Column(name = "lob_details_id")
	private String lobDetailsId;

	@Column(name = "applicable", nullable=false)
	private boolean applicable = false;

	@Column(name = "potential")
	private String potential;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "currency")
	private String currency;
	
	@Column(name="customer_id")
	private String customerId;

}
