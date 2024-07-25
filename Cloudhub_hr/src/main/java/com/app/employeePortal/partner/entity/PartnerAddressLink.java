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
@Table(name = "partner_address_link")
public class PartnerAddressLink {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.partner.generator.PartnerAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="partner_address_link_id")
	private String id;

	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;

}
