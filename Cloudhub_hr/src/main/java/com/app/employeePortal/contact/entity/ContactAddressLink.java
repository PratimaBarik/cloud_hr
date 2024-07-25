package com.app.employeePortal.contact.entity;

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
@Table(name="contact_address_link")
public class ContactAddressLink {
	
	@Id
	@GenericGenerator(name = "contact_address_link_id", strategy = "com.app.employeePortal.contact.generator.ContactAddressLinkGenerator")
	@GeneratedValue(generator = "contact_address_link_id")
	
	
	@Column(name="contact_address_link_id")
	private String contact_address_link_id;
	
	@Column(name="contact_id")
	private String contact_id;
	
	@Column(name="address_id")
	private String address_id;
	
	
	
	@Column(name="creation_date")
	private Date creation_date;

	
	
	
}
