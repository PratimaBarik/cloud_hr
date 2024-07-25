package com.app.employeePortal.commercial.entity;

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
@Table(name = "partner_commission")

public class PartnerCommission {
	
	@Id
	@GenericGenerator(name = "partner_commission_id", strategy = "com.app.employeePortal.commercial.generator.PartnerCommissionGenerator")
    @GeneratedValue(generator = "partner_commission_id")
	
	@Column(name="partner_commission_id")
	private String partnerCommissionId;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="requirement_type")
	private String requirementType;
	
	@Column(name="commission_deal")
	private String commissionDeal;
	
	@Column(name="payment_date")
	private String paymentDate;
	
	@Column(name="commission_amount")
	private String commissionAmount;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;
	
}
