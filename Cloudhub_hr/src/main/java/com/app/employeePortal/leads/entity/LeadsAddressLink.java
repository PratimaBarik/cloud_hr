package com.app.employeePortal.leads.entity;

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
@Table(name = "leads_address_link")
public class LeadsAddressLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.leads.generator.LeadsAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="leads_address_link_id")
	private String id;

	@Column(name="leads_id")
	private String leadsId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;

	
	
}
