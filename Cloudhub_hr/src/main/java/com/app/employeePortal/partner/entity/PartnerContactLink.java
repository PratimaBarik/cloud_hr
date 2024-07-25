package com.app.employeePortal.partner.entity;

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
@Table(name="partner_contact_link")
public class PartnerContactLink {
	@Id
	@GenericGenerator(name = "partner_contact_link_id", strategy = "com.app.employeePortal.partner.generator.PartnerContactLinkGenerator")
    @GeneratedValue(generator = "partner_contact_link_id")
	
	@Column(name="partner_contact_link_id")
	private String id;

	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="contact_role")
	private String contactRole;
	
	@Column(name="latitude")
	private String latitude;
	
	@Column(name="longitude")
	private String longitude;

}
