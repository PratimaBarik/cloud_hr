package com.app.employeePortal.ruleEngine.entity;

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
@Table(name="org_warm_leads_aging")
public class OrganizationWarmLeadsAgingRule {
	@Id
	@GenericGenerator(name = "org_warm_leads_aging_id", strategy = "com.app.employeePortal.ruleEngine.generator.OrganizationWarmLeadsGenerator")
	@GeneratedValue(generator = "org_warm_leads_aging_id")	
	
	@Column(name="org_warm_leads_aging_id")
	private String org_warm_leads_aging_id;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="leads_category")
	private String leads_category;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

	


}
