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
@Table(name="org_currency_link")
public class OrganizationCurrencyLink {
	
	@Id
	@GenericGenerator(name = "org_currency_link_id", strategy = "com.app.employeePortal.organization.generator.OrganizationCurrencyLinkGenerator")
	@GeneratedValue(generator = "org_currency_link_id")
	
	
	@Column(name="org_currency_link_id")
	private String orgCurrencyLinkId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="currency_id")
	private String currencyId;
	
	@Column(name="pair_currency_id")
	private String pairCurrencyId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
}
