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
@Table(name = "phone_brand_count")
public class PhoneBrandCount {

	@Id
	@GenericGenerator(name = "id", strategy="com.app.employeePortal.Opportunity.generator.PhoneBrandCountGenerator")
    @GeneratedValue(generator ="id")
	
	@Column(name = "phone_brand_count_id")
    private String id;
	
	@Column(name = "order_phone_id")
    private String orderPhoneId;
	
	@Column(name = "total_quantity")
    private int totalQuantity;
	
	@Column(name = "remaining_quantity")
    private int remainingQuantity;
	
	@Column(name = "company")
    private String company;
	
	@Column(name="opportunity_id")
	private String opportunityId;
}
