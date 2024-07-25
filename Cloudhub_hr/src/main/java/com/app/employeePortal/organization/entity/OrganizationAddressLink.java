package com.app.employeePortal.organization.entity;

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
@Table(name="organization_address_link")
public class OrganizationAddressLink {
	
	@Id
	@GenericGenerator(name = "organization_address_link_id", strategy = "com.app.employeePortal.organization.generator.OrganizationAddressLinkGenerator")
	@GeneratedValue(generator = "organization_address_link_id")
	
	
	@Column(name="organization_address_link_id")
	private String organization_address_link_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="address_id")
	private String address_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
	

}
