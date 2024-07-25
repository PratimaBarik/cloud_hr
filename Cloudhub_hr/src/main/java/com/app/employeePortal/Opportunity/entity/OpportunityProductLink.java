package com.app.employeePortal.Opportunity.entity;

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
@Table(name = "opportunity_product")
public class OpportunityProductLink extends Auditable{

	@Id
	@GenericGenerator(name = "opportunity_product_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityProductGenerator")
    @GeneratedValue(generator = "opportunity_product_id")
	
	@Column(name="opportunity_product_id")
	private String id;
	
	@Column(name="opportunity_id")
	private String opportunityId;
	
	@Column(name = "unit")
	private float unit;
	
	@Column(name = "user_id")
	private String userId;

	@Column(name = "product_id")
	private String productId;

}
