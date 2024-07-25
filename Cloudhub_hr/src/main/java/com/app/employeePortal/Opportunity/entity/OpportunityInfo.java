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
@Table(name="opportunity_info")
public class OpportunityInfo {
	@Id
	@GenericGenerator(name = "opportunity_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityInfoGenerator")
    @GeneratedValue(generator = "opportunity_id")
	
	
	
	
	
	@Column(name="opportunity_id")
	private String id;
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	
	
	
	
	

}
