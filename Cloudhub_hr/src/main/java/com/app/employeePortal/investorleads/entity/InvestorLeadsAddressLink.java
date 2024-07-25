package com.app.employeePortal.investorleads.entity;

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
@Table(name = "investor_leads_address_link")
public class InvestorLeadsAddressLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.investorleads.generator.InvestorLeadsAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="investor_leads_address_link_id")
	private String id;

	@Column(name="investor_leads_id")
	private String investorLeadsId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;

	
	
}
