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
@Table(name = "investor_leads_contact_link")
public class InvestorLeadsContactLink {
	
	@Id
	@GenericGenerator(name = "investor_leads_contact_link_id", strategy = "com.app.employeePortal.investorleads.generator.InvestorLeadsContactLinkGenerator")
    @GeneratedValue(generator = "investor_leads_contact_link_id")
	
	@Column(name="investor_leads_contact_link_id")
	private String id;

	@Column(name="investor_leads_id")
	private String investorLeadsId;
	
		
	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name="contactId")
	private String contactId;

	
}
