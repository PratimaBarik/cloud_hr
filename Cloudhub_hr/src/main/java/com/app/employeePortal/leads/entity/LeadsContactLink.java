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
@Table(name = "leads_contact_link")
public class LeadsContactLink {
	
	@Id
	@GenericGenerator(name = "leads_contact_link_id", strategy = "com.app.employeePortal.leads.generator.LeadsContactLinkGenerator")
    @GeneratedValue(generator = "leads_contact_link_id")
	
	@Column(name="leads_contact_link_id")
	private String id;

	@Column(name="leads_id")
	private String leadsId;
	
		
	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name="contactId")
	private String contactId;

	
}
