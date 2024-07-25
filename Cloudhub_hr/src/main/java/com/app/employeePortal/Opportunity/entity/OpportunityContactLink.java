package com.app.employeePortal.Opportunity.entity;

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
@Table(name="opportunity_conatct_link")
public class OpportunityContactLink {
	@Id
	@GenericGenerator(name = "opportunity_conatct_link_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityContactLinkGenerator")
	@GeneratedValue(generator = "opportunity_conatct_link_id")
	
	@Column(name="opportunity_conatct_link_id")
	private String id;
	
	@Column(name="opportunity_id")
	private String opportunityId;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="creation_date")
	private Date creationDate;

	
	
	
	
	

}
