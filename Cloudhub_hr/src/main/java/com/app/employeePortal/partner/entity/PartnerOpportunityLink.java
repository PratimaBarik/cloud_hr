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
@Table(name="partner_oppotunity_link")
public class PartnerOpportunityLink {
	
	@Id
	@GenericGenerator(name = "partner_oppotunity_link_id", strategy = "com.app.employeePortal.partner.generator.PartnerOpportunityLinkGenerator")
    @GeneratedValue(generator = "partner_oppotunity_link_id")
	
	@Column(name="partner_oppotunity_link_id")
	private String id;

	@Column(name="partner_id")
	private String partner_id;
	
	@Column(name="oppotunity_id")
	private String opportunityId;
	
	@Column(name="creation_date")
	private Date creationDate;

}
