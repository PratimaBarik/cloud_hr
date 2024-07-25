package com.app.employeePortal.recruitment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="commissions")

public class Commission {
	@Id
    @GenericGenerator(name = "commission_id", strategy = "com.app.employeePortal.recruitment.generator.CommissionGenerator")
    @GeneratedValue(generator = "commission_id")
	
	@Column(name="commission_id")
	private String commissionId;
	

	@Column(name="user_id")
	private String userId;

	
	@Column(name="commission_Price")
	private float commissionPrice;
	
	
	@Column(name="org_id")
	private String orgId;
	
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="calculated_On")
	private String calculatedOn;
	
	@Column(name="commmision_Person")
	private String comPersion;
	
	@Column(name="type")
	private String type;
	
}
